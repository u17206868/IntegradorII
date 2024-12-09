package Servlets;

import DAO.ClienteDAO;
import DAO.DetalleVentaDAO;
import DAO.ProductoDAO;
import DAO.VentaDAO;
import Entidad.Cliente;
import Entidad.DetalleVenta;
import Entidad.Producto;
import Entidad.Venta;
import Entidad.VentaConDetalles;
import conexion.Conexion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/VentaServlet")
public class VentaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VentaDAO ventaDAO;
    private ClienteDAO clienteDAO;
    private DetalleVentaDAO detalleVentaDAO;
    private ProductoDAO productoDAO;
    private Conexion conexion;
    private Connection connection;

    public VentaServlet() {
        super();
        conexion = new Conexion();
        connection = conexion.conectar();
        this.ventaDAO = new VentaDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
        this.detalleVentaDAO = new DetalleVentaDAO(connection);
        this.productoDAO = new ProductoDAO(conexion);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertVenta(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateVenta(request, response);
                    break;
                case "changeStatus":
                    cambiarEstadoVenta(request, response);
                    break;
                case "getStatus":
                    obtenerEstadoVenta(request, response);
                    break;
                default:
                    listVentas(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listVentas(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Venta> listVenta = ventaDAO.obtenerVentas();
        List<VentaConDetalles> listVentaConDetalles = new ArrayList<>();
        for (Venta venta : listVenta) {
            List<DetalleVenta> detallesVentas = detalleVentaDAO.obtenerDetalleVentas(venta.getIdVenta());
            for (DetalleVenta detalle : detallesVentas) {
                VentaConDetalles ventaConDetalles = new VentaConDetalles();
                ventaConDetalles.setIdVenta(venta.getIdVenta());
                ventaConDetalles.setDniCliente(venta.getDniCliente());
                ventaConDetalles.setProducto(detalle.getProducto());
                ventaConDetalles.setCantidad(detalle.getCantidad());
                ventaConDetalles.setPrecio(detalle.getPrecio());
                ventaConDetalles.setSubtotal(venta.getSubtotal());
                ventaConDetalles.setIgv(venta.getIgv());
                ventaConDetalles.setTotal(venta.getTotal());
                ventaConDetalles.setFechaVenta(venta.getFechaVenta());
                ventaConDetalles.setFechaRegistro(venta.getFechaRegistro());
                listVentaConDetalles.add(ventaConDetalles);
            }
        }
        List<Cliente> listClientes = clienteDAO.obtenerClientes();
        List<Producto> listProductos = productoDAO.obtenerProductos();
        request.setAttribute("listVenta", listVentaConDetalles);
        request.setAttribute("listClientes", listClientes);
        request.setAttribute("listProductos", listProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Ventas.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Cliente> listClientes = clienteDAO.obtenerClientes();
        List<Producto> listProductos = productoDAO.obtenerProductos();
        request.setAttribute("listClientes", listClientes);
        request.setAttribute("listProductos", listProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Ventas.jsp");
        dispatcher.forward(request, response);
    }

        private void insertVenta(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = clienteDAO.getCliente(idCliente);
        String dniCliente = cliente.getDni();

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = productoDAO.getProducto(idProducto);
        String nombreProducto = producto.getNombre();

        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        double precio = Double.parseDouble(request.getParameter("precio"));
        int tiempo = Integer.parseInt(request.getParameter("tiempo"));
        double subtotal = cantidad * precio;
        double igv = subtotal * 0.18;
        double total = subtotal + igv;
        Date fechaRegistro = new Date();
        String fechaVenta = request.getParameter("fechaVenta");

        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdCliente(idCliente);
        nuevaVenta.setDniCliente(dniCliente);
        nuevaVenta.setFechaVenta(fechaVenta);
        nuevaVenta.setTotal(total);
        nuevaVenta.setSubtotal(subtotal);
        nuevaVenta.setIgv(igv);
        nuevaVenta.setFechaRegistro(fechaRegistro);
        nuevaVenta.setEstado("Pendiente"); // Estado inicial

        ventaDAO.crearVenta(nuevaVenta);

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setIdVenta(nuevaVenta.getIdVenta());
        detalleVenta.setIdProducto(idProducto);
        detalleVenta.setProducto(nombreProducto);
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setTiempo(tiempo);
        detalleVenta.setPrecio(precio);
        detalleVenta.setEstado("Pendiente"); // Estado inicial

        detalleVentaDAO.crearDetalleVenta(detalleVenta);

        // Sincronizar el estado de la venta con los detalles de la venta
        detalleVentaDAO.sincronizarEstadosConVenta(nuevaVenta.getIdVenta(), nuevaVenta.getEstado());

        // Redirigir al módulo de producción para reflejar los cambios
        response.sendRedirect("OrdenProduccionServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        Venta ventaExistente = ventaDAO.getVenta(idVenta);
        List<Cliente> listClientes = clienteDAO.obtenerClientes();
        List<Producto> listProductos = productoDAO.obtenerProductos();
        request.setAttribute("venta", ventaExistente);
        request.setAttribute("listClientes", listClientes);
        request.setAttribute("listProductos", listProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Ventas.jsp");
        dispatcher.forward(request, response);
    }

    private void updateVenta(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        try {
            int idVenta = parseIntOrDefault(request.getParameter("idVenta"), 0);
            int idCliente = parseIntOrDefault(request.getParameter("idCliente"), 0);
            String dniCliente = request.getParameter("dniCliente");
            String fechaVenta = request.getParameter("fechaVenta");
            double total = parseDoubleOrDefault(request.getParameter("total"), 0.0);
            double subtotal = parseDoubleOrDefault(request.getParameter("subtotal"), 0.0);
            double igv = parseDoubleOrDefault(request.getParameter("igv"), 0.0);
            int idProducto = parseIntOrDefault(request.getParameter("idProducto"), 0);
            String producto = request.getParameter("producto");
            int cantidad = parseIntOrDefault(request.getParameter("cantidad"), 0);
            double precio = parseDoubleOrDefault(request.getParameter("precio"), 0.0);
            int tiempo = parseIntOrDefault(request.getParameter("tiempo"), 0);
            String estadoVenta = request.getParameter("estado"); // Estado de la venta
            String estadoDetalle = request.getParameter("estadoDetalle"); // Estado del detalle de venta

            Venta ventaModificada = new Venta();
            ventaModificada.setIdVenta(idVenta);
            ventaModificada.setIdCliente(idCliente);
            ventaModificada.setDniCliente(dniCliente);
            ventaModificada.setFechaVenta(fechaVenta);
            ventaModificada.setTotal(total);
            ventaModificada.setSubtotal(subtotal);
            ventaModificada.setIgv(igv);
            ventaModificada.setEstado(estadoVenta); // Actualizar estado de la venta

            ventaDAO.modificarVenta(ventaModificada);

            DetalleVenta detalleModificado = new DetalleVenta();
            detalleModificado.setIdVenta(idVenta);
            detalleModificado.setIdProducto(idProducto);
            detalleModificado.setProducto(producto);
            detalleModificado.setCantidad(cantidad);
            detalleModificado.setPrecio(precio);
            detalleModificado.setTiempo(tiempo);
            detalleModificado.setEstado(estadoDetalle); // Actualizar estado del detalle de venta

            detalleVentaDAO.modificarDetalleVenta(detalleModificado);

            // Sincronizar el estado de la venta con los detalles de la venta
            detalleVentaDAO.sincronizarEstadosConVenta(idVenta, estadoVenta);

            response.sendRedirect("VentaServlet?action=list");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("VentaServlet?action=edit&id=" + request.getParameter("idVenta"));
        }
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void cambiarEstadoVenta(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idVenta = parseIntOrDefault(request.getParameter("idVenta"), 0);
        String estado = request.getParameter("estado");

        if (idVenta > 0 && estado != null && !estado.isEmpty()) {
            ventaDAO.actualizarEstadoVenta(idVenta, estado);
            // Sincronizar el estado de la venta con los detalles de la venta
            detalleVentaDAO.sincronizarEstadosConVenta(idVenta, estado);
        }

        // Redirigir a la página de listado de ventas
        response.sendRedirect("VentaServlet?action=list");
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void obtenerEstadoVenta(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idVenta = parseIntOrDefault(request.getParameter("idVenta"), 0);
        if (idVenta > 0) {
            Venta venta = ventaDAO.getVenta(idVenta);
            if (venta != null) {
                String estado = venta.getEstado();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"estado\": \"" + estado + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
