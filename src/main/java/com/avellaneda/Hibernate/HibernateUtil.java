package com.avellaneda.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();


    public static void cleanUpMessage() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query<Mensaje> query = session.createQuery("FROM Mensaje m ORDER BY m.fechaCreacion DESC", Mensaje.class);
            List<Mensaje> mensajes = query.getResultList();

            for (int i = 10; i < mensajes.size(); i++) {
                Mensaje mensajeToDelete = mensajes.get(i);
                session.delete(mensajeToDelete);
            }

            transaction.commit();

        }
    }

    public static void transactionUser(Usuario usuario) {
        if (usuario.getPassword() != null) {
            Session session = getSession();
            session.beginTransaction();

            session.save(usuario);
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void transactionMessage(Mensaje mensaje) {
        Session session = getSession();
        session.beginTransaction();

        session.save(mensaje);
        session.getTransaction().commit();
        session.close();
    }

    public static List getUsuarios() {
        Session session = getSession();
        session.beginTransaction();

        List usuarios = session.createQuery("from Usuario").list();

        session.getTransaction().commit();
        session.close();

        return usuarios;
    }

    public static List getMensajes() {
        Session session = getSession();
        session.beginTransaction();

        List mensajes = session.createQuery("from Mensaje").list();

        session.getTransaction().commit();
        session.close();

        return mensajes;
    }

    private static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession() {
        sessionFactory.close();
    }
}
