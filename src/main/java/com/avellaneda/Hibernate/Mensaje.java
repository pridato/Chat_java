package com.avellaneda.Hibernate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private String remitente_bien;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario remitente; // Usuario que envía el mensaje

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Mensaje() {

    }

    public Mensaje(String contenido, Usuario remitente, String remitente_bien) {
        this.contenido = contenido;
        this.remitente = remitente;
        this.remitente_bien = remitente_bien;
        this.fechaCreacion = new Date();

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
        return contenido + " de " + remitente_bien + " para " + remitente;
    }
}
