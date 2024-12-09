package Servlets;

import DAO.MateriaPrimaDAO;
import Entidad.MateriaPrima;
import conexion.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MateriasPrimasServlet")
public class MateriasPrimasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MateriaPrimaDAO materiaPrimaDAO;

    public void init() {
        Conexion conexion = new Conexion();
        Connection connection = conexion.conectar();
        this.materiaPrimaDAO = new MateriaPrimaDAO(connection);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            listMateriasPrimas(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    addMateriaPrima(request, response);
                    break;
                case "delete":
                    deleteMateriaPrima(request, response);
                    break;
                case "update":
                    updateMateriaPrima(request, response);
                    break;
                default:
                    listMateriasPrimas(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void updateMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id_materia_prima")); // Aquí es donde ocurre el error si el valor es nulo
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String tipo_materia = request.getParameter("tipo_materia");

        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setIdMateriaPrima(id);
        materiaPrima.setNombre(nombre);
        materiaPrima.setTipo(tipo);
        materiaPrima.setCantidad(cantidad);
        materiaPrima.setTipo_materia(tipo_materia);

        materiaPrimaDAO.actualizarMateriaPrima(materiaPrima);
        response.sendRedirect("MateriasPrimasServlet?action=list");
    }

    private void listMateriasPrimas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<MateriaPrima> listaMateriasPrimas = materiaPrimaDAO.obtenerMateriasPrimas();
        request.setAttribute("materiasPrimas", listaMateriasPrimas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("gestionarMateriasPrimas.jsp");
        dispatcher.forward(request, response);
    }

    private void addMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String tipo_materia = request.getParameter("tipo_materia");

        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNombre(nombre);
        materiaPrima.setTipo(tipo);
        materiaPrima.setCantidad(cantidad);
        materiaPrima.setTipo_materia(tipo_materia);

        materiaPrimaDAO.agregarMateriaPrima(materiaPrima);
        response.sendRedirect("MateriasPrimasServlet?action=list");
    }

    private void deleteMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id_materia_prima"));
        materiaPrimaDAO.eliminarMateriaPrima(id);
        response.sendRedirect("MateriasPrimasServlet?action=list");
    }
}
