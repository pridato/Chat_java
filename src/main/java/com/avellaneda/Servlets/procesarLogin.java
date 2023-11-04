package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.HibernateUtil;
import com.avellaneda.Hibernate.Usuario;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class procesarLogin extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String accion = request.getParameter("accion");

        if(accion.equals("crearCuenta")) {
            Usuario usuario = new Usuario(user, password);
            HibernateUtil.transactionUser(usuario);
        }


        System.out.println(user + " " + password);
        // redireccionar chat
        try {
            response.sendRedirect("chat.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
