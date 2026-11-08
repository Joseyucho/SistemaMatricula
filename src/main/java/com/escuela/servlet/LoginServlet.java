package com.escuela.servlet;

import com.escuela.dao.UsuarioDAO;
import com.escuela.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String usuario = req.getParameter("usuario");
        String clave = req.getParameter("clave");

        // --- DIAGNÓSTICO 1 ---
        System.out.println("==================================================");
        System.out.println("--> [SERVLET] Petición recibida");
        System.out.println("--> [SERVLET] Usuario recibido: [" + usuario + "]");
        System.out.println("--> [SERVLET] Clave recibida: [" + clave + "]");
        System.out.println("==================================================");

        try {
            Usuario u = dao.validar(usuario, clave);
            
            if (u == null) {
                // --- DIAGNÓSTICO 2: Fallo en DAO (Credenciales incorrectas) ---
                System.out.println("--> [SERVLET] ❌ ERROR: dao.validar() devolvió NULL.");
                System.out.println("--> [SERVLET] Motivos posibles: Usuario no existe, clave incorrecta o hash corrupto en la BD.");
                
                req.setAttribute("error", "Usuario o contraseña incorrectos.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            // --- DIAGNÓSTICO 3: Login exitoso ---
            System.out.println("--> [SERVLET] ✅ Login exitoso para: " + u.getUsuario() + " (Rol: " + u.getRol() + ")");

            HttpSession ses = req.getSession(true);
            ses.setAttribute("usuario", u);
            ses.setAttribute("rol", u.getRol());

            switch (u.getRol()) {
                case "ADMIN":      resp.sendRedirect(req.getContextPath() + "/vistas/admin/dashboard.jsp"); break;
                case "SECRETARIA": resp.sendRedirect(req.getContextPath() + "/vistas/secretaria/alumnos.jsp"); break;
                case "DOCENTE":    resp.sendRedirect(req.getContextPath() + "/vistas/docente/dashboard.jsp"); break;
                case "DIRECTOR":   resp.sendRedirect(req.getContextPath() + "/vistas/director/dashboard.jsp"); break;
                case "TESORERIA":  resp.sendRedirect(req.getContextPath() + "/vistas/tesoreria/pagos.jsp"); break;
                default:           resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        } catch (Exception e) {
            // --- DIAGNÓSTICO 4: Error de Base de Datos o Código ---
            System.err.println("==================================================");
            System.err.println("--> [SERVLET] ❌ ERROR GRAVE AL INTENTAR LOGUEAR");
            System.err.println("--> [SERVLET] Causa: " + e.getMessage());
            e.printStackTrace();
            System.err.println("==================================================");
            
            req.setAttribute("error", "Error interno del servidor. Revisa la consola.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}