<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Usuarios</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Lista de Usuarios</h1>
        </header>
        <section>
            <div class="mb-3">
                <!-- Botón para abrir la ventana modal de agregar usuario -->
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarUsuarioModal">Agregar Usuario</button>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Rol</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr>
                            <td>${usuario.idUsuario}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.apellido}</td>
                            <td>${usuario.correo}</td>
                            <td>${usuario.telefono}</td>
                            <td>${usuario.rol}</td>
                            <td>
                                <!-- Botones para modificar y eliminar -->
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarUsuarioModal-${usuario.idUsuario}">Modificar</button>
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarUsuarioModal-${usuario.idUsuario}">Eliminar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar usuario -->
                        <div class="modal fade" id="modificarUsuarioModal-${usuario.idUsuario}" tabindex="-1" aria-labelledby="modificarUsuarioModalLabel-${usuario.idUsuario}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarUsuarioModalLabel-${usuario.idUsuario}">Modificar Usuario</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="UserServlet" method="POST">
                                            <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                                            <input type="hidden" name="action" value="update"> <!-- Parámetro para actualizar usuario -->
                                            <div class="form-group">
                                                <label for="nombre">Nombre</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="apellido">Apellido</label>
                                                <input type="text" class="form-control" id="apellido" name="apellido" value="${usuario.apellido}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="correo">Correo</label>
                                                <input type="email" class="form-control" id="correo" name="correo" value="${usuario.correo}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="telefono">Teléfono</label>
                                                <input type="text" class="form-control" id="telefono" name="telefono" value="${usuario.telefono}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="rol">Rol</label>
                                                <select class="form-control" id="rol" name="rol" required>
                                                    <c:forEach var="rol" items="${roles}">
                                                        <option value="${rol.idRol}" <c:if test="${usuario.idRol == rol.idRol}">selected</c:if>>${rol.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal para eliminar usuario -->
                        <div class="modal fade" id="eliminarUsuarioModal-${usuario.idUsuario}" tabindex="-1" aria-labelledby="eliminarUsuarioModalLabel-${usuario.idUsuario}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="eliminarUsuarioModalLabel-${usuario.idUsuario}">Eliminar Usuario</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>¿Estás seguro de que deseas eliminar al usuario ${usuario.nombre} ${usuario.apellido}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="UserServlet" method="POST">
                                            <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                                            <input type="hidden" name="action" value="delete"> <!-- Parámetro para eliminar usuario -->
                                            <button type="submit" class="btn btn-danger">Eliminar</button>
                                        </form>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </div>

    <!-- Modal para agregar usuario -->
    <div class="modal fade" id="agregarUsuarioModal" tabindex="-1" aria-labelledby="agregarUsuarioModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarUsuarioModalLabel">Agregar Usuario</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="UserServlet" method="POST">
                        <input type="hidden" name="action" value="add"> <!-- Parámetro para agregar usuario -->
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido">Apellido</label>
                            <input type="text" class="form-control" id="apellido" name="apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="correo">Correo</label>
                            <input type="email" class="form-control" id="correo" name="correo" required>
                        </div>
                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input type="text" class="form-control" id="telefono" name="telefono" required>
                        </div>
                        <div class="form-group">
                            <label for="rol">Rol</label>
                            <select class="form-control" id="rol" name="rol" required>
                                <c:forEach var="rol" items="${roles}">
                                    <option value="${rol.idRol}">${rol.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="contrasena">Contraseña</label>
                            <input type="password" class="form-control" id="contrasena" name="contrasena" required>
                        </div>
                        <button type="submit" class="btn btn-success">Agregar Usuario</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

