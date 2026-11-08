package com.escuela.modelo;

public class Curso {
    private int id, grado, docenteId;
    private String nombre, nivel, seccion, nombreDocente, horaInicio, horaFin, diaSemana;
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getGrado(){return grado;} public void setGrado(int g){this.grado=g;}
    public int getDocenteId(){return docenteId;} public void setDocenteId(int d){this.docenteId=d;}
    public String getNombre(){return nombre;} public void setNombre(String n){this.nombre=n;}
    public String getNivel(){return nivel;} public void setNivel(String n){this.nivel=n;}
    public String getSeccion(){return seccion;} public void setSeccion(String s){this.seccion=s;}
    public String getNombreDocente(){return nombreDocente;} public void setNombreDocente(String n){this.nombreDocente=n;}
    public String getHoraInicio(){return horaInicio;} public void setHoraInicio(String h){this.horaInicio=h;}
    public String getHoraFin(){return horaFin;} public void setHoraFin(String h){this.horaFin=h;}
    public String getDiaSemana(){return diaSemana;} public void setDiaSemana(String d){this.diaSemana=d;}
}