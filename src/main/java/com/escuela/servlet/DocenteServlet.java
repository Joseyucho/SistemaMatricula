package com.escuela.servlet;

import com.escuela.facade.MatriculaFacade;
import com.escuela.modelo.Docente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DocenteServlet")
public class DocenteServlet extends HttpServlet {
    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("docentes", facade.listarDocentes());
        req.getRequestDispatcher("/vistas/secretaria/docentes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Docente d = new Docente();
            d.setDni(req.getParameter("dni"));
            d.setNombres(req.getParameter("nombres"));
            d.setApellidos(req.getParameter("apellidos"));
            d.setEspecialidad(req.getParameter("especialidad"));
            d.setTelefono(req.getParameter("telefono"));
            d.setEmail(req.getParameter("email"));

            if (facade.registrarDocente(d, req.getParameter("usuario"), req.getParameter("clave"))) {
                req.getSession().setAttribute("msg", "✅ Docente registrado con credenciales.");
            } else {
                req.getSession().setAttribute("error", "❌ Error al registrar docente.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "❌ " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/DocenteServlet");
    }
}