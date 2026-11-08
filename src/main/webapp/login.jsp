<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema Matrícula</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <div class="login-card">
        <div class="login-header">
            <i class="bi bi-mortarboard-fill" style="font-size:3rem;"></i>
            <h2>Sistema de Matrícula</h2>
            <p class="mb-0">Colegio Primaria y Secundaria</p>
        </div>
        <div class="p-4">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">${requestScope.error}</div>
            <% } %>
            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                <div class="mb-3">
                    <label class="form-label">Usuario</label>
                    <input type="text" name="usuario" class="form-control" required autofocus>
                </div>
                <div class="mb-3">
                    <label class="form-label">Contraseña</label>
                    <input type="password" name="clave" class="form-control" required>
                </div>
                <button class="btn btn-primary w-100 py-2">
                    <i class="bi bi-box-arrow-in-right"></i> Ingresar
                </button>
            </form>
            <hr>
            <small class="text-muted">
                <strong>Usuarios de prueba (clave: admin123):</strong><br>
                admin · secretaria · docente · director · tesoreria
            </small>
        </div>
    </div>
</div>
</body>
</html>