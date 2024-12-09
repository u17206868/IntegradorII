<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Clientes</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Lista de Clientes</h1>
        </header>
        <section>
            <form action="ClientServlet" method="get">
                <input type="hidden" name="action" value="report">
                <div class="form-group col-md-12 align-self-end">
                    <button type="submit" class="btn btn-primary">Generar Informe PDF</button>
                </div>
            </form>
            <div class="mb-3">
                <!-- Botón para abrir la ventana modal de agregar cliente -->
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarClienteModal">Agregar Cliente</button>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Dirección</th>
                        <th>DNI</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cliente" items="${clientes}">
                        <tr>
                            <td>${cliente.idCliente}</td>
                            <td>${cliente.nombre}</td>
                            <td>${cliente.apellido}</td>
                            <td>${cliente.correo}</td>
                            <td>${cliente.telefono}</td>
                            <td>${cliente.direccion}</td>
                            <td>${cliente.dni}</td>
                            <td>
                                <!-- Botones para modificar y eliminar -->
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarClienteModal-${cliente.idCliente}">Modificar</button>
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarClienteModal-${cliente.idCliente}">Eliminar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar cliente -->
                        <div class="modal fade" id="modificarClienteModal-${cliente.idCliente}" tabindex="-1" aria-labelledby="modificarClienteModalLabel-${cliente.idCliente}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarClienteModalLabel-${cliente.idCliente}">Modificar Cliente</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="ClientServlet" method="POST">
                                            <input type="hidden" name="idCliente" value="${cliente.idCliente}">
                                            <input type="hidden" name="action" value="update"> <!-- Parámetro para actualizar cliente -->
                                            <div class="form-group">
                                                <label for="nombre">Nombre</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="${cliente.nombre}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="apellido">Apellido</label>
                                                <input type="text" class="form-control" id="apellido" name="apellido" value="${cliente.apellido}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="correo">Correo</label>
                                                <input type="email" class="form-control" id="correo" name="correo" value="${cliente.correo}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="telefono">Teléfono</label>
                                                <input type="text" class="form-control" id="telefono" name="telefono" value="${cliente.telefono}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="direccion">Dirección</label>
                                                <input type="text" class="form-control" id="direccion" name="direccion" value="${cliente.direccion}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="dni">DNI</label>
                                                <input type="text" class="form-control" id="dni" name="dni" value="${cliente.dni}" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal para eliminar cliente -->
                        <div class="modal fade" id="eliminarClienteModal-${cliente.idCliente}" tabindex="-1" aria-labelledby="eliminarClienteModalLabel-${cliente.idCliente}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="eliminarClienteModalLabel-${cliente.idCliente}">Eliminar Cliente</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>¿Estás seguro de que deseas eliminar al cliente ${cliente.nombre} ${cliente.apellido}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="ClientServlet" method="POST">
                                            <input type="hidden" name="idCliente" value="${cliente.idCliente}">
                                            <input type="hidden" name="action" value="delete"> <!-- Parámetro para eliminar cliente -->
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

    <!-- Modal para agregar cliente -->
    <div class="modal fade" id="agregarClienteModal" tabindex="-1" aria-labelledby="agregarClienteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarClienteModalLabel">Agregar Cliente</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="ClientServlet" method="POST">
                        <input type="hidden" name="action" value="add"> <!-- Parámetro para agregar cliente -->
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
                            <label for="direccion">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" required>
                        </div>
                        <div class="form-group">
                            <label for="dni">DNI</label>
                            <input type="text" class="form-control" id="dni" name="dni" required>
                        </div>
                        <button type="submit" class="btn btn-success">Agregar Cliente</button>
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