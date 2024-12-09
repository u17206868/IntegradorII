<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Trabajador</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

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
        }
        .content {
            margin-left: 250px;
            padding: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="includes/menuTrabajador.jsp" />
    <jsp:include page="includes/header.jsp" />
    
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Bienvenido Trabajador, <c:out value="${usuario.nombre}"/> <c:out value="${usuario.apellido}"/></h1>
            <p class="text-center">Rol: <c:out value="${usuario.rol}"/></p>
        </header>
        <section>
            <h2 class="text-center">Opciones Disponibles</h2>
            <ul class="list-group">
                <li class="list-group-item"><a href="#">Ver Tareas Diarias</a></li>
                <li class="list-group-item"><a href="#">Actualizar Estado</a></li>
                <!-- Más opciones según sea necesario -->
            </ul>
        </section>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
