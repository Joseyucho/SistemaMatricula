<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.escuela.modelo.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // Validación de sesión rápida (por si acaso)
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); 
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Cursos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">🏫 Gestión de Cursos</span>
    <div class="text-white">
        <%-- Usamos el objeto u para evitar errores si no existe getNombreCompleto() --%>
        <span class="badge-rol me-3"><%= u.getNombres() + " " + u.getApellidos() %></span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>

<div class="container mt-4">
    <%-- Mensajes de éxito o error --%>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show">
            ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-dismissible fade show">
            ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="msg" scope="session"/>
    </c:if>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Cursos registrados</h3>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalCurso">
            <i class="bi bi-plus-circle"></i> Nuevo Curso
        </button>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Curso</th>
                        <th>Nivel</th>
                        <th>Grado</th>
                        <th>Sección</th>
                        <th>Docente</th>
                        <th>Horario</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${requestScope.cursos}">
                        <tr>
                            <td>${c.nombre}</td>
                            <td>${c.nivel}</td>
                            <td>${c.grado}°</td>
                            <td>${c.seccion}</td>
                            <td>${c.nombreDocente}</td>
                            <td>${c.horaInicio} - ${c.horaFin} (${c.diaSemana})</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty requestScope.cursos}">
                        <tr>
                            <td colspan="6" class="text-center text-muted">No hay cursos registrados.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal para Nuevo Curso -->
<div class="modal fade" id="modalCurso" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/CursoServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Nuevo Curso</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Nivel</label>
                        <select name="nivel" class="form-select" required>
                            <option value="PRIMARIA">Primaria</option>
                            <option value="SECUNDARIA">Secundaria</option>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Grado</label>
                            <select name="grado" class="form-select" required>
                                <option value="1">1°</option><option value="2">2°</option>
                                <option value="3">3°</option><option value="4">4°</option>
                                <option value="5">5°</option><option value="6">6°</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Sección</label>
                            <select name="seccion" class="form-select" required>
                                <option value="A">A</option><option value="B">B</option>
                                <option value="C">C</option>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Docente</label>
                        <select name="docenteId" class="form-select" required>
                            <option value="">-- Seleccione Docente --</option>
                            <c:forEach var="d" items="${requestScope.docentes}">
                                <option value="${d.id}">${d.apellidos}, ${d.nombres}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Hora Inicio</label>
                            <input type="time" name="horaInicio" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Hora Fin</label>
                            <input type="time" name="horaFin" class="form-control" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Día</label>
                        <select name="diaSemana" class="form-select" required>
                            <option value="Lunes">Lunes</option>
                            <option value="Martes">Martes</option>
                            <option value="Miércoles">Miércoles</option>
                            <option value="Jueves">Jueves</option>
                            <option value="Viernes">Viernes</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>