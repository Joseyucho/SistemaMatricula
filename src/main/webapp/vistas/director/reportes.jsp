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
<head><meta charset="UTF-8"><title>Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<nav class="navbar navbar-colegio px-3">
    <span class="navbar-brand">📊 Panel Director - Reportes</span>
    <div class="text-white">
        <span class="badge-rol me-3">${usuario.nombreCompleto}</span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Salir</a>
    </div>
</nav>
<div class="container mt-4">
    <div class="row g-4">
        <div class="col-md-6">
            <div class="card card-dashboard p-4">
                <h5><i class="bi bi-file-person"></i> Reporte de Matrícula</h5>
                <form action="${pageContext.request.contextPath}/ReporteServlet" method="get" target="_blank">
                    <input type="hidden" name="tipo" value="matricula">
                    <div class="mb-2"><label class="form-label">Nivel</label>
                        <select name="nivel" class="form-select">
                            <option value="">Todos</option>
                            <option value="PRIMARIA">Primaria</option>
                            <option value="SECUNDARIA">Secundaria</option>
                        </select></div>
                    <div class="row">
                        <div class="col-md-6"><label class="form-label">Grado</label>
                            <select name="grado" class="form-select">
                                <option value="">Todos</option>
                                <option value="1">1°</option><option value="2">2°</option>
                                <option value="3">3°</option><option value="4">4°</option>
                                <option value="5">5°</option><option value="6">6°</option>
                            </select></div>
                        <div class="col-md-6"><label class="form-label">Sección</label>
                            <select name="seccion" class="form-select">
                                <option value="">Todas</option>
                                <option value="A">A</option><option value="B">B</option><option value="C">C</option>
                            </select></div>
                    </div>
                    <button class="btn btn-danger mt-3 w-100"><i class="bi bi-file-pdf"></i> Generar PDF</button>
                </form>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card card-dashboard p-4">
                <h5><i class="bi bi-cash-stack"></i> Reporte Económico</h5>
                <form action="${pageContext.request.contextPath}/ReporteServlet" method="get" target="_blank">
                    <input type="hidden" name="tipo" value="economico">
                    <div class="mb-2"><label class="form-label">Desde</label>
                        <input type="date" name="fechaIni" class="form-control" required></div>
                    <div class="mb-2"><label class="form-label">Hasta</label>
                        <input type="date" name="fechaFin" class="form-control" required></div>
                    <button class="btn btn-danger mt-3 w-100"><i class="bi bi-file-pdf"></i> Generar PDF</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>