package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.Mensaje;
import com.avellaneda.Hibernate.HibernateUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.avellaneda.Hibernate.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint("/chat")
public class WebSocket {

    private static final List<Session> sessions = new ArrayList<>();
    private static List mensajes = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {

        sessions.add(session);
        mensajes = HibernateUtil.getMensajes();

        for (Object mensaje : mensajes) {
            Mensaje men = (Mensaje) mensaje;
            try {
                sessions.get(0).getBasicRemote().sendText(mensaje.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(String message){

        // Confirmar si hay mas de 10 mensajes
        Mensaje mensajeObj = getMensaje(message);
        HibernateUtil.transactionMessage(mensajeObj);

        mensajes = HibernateUtil.getMensajes();

        if (mensajes.size() > 10) {
            HibernateUtil.cleanUpMessage();
            mensajes = HibernateUtil.getMensajes();
        }

        // Reenviar el mensaje a todas las sesiones
        try {
            sessions.get(0).getBasicRemote().sendText("reload");
            sessions.get(0).getBasicRemote().sendText(mensajeObj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Mensaje getMensaje(String message) {

        System.out.println(message);
        Mensaje mensajeObj = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(message);

            String mensaje = jsonNode.get("mensaje").asText();
            String destinatario = jsonNode.get("destinatario").asText();
            String remitente = jsonNode.get("user").asText();

            mensajeObj = new Mensaje(mensaje, new Usuario(destinatario), remitente);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return mensajeObj;
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

}
