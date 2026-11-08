<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.*, com.colegio.facade.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !"DOCENTE".equals(u.getRol())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
    MatriculaFacade f = new MatriculaFacade();
    Docente doc = f.buscarDocentePorUsuario(u.getId());
    request.setAttribute("alumnos", f.listarAlumnos());
    request.setAttribute("cursos", doc != null ? f.listarCursosPorDocente(doc.getId()) : new java.util.ArrayList<>());
%>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Registro de Asistencia</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">📋 Registro de Asistencia</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/vistas/docente/dashboard.jsp" class="btn btn-outline-light btn-sm">Volver</a>
    </div>
</nav>
<div class="container mt-4">
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success">${sessionScope.msg}</div>
        <c:remove var="msg" scope="session"/>
    </c:if>

    <div class="card">
        <div class="card-header bg-success text-white"><h5>Registrar Asistencia</h5></div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/AsistenciaServlet" method="post">
                <div class="row g-3">
                    <div class="col-md-4"><label class="form-label">Alumno</label>
                        <select name="alumnoId" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <c:forEach var="a" items="${requestScope.alumnos}">
                                <option value="${a.id}">${a.nombreCompleto} (${a.nivel} ${a.grado}°${a.seccion})</option>
                            </c:forEach>
                        </select></div>
                    <div class="col-md-3"><label class="form-label">Curso</label>
                        <select name="cursoId" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <c:forEach var="c" items="${requestScope.cursos}">
                                <option value="${c.id}">${c.nombre} - ${c.grado}°${c.seccion}</option>
                            </c:forEach>
                        </select></div>
                    <div class="col-md-2"><label class="form-label">Fecha</label>
                        <input type="date" name="fecha" class="form-control" required></div>
                    <div class="col-md-2"><label class="form-label">Estado</label>
                        <select name="estado" class="form-select" required>
                            <option value="PRESENTE">Presente</option>
                            <option value="AUSENTE">Ausente</option>
                            <option value="TARDE">Tarde</option>
                            <option value="JUSTIFICADO">Justificado</option>
                        </select></div>
                    <div class="col-md-1 d-flex align-items-end">
                        <button class="btn btn-success w-100"><i class="bi bi-save"></i></button></div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>