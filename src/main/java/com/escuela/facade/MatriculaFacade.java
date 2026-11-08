package com.escuela.facade;

import com.escuela.dao.*;
import com.escuela.modelo.*;
import com.escuela.util.SeguridadUtil;

import java.math.BigDecimal;
import java.util.List;

public class MatriculaFacade {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final DocenteDAO docenteDAO = new DocenteDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private final PagoDAO pagoDAO = new PagoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /** Matricula alumno + genera calendario de pagos */
    public boolean matricularAlumno(Alumno alumno) {
        if (!alumnoDAO.insertar(alumno)) return false;
        int anio = java.time.LocalDate.now().getYear();
        pagoDAO.generarMensualidades(alumno.getId(), anio, new BigDecimal("250.00"));
        return true;
    }

    /** Registra docente + usuario asociado */
    public boolean registrarDocente(Docente d, String usuario, String clave) {
        Usuario u = new Usuario();
        u.setUsuario(usuario);
        u.setRol("DOCENTE");
        u.setNombres(d.getNombres());
        u.setApellidos(d.getApellidos());
        if (!usuarioDAO.crear(u, clave)) return false;
        d.setUsuarioId(u.getId());
        return docenteDAO.insertar(d);
    }

    public List<Alumno> listarAlumnos() { return alumnoDAO.listar(); }
    public List<Alumno> listarAlumnosPorGrado(String nivel, int grado, String seccion) {
        return alumnoDAO.listarPorGrado(nivel, grado, seccion);
    }
    public List<Docente> listarDocentes() { return docenteDAO.listar(); }
    public List<Curso> listarCursos() { return cursoDAO.listar(); }
    public List<Curso> listarCursosPorDocente(int docenteId) {
        return cursoDAO.listarPorDocente(docenteId);
    }
    public boolean registrarCurso(Curso c) { return cursoDAO.insertar(c); }
    public int mesesAdeudados(int alumnoId) { return alumnoDAO.contarMesesAdeudados(alumnoId); }
    public Alumno buscarAlumno(int id) { return alumnoDAO.buscarPorId(id); }
    public Docente buscarDocentePorUsuario(int usuarioId) { return docenteDAO.buscarPorUsuarioId(usuarioId); }
    public String hash(String clave) { return SeguridadUtil.hash(clave); }
    public List<Curso> listarTodosLosCursos() {
    // Asumiendo que tienes un CursoDAO con el método listar()
    return cursoDAO.listar(); 
}
}
