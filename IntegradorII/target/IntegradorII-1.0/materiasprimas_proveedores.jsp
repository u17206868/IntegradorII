<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Proveedores y Materias Primas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <h1>Lista de Proveedores y Materias Primas</h1>
    
    <h2>Agregar Proveedor o Materia Prima</h2>
    <form action="ProveedorMateriaPrimaServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label for="id_proveedor">Proveedor:</label>
        <select name="id_proveedor" required>
            <c:forEach var="proveedor" items="${proveedores}">
                <option value="${proveedor.id}">${proveedor.nombre}</option>
            </c:forEach>
        </select>
        <label for="id_materia_prima">Materia Prima:</label>
        <select name="id_materia_prima" required>
            <c:forEach var="materiaPrima" items="${materiasPrimas}">
                <option value="${materiaPrima.id}">${materiaPrima.nombre}</option>
            </c:forEach>
        </select>
        <label for="precio">Precio:</label>
        <input type="number" step="0.01" name="precio" required>
        <label for="tiempo_entrega">Tiempo de Entrega (días):</label>
        <input type="number" name="tiempo_entrega" required>
        <button type="submit">Agregar</button>
    </form>

    <h2>Proveedores y Materias Primas</h2>
    <table border="1">
        <tr>
            <th>Proveedor</th>
            <th>Materia Prima</th>
            <th>Precio</th>
            <th>Tiempo de Entrega (días)</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="proveedorMateriaPrima" items="${proveedoresMateriasPrimas}">
            <tr>
                <td>${proveedorMateriaPrima.nombreProveedor}</td>
                <td>${proveedorMateriaPrima.nombreMateriaPrima}</td>
                <td>${proveedorMateriaPrima.precio}</td>
                <td>${proveedorMateriaPrima.tiempoEntrega}</td>
                <td>
                    <form action="ProveedorMateriaPrimaServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${proveedorMateriaPrima.id}">
                        <button type="submit">Eliminar</button>
                    </form>
                    <form action="ProveedorMateriaPrimaServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${proveedorMateriaPrima.id}">
                        <input type="hidden" name="id_proveedor" value="${proveedorMateriaPrima.idProveedor}">
                        <input type="hidden" name="id_materia_prima" value="${proveedorMateriaPrima.idMateriaPrima}">
                        <input type="number" step="0.01" name="precio" value="${proveedorMateriaPrima.precio}" required>
                        <input type="number" name="tiempo_entrega" value="${proveedorMateriaPrima.tiempoEntrega}" required>
                        <button type="submit">Modificar</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
