<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administrador</title>
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

        .navbar-custom {
            background-color: #343a40; /* Color oscuro para el navbar */
        }

        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: white;
        }

        .navbar-custom .navbar-brand:hover,
        .navbar-custom .nav-link:hover {
            color: #ffcc00; /* Color de hover */
        }
    </style>
</head>
<body>
    
    <!-- Header Section (Navbar) -->
    <nav class="navbar navbar-expand-lg navbar-custom fixed-top">
        <div class="container-fluid">      
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">                  
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/CerrarSesionServlet">Cerrar Sesión</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
