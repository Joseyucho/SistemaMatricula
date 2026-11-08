<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.escuela.modelo.Usuario" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !"ADMIN".equals(u.getRol())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">👑 Gestión de Usuarios</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/vistas/admin/dashboard.jsp" class="btn btn-outline-light btn-sm me-2">Volver</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>

<div class="container mt-4">
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-dismissible fade show">${sessionScope.msg}
            <button class="btn-close" data-bs-dismiss="alert"></button></div>
        <c:remove var="msg" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show">${sessionScope.error}
            <button class="btn-close" data-bs-dismiss="alert"></button></div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <div class="d-flex justify-content-between mb-3">
        <h3>Usuarios del Sistema</h3>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalNuevoUsuario">
            <i class="bi bi-person-plus-fill"></i> Nuevo Usuario
        </button>
    </div>

    <div class="card">
        <div class="card-body table-responsive">
            <table class="table table-hover">
                <thead class="table-colegio">
                    <tr>
                        <th>ID</th><th>Usuario</th><th>Rol</th>
                        <th>Nombre Completo</th><th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usr" items="${requestScope.usuarios}">
                        <tr>
                            <td>${usr.id}</td>
                            <td><strong>${usr.usuario}</strong></td>
                            <td>
                                <c:choose>
                                    <c:when test="${usr.rol == 'ADMIN'}"><span class="badge bg-danger">ADMIN</span></c:when>
                                    <c:when test="${usr.rol == 'SECRETARIA'}"><span class="badge bg-primary">SECRETARIA</span></c:when>
                                    <c:when test="${usr.rol == 'DOCENTE'}"><span class="badge bg-success">DOCENTE</span></c:when>
                                    <c:when test="${usr.rol == 'DIRECTOR'}"><span class="badge bg-warning text-dark">DIRECTOR</span></c:when>
                                    <c:when test="${usr.rol == 'TESORERIA'}"><span class="badge bg-info text-dark">TESORERIA</span></c:when>
                                </c:choose>
                            </td>
                            <td>${usr.nombreCompleto}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${usr.activo}"><span class="badge bg-success">Activo</span></c:when>
                                    <c:otherwise><span class="badge bg-secondary">Inactivo</span></c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Nuevo Usuario -->
<div class="modal fade" id="modalNuevoUsuario" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Registrar Nuevo Usuario</h5>
                    <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Nombre de Usuario</label>
                        <input name="usuario" class="form-control" required autocomplete="off">
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Nombres</label>
                            <input name="nombres" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Apellidos</label>
                            <input name="apellidos" class="form-control" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Rol del Sistema</label>
                        <select name="rol" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="SECRETARIA">SECRETARIA</option>
                            <option value="DOCENTE">DOCENTE</option>
                            <option value="DIRECTOR">DIRECTOR</option>
                            <option value="TESORERIA">TESORERIA</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contraseña</label>
                        <input type="password" name="clave" class="form-control" required>
                        <small class="text-muted">Será encriptada con BCrypt automáticamente.</small>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary"><i class="bi bi-save"></i> Guardar Usuario</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>