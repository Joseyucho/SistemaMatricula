package com.escuela.modelo;
import java.util.Date;
public class Asistencia {
    private int id, alumnoId, cursoId;
    private Date fecha;
    private String estado, alumno;
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getAlumnoId(){return alumnoId;} public void setAlumnoId(int a){this.alumnoId=a;}
    public int getCursoId(){return cursoId;} public void setCursoId(int c){this.cursoId=c;}
    public Date getFecha(){return fecha;} public void setFecha(Date f){this.fecha=f;}
    public String getEstado(){return estado;} public void setEstado(String e){this.estado=e;}
    public String getAlumno(){return alumno;} public void setAlumno(String a){this.alumno=a;}
}