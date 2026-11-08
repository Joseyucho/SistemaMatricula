<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.*, com.escuela.facade.*, java.util.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !("TESORERIA".equals(u.getRol()) || "ADMIN".equals(u.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
    MatriculaFacade mf = new MatriculaFacade();
    PagoFacade pf = new PagoFacade();
    request.setAttribute("alumnos", mf.listarAlumnos());
    String idStr = request.getParameter("alumnoId");
    if (idStr != null && !idStr.isEmpty()) {
        int id = Integer.parseInt(idStr);
        request.setAttribute("pendientes", pf.estadoCuenta(id));
        request.setAttribute("alumnoSel", mf.buscarAlumno(id));
    }
%>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Tesorería - Pagos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">💰 Tesorería</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
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

    <div class="card mb-3">
        <div class="card-header bg-primary text-white"><h5>Buscar alumno</h5></div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/PagoServlet" method="get">
                <div class="row g-2">
                    <div class="col-md-8">
                        <select name="alumnoId" class="form-select" required>
                            <option value="">-- Seleccione un alumno --</option>
                            <c:forEach var="a" items="${requestScope.alumnos}">
                                <option value="${a.id}">${a.nombreCompleto} - ${a.nivel} ${a.grado}°${a.seccion}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-primary w-100"><i class="bi bi-search"></i> Consultar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${not empty requestScope.pendientes}">
        <div class="card">
            <div class="card-header bg-warning d-flex justify-content-between">
                <h5 class="mb-0">Estado de cuenta</h5>
                <a href="${pageContext.request.contextPath}/ReporteServlet?tipo=pagos&alumnoId=${alumnoSel.id}"
                   target="_blank" class="btn btn-sm btn-outline-dark">
                    <i class="bi bi-file-pdf"></i> Imprimir
                </a>
            </div>
            <div class="card-body table-responsive">
                <table class="table table-hover">
                    <thead class="table-colegio">
                        <tr><th>Mes</th><th>Concepto</th><th>Monto</th>
                            <th>Vencimiento</th><th>Estado</th><th>Acción</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${requestScope.pendientes}">
                            <tr>
                                <td>${p.mes}</td><td>${p.concepto}</td>
                                <td>S/ ${p.monto}</td>
                                <td>${p.fechaVencimiento}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.estado == 'PAGADO'}">
                                            <span class="badge bg-success">PAGADO</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">PENDIENTE</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${p.estado == 'PENDIENTE'}">
                                        <form action="${pageContext.request.contextPath}/PagoServlet" method="post" class="d-inline">
                                            <input type="hidden" name="calendarioId" value="${p.id}">
                                            <input type="hidden" name="alumnoId" value="${alumnoSel.id}">
                                            <input type="hidden" name="monto" value="${p.monto}">
                                            <button class="btn btn-sm btn-success">
                                                <i class="bi bi-cash-coin"></i> Registrar pago
                                            </button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>