package com.escuela.servlet;

import com.escuela.facade.MatriculaFacade;
import com.escuela.modelo.Alumno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/AlumnoServlet")
public class AlumnoServlet extends HttpServlet {
    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("alumnos", facade.listarAlumnos());
        req.getRequestDispatcher("/vistas/secretaria/alumnos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Alumno a = new Alumno();
            a.setDni(req.getParameter("dni"));
            a.setNombres(req.getParameter("nombres"));
            a.setApellidos(req.getParameter("apellidos"));
            a.setDireccion(req.getParameter("direccion"));
            a.setApoderado(req.getParameter("apoderado"));
            a.setTelefono(req.getParameter("telefono"));
            a.setNivel(req.getParameter("nivel"));
            a.setGrado(Integer.parseInt(req.getParameter("grado")));
            a.setSeccion(req.getParameter("seccion"));

            if (facade.matricularAlumno(a)) {
                req.getSession().setAttribute("msg", "✅ Alumno matriculado y calendario generado.");
            } else {
                req.getSession().setAttribute("error", "❌ Error al matricular (¿DNI duplicado?).");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "❌ " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/AlumnoServlet");
    }
}