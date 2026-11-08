<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Acceso Bloqueado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body class="d-flex align-items-center justify-content-center vh-100 bg-light">
    <div class="card shadow-lg text-center p-5" style="max-width: 500px; border-radius: 20px;">
        <div class="text-danger mb-3">
            <i class="bi bi-shield-lock-fill" style="font-size: 4rem;"></i>
        </div>
        <h3 class="fw-bold text-danger">Acceso Bloqueado</h3>
        <p class="text-muted mt-2">El alumno tiene más de 2 mensualidades vencidas. Regularice el pago en Tesorería para poder registrar notas.</p>
        <a href="${pageContext.request.contextPath}/vistas/docente/dashboard.jsp" class="btn btn-primary mt-3">
            <i class="bi bi-arrow-left"></i> Volver al Inicio
        </a>
    </div>
</body>
</html>