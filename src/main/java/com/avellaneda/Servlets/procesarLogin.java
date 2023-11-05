package com.avellaneda.Servlets;

import com.avellaneda.Hibernate.HibernateUtil;
import com.avellaneda.Hibernate.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class procesarLogin extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String accion = request.getParameter("accion");

        Usuario usuario = new Usuario(user, password);
        if (accion != null && accion.equals("crearCuenta")) {

            HibernateUtil.transactionUser(usuario);
        }

        List usuarios = HibernateUtil.getUsuarios();

        // con stream
        boolean encontrado = usuarios.stream().anyMatch(us -> ((Usuario) us).getNombre().equals(user) && ((Usuario) us).getPassword().equals(password));

        try {

            if (encontrado) {
                try {
                    getServletContext().setAttribute("usuario", usuario);
                    response.sendRedirect("chat.jsp?user=" + user);
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
