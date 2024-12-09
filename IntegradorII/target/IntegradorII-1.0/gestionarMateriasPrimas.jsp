<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Materias Primas</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="src/css/styles.css" rel="stylesheet"> <!-- Enlace al archivo CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
   <jsp:include page="includes/menuAdmin.jsp" />
   <jsp:include page="includes/header.jsp" />
<div class="content">
    <header class="my-4">
        <h1 class="text-center">Lista de Materias Primas</h1>
    </header>
    <section>
        <div class="mb-3">
            <!-- Botón para abrir la ventana modal de agregar materia prima -->
            <button class="btn btn-success" data-toggle="modal" data-target="#agregarMateriaPrimaModal">Agregar Materia Prima</button>
        </div>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Tipo</th>
                    <th>Cantidad</th>
                    <th>Tipo Materia</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="materia" items="${materiasPrimas}">
                    <tr>
                        <td>${materia.idMateriaPrima}</td>
                        <td>${materia.nombre}</td>
                        <td>${materia.tipo}</td>
                        <td>${materia.cantidad}</td>
                        <td>${materia.tipo_materia}</td>
                        <td>
                            <!-- Botones para modificar y eliminar -->
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modificarMateriaPrimaModal-${materia.idMateriaPrima}">Modificar</button>
                            <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#eliminarMateriaPrimaModal-${materia.idMateriaPrima}">Eliminar</button>
                        </td>
                    </tr>

                    <!-- Modal para modificar materia prima -->
<div class="modal fade" id="modificarMateriaPrimaModal-${materia.idMateriaPrima}" tabindex="-1" aria-labelledby="modificarMateriaPrimaModalLabel-${materia.idMateriaPrima}" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificarMateriaPrimaModalLabel-${materia.idMateriaPrima}">Modificar Materia Prima</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="MateriasPrimasServlet" method="POST">
                    <input type="hidden" name="id_materia_prima" value="${materia.idMateriaPrima}">
                    <input type="hidden" name="action" value="update"> <!-- Parámetro para actualizar materia prima -->
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${materia.nombre}" required>
                    </div>
                    <div class="form-group">
                        <label for="tipo">Tipo</label>
                        <input type="text" class="form-control" id="tipo" name="tipo" value="${materia.tipo}" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidad">Cantidad</label>
                        <input type="number" class="form-control" id="cantidad" name="cantidad" value="${materia.cantidad}" required>
                    </div>
                    <div class="form-group">
                        <label for="tipo_materia">Tipo Materia</label>
                        <select class="form-control" id="tipo_materia" name="tipo_materia" required>
                            <option value="reciclada" ${materia.tipo_materia == 'reciclada' ? 'selected' : ''}>Reciclada</option>
                            <option value="comprada" ${materia.tipo_materia == 'comprada' ? 'selected' : ''}>Comprada</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                </form>
            </div>
        </div>
    </div>
</div>

                    <!-- Modal para eliminar materia prima -->
<div class="modal fade" id="eliminarMateriaPrimaModal-${materia.idMateriaPrima}" tabindex="-1" aria-labelledby="eliminarMateriaPrimaModalLabel-${materia.idMateriaPrima}" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="eliminarMateriaPrimaModalLabel-${materia.idMateriaPrima}">Eliminar Materia Prima</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas eliminar la materia prima ${materia.nombre}?</p>
            </div>
            <div class="modal-footer">
                <form action="MateriasPrimasServlet" method="POST">
                    <input type="hidden" name="id_materia_prima" value="${materia.idMateriaPrima}">
                    <input type="hidden" name="action" value="delete"> <!-- Parámetro para eliminar materia prima -->
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

<!-- Modal para agregar materia prima -->
<div class="modal fade" id="agregarMateriaPrimaModal" tabindex="-1" aria-labelledby="agregarMateriaPrimaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="agregarMateriaPrimaModalLabel">Agregar Materia Prima</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="MateriasPrimasServlet" method="POST">
                    <input type="hidden" name="action" value="add"> <!-- Parámetro para agregar materia prima -->
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="form-group">
                        <label for="tipo">Tipo</label>
                        <input type="text" class="form-control" id="tipo" name="tipo" required>
                    </div>
                    <div class="form-group">
                        <label for="cantidad">Cantidad</label>
                        <input type="number" class="form-control" id="cantidad" name="cantidad" required>
                    </div>
                    <div class="form-group">
                        <label for="tipo_materia">Tipo Materia</label>
                        <select class="form-control" id="tipo_materia" name="tipo_materia" required>
                            <option value="reciclada">Reciclada</option>
                            <option value="comprada">Comprada</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success">Agregar Materia Prima</button>
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
