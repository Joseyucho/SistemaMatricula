package com.escuela.modelo;

public class Docente {
    private int id, usuarioId;
    private String dni, nombres, apellidos, especialidad, telefono, email;
    public int getId() { return id; } public void setId(int id){this.id=id;}
    public int getUsuarioId() { return usuarioId; } public void setUsuarioId(int u){this.usuarioId=u;}
    public String getDni(){return dni;} public void setDni(String d){this.dni=d;}
    public String getNombres(){return nombres;} public void setNombres(String n){this.nombres=n;}
    public String getApellidos(){return apellidos;} public void setApellidos(String a){this.apellidos=a;}
    public String getEspecialidad(){return especialidad;} public void setEspecialidad(String e){this.especialidad=e;}
    public String getTelefono(){return telefono;} public void setTelefono(String t){this.telefono=t;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getNombreCompleto(){return apellidos + ", " + nombres;}
}