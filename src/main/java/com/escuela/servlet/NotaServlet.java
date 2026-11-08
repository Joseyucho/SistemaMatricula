package com.escuela.servlet;

import com.escuela.dao.NotaDAO;
import com.escuela.facade.MatriculaFacade;
import com.escuela.modelo.Nota;
import com.escuela.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/NotaServlet")
public class NotaServlet extends HttpServlet {
    
    private final MatriculaFacade facade = new MatriculaFacade();
    private final NotaDAO notaDAO = new NotaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        Usuario u = (Usuario) session.getAttribute("usuario");
        String vistaRetorno = "/vistas/docente/notas.jsp"; // Ruta por defecto
        
        // Redirección dinámica según el rol (si es admin o director, que vuelva a su vista)
        if (u != null && "ADMIN".equals(u.getRol())) {
            vistaRetorno = "/vistas/admin/notas.jsp";
        } else if (u != null && "DIRECTOR".equals(u.getRol())) {
            vistaRetorno = "/vistas/director/notas.jsp";
        }

        try {
            int alumnoId = Integer.parseInt(req.getParameter("alumnoId"));
            int cursoId = Integer.parseInt(req.getParameter("cursoId"));
            int bimestre = Integer.parseInt(req.getParameter("bimestre"));
            double notaValor = Double.parseDouble(req.getParameter("nota"));

            // 1. Validar si el alumno debe notas
            int mesesDeuda = facade.mesesAdeudados(alumnoId);
            if (mesesDeuda >= 2) {
                // CORRECCIÓN DE RUTA AQUÍ:
                resp.sendRedirect(req.getContextPath() + "/vistas/bloqueado.jsp");
                return;
            }

            // 2. Registrar la nota en la BD
            Nota n = new Nota();
            n.setAlumnoId(alumnoId);
            n.setCursoId(cursoId);
            n.setBimestre(bimestre);
            n.setNota(notaValor);

            if (notaDAO.insertar(n)) {
                session.setAttribute("msg", "✅ Nota registrada exitosamente.");
            } else {
                session.setAttribute("error", "❌ Error al registrar la nota en la base de datos.");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "❌ Error en los datos enviados: " + e.getMessage());
        } catch (Exception e) {
            session.setAttribute("error", "❌ " + e.getMessage());
            e.printStackTrace();
        }
        
        // 3. Redirigir de vuelta
        resp.sendRedirect(req.getContextPath() + vistaRetorno);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/vistas/docente/notas.jsp");
    }
}