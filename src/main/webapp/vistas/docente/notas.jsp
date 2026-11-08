<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.escuela.modelo.*, com.escuela.facade.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // 1. Validación de sesión y roles
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
    
    String rol = u.getRol();
    boolean esDocente = "DOCENTE".equals(rol);
    boolean esAdminODirector = "ADMIN".equals(rol) || "DIRECTOR".equals(rol);

    if (!esDocente && !esAdminODirector) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }

    // 2. Lógica de negocio
    MatriculaFacade f = new MatriculaFacade();
    List<Curso> listaCursos;
    
    if (esDocente) {
        Docente doc = f.buscarDocentePorUsuario(u.getId());
        listaCursos = (doc != null) ? f.listarCursosPorDocente(doc.getId()) : new ArrayList<>();
    } else {
        listaCursos = f.listarTodosLosCursos();
    }

    request.setAttribute("alumnos", f.listarAlumnos());
    request.setAttribute("cursos", listaCursos);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Notas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        /* Estilo para cuando el campo está deshabilitado */
        .input-bloqueado { background-color: #e9ecef; cursor: not-allowed; }
    </style>
</head>
<body>

    <jsp:include page="/WEB-INF/nav.jsp" />

    <div class="container mt-4">
        <div class="alerta-mora mb-4">
            <i class="bi bi-exclamation-triangle-fill fs-4"></i>
            <div><strong>Importante:</strong> Los alumnos con más de 2 mensualidades vencidas no podrán recibir notas.</div>
        </div>

        <div id="alerta-deuda" class="alert alert-danger d-none">
            <i class="bi bi-x-circle-fill"></i> Este alumno tiene 2 o más mensualidades vencidas. El registro de notas está bloqueado.
        </div>

        <%-- Mensajes de error/éxito --%>
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

        <div class="card card-dashboard">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="bi bi-pencil-square"></i> Registrar Nueva Nota</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/NotaServlet" method="post" id="formNota">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label">Alumno</label>
                            <select name="alumnoId" id="alumnoId" class="form-select" required onchange="verificarDeuda(this.value)">
                                <option value="">-- Seleccione Alumno --</option>
                                <c:forEach var="a" items="${requestScope.alumnos}">
                                    <option value="${a.id}">${a.nombreCompleto} (${a.nivel} ${a.grado}°${a.seccion})</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="col-md-3">
                            <label class="form-label">Curso</label>
                            <select name="cursoId" class="form-select" required>
                                <option value="">-- Seleccione Curso --</option>
                                <c:forEach var="c" items="${requestScope.cursos}">
                                    <option value="${c.id}">${c.nombre} - ${c.grado}°${c.seccion}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="col-md-2">
                            <label class="form-label">Bimestre</label>
                            <select name="bimestre" class="form-select" required>
                                <option value="1">I Bimestre</option>
                                <option value="2">II Bimestre</option>
                                <option value="3">III Bimestre</option>
                                <option value="4">IV Bimestre</option>
                            </select>
                        </div>
                        
                        <div class="col-md-2">
                            <label class="form-label">Nota (0-20)</label>
                            <input type="number" name="nota" id="notaInput" class="form-control" min="0" max="20" step="0.01" placeholder="Ej. 15.5" required>
                        </div>
                        
                        <div class="col-md-1 d-flex align-items-end">
                            <button type="submit" id="btnGuardar" class="btn btn-primary w-100" title="Guardar Nota">
                                <i class="bi bi-save"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Función que consulta al servidor si el alumno tiene deudas
        function verificarDeuda(alumnoId) {
            const notaInput = document.getElementById('notaInput');
            const btnGuardar = document.getElementById('btnGuardar');
            const alertaDeuda = document.getElementById('alerta-deuda');

            if (!alumnoId) {
                // Si no hay alumno seleccionado, habilitamos todo
                notaInput.disabled = false;
                notaInput.classList.remove('input-bloqueado');
                btnGuardar.disabled = false;
                alertaDeuda.classList.add('d-none');
                return;
            }

            // Hacemos una petición AJAX a un Servlet que verifique la deuda
            fetch('${pageContext.request.contextPath}/VerificarDeudaServlet?alumnoId=' + alumnoId)
                .then(response => response.json())
                .then(data => {
                    if (data.tieneDeuda) {
                        // BLOQUEAR CAMPOS
                        notaInput.disabled = true;
                        notaInput.value = "";
                        notaInput.placeholder = "Alumno con deuda";
                        notaInput.classList.add('input-bloqueado');
                        btnGuardar.disabled = true;
                        alertaDeuda.classList.remove('d-none');
                    } else {
                        // DESBLOQUEAR CAMPOS
                        notaInput.disabled = false;
                        notaInput.placeholder = "Ej. 15.5";
                        notaInput.classList.remove('input-bloqueado');
                        btnGuardar.disabled = false;
                        alertaDeuda.classList.add('d-none');
                    }
                })
                .catch(error => console.error('Error al verificar deuda:', error));
        }
    </script>
</body>
</html>