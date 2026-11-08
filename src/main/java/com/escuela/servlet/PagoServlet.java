package com.escuela.servlet;

import com.escuela.facade.MatriculaFacade;
import com.escuela.facade.PagoFacade;
import com.escuela.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/PagoServlet")
public class PagoServlet extends HttpServlet {
    private final PagoFacade pagoFacade = new PagoFacade();
    private final MatriculaFacade matFacade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idStr = req.getParameter("alumnoId");
        req.setAttribute("alumnos", matFacade.listarAlumnos());
        if (idStr != null && !idStr.isEmpty()) {
            int alumnoId = Integer.parseInt(idStr);
            req.setAttribute("pendientes", pagoFacade.estadoCuenta(alumnoId));
            req.setAttribute("alumnoSel", matFacade.buscarAlumno(alumnoId));
        }
        req.getRequestDispatcher("/vistas/tesoreria/pagos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int calendarioId = Integer.parseInt(req.getParameter("calendarioId"));
            int alumnoId = Integer.parseInt(req.getParameter("alumnoId"));
            BigDecimal monto = new BigDecimal(req.getParameter("monto"));
            Usuario u = (Usuario) req.getSession().getAttribute("usuario");

            boolean ok = pagoFacade.registrarPago(calendarioId, alumnoId, monto, u.getId());
            req.getSession().setAttribute(ok ? "msg" : "error",
                    ok ? "✅ Pago registrado." : "❌ Error al registrar pago.");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "❌ " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/PagoServlet?alumnoId=" + req.getParameter("alumnoId"));
    }
}