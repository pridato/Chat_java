package com.avellaneda.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static void transactionUser(Usuario usuario) {
        Session session = getSession();
        session.beginTransaction();

        session.save(usuario);
        session.getTransaction().commit();
        session.close();

    }

    public static void transactionMessage(Mensaje mensaje) {
        Session session = getSession();
        session.beginTransaction();

        session.save(mensaje);
        session.getTransaction().commit();
        session.close();
    }

    private static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession() {
        sessionFactory.close();
    }
}
