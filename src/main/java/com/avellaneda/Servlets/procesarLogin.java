package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.HibernateUtil;
import com.avellaneda.Hibernate.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class procesarLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String accion = request.getParameter("accion");

        if (accion != null && accion.equals("crearCuenta")) {
            Usuario usuario = new Usuario(user, password);
            HibernateUtil.transactionUser(usuario);
        }

        List usuarios = HibernateUtil.getUsuarios();

        // con stream
        boolean encontrado = usuarios.stream().anyMatch(usuario -> ((Usuario) usuario).getNombre().equals(user) && ((Usuario) usuario).getPassword().equals(password));
        System.out.println(encontrado);

        try {

            if (encontrado) {
                try {
                    response.sendRedirect("chat.jsp");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                response.sendRedirect("login.jsp?errorMensaje=Usuario no existente. Intente de nuevo.");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
