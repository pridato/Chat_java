package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.Mensaje;
import com.avellaneda.Hibernate.HibernateUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avellaneda.Hibernate.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;

import javax.websocket.server.PathParam;

@ServerEndpoint("/chat")
public class WebSocket {

    private static final Map<String, Session> userSessions = new HashMap<>();
    private static List mensajes = new ArrayList<>();
    private Session session;
    private String usuario = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String usuario) {

        this.session = session;
        this.usuario = usuario;

        userSessions.put(usuario, session);
        mensajes = HibernateUtil.getMensajes();

        for (Object mensaje : mensajes) {
            Mensaje men = (Mensaje) mensaje;
            try {

                session.getBasicRemote().sendText(mensaje.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(String message) {

        // Confirmar si hay mas de 10 mensajes
        Mensaje mensajeObj = getMensaje(message);

        if (usuario != null) {
            userSessions.put(usuario, session);

            for (Map.Entry<String, Session> entry : userSessions.entrySet()) {
                String key = entry.getKey();
                Session value = entry.getValue();
                System.out.println("Clave: " + key + ", Valor: " + value);
            }
        }


        if (mensajeObj == null) {
            return;
        }
        HibernateUtil.transactionMessage(mensajeObj);

        mensajes = HibernateUtil.getMensajes();

        if (mensajes.size() > 10) {
            HibernateUtil.cleanUpMessage();
            mensajes = HibernateUtil.getMensajes();
        }

        // Reenviar el mensaje a todas las sesiones
        if (usuario != null) {
            try {

                for (Map.Entry<String, Session> entry : userSessions.entrySet()) {

                    Session value = entry.getValue();
                    if (value.isOpen()) {
                        value.getBasicRemote().sendText("reload");
                        value.getBasicRemote().sendText(mensajeObj.toString());
                    }
                }

                session.getBasicRemote().sendText(mensajeObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private Mensaje getMensaje(String message) {

        Mensaje mensajeObj = null;
        if (comprobarJson(message)) {

            mensajeObj = null;

            try {
                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode jsonNode = objectMapper.readTree(message);
                usuario = jsonNode.get("user").asText();
                String mensaje = jsonNode.get("mensaje").asText();
                String destinatario = jsonNode.get("destinatario").asText();

                mensajeObj = new Mensaje(mensaje, new Usuario(destinatario), usuario);

                return mensajeObj;

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return mensajeObj;
    }

    private Boolean comprobarJson(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);
            return true; // La cadena es un JSON válido
        } catch (JsonParseException e) {
            return false; // La cadena no es un JSON válido
        } catch (Exception e) {
            return false; // Ocurrió otro error al intentar analizar la cadena
        }
    }

    @OnClose
    public void onClose(Session session) {
        userSessions.remove(session.getUserProperties().get("usuario"));
    }

}
