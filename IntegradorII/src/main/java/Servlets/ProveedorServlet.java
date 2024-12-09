package Servlets;

import DAO.ProveedorDAO;
import Entidad.Proveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Proveedor> proveedores = proveedorDAO.obtenerProveedores();
        request.setAttribute("proveedores", proveedores);
        request.getRequestDispatcher("proveedor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                agregarProveedor(request, response);
                break;
            case "update":
                modificarProveedor(request, response);
                break;
            case "delete":
                eliminarProveedor(request, response);
                break;
            default:
                doGet(request, response);
                break;
        }
    }

    private void agregarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setNombre(nombre);
        nuevoProveedor.setContacto(contacto);
        nuevoProveedor.setTelefono(telefono);
        nuevoProveedor.setDireccion(direccion);

        if (proveedorDAO.agregarProveedor(nuevoProveedor)) {
            response.sendRedirect("ProveedorServlet");
        } else {
            request.setAttribute("error", "Error al agregar el proveedor");
            request.getRequestDispatcher("proveedor.jsp").forward(request, response);
        }
    }

    private void modificarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        Proveedor proveedorModificado = new Proveedor();
        proveedorModificado.setIdProveedor(idProveedor);
        proveedorModificado.setNombre(nombre);
        proveedorModificado.setContacto(contacto);
        proveedorModificado.setTelefono(telefono);
        proveedorModificado.setDireccion(direccion);

        if (proveedorDAO.actualizarProveedor(proveedorModificado)) {
            response.sendRedirect("ProveedorServlet");
        } else {
            request.setAttribute("error", "Error al modificar el proveedor");
            request.getRequestDispatcher("proveedor.jsp").forward(request, response);
        }
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));

        if (proveedorDAO.eliminarProveedor(idProveedor)) {
            response.sendRedirect("ProveedorServlet");
        } else {
            request.setAttribute("error", "Error al eliminar el proveedor");
            request.getRequestDispatcher("proveedor.jsp").forward(request, response);
        }
    }
}


