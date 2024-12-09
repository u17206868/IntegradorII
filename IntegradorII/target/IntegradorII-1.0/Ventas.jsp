<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Ventas</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        .estado-pendiente {
            background-color: #f8d7da !important;
            color: #721c24 !important;
        }
        .estado-enproceso {
            background-color: #fff3cd !important;
            color: #856404 !important;
        }
        .estado-completada {
            background-color: #d4edda !important;
            color: #155724 !important;
        }
    </style>
</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />

    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Lista de Ventas</h1>
        </header>

        <section>
            <div class="mb-3">
                <!-- Bot�n para abrir la ventana modal de agregar proveedor -->
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarEditarVentaModal">Agregar Venta</button>
                <a href="UtilServlet?action=venta_exportar_pdf" target="_blank" class="btn btn-danger">
                    <i class="fas fa-file-pdf"></i> Exportar PDF
                </a>

            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Venta</th>
                        <th>DNI Cliente</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>Subtotal</th>
                        <th>IGV</th>
                        <th>Total</th>
                        <th>Fecha Venta</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="venta" items="${listVenta}">
                        <tr>
                            <td>${venta.idVenta}</td>
                            <td>
                                <c:forEach var="cliente" items="${listClientes}">
                                    <c:if test="${cliente.dni == venta.dniCliente}">
                                        ${cliente.nombre} ${cliente.apellido}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${venta.producto}</td>
                            <td>${venta.cantidad}</td>
                            <td>${venta.precio}</td>
                            <td>${venta.subtotal}</td>
                            <td>${venta.igv}</td>
                            <td>${venta.total}</td>
                            <td>${venta.fechaVenta}</td>
                            <td>
                                <select name="estado" class="form-control estado-selector" data-id="${venta.idVenta}">
                                    <option value="Pendiente" <c:if test="${venta.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                    <option value="En Proceso" <c:if test="${venta.estado == 'En Proceso'}">selected</c:if>>En Proceso</option>
                                    <option value="Completada" <c:if test="${venta.estado == 'Completada'}">selected</c:if>>Completada</option>
                                </select>
                            </td>
                            <td>
                                <!-- Botones para modificar y eliminar -->
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarVentaModal-${venta.idVenta}">Modificar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar venta -->
                        <div class="modal fade" id="modificarVentaModal-${venta.idVenta}" tabindex="-1" aria-labelledby="modificarVentaModalLabel-${venta.idVenta}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarVentaModalLabel-${venta.idVenta}">Modificar Venta</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="VentaServlet" method="POST">
                                            <input type="hidden" name="idVenta" value="${venta.idVenta}">
                                            <input type="hidden" name="action" value="update"> <!-- Par�metro para actualizar venta -->
                                            <div class="form-group">
                                                <label for="dniCliente">DNI Cliente</label>
                                                <input type="text" class="form-control" id="dniCliente" name="dniCliente" value="${venta.dniCliente}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="producto">Producto</label>
                                                <input type="text" class="form-control" id="producto" name="producto" value="${venta.producto}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="cantidad">Cantidad</label>
                                                <input type="number" class="form-control" id="cantidad" name="cantidad" value="${venta.cantidad}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="precio">Precio</label>
                                                <input type="text" class="form-control" id="precio" name="precio" value="${venta.precio}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="estadoDetalle">Estado del Detalle</label>
                                                <select id="estadoDetalle" name="estadoDetalle" class="form-control" required>
                                                    <option value="Pendiente" <c:if test="${venta.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                                    <option value="En Proceso" <c:if test="${venta.estado == 'En Proceso'}">selected</c:if>>En Proceso</option>
                                                    <option value="Completada" <c:if test="${venta.estado == 'Completada'}">selected</c:if>>Completada</option>
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

    <!-- Modal para agregar -->
    <div class="modal fade" id="agregarEditarVentaModal" tabindex="-1" aria-labelledby="agregarEditarVentaModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarEditarVentaModalLabel">Agregar Venta</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="VentaServlet" method="post">
                        <input type="hidden" name="action" value="insert"> <!-- Par�metro para agregar venta -->
                        <div class="form-group">
                            <label for="cliente">Cliente:</label>
                            <select name="idCliente" id="cliente" class="form-control" required>
                                <c:forEach var="cliente" items="${listClientes}">
                                    <option value="${cliente.idCliente}">${cliente.nombre} ${cliente.apellido}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="producto">Producto:</label>
                            <select name="idProducto" id="producto" class="form-control" required>
                                <c:forEach var="producto" items="${listProductos}">
                                    <option value="${producto.idProducto}">${producto.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="cantidad">Cantidad:</label>
                            <input type="number" name="cantidad" id="cantidad" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label for="tiempo">Tiempo:</label>
                            <input type="number" name="tiempo" id="tiempo" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label for="precio">Precio:</label>
                            <input type="number" name="precio" id="precio" class="form-control" required/>
                        </div>
                         <div class="form-group">
                            <label for="fechaVenta">Fecha Venta:</label>
                            <input type="date" name="fechaVenta" id="fechaVenta" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label for="estado">Estado</label>
                            <select name="estado" id="estado" class="form-control estado-selector-agregar">
                                <option value="Pendiente" selected>Pendiente</option>
                                <option value="En Proceso">En Proceso</option>
                                <option value="Completada">Completada</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Agregar Venta</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript para manejar el cambio de estado -->
    <script>
        $(document).ready(function() {
            // Cargar el estado inicial desde la base de datos y aplicar estilos
            $('.estado-selector').each(function() {
                var idVenta = $(this).data('id');
                var selector = $(this);

                $.ajax({
                    url: 'VentaServlet',
                    method: 'GET',
                    data: {
                        action: 'getStatus',
                        idVenta: idVenta
                    },
                    success: function(response) {
                        selector.val(response.estado);
                        actualizarEstiloEstado(selector, response.estado);
                    },
                    error: function(xhr, status, error) {
                        console.error('Error al obtener el estado:', error);
                    }
                });
            });

            // Manejar el cambio de estado con AJAX para cada selector
            $('.estado-selector').change(function() {
                var idVenta = $(this).data('id');
                var estado = $(this).val();

                $.ajax({
                    url: 'VentaServlet',
                    method: 'GET',
                    data: {
                        action: 'changeStatus',
                        idVenta: idVenta,
                        estado: estado
                    },
                    success: function(response) {
                        actualizarEstiloEstado($(this), estado);
                        console.log('Estado actualizado correctamente');
                    }.bind(this),
                    error: function(xhr, status, error) {
                        console.error('Error al cambiar el estado:', error);
                    }
                });
            });

            // Aplicar estilos para el estado en el modal de agregar
            $('.estado-selector-agregar').change(function() {
                var estado = $(this).val();
                actualizarEstiloEstado($(this), estado);
            });

            function actualizarEstiloEstado(selector, estado) {
                selector.removeClass('estado-pendiente estado-enproceso estado-completada');
                if (estado === 'Pendiente') {
                    selector.addClass('estado-pendiente');
                } else if (estado === 'En Proceso') {
                    selector.addClass('estado-enproceso');
                } else if (estado === 'Completada') {
                    selector.addClass('estado-completada');
                }
            }

            // Aplicar estilos iniciales para el modal de agregar
            actualizarEstiloEstado($('.estado-selector-agregar'), $('.estado-selector-agregar').val());
        });
    </script>
</body>
</html>
                               