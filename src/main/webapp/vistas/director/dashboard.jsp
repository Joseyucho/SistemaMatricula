<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !("DIRECTOR".equals(u.getRol()) || "ADMIN".equals(u.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Panel Director</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">📊 Panel Director</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>
<div class="container mt-4">
    <div class="card card-dashboard p-4">
        <h5><i class="bi bi-file-earmark-pdf"></i> Reportes</h5>
        <p class="text-muted">Consultar e imprimir reportes del colegio</p>
        <a href="${pageContext.request.contextPath}/vistas/director/reportes.jsp" class="btn btn-danger">Ir a Reportes</a>
    </div>
</div>
</body>
</html>