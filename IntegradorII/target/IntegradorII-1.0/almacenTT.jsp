<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Movimientos de Almacén</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="includes/menuTrabajador.jsp" />
    <jsp:include page="includes/header.jsp" />
    <div class="content">
        <header class="my-4">
        <h1 class="text-center">Movimientos de Almacén</h1>
        </header>
        <section>
            <h2 class="my-4">Movimientos de Almacén - Pendientes</h2>
            <div class="mb-3">
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarMovimientoModal">Agregar Movimiento de Almacén</button>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Movimiento</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Fecha de Movimiento</th>
                        <th>Almacén</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movimiento" items="${movimientosPendientes}">
                        <tr>
                            <td>${movimiento.idMovimiento}</td>
                            <td>
                                <c:forEach var="producto" items="${productos}">
                                    <c:if test="${producto.idProducto == movimiento.idProducto}">
                                        ${producto.nombre}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${movimiento.cantidad}</td>
                            <td>${movimiento.fechaMovimiento}</td>
                            <td>
                                <c:forEach var="almacen" items="${almacenes}">
                                    <c:if test="${almacen.idAlmacen == movimiento.idAlmacen}">
                                        ${almacen.nombre}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${movimiento.estado}</td>
                            <td>
                                <form action="MovimientoAlmacenServlet" method="post">
                                    <input type="hidden" name="action" value="complete">
                                    <input type="hidden" name="idMovimiento" value="${movimiento.idMovimiento}">
                                    <c:if test="${movimiento.estado == 'Completada'}">
                                        <button type="button" class="btn btn-secondary btn-sm" disabled>Completada</button>
                                    </c:if>
                                    <c:if test="${movimiento.estado != 'Completada'}">
                                        <button type="submit" class="btn btn-success btn-sm">Marcar como Completada</button>
                                    </c:if>
                                </form>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarMovimientoModal-${movimiento.idMovimiento}">Modificar</button>
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarMovimientoModal-${movimiento.idMovimiento}">Eliminar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar movimiento -->
                        <div class="modal fade" id="modificarMovimientoModal-${movimiento.idMovimiento}" tabindex="-1" aria-labelledby="modificarMovimientoModalLabel-${movimiento.idMovimiento}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarMovimientoModalLabel-${movimiento.idMovimiento}">Modificar Movimiento de Almacén</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="MovimientoAlmacenServlet" method="POST">
                                            <input type="hidden" name="idMovimiento" value="${movimiento.idMovimiento}">
                                            <input type="hidden" name="action" value="update">
                                            <div class="form-group">
                                                <label for="idProducto">Producto</label>
                                                <select id="idProducto" name="idProducto" class="form-control">
                                                    <c:forEach var="producto" items="${productos}">
                                                        <option value="${producto.idProducto}" <c:if test="${producto.idProducto == movimiento.idProducto}">selected</c:if>>${producto.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="cantidad">Cantidad</label>
                                                <input type="number" class="form-control" id="cantidad" name="cantidad" value="${movimiento.cantidad}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="fechaMovimiento">Fecha de Movimiento</label>
                                                <input type="text" class="form-control datepicker" id="fechaMovimiento" name="fechaMovimiento" value="${movimiento.fechaMovimiento}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="idAlmacen">Almacén</label>
                                                <select id="idAlmacen" name="idAlmacen" class="form-control">
                                                    <c:forEach var="almacen" items="${almacenes}">
                                                        <option value="${almacen.idAlmacen}" <c:if test="${almacen.idAlmacen == movimiento.idAlmacen}">selected</c:if>>${almacen.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="estado">Estado</label>
                                                <select id="estado" name="estado" class="form-control">
                                                    <option value="Pendiente" <c:if test="${movimiento.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                                    <option value="Completada" <c:if test="${movimiento.estado == 'Completada'}">selected</c:if>>Completada</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </tbody>
            </table>
            <h2 class="my-4">Movimientos de Almacén - Completados</h2>
            
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Movimiento</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Fecha de Movimiento</th>
                        <th>Almacén</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movimiento" items="${movimientosCompletados}">
                        <tr>
                            <td>${movimiento.idMovimiento}</td>
                            <td>
                                <c:forEach var="producto" items="${productos}">
                                    <c:if test="${producto.idProducto == movimiento.idProducto}">
                                        ${producto.nombre}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${movimiento.cantidad}</td>
                            <td>${movimiento.fechaMovimiento}</td>
                            <td>
                                <c:forEach var="almacen" items="${almacenes}">
                                    <c:if test="${almacen.idAlmacen == movimiento.idAlmacen}">
                                        ${almacen.nombre}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${movimiento.estado}</td>
                            <td>
                                <form action="MovimientoAlmacenServlet" method="post">
                                    <input type="hidden" name="action" value="complete">
                                    <input type="hidden" name="idMovimiento" value="${movimiento.idMovimiento}">
                                    <c:if test="${movimiento.estado == 'Completada'}">
                                        <button type="button" class="btn btn-secondary btn-sm" disabled>Completada</button>
                                    </c:if>
                                    <c:if test="${movimiento.estado != 'Completada'}">
                                        <button type="submit" class="btn btn-success btn-sm">Marcar como Completada</button>
                                    </c:if>
                                </form>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarMovimientoModal-${movimiento.idMovimiento}">Modificar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar movimiento -->
                        <div class="modal fade" id="modificarMovimientoModal-${movimiento.idMovimiento}" tabindex="-1" aria-labelledby="modificarMovimientoModalLabel-${movimiento.idMovimiento}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarMovimientoModalLabel-${movimiento.idMovimiento}">Modificar Movimiento de Almacén</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="MovimientoAlmacenServlet" method="POST">
                                            <input type="hidden" name="idMovimiento" value="${movimiento.idMovimiento}">
                                            <input type="hidden" name="action" value="update">
                                            <div class="form-group">
                                                <label for="idProducto">Producto</label>
                                                <select id="idProducto" name="idProducto" class="form-control">
                                                    <c:forEach var="producto" items="${productos}">
                                                        <option value="${producto.idProducto}" <c:if test="${producto.idProducto == movimiento.idProducto}">selected</c:if>>${producto.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="cantidad">Cantidad</label>
                                                <input type="number" class="form-control" id="cantidad" name="cantidad" value="${movimiento.cantidad}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="fechaMovimiento">Fecha de Movimiento</label>
                                                <input type="text" class="form-control datepicker" id="fechaMovimiento" name="fechaMovimiento" value="${movimiento.fechaMovimiento}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="idAlmacen">Almacén</label>
                                                <select id="idAlmacen" name="idAlmacen" class="form-control">
                                                    <c:forEach var="almacen" items="${almacenes}">
                                                        <option value="${almacen.idAlmacen}" <c:if test="${almacen.idAlmacen == movimiento.idAlmacen}">selected</c:if>>${almacen.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="estado">Estado</label>
                                                <select id="estado" name="estado" class="form-control">
                                                    <option value="Pendiente" <c:if test="${movimiento.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                                    <option value="Completada" <c:if test="${movimiento.estado == 'Completada'}">selected</c:if>>Completada</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </tbody>
            </table>
        </section>
    </div>

    <!-- Modal para agregar movimiento -->
    <div class="modal fade" id="agregarMovimientoModal" tabindex="-1" aria-labelledby="agregarMovimientoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarMovimientoModalLabel">Agregar Movimiento de Almacén</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="MovimientoAlmacenServlet" method="POST">
                        <input type="hidden" name="action" value="add">
                        <div class="form-group">
                            <label for="idProducto">Producto</label>
                            <select id="idProducto" name="idProducto" class="form-control">
                                <c:forEach var="producto" items="${productos}">
                                    <option value="${producto.idProducto}">${producto.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="cantidad">Cantidad</label>
                            <input type="number" class="form-control" id="cantidad" name="cantidad" required>
                        </div>
                        <div class="form-group">
                            <label for="fechaMovimiento">Fecha de Movimiento:</label>
                            <input type="date" class="form-control" id="fechaMovimiento" name="fechaMovimiento" required>
                        </div>
                        <div class="form-group">
                            <label for="idAlmacen">Almacén</label>
                            <select id="idAlmacen" name="idAlmacen" class="form-control">
                                <c:forEach var="almacen" items="${almacenes}">
                                    <option value="${almacen.idAlmacen}">${almacen.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="estado">Estado</label>
                        <select id="estado" name="estado" class="form-control">
                            <option value="Pendiente">Pendiente</option>
                            <option value="Completada">Completada</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success">Agregar Movimiento</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>
<script>
    $(document).ready(function() {
        $('#calendar').fullCalendar({
            events: [
                <c:forEach var="orden" items="${ordenesProduccion}">
                    {
                        title: '${orden.tipoMaterial}',
                        start: '${orden.fecha}',
                        end: '${orden.fecha}', // Utiliza orden.fecha + tiempoEstimado para mostrar la duración
                        backgroundColor: '${orden.estado == "Completada" ? "#28a745" : "#ffc107"}', // Verde para completadas, amarillo para pendientes
                        borderColor: '${orden.estado == "Completada" ? "#28a745" : "#ffc107"}',
                        textColor: 'white'
                    },
                </c:forEach>
            ],
            eventRender: function(event, element) {
                // Cambiar el color del evento si está en curso
                var fechaInicio = moment(event.start);
                var fechaFin = fechaInicio.add(event.tiempoEstimado, 'days');
                if (moment().isBetween(fechaInicio, fechaFin, null, '[]') && event.backgroundColor !== "#28a745") {
                    event.backgroundColor = "#17a2b8"; // Color para eventos en curso
                    event.borderColor = "#17a2b8";
                }
            }
        });
    });
</script>
</body>
</html>
