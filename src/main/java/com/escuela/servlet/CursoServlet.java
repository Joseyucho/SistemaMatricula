package com.escuela.servlet;

import com.escuela.facade.MatriculaFacade;
import com.escuela.modelo.Curso;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/CursoServlet")
public class CursoServlet extends HttpServlet {
    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("cursos", facade.listarCursos());
        req.setAttribute("docentes", facade.listarDocentes());
        req.getRequestDispatcher("/vistas/secretaria/cursos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Curso c = new Curso();
            c.setNombre(req.getParameter("nombre"));
            c.setNivel(req.getParameter("nivel"));
            c.setGrado(Integer.parseInt(req.getParameter("grado")));
            c.setSeccion(req.getParameter("seccion"));
            c.setDocenteId(Integer.parseInt(req.getParameter("docenteId")));
            
            String horaIni = req.getParameter("horaInicio");
            String horaFin = req.getParameter("horaFin");
            c.setHoraInicio(horaIni.length() == 5 ? horaIni + ":00" : horaIni);
            c.setHoraFin(horaFin.length() == 5 ? horaFin + ":00" : horaFin);
            
            c.setDiaSemana(req.getParameter("diaSemana"));
            
            if (facade.registrarCurso(c)) {
                req.getSession().setAttribute("msg", "✅ Curso registrado exitosamente.");
            } else {
                req.getSession().setAttribute("error", "❌ Error al registrar curso en la base de datos.");
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("error", "❌ Error en los números: " + e.getMessage());
        } catch (Exception e) {
            // Si falla por SQL, esto te dirá EXACTAMENTE qué columna falta o está mal
            req.getSession().setAttribute("error", "❌ " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/CursoServlet");
    }
}