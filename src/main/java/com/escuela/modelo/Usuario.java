package com.escuela.modelo;

public class Usuario {
    private int id;
    private String usuario, clave, rol, nombres, apellidos;
    private boolean activo = true;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String u) { this.usuario = u; }
    public String getClave() { return clave; }
    public void setClave(String c) { this.clave = c; }
    public String getRol() { return rol; }
    public void setRol(String r) { this.rol = r; }
    public String getNombres() { return nombres; }
    public void setNombres(String n) { this.nombres = n; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String a) { this.apellidos = a; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean a) { this.activo = a; }
    public String getNombreCompleto() { return nombres + " " + apellidos; }
}