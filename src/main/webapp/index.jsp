<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.escuela.modelo.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u != null) {
        String destino = request.getContextPath();
        switch (u.getRol()) {
            case "ADMIN":      destino += "/vistas/admin/dashboard.jsp"; break;
            case "SECRETARIA": destino += "/vistas/secretaria/alumnos.jsp"; break;
            case "DOCENTE":    destino += "/vistas/docente/dashboard.jsp"; break;
            case "DIRECTOR":   destino += "/vistas/director/dashboard.jsp"; break;
            case "TESORERIA":  destino += "/vistas/tesoreria/pagos.jsp"; break;
            default:           destino += "/login.jsp";
        }
        response.sendRedirect(destino);
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="2;url=${pageContext.request.contextPath}/login.jsp">
    <title>Bienvenido - Sistema de Matrícula</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <div class="login-card">
        <div class="login-header">
            <i class="bi bi-mortarboard-fill" style="font-size: 3.5rem;"></i>
            <h2>Sistema de Matrícula</h2>
            <p class="mb-0">Colegio · Primaria y Secundaria</p>
        </div>
        <div class="p-4 text-center">
            <p class="lead">Bienvenido al sistema.</p>
            <p class="text-muted">Serás redirigido al inicio de sesión en unos segundos…</p>
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary w-100 py-2">
                <i class="bi bi-box-arrow-in-right"></i> Ir al Login
            </a>
            <hr>
            <small class="text-muted">
                <strong>Usuarios de prueba (clave: admin123):</strong><br>
                👑 admin · 📝 secretaria · 👨‍🏫 docente · 🎓 director · 💰 tesoreria
            </small>
        </div>
    </div>
</div>
</body>
</html>