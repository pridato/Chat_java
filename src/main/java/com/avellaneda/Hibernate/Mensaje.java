package com.avellaneda.Hibernate;

public class Mensaje {

    private int id;
    private String texto;
    private Usuario usuario;

    public Mensaje() {
    }

    public Mensaje(String texto, Usuario usuario) {
        this.texto = texto;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
