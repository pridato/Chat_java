package com.avellaneda.Hibernate;

import javax.persistence.*;

@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    @ManyToOne
    private Usuario remitente; // Usuario que envía el mensaje

    public Mensaje() {

    }

    public Mensaje(String contenido, Usuario remitente) {
        this.contenido = contenido;
        this.remitente = remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    // string
    public String toString() {
        return "Mensaje: " + contenido + " de " + remitente;
    }
}
