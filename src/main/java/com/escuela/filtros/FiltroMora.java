package com.escuela.filtros;

import com.escuela.dao.AlumnoDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/NotaServlet", "/AsistenciaServlet"})
public class FiltroMora implements Filter {
    private final AlumnoDAO dao = new AlumnoDAO();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) res;
        String idStr = r.getParameter("alumnoId");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            if (dao.contarMesesAdeudados(id) > 2) {
                r.getSession().setAttribute("error",
                    "⛔ ALUMNO BLOQUEADO: debe más de 2 mensualidades. Regularice en Tesorería.");
                s.sendRedirect(r.getContextPath() + "/vistas/bloqueado.jsp");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}