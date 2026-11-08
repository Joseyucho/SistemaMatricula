package com.escuela.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("reporteBean")
@SessionScoped
public class ReporteBean implements Serializable {
    private String nivel, seccion;
    private int grado;
    private String fechaIni, fechaFin;

    // getters/setters
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public int getGrado() { return grado; }
    public void setGrado(int grado) { this.grado = grado; }
    public String getFechaIni() { return fechaIni; }
    public void setFechaIni(String f) { this.fechaIni = f; }
    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String f) { this.fechaFin = f; }
}