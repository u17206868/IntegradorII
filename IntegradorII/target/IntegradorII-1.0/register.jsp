<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('registerForm').addEventListener('submit', function(event) {
                let nombre = document.getElementById('nombre').value;
                let apellido = document.getElementById('apellido').value;
                let correo = document.getElementById('correo').value;
                let telefono = document.getElementById('telefono').value;
                let contrasena = document.getElementById('contrasena').value;

                if (nombre === '' || apellido === '' || correo === '' || telefono === '' || contrasena === '') {
                    event.preventDefault();
                    alert('Por favor, complete todos los campos.');
                }
            });
        });
    </script>
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="row w-100">
            <div class="col-md-4 mx-auto">
                <header class="mb-4">
                    <h1 class="text-center">Registro de Usuario</h1>
                </header>
                <section>
                    <form id="registerForm" action="RegistroServlet" method="POST" class="p-4 border rounded bg-white">
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <input type="text" id="nombre" name="nombre" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido">Apellido:</label>
                            <input type="text" id="apellido" name="apellido" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="correo">Correo:</label>
                            <input type="email" id="correo" name="correo" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="telefono">Teléfono:</label>
                            <input type="text" id="telefono" name="telefono" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="contrasena">Contraseña:</label>
                            <input type="password" id="contrasena" name="contrasena" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Registrar</button>
                    </form>
                    <div class="text-success my-2">
                        <c:if test="${not empty registroMessage}">
                            <p>${registroMessage}</p>
                        </c:if>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
