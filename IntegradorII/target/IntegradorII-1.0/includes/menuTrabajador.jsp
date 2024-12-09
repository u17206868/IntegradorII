<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark flex-column vh-100 fixed-left">
    <!-- Logo y T�tulo -->
    <div class="text-center py-2">
        <img src="src/img/LogoReportes.jpg" class="img-fluid rounded-circle mx-auto d-block" alt="Logo Trabajador" style="max-width: 120px;">
    </div>
    <a class="navbar-brand text-center mt-2" href="#">Trabajador</a>
    
    <!-- Bot�n Toggler para dispositivos peque�os -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTrabajador" aria-controls="navbarTrabajador" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    
    <!-- Opciones del Men� -->
    <div class="collapse navbar-collapse" id="navbarTrabajador">
        <ul class="navbar-nav flex-column w-100">
            <li class="nav-item">
                <a class="nav-link text-white" href="workerDashboard.jsp">
                    <i class="fas fa-home"></i> Inicio
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="OrdenProduccionServlet">
                    <i class="fas fa-cogs"></i> Producci�n
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="OrdenSelladoServlet">
                    <i class="fas fa-warehouse"></i> Sellado
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="MovimientoAlmacenServlet">
                    <i class="fas fa-box"></i> Almac�n
                </a>
            </li>
        </ul>
    </div>
</nav>
