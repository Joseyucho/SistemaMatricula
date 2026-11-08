<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.escuela.modelo.Usuario" %>
<%
    // Obtenemos el usuario logueado de la sesión
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
    String rolUsuario = (usuarioLogueado != null) ? usuarioLogueado.getRol().toLowerCase() : "";
    String nombreCompleto = (usuarioLogueado != null) ? usuarioLogueado.getNombres() + " " + usuarioLogueado.getApellidos() : "Invitado";
%>

<nav class="navbar navbar-expand-lg navbar-colegio px-3 mb-4">
    <div class="container-fluid">
        <!-- Botón para retroceder a la página anterior -->
        <a href="javascript:history.back()" class="btn btn-outline-light btn-sm me-3" title="Volver atrás">
            <i class="bi bi-arrow-left"></i> Volver
        </a>

        <!-- Nombre de la opción actual (se puede personalizar pasando un parámetro) -->
        <span class="navbar-brand mb-0 h1">
            <i class="bi bi-mortarboard-fill"></i> Sistema de Matrícula
        </span>

        <div class="ms-auto text-white d-flex align-items-center">
            <span class="badge-rol me-3"><%= nombreCompleto %> (<%= rolUsuario.toUpperCase() %>)</span>
            
            <!-- Botón de Inicio (redirige al dashboard según el rol) -->
            <a href="${pageContext.request.contextPath}/vistas/<%= rolUsuario %>/dashboard.jsp" class="btn btn-outline-light btn-sm me-2" title="Ir al inicio">
                <i class="bi bi-house-door"></i> Inicio
            </a>

            <!-- Botón de Salir -->
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-danger btn-sm" title="Cerrar sesión">
                <i class="bi bi-box-arrow-right"></i> Salir
            </a>
        </div>
    </div>
</nav>