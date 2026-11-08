<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.escuela.modelo.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !("SECRETARIA".equals(u.getRol()) || "ADMIN".equals(u.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><title>Gestión de Alumnos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">🎓 Gestión de Alumnos</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
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
        <h3>Listado de Alumnos</h3>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalNuevo">
            <i class="bi bi-plus-circle"></i> Nuevo Alumno
        </button>
    </div>

    <div class="card">
        <div class="card-body table-responsive">
            <table class="table table-hover">
                <thead class="table-colegio">
                    <tr><th>DNI</th><th>Apellidos y Nombres</th><th>Nivel</th>
                        <th>Grado</th><th>Sección</th><th>Apoderado</th><th>Acciones</th></tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${requestScope.alumnos}">
                        <tr>
                            <td>${a.dni}</td>
                            <td>${a.nombreCompleto}</td>
                            <td><span class="badge bg-info">${a.nivel}</span></td>
                            <td>${a.grado}°</td>
                            <td>${a.seccion}</td>
                            <td>${a.apoderado}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/ReporteServlet?tipo=notas&alumnoId=${a.id}"
                                   target="_blank" class="btn btn-sm btn-outline-danger">
                                    <i class="bi bi-file-pdf"></i> Notas
                                </a>
                                <a href="${pageContext.request.contextPath}/ReporteServlet?tipo=pagos&alumnoId=${a.id}"
                                   target="_blank" class="btn btn-sm btn-outline-warning">
                                    <i class="bi bi-cash"></i> Pagos
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="modalNuevo" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/AlumnoServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Matricular Nuevo Alumno</h5>
                    <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-4"><label class="form-label">DNI</label>
                            <input type="text" name="dni" class="form-control" maxlength="8" required></div>
                        <div class="col-md-4"><label class="form-label">Nombres</label>
                            <input type="text" name="nombres" class="form-control" required></div>
                        <div class="col-md-4"><label class="form-label">Apellidos</label>
                            <input type="text" name="apellidos" class="form-control" required></div>
                        <div class="col-md-6"><label class="form-label">Dirección</label>
                            <input type="text" name="direccion" class="form-control"></div>
                        <div class="col-md-6"><label class="form-label">Apoderado</label>
                            <input type="text" name="apoderado" class="form-control"></div>
                        <div class="col-md-4"><label class="form-label">Teléfono</label>
                            <input type="text" name="telefono" class="form-control"></div>
                        <div class="col-md-3"><label class="form-label">Nivel</label>
                            <select name="nivel" class="form-select" required>
                                <option value="PRIMARIA">Primaria</option>
                                <option value="SECUNDARIA">Secundaria</option>
                            </select></div>
                        <div class="col-md-2"><label class="form-label">Grado</label>
                            <select name="grado" class="form-select" required>
                                <option value="1">1°</option><option value="2">2°</option>
                                <option value="3">3°</option><option value="4">4°</option>
                                <option value="5">5°</option><option value="6">6°</option>
                            </select></div>
                        <div class="col-md-3"><label class="form-label">Sección</label>
                            <select name="seccion" class="form-select" required>
                                <option value="A">A</option><option value="B">B</option>
                                <option value="C">C</option>
                            </select></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary">Registrar y generar calendario</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>