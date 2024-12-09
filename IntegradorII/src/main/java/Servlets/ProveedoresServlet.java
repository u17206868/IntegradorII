package Servlets;

import DAO.ProveedorDAO;
import Entidad.Proveedor;
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

@WebServlet("/ProveedoresServlet")
public class ProveedoresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProveedorDAO proveedorDAO;

    public void init() {
        proveedorDAO = new ProveedorDAO();
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        listProveedores(request, response);
    } catch (SQLException e) {
        throw new ServletException(e);
    }
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    try {
        switch (action) {
            case "add":
                addProveedor(request, response);
                break;
            case "delete":
                deleteProveedor(request, response);
                break;
            case "update":
                updateProveedor(request, response);
                break;
            default:
                listProveedores(request, response);
                break;
        }
    } catch (SQLException e) {
        throw new ServletException(e);
    }
}


    private void addProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setContacto(contacto);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);

        proveedorDAO.agregarProveedor(proveedor);
        response.sendRedirect("proveedores.jsp");
    }

    private void deleteProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id_proveedor"));
        proveedorDAO.eliminarProveedor(id);
        response.sendRedirect("proveedores.jsp");
    }

    private void updateProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id_proveedor"));
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(id);
        proveedor.setNombre(nombre);
        proveedor.setContacto(contacto);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);

        proveedorDAO.actualizarProveedor(proveedor);
        response.sendRedirect("proveedores.jsp");
    }

    private void listProveedores(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Proveedor> listaProveedores = proveedorDAO.obtenerProveedores();
        request.setAttribute("proveedores", listaProveedores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("proveedores.jsp");
        dispatcher.forward(request, response);
    }
    
}
