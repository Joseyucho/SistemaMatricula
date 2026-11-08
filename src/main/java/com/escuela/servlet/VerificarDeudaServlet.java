package com.escuela.servlet;

import com.escuela.facade.MatriculaFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/VerificarDeudaServlet")
public class VerificarDeudaServlet extends HttpServlet {
    
    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            int alumnoId = Integer.parseInt(req.getParameter("alumnoId"));
            int mesesDeuda = facade.mesesAdeudados(alumnoId);
            
            // Si tiene 2 o más meses de deuda, la respuesta será true
            boolean tieneDeuda = mesesDeuda >= 2;
            
            out.print("{\"tieneDeuda\": " + tieneDeuda + ", \"meses\": " + mesesDeuda + "}");
            
        } catch (Exception e) {
            resp.setStatus(500);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}