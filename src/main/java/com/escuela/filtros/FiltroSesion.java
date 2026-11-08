package com.escuela.filtros;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/vistas/*", "/AlumnoServlet", "/DocenteServlet",
        "/CursoServlet", "/NotaServlet", "/AsistenciaServlet", "/PagoServlet", "/ReporteServlet"})
public class FiltroSesion implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) res;
        HttpSession ses = r.getSession(false);
        if (ses == null || ses.getAttribute("usuario") == null) {
            s.sendRedirect(r.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(req, res);
    }
}