<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de Sesión</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: url('${pageContext.request.contextPath}/src/img/logoReportes.jpg') no-repeat center center fixed;
            background-size: cover;
            font-family: 'Arial', sans-serif;
        }
        .login-container {
            background-color: rgba(255, 255, 255, 0.85);
            border-radius: 10px;
            padding: 2rem;
            max-width: 900px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .login-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .login-header h1 {
            font-size: 2rem;
            color: #333;
        }
        .login-header img {
            width: 150px;
            margin-bottom: 1rem;
        }
        .form-control {
            border-radius: 30px;
            padding: 0.75rem 1.5rem;
        }
        .btn-primary, .btn-secondary {
            border-radius: 30px;
            padding: 0.5rem 1.5rem;
        }
        .custom-checkbox {
            margin-top: 1rem;
        }
        .forgot-password {
            text-align: right;
            margin-top: 0.5rem;
        }
        .forgot-password a {
            color: #007bff;
            text-decoration: none;
        }
        .forgot-password a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="login-container">
            <div class="login-header">
                <img src="${pageContext.request.contextPath}/src/img/logini2.png" class="img-fluid mx-auto d-block" alt="Logo Admin">
                <h1>Inicio de Sesión</h1>
            </div>
            <form id="loginForm" action="LoginServlet" method="POST">
                <div class="form-group">
                    <label for="correo">Usuario</label>
                    <input type="text" id="correo" name="correo" class="form-control" placeholder="Nombre de usuario" required>
                </div>
                <div class="form-group">
                    <label for="contrasena">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena" class="form-control" placeholder="Contraseña" required>
                </div>
                <div class="custom-checkbox">
                    <input type="checkbox" id="rememberMe" name="rememberMe">
                    <label for="rememberMe">Recordarme</label>
                </div>
                
                <button type="submit" class="btn btn-primary btn-block">Iniciar Sesión</button>
                <hr>
                <div class="text-center">
                    <button type="button" class="btn btn-secondary btn-block" onclick="window.location.href='register.jsp'">Registrar</button>
                </div>
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
