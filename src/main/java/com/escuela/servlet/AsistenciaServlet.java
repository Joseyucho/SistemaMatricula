package com.escuela.servlet;

import com.escuela.dao.AsistenciaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AsistenciaServlet")
public class AsistenciaServlet extends HttpServlet {
    private final AsistenciaDAO dao = new AsistenciaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            int alumnoId = Integer.parseInt(req.getParameter("alumnoId"));
            int cursoId  = Integer.parseInt(req.getParameter("cursoId"));
            java.sql.Date fecha = java.sql.Date.valueOf(req.getParameter("fecha"));
            String estado = req.getParameter("estado");

            dao.registrar(alumnoId, cursoId, fecha, estado);
            req.getSession().setAttribute("msg", "✅ Asistencia registrada.");
        } catch (SQLException e) {
            req.getSession().setAttribute("error", "⛔ " + e.getMessage());
        } catch (Exception e) {
            req.getSession().setAttribute("error", "❌ " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/vistas/docente/asistencia.jsp");
    }
}