package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.HibernateUtil;
import com.avellaneda.Hibernate.Mensaje;
import com.avellaneda.Hibernate.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnviarMensajeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String mensaje = request.getParameter("mensaje");
        String user = request.getParameter("user");

        Usuario usuario = new Usuario(user);
        Mensaje mensajeObj = new Mensaje(mensaje, usuario);

        HibernateUtil.transactionMessage(mensajeObj);


        out.println(mensajeObj);
        System.out.println(mensaje + " a " + user);

        out.close();

    }
}
