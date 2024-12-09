<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Órdenes de Sellado</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />
<div class="content">
    <header class="my-4">
        <h1 class="text-center">Órdenes de Sellado</h1>
    </header>
    <section>
        <h3 class="my-4">Generar Informe en PDF</h3>
        <form action="OrdenSelladoServlet" method="get">
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
    <div class="mb-3">
        <!-- Botón para abrir la ventana modal de agregar orden -->
        <button class="btn btn-success" data-toggle="modal" data-target="#agregarOrdenModal">Agregar Orden de Sellado</button>
    </div>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>ID Orden de Producción</th>
                <th>Usuario</th>
                <th>Medida Bolsa</th>
                <th>Cantidad a Sellar</th>
                <th>Fecha Orden</th>
                <th>Estado</th>
                <th>Tiempo Estimado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="orden" items="${ordenesSellado}">
                <tr>
                    <td>${orden.idOrdenSellado}</td>
                    <td>${orden.idOrden}</td>
                    <td>
                        <c:forEach var="trabajador" items="${trabajadores}">
                            <c:if test="${trabajador.idUsuario == orden.idUsuario}">
                                ${trabajador.nombre} ${trabajador.apellido}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${orden.medidaBolsa}</td>
                    <td>${orden.cantidadASellar}</td>
                    <td>${orden.fechaOrden}</td>
                    <td>${orden.estado}</td>
                    <td>${orden.tiempoEstimado}</td>
                    <td>
                        <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalModificar${orden.idOrdenSellado}">Modificar</button>
                        <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modalBorrar${orden.idOrdenSellado}">Borrar</button>
                    </td>
                </tr>
<!-- Modal Modificar -->
<div class="modal fade" id="modalModificar${orden.idOrdenSellado}" tabindex="-1" aria-labelledby="modalModificarLabel${orden.idOrdenSellado}" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalModificarLabel${orden.idOrdenSellado}">Modificar Orden de Sellado</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="OrdenSelladoServlet" method="post">
                    <input type="hidden" name="idOrdenSellado" value="${orden.idOrdenSellado}">
                    <input type="hidden" name="action" value="update">
                    <div class="form-group">
                        <label for="idOrden">ID de Orden de Producción:</label>
                        <select id="idOrden" name="idOrden" class="form-control">
                            <c:forEach var="ordenProduccion" items="${listaOrdenesProduccion}">
                                <option value="${ordenProduccion.idOrden}" <c:if test="${ordenProduccion.idOrden == orden.idOrden}">selected</c:if>>${ordenProduccion.idOrden} ${ordenProduccion.fecha}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="trabajador">Trabajador</label>
                        <select id="trabajador" name="trabajador" class="form-control">
                            <c:forEach var="trabajador" items="${trabajadores}">
                                <option value="${trabajador.idUsuario}" <c:if test="${trabajador.idUsuario == orden.idUsuario}">selected</c:if>>${trabajador.nombre} ${trabajador.apellido}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="medidaBolsa">Medida de la Bolsa:</label>
                        <input type="text" class="form-control" id="medidaBolsa" name="medidaBolsa" value="${orden.medidaBolsa}" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidadASellar">Cantidad a Sellar:</label>
                        <input type="text" class="form-control" id="cantidadASellar" name="cantidadASellar" value="${orden.cantidadASellar}">
                    </div>
                    <div class="form-group">
                        <label for="fechaOrden">Fecha de la Orden:</label>
                        <input type="text" class="form-control" id="fechaOrden" name="fechaOrden" value="${orden.fechaOrden}">
                    </div>
                    <div class="form-group">
                        <label for="tiempoEstimado">Tiempo Estimado:</label>
                        <input type="text" class="form-control" id="tiempoEstimado" name="tiempoEstimado" value="${orden.tiempoEstimado}">
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


                <!-- Modal Borrar -->
                <div class="modal fade" id="modalBorrar${orden.idOrdenSellado}" tabindex="-1" aria-labelledby="modalBorrarLabel${orden.idOrdenSellado}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="modalBorrarLabel${orden.idOrdenSellado}">Borrar Orden de Sellado</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                ¿Estás seguro de que deseas borrar esta orden de sellado?
                            </div>
                            <div class="modal-footer">
                                <form action="OrdenSelladoServlet" method="post">
                                    <input type="hidden" name="idOrdenSellado" value="${orden.idOrdenSellado}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn btn-danger">Borrar</button>
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

<!-- Modal Agregar -->
<div class="modal fade" id="agregarOrdenModal" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalAgregarLabel">Agregar Orden de Sellado</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="OrdenSelladoServlet" method="post">
                    <input type="hidden" name="action" value="insert">
                    <div class="form-group">
                        <label for="idOrden">ID de Orden de Producción:</label>                       
                        <select id="idOrden" name="idOrden" class="form-control" required>
                            <c:forEach var="orden" items="${listaOrdenesProduccion}">
                                <c:if test="${orden.estado == 'Completada'}">
                                    <option value="${orden.idOrden}">${orden.idOrden} ${orden.fecha}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="trabajador">Trabajador</label>
                        <select id="trabajador" name="trabajador" class="form-control" required>
                            <c:forEach var="trabajador" items="${trabajadores}">
                                <option value="${trabajador.idUsuario}">${trabajador.nombre} ${trabajador.apellido}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="medidaBolsa">Medida de la Bolsa:</label>
                        <input type="text" class="form-control" id="medidaBolsa" name="medidaBolsa" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidadASellar">Cantidad a Sellar:</label>
                        <input type="number" class="form-control" id="cantidadASellar" name="cantidadASellar" required>
                    </div>
                    <div class="form-group">
                        <label for="fechaOrden">Fecha de la Orden:</label>
                        <input type="date" class="form-control" id="fechaOrden" name="fechaOrden" required>
                    </div>
                    <div class="form-group">
                        <label for="tiempoEstimado">Tiempo Estimado:</label>
                        <input type="number" class="form-control" id="tiempoEstimado" name="tiempoEstimado" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Agregar Orden</button>
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
</body>
</html>
                            