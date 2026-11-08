package com.escuela.modelo;

public class Alumno {
    private int id, grado;
    private String dni, nombres, apellidos, direccion, apoderado, telefono, nivel, seccion;
    private boolean estado = true;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getGrado() { return grado; }
    public void setGrado(int g) { this.grado = g; }
    public String getDni() { return dni; }
    public void setDni(String d) { this.dni = d; }
    public String getNombres() { return nombres; }
    public void setNombres(String n) { this.nombres = n; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String a) { this.apellidos = a; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String d) { this.direccion = d; }
    public String getApoderado() { return apoderado; }
    public void setApoderado(String a) { this.apoderado = a; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public String getNivel() { return nivel; }
    public void setNivel(String n) { this.nivel = n; }
    public String getSeccion() { return seccion; }
    public void setSeccion(String s) { this.seccion = s; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean e) { this.estado = e; }
    public String getNombreCompleto() { return apellidos + ", " + nombres; }
}