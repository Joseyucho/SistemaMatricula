<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !"ADMIN".equals(u.getRol())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><title>Dashboard Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">🎓 Panel Administrador</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>
<div class="container mt-4">
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-people-fill" style="font-size:2.5rem;color:#1e3a8a;"></i>
                <h5 class="mt-2">Alumnos</h5>
                <a href="${pageContext.request.contextPath}/AlumnoServlet" class="btn btn-primary w-100">Gestionar</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-person-video3" style="font-size:2.5rem;color:#1e3a8a;"></i>
                <h5 class="mt-2">Docentes</h5>
                <a href="${pageContext.request.contextPath}/DocenteServlet" class="btn btn-primary w-100">Gestionar</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-journal-bookmark-fill" style="font-size:2.5rem;color:#1e3a8a;"></i>
                <h5 class="mt-2">Cursos</h5>
                <a href="${pageContext.request.contextPath}/CursoServlet" class="btn btn-primary w-100">Gestionar</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-cash-stack" style="font-size:2.5rem;color:#f59e0b;"></i>
                <h5 class="mt-2">Tesorería</h5>
                <a href="${pageContext.request.contextPath}/PagoServlet" class="btn btn-warning w-100 text-white">Ir</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-file-earmark-pdf" style="font-size:2.5rem;color:#dc2626;"></i>
                <h5 class="mt-2">Reportes</h5>
                <a href="${pageContext.request.contextPath}/vistas/director/reportes.jsp" class="btn btn-danger w-100">Ver</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-dashboard p-4 text-center">
                <i class="bi bi-pencil-square" style="font-size:2.5rem;color:#10b981;"></i>
                <h5 class="mt-2">Notas</h5>
                <a href="${pageContext.request.contextPath}/vistas/docente/notas.jsp" class="btn btn-success w-100">Ir</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>