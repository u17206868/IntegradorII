<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movimientos de Almacén - Administrador</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
    <jsp:include page="includes/menuAdmin.jsp" />
    <jsp:include page="includes/header.jsp" />
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Movimientos de Almacén</h1>
        </header>
        <section>
            <!-- Formulario para generar PDF -->
            <h3 class="my-4">Generar Informe en PDF</h3>
            <form action="MovimientoAlmacenServlet" method="get">
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
            <h2 class="my-4">Movimientos de Almacén - Pendientes</h2>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Movimiento</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Fecha de Movimiento</th>
                        <th>Almacén</th>
                        <th>Estado</th>
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
                        </tr>
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        </section>
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
        