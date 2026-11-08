package com.escuela.modelo;
import java.math.BigDecimal;
import java.util.Date;

public class Nota {
    private int id, alumnoId, cursoId, bimestre, anio;
    private BigDecimal nota;
    private String alumno, curso;
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getAlumnoId(){return alumnoId;} public void setAlumnoId(int a){this.alumnoId=a;}
    public int getCursoId(){return cursoId;} public void setCursoId(int c){this.cursoId=c;}
    public int getBimestre(){return bimestre;} public void setBimestre(int b){this.bimestre=b;}
    public int getAnio(){return anio;} public void setAnio(int a){this.anio=a;}
    public BigDecimal getNota(){return nota;} public void setNota(BigDecimal n){this.nota=n;}
    public String getAlumno(){return alumno;} public void setAlumno(String a){this.alumno=a;}
    public String getCurso(){return curso;} public void setCurso(String c){this.curso=c;}
}