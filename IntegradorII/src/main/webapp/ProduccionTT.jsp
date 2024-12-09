<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Órdenes de Producción del Trabajador</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

</head>
<body>
    <jsp:include page="includes/menuTrabajador.jsp" />
     <jsp:include page="includes/header.jsp" />
    <div class="content">
        <header class="my-4">
            <h1 class="text-center">Órdenes de Producción</h1>
        </header>
        <section>
            <div id="calendar"></div>
            <a href="UtilServlet?action=ordenProd_exportar_pdf" target="_blank" class="btn btn-danger">
                <i class="fas fa-file-pdf"></i> Exportar PDF
            </a>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tipo de Material</th>
                        <th>Tiempo Estimado (días)</th>
                        <th>Fecha</th>
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
                            <td>${orden.tiempoEstimado}</td>
                            <td>${orden.fecha}</td>
                            <td>
                                <form action="OrdenProduccionServlet" method="post">
                                    <input type="hidden" name="action" value="complete">
                                    <input type="hidden" name="idOrden" value="${orden.idOrden}">
                                    <c:if test="${orden.estado == 'Completada'}">
                                        <button type="button" class="btn btn-secondary btn-sm" disabled>Completada</button>
                                    </c:if>
                                    <c:if test="${orden.estado != 'Completada'}">
                                        <button type="submit" class="btn btn-success btn-sm">Marcar como Completada</button>
                                    </c:if>
                                </form>
                            </td>
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
