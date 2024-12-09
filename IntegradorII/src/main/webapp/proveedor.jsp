<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Proveedores</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />  
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Lista de Proveedores</h1>
        </header>
        <section>
            <div class="mb-3">
                <!-- Botón para abrir la ventana modal de agregar proveedor -->
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarProveedorModal">Agregar Proveedor</button>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Contacto</th>
                        <th>Teléfono</th>
                        <th>Dirección</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="proveedor" items="${proveedores}">
                        <tr>
                            <td>${proveedor.idProveedor}</td>
                            <td>${proveedor.nombre}</td>
                            <td>${proveedor.contacto}</td>
                            <td>${proveedor.telefono}</td>
                            <td>${proveedor.direccion}</td>
                            <td>
                                <!-- Botones para modificar y eliminar -->
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarProveedorModal-${proveedor.idProveedor}">Modificar</button>
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarProveedorModal-${proveedor.idProveedor}">Eliminar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar proveedor -->
                        <div class="modal fade" id="modificarProveedorModal-${proveedor.idProveedor}" tabindex="-1" aria-labelledby="modificarProveedorModalLabel-${proveedor.idProveedor}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarProveedorModalLabel-${proveedor.idProveedor}">Modificar Proveedor</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="ProveedorServlet" method="POST">
                                            <input type="hidden" name="idProveedor" value="${proveedor.idProveedor}">
                                            <input type="hidden" name="action" value="update"> <!-- Parámetro para actualizar proveedor -->
                                            <div class="form-group">
                                                <label for="nombre">Nombre</label>
                                                <input type="text" class="form-control" id="nombre" name="nombre" value="${proveedor.nombre}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="contacto">Contacto</label>
                                                <input type="text" class="form-control" id="contacto" name="contacto" value="${proveedor.contacto}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="telefono">Teléfono</label>
                                                <input type="text" class="form-control" id="telefono" name="telefono" value="${proveedor.telefono}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="direccion">Dirección</label>
                                                <input type="text" class="form-control" id="direccion" name="direccion" value="${proveedor.direccion}" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal para eliminar proveedor -->
                        <div class="modal fade" id="eliminarProveedorModal-${proveedor.idProveedor}" tabindex="-1" aria-labelledby="eliminarProveedorModalLabel-${proveedor.idProveedor}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="eliminarProveedorModalLabel-${proveedor.idProveedor}">Eliminar Proveedor</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>¿Estás seguro de que deseas eliminar al proveedor ${proveedor.nombre}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="ProveedorServlet" method="POST">
                                            <input type="hidden" name="idProveedor" value="${proveedor.idProveedor}">
                                            <input type="hidden" name="action" value="delete"> <!-- Parámetro para eliminar proveedor -->
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

    <!-- Modal para agregar proveedor -->
    <div class="modal fade" id="agregarProveedorModal" tabindex="-1" aria-labelledby="agregarProveedorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarProveedorModalLabel">Agregar Proveedor</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="ProveedorServlet" method="POST">
                        <input type="hidden" name="action" value="add"> <!-- Parámetro para agregar proveedor -->
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="contacto">Contacto</label>
                            <input type="text" class="form-control" id="contacto" name="contacto" required>
                        </div>
                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input type="text" class="form-control" id="telefono" name="telefono" required>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" required>
                        </div>
                        <button type="submit" class="btn btn-success">Agregar Proveedor</button>
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
