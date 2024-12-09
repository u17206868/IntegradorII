<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark flex-column vh-100 fixed-left">
    <div class="text-center py-3">
        <img src="src/img/LogoReportes.jpg" class="rounded-circle img-fluid mx-auto" alt="Logo Admin" style="width: 120px; height: 120px; object-fit: cover;">
    </div>
    <a class="navbar-brand text-center text-white mb-3" href="#">Admin</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarAdmin" aria-controls="navbarAdmin" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarAdmin">
        <ul class="navbar-nav flex-column w-100">
            <li class="nav-item">
                <a class="nav-link text-white" href="adminDashboard.jsp">
                    <i class="fas fa-home"></i> Inicio
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="UserServlet">
                    <i class="fas fa-users"></i> Usuarios
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="VentaServlet">
                    <i class="fas fa-cash-register"></i> Ventas
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="OrdenProduccionServlet">
                    <i class="fas fa-cogs"></i> Producción
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="OrdenSelladoServlet">
                    <i class="fas fa-wrench"></i> Sellado
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="ClientServlet">
                    <i class="fas fa-users-cog"></i> Clientes
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="ProveedorServlet">
                    <i class="fas fa-truck"></i> Proveedores
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="MateriasPrimasServlet">
                    <i class="fas fa-leaf"></i> Materia Prima
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="MovimientoAlmacenServlet">
                    <i class="fas fa-warehouse"></i> Inventario
                </a>
            </li>
        </ul>
    </div>
</nav>
