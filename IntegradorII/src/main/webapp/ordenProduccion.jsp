<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Órdenes de Producción</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        .estado-pendiente {
            background-color: #f8d7da !important;
            color: #721c24 !important;
        }
        .estado-terminado {
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
            <h1 class="text-center">Órdenes de Producción</h1>
        </header>
        <h3 class="text-center">PRODUCTOS A PRODUCIR</h3>
        <section>
            <table class="table table-striped">
                <thead>
                    <tr>
                        
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Tiempo</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="detalle" items="${detallesPendientes}">
                        <tr>
                            
                            <td>${detalle.producto}</td>
                            <td>${detalle.cantidad}</td>
                            <td>${detalle.tiempo}</td>
                            
                            <td>
                                <select class="form-control estado-selector" data-id="${detalle.idDetalle}">
                                    <option value="Pendiente" class="estado-pendiente" <c:if test="${detalle.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                    <option value="Terminado" class="estado-terminado" <c:if test="${detalle.estado == 'Terminado'}">selected</c:if>>Terminado</option>
                                </select>
                            </td>
                            <td>
                                <button class="btn btn-primary btn-sm actualizar-estado" data-id="${detalle.idDetalle}">Actualizar</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 class="text-center">ORDENES</h3>
            <div class="mb-3">
                <!-- Botón para abrir la ventana modal de agregar orden -->
                <button class="btn btn-success" data-toggle="modal" data-target="#agregarOrdenModal">Agregar Orden de Producción</button>
                <a href="UtilServlet?action=ordenProd_exportar_pdf" target="_blank" class="btn btn-danger">
                    <i class="fas fa-file-pdf"></i> Exportar PDF
                </a>

                <button class="btn btn-primary" data-toggle="modal" data-target="#enviarCorreoModal">
                    <i class="fas fa-envelope"></i> Enviar por Correo
                </button>
            </div>
            
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tipo de Material</th>
                        <th>Trabajador</th>
                        <th>Tiempo Estimado (días)</th>
                        <th>Fecha</th>
                        <th>Estado</th> <!-- Nuevo campo estado -->
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orden" items="${ordenesProduccion}">
                        <tr>
                            <td>${orden.idOrden}</td>
                            <td>
                                <c:forEach var="materia" items="${materiasPrimas}">
                                    <c:if test="${materia.idMateriaPrima == orden.tipoMaterial}">
                                        ${materia.nombre}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="trabajador" items="${trabajadores}">
                                    <c:if test="${trabajador.idUsuario == orden.trabajador}">
                                        ${trabajador.nombre} ${trabajador.apellido}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${orden.tiempoEstimado}</td>
                            <td>${orden.fecha}</td>
                            <td>${orden.estado}</td>
                            <td>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarOrdenModal-${orden.idOrden}">Modificar</button>
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarOrdenModal-${orden.idOrden}">Eliminar</button>
                            </td>
                        </tr>

                        <!-- Modal para modificar orden -->
                        <div class="modal fade" id="modificarOrdenModal-${orden.idOrden}" tabindex="-1" aria-labelledby="modificarOrdenModalLabel-${orden.idOrden}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modificarOrdenModalLabel-${orden.idOrden}">Modificar Orden de Producción</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="OrdenProduccionServlet" method="POST">
                                            <input type="hidden" name="idOrden" value="${orden.idOrden}">
                                            <input type="hidden" name="action" value="update"> <!-- Parámetro para actualizar orden -->
                                            <div class="form-group">
                                                <label for="tipoMaterial">Tipo de Material</label>
                                                <select id="tipoMaterial" name="tipoMaterial" class="form-control">
                                                    <c:forEach var="materia" items="${materiasPrimas}">
                                                        <option value="${materia.idMateriaPrima}" <c:if test="${materia.idMateriaPrima == orden.tipoMaterial}">selected</c:if>>${materia.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="trabajador">Trabajador</label>
                                                <select id="trabajador" name="trabajador" class="form-control">
                                                    <c:forEach var="trabajador" items="${trabajadores}">
                                                        <option value="${trabajador.idUsuario}" <c:if test="${trabajador.idUsuario == orden.trabajador}">selected</c:if>>${trabajador.nombre} ${trabajador.apellido}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="tiempoEstimado">Tiempo Estimado (días)</label>
                                                <input type="number" class="form-control" id="tiempoEstimado" name="tiempoEstimado" value="${orden.tiempoEstimado}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="fecha">Fecha</label>
                                                <input type="text" class="form-control datepicker" id="fecha" name="fecha" value="${orden.fecha}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="estado">Estado</label>
                                                <select id="estado" name="estado" class="form-control">
                                                    <option value="Pendiente" <c:if test="${orden.estado == 'Pendiente'}">selected</c:if>>Pendiente</option>
                                                    <option value="Completada" <c:if test="${orden.estado == 'Completada'}">selected</c:if>>Completada</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal para eliminar orden -->
                        <div class="modal fade" id="eliminarOrdenModal-${orden.idOrden}" tabindex="-1" aria-labelledby="eliminarOrdenModalLabel-${orden.idOrden}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="eliminarOrdenModalLabel-${orden.idOrden}">Eliminar Orden de Producción</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>¿Estás seguro de que deseas eliminar la orden de producción ${orden.idOrden}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="OrdenProduccionServlet" method="POST">
                                            <input type="hidden" name="idOrden" value="${orden.idOrden}">
                                            <input type="hidden" name="action" value="delete"> <!-- Parámetro para eliminar orden -->
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
            <h3 class="my-4">Generar Informe en PDF</h3>
            <form action="OrdenProduccionServlet" method="get">
                <input type="hidden" name="action" value="report">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="fechaInicio">Fecha Inicio</label>
                        <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="fechaFin">Fecha Fin</label>
                        <input type="date" class="form-control" id="fechaFin" name="fechaFin" required>
                    </div>
                    <div class="form-group col-md-2 align-self-end">
                        <button type="submit" class="btn btn-primary">Generar Informe PDF</button>
                    </div>
                </div>
            </form>
        </section>
    </div>

    <!-- Modal para agregar orden -->
    <div class="modal fade" id="agregarOrdenModal" tabindex="-1" aria-labelledby="agregarOrdenModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="agregarOrdenModalLabel">Agregar Orden de Producción</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="OrdenProduccionServlet" method="POST">
                        <input type="hidden" name="action" value="add"> <!-- Parámetro para agregar orden -->
                        <div class="form-group">
                            <label for="tipoMaterial">Tipo de Material</label>
                            <select id="tipoMaterial" name="tipoMaterial" class="form-control">
                                <c:forEach var="materia" items="${materiasPrimas}">
                                    <option value="${materia.idMateriaPrima}">${materia.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="trabajador">Trabajador</label>
                            <select id="trabajador" name="trabajador" class="form-control">
                                <c:forEach var="trabajador" items="${trabajadores}">
                                    <option value="${trabajador.idUsuario}">${trabajador.nombre} ${trabajador.apellido}</option>
                                </c:forEach>
                            </select>
                        </div>
                                                <div class="form-group">
                            <label for="tiempoEstimado">Tiempo Estimado (días)</label>
                            <input type="number" class="form-control" id="tiempoEstimado" name="tiempoEstimado" required>
                        </div>
                        <div class="form-group">
                            <label for="fecha">Fecha</label>
                            <input type="text" class="form-control datepicker" id="fecha" name="fecha" required>
                        </div>
                        <div class="form-group">
                            <label for="estado">Estado</label> <!-- Nuevo campo estado -->
                            <select id="estado" name="estado" class="form-control">
                                <option value="Pendiente">Pendiente</option>
                                <option value="Completada">Completada</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success">Agregar Orden</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="enviarCorreoModal" tabindex="-1" aria-labelledby="enviarCorreoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="enviarCorreoModalLabel">Enviar Orden por Correo</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="UtilServlet" method="POST">
                        <div class="form-group">
                            <label for="correo">Correo Electrónico</label>
                            <input value="${sessionScope.usuario.correo}" type="email" class="form-control" id="correo" 
                                   name="correo" placeholder="Ingrese el correo electrónico" required>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            <input type="hidden" name="action" value="enviarCorreo_ordenProd">
                            <i class="fas fa-envelope"></i> Enviar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(function() {
            $(".datepicker").datepicker({
                dateFormat: "yy-mm-dd"
            });
        });
    </script>
    <!-- JavaScript para manejar el cambio de estado -->
    <script>
        $(document).ready(function() {
            $('.actualizar-estado').click(function() {
                var idDetalle = $(this).data('id');
                var estado = $('.estado-selector[data-id="' + idDetalle + '"]').val();

                if (estado === 'Terminado') {
                    $.ajax({
                        url: 'OrdenProduccionServlet',
                        method: 'POST',
                        data: {
                            action: 'changeDetalleStatus',
                            idDetalle: idDetalle,
                            estado: estado
                        },
                        success: function(response) {
                            console.log('Estado actualizado correctamente');
                            location.reload();
                        },
                        error: function(xhr, status, error) {
                            console.error('Error al cambiar el estado:', error);
                        }
                    });
                }
            });

            function actualizarEstiloEstado(selector, estado) {
                selector.removeClass('estado-pendiente estado-terminado');
                if (estado === 'Pendiente') {
                    selector.addClass('estado-pendiente');
                } else if (estado === 'Terminado') {
                    selector.addClass('estado-terminado');
                }
            }
        });
    </script>
</body>
</html>
     