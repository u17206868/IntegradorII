<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administrador</title>
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .fixed-left {
            position: fixed;
            left: 0;
            top: 0;
            height: 100%;
            width: 250px;
            z-index: 1000;
            overflow-x: hidden;
            padding-top: 20px;
            background-color: #f8f9fa; /* Fondo claro */
        }

        .content {
            margin-left: 260px; /* Ajustado para margen más amplio */
            padding: 20px;
        }

        .logout-button {
            position: fixed;
            bottom: 20px;
            left: 20px;
            width: 210px;
            z-index: 1001; /* Asegura que el botón esté sobre otros elementos */
        }
    </style>
</head>
<body>
    <div class="fixed-left">
        <jsp:include page="includes/menuAdmin.jsp" />
    </div>
    
    <!-- Incluir el header.jsp con la fecha y los datos del usuario -->
    <jsp:include page="includes/header.jsp" />

    <div class="content">
        <header class="my-4">
            <div class="jumbotron text-center">
                <h1>Bienvenido a Nuestra Aplicación de Gestión</h1>
                <p>Optimiza tu gestión de almacenes y producción de manera eficiente y efectiva.</p>
            </div>
            <h2 class="text-center">Opciones Administrativas</h2>
            <ul class="list-group">
                <li class="list-group-item"><a href="#">Gestionar Usuarios</a></li>
                <li class="list-group-item"><a href="#">Generar Reportes</a></li>
                <!-- Más opciones según sea necesario -->
            </ul>
        </header>

        <section>
            <h2>Características Principales</h2>
            <ul>
                <li>Gestión completa de inventarios y almacenes.</li>
                <li>Seguimiento y administración de órdenes de producción.</li>
                <li>Generación de informes detallados en formato PDF.</li>
            </ul>
        </section>

        <section class="text-center">
            <h2>Accesos Rápidos</h2>
            <div class="btn-group">
                <a href="MovimientoAlmacenServlet" class="btn btn-primary">Gestión de Inventarios</a>
                <a href="OrdenProduccionServlet" class="btn btn-secondary">Órdenes de Producción</a>
                <a href="VentaServlet" class="btn btn-success">Ventas</a>
                <a href="informes.jsp" class="btn btn-info">Generar Informes</a>
            </div>
        </section>

        <section>
            <h2>Testimonios</h2>
            <blockquote class="blockquote">
                <p class="mb-0">"Esta aplicación ha transformado la manera en que gestionamos nuestros almacenes. Altamente recomendada!"</p>
                <footer class="blockquote-footer">Juan Pérez, <cite title="Source Title">Empresa XYZ</cite></footer>
            </blockquote>
            <blockquote class="blockquote">
                <p class="mb-0">"La generación de informes en PDF nos ha ahorrado muchísimo tiempo. ¡Es una herramienta indispensable!"</p>
                <footer class="blockquote-footer">María López, <cite title="Source Title">Negocio ABC</cite></footer>
            </blockquote>
        </section>

        <section>
            <h2>Soporte y Contacto</h2>
            <p>Si necesitas ayuda, no dudes en contactar con nuestro equipo de soporte.</p>
            <p>Email: soporte@tuaplicacion.com</p>
            <p>Teléfono: +123 456 7890</p>
        </section>

        <section>
            <h2>Noticias y Actualizaciones</h2>
            <ul>
                <li><strong>Versión 1.2:</strong> Nueva funcionalidad de generación de informes en PDF.</li>
                <li><strong>Versión 1.1:</strong> Mejoras en la gestión de inventarios y rendimiento.</li>
            </ul>
        </section>
    </div>
    
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Título del Modal</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Texto para el modal.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="button" class="btn btn-primary">Guardar cambios</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Trigger Modal -->
    <div class="content">
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Abrir Modal
        </button>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
