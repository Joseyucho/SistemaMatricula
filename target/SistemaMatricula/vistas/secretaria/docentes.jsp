<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.Usuario" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !("SECRETARIA".equals(u.getRol()) || "ADMIN".equals(u.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Gestión de Docentes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">🎓 Gestión de Docentes</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>
<div class="container mt-4">
    <div class="d-flex justify-content-between mb-3">
        <h3>Listado de Docentes</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalNuevoDoc">
            <i class="bi bi-person-plus"></i> Nuevo Docente
        </button>
    </div>
    <div class="card"><div class="card-body table-responsive">
        <table class="table table-hover">
            <thead class="table-colegio">
                <tr><th>DNI</th><th>Nombre</th><th>Especialidad</th><th>Teléfono</th><th>Email</th></tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${requestScope.docentes}">
                    <tr>
                        <td>${d.dni}</td><td>${d.nombreCompleto}</td>
                        <td>${d.especialidad}</td><td>${d.telefono}</td><td>${d.email}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div></div>
</div>

<div class="modal fade" id="modalNuevoDoc">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/DocenteServlet" method="post">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Registrar Nuevo Docente</h5>
                    <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-4"><label class="form-label">DNI</label>
                            <input name="dni" class="form-control" maxlength="8" required></div>
                        <div class="col-md-4"><label class="form-label">Nombres</label>
                            <input name="nombres" class="form-control" required></div>
                        <div class="col-md-4"><label class="form-label">Apellidos</label>
                            <input name="apellidos" class="form-control" required></div>
                        <div class="col-md-6"><label class="form-label">Especialidad</label>
                            <input name="especialidad" class="form-control"></div>
                        <div class="col-md-3"><label class="form-label">Teléfono</label>
                            <input name="telefono" class="form-control"></div>
                        <div class="col-md-3"><label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control"></div>
                        <hr><p class="text-muted">Credenciales de acceso:</p>
                        <div class="col-md-6"><label class="form-label">Usuario</label>
                            <input name="usuario" class="form-control" required></div>
                        <div class="col-md-6"><label class="form-label">Contraseña</label>
                            <input type="password" name="clave" class="form-control" required></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success">Guardar Docente</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>