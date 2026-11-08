package com.escuela.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Pago {
    private int id, alumnoId, mes, anio;
    private String concepto, estado, alumno;
    private BigDecimal monto;
    private Date fechaVencimiento;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getAlumnoId(){return alumnoId;} public void setAlumnoId(int a){this.alumnoId=a;}
    public int getMes(){return mes;} public void setMes(int m){this.mes=m;}
    public int getAnio(){return anio;} public void setAnio(int a){this.anio=a;}
    public String getConcepto(){return concepto;} public void setConcepto(String c){this.concepto=c;}
    public String getEstado(){return estado;} public void setEstado(String e){this.estado=e;}
    public String getAlumno(){return alumno;} public void setAlumno(String a){this.alumno=a;}
    public BigDecimal getMonto(){return monto;} public void setMonto(BigDecimal m){this.monto=m;}
    public Date getFechaVencimiento(){return fechaVencimiento;} public void setFechaVencimiento(Date f){this.fechaVencimiento=f;}
}