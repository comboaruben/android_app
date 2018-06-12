package com.example.ruben.proyecto_cs_dam;

public class Pedido {
    private String id, nombre,email,dia,pedido;

    public Pedido(String id, String nombre, String email, String dia, String pedido) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.dia = dia;
        this.pedido = pedido;
    }

    public Pedido(String id, String nombre, String email, String dia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.dia = dia;
    }

    public Pedido() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }
}
