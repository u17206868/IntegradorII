package Servlets;

import DAO.ProveedorDAO;
import DAO.MateriaPrimaDAO;
import DAO.ProveedorMateriaPrimaDAO;
import Entidad.Proveedor;
import Entidad.MateriaPrima;
import Entidad.ProveedorMateriaPrima;
import Entidad.ProveedorMateriaPrimaDTO;
import conexion.Conexion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProveedorMateriaPrimaServlet")
public class ProveedorMateriaPrimaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProveedorMateriaPrimaDAO proveedorMateriaPrimaDAO;
    private ProveedorDAO proveedorDAO;
    private MateriaPrimaDAO materiaPrimaDAO;

    public void init() {
        Conexion conexion = new Conexion();
        proveedorMateriaPrimaDAO = new ProveedorMateriaPrimaDAO(conexion.conectar());
        proveedorDAO = new ProveedorDAO();
        materiaPrimaDAO = new MateriaPrimaDAO(conexion.conectar());
    }

    // Método para manejar la petición GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            listProveedoresMateriasPrimas(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Método para manejar la petición POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    addProveedorMateriaPrima(request, response);
                    break;
                case "delete":
                    deleteProveedorMateriaPrima(request, response);
                    break;
                case "update":
                    updateProveedorMateriaPrima(request, response);
                    break;
                default:
                    listProveedoresMateriasPrimas(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Método para agregar una nueva relación de proveedor y materia prima
    private void addProveedorMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int idProveedor = Integer.parseInt(request.getParameter("id_proveedor"));
        int idMateriaPrima = Integer.parseInt(request.getParameter("id_materia_prima"));
        double precio = Double.parseDouble(request.getParameter("precio"));
        int tiempoEntrega = Integer.parseInt(request.getParameter("tiempo_entrega"));

        ProveedorMateriaPrima proveedorMateriaPrima = new ProveedorMateriaPrima();
        proveedorMateriaPrima.setIdProveedor(idProveedor);
        proveedorMateriaPrima.setIdMateriaPrima(idMateriaPrima);
        proveedorMateriaPrima.setPrecio(precio);
        proveedorMateriaPrima.setTiempoEntrega(tiempoEntrega);

        proveedorMateriaPrimaDAO.addProveedorMateriaPrima(proveedorMateriaPrima);
        response.sendRedirect("ProveedorMateriaPrimaServlet");
    }

    // Método para eliminar una relación de proveedor y materia prima
    private void deleteProveedorMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        proveedorMateriaPrimaDAO.deleteProveedorMateriaPrima(id);
        response.sendRedirect("ProveedorMateriaPrimaServlet");
    }

    // Método para actualizar una relación de proveedor y materia prima
    private void updateProveedorMateriaPrima(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int idProveedor = Integer.parseInt(request.getParameter("id_proveedor"));
        int idMateriaPrima = Integer.parseInt(request.getParameter("id_materia_prima"));
        double precio = Double.parseDouble(request.getParameter("precio"));
        int tiempoEntrega = Integer.parseInt(request.getParameter("tiempo_entrega"));

        ProveedorMateriaPrima proveedorMateriaPrima = new ProveedorMateriaPrima();
        proveedorMateriaPrima.setId(id);
        proveedorMateriaPrima.setIdProveedor(idProveedor);
        proveedorMateriaPrima.setIdMateriaPrima(idMateriaPrima);
        proveedorMateriaPrima.setPrecio(precio);
        proveedorMateriaPrima.setTiempoEntrega(tiempoEntrega);

        proveedorMateriaPrimaDAO.updateProveedorMateriaPrima(proveedorMateriaPrima);
        response.sendRedirect("ProveedorMateriaPrimaServlet");
    }

    // Método para listar las relaciones de proveedores y materias primas
    private void listProveedoresMateriasPrimas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<ProveedorMateriaPrimaDTO> listaProveedoresMateriasPrimas = proveedorMateriaPrimaDAO.getAllProveedoresMateriasPrimas();
        List<Proveedor> proveedores = proveedorDAO.obtenerProveedores();
        List<MateriaPrima> materiasPrimas = materiaPrimaDAO.obtenerMateriasPrimas();

        request.setAttribute("proveedoresMateriasPrimas", listaProveedoresMateriasPrimas);
        request.setAttribute("proveedores", proveedores);
        request.setAttribute("materiasPrimas", materiasPrimas);

        RequestDispatcher dispatcher = request.getRequestDispatcher("materiasprimas_proveedores.jsp");
        dispatcher.forward(request, response);
    }
}
