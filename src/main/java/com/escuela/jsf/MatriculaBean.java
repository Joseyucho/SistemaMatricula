package com.escuela.jsf;

import com.escuela.facade.MatriculaFacade;
import com.escuela.modelo.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("matriculaBean")
@SessionScoped
public class MatriculaBean implements Serializable {
    private final MatriculaFacade facade = new MatriculaFacade();

    private Alumno alumno = new Alumno();
    private Docente docente = new Docente();
    private String usuarioDoc, claveDoc;

    private List<Alumno> alumnos;
    private List<Docente> docentes;
    private List<Curso> cursos;

    @PostConstruct
    public void init() { refrescar(); }

    public void refrescar() {
        alumnos = facade.listarAlumnos();
        docentes = facade.listarDocentes();
        cursos = facade.listarCursos();
    }

    public String guardarAlumno() {
        if (facade.matricularAlumno(alumno)) {
            alumno = new Alumno();
            refrescar();
            msg("Alumno matriculado correctamente.");
        } else msg("Error al matricular.");
        return null;
    }

    public String guardarDocente() {
        if (facade.registrarDocente(docente, usuarioDoc, claveDoc)) {
            docente = new Docente();
            usuarioDoc = claveDoc = null;
            refrescar();
            msg("Docente registrado.");
        } else msg("Error al registrar docente.");
        return null;
    }

    private void msg(String texto) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno a) { this.alumno = a; }
    public Docente getDocente() { return docente; }
    public void setDocente(Docente d) { this.docente = d; }
    public String getUsuarioDoc() { return usuarioDoc; }
    public void setUsuarioDoc(String u) { this.usuarioDoc = u; }
    public String getClaveDoc() { return claveDoc; }
    public void setClaveDoc(String c) { this.claveDoc = c; }
    public List<Alumno> getAlumnos() { return alumnos; }
    public List<Docente> getDocentes() { return docentes; }
    public List<Curso> getCursos() { return cursos; }
}