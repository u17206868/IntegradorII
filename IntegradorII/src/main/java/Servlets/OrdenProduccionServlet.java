package Servlets;

import DAO.DetalleVentaDAO;
import DAO.OrdenProduccionDAO;
import DAO.UsuarioDAO;
import Entidad.DetalleVenta;
import Entidad.MateriaPrima;
import Entidad.OrdenProduccion;
import Entidad.Usuario;
import conexion.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


@WebServlet("/OrdenProduccionServlet")
public class OrdenProduccionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdenProduccionDAO ordenProduccionDAO;
    private UsuarioDAO usuarioDAO;
    private DetalleVentaDAO detalleVentaDAO;
    private Conexion conexion;
    private Connection connection;

    public OrdenProduccionServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            conexion = new Conexion();
            connection = conexion.conectar();
            ordenProduccionDAO = new OrdenProduccionDAO(conexion);
            usuarioDAO = new UsuarioDAO(conexion);
            detalleVentaDAO = new DetalleVentaDAO(connection);
        } catch (Exception e) {
            throw new ServletException("Error al inicializar el servlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "add":
                agregarOrden(request, response);
                break;
            case "update":
                modificarOrden(request, response);
                break;
            case "delete":
                eliminarOrden(request, response);
                break;
            case "complete":
                completarOrden(request, response);
                break;
            case "changeDetalleStatus":
                cambiarEstadoDetalle(request, response);
                break;
                case "report":
                    generarInformePDF(request, response);
                    break;
                default:
                    listarOrdenesProduccion(request, response);
                    break;
            }
        } catch (SQLException | DocumentException e) {
            throw new ServletException(e);
        }
    }

    private void listarOrdenesProduccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String rol = usuario.getRol();
        Integer idUsuario = usuario.getIdUsuario();

        List<OrdenProduccion> ordenesProduccion = null;
        List<MateriaPrima> materiasPrimas = null;
        List<Usuario> trabajadores = null;
        List<DetalleVenta> detallesPendientes = null;

        try {
            if ("Administrador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccion();
                trabajadores = usuarioDAO.obtenerTrabajadores();
            } else if ("Trabajador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccionPorTrabajador(idUsuario);
            }
            materiasPrimas = ordenProduccionDAO.obtenerMateriasPrimas();
            detallesPendientes = detalleVentaDAO.obtenerDetallesPendientes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("ordenesProduccion", ordenesProduccion);
        request.setAttribute("materiasPrimas", materiasPrimas);
        request.setAttribute("trabajadores", trabajadores);
        request.setAttribute("detallesPendientes", detallesPendientes);

        if ("Administrador".equals(rol)) {
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        } else if ("Trabajador".equals(rol)) {
            request.getRequestDispatcher("ProduccionTT.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void generarInformePDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        List<OrdenProduccion> ordenesProduccion = ordenProduccionDAO.obtenerOrdenesPorFecha(fechaInicio, fechaFin);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeOrdenesProduccion.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Informe de Órdenes de Producción"));
        document.add(new Paragraph("Fecha Inicio: " + fechaInicio));
        document.add(new Paragraph("Fecha Fin: " + fechaFin));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("ID Orden", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tipo Material", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Trabajador", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tiempo Estimado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Estado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (OrdenProduccion orden : ordenesProduccion) {
            table.addCell(Integer.toString(orden.getIdOrden()));
            table.addCell(Integer.toString(orden.getTipoMaterial()));
            table.addCell(Integer.toString(orden.getTrabajador()));
            table.addCell(Integer.toString(orden.getTiempoEstimado()));
            table.addCell(orden.getFecha().toString());
            table.addCell(orden.getEstado());
        }

        document.add(table);
        document.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void agregarOrden(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tipoMaterial = Integer.parseInt(request.getParameter("tipoMaterial"));
        int trabajador = Integer.parseInt(request.getParameter("trabajador"));
        int tiempoEstimado = Integer.parseInt(request.getParameter("tiempoEstimado"));
        Date fecha = new Date();
        String estado = "Pendiente";

        OrdenProduccion nuevaOrden = new OrdenProduccion();
        nuevaOrden.setTipoMaterial(tipoMaterial);
        nuevaOrden.setTrabajador(trabajador);
        nuevaOrden.setTiempoEstimado(tiempoEstimado);
        nuevaOrden.setFecha(fecha);
        nuevaOrden.setEstado(estado);

        try {
            ordenProduccionDAO.crearOrden(nuevaOrden);
            response.sendRedirect("OrdenProduccionServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al agregar la orden de producción");
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        }
    }

    private void modificarOrden(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("idOrden"));
        int tipoMaterial = Integer.parseInt(request.getParameter("tipoMaterial"));
        int trabajador = Integer.parseInt(request.getParameter("trabajador"));
        int tiempoEstimado = Integer.parseInt(request.getParameter("tiempoEstimado"));
        Date fecha = new Date();
        String estado = request.getParameter("estado");

        OrdenProduccion ordenModificada = new OrdenProduccion();
        ordenModificada.setIdOrden(idOrden);
        ordenModificada.setTipoMaterial(tipoMaterial);
        ordenModificada.setTrabajador(trabajador);
        ordenModificada.setTiempoEstimado(tiempoEstimado);
        ordenModificada.setFecha(fecha);
        ordenModificada.setEstado(estado);

        try {
            ordenProduccionDAO.modificarOrden(ordenModificada);
            response.sendRedirect("OrdenProduccionServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al modificar la orden de producción");
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        }
    }

       private void completarOrden(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("idOrden"));

        try {
            OrdenProduccion orden = ordenProduccionDAO.obtenerOrdenPorId(idOrden);
            if (orden != null) {
                orden.setEstado("Completada");
                ordenProduccionDAO.modificarOrden(orden);
            }
            response.sendRedirect("OrdenProduccionServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al marcar la orden de producción como completada");
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        }
    }

    private void eliminarOrden(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("idOrden"));

        try {
            ordenProduccionDAO.eliminarOrden(idOrden);
            response.sendRedirect("OrdenProduccionServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar la orden de producción");
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        }
    }

    private void cambiarEstadoDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        String estado = request.getParameter("estado");

        try {
            if ("Terminado".equals(estado)) {
                detalleVentaDAO.actualizarEstadoDetalleVenta(idDetalle, estado);

                // Obtener el id de la venta asociada al detalle
                String query = "SELECT id_venta FROM detallesventas WHERE id_detalle = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setInt(1, idDetalle);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int idVenta = rs.getInt("id_venta");
                            detalleVentaDAO.actualizarEstadoVenta(idVenta, "En Proceso");
                        }
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("error", "Error al cambiar el estado del detalle de venta");
            request.getRequestDispatcher("ordenProduccion.jsp").forward(request, response);
        }
    }

    private void cargarDatos(HttpServletRequest request) throws SQLException {
        List<OrdenProduccion> ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccion();
        List<MateriaPrima> materiasPrimas = ordenProduccionDAO.obtenerMateriasPrimas();
        List<Usuario> trabajadores = usuarioDAO.obtenerTrabajadores();

        request.setAttribute("ordenesProduccion", ordenesProduccion);
        request.setAttribute("materiasPrimas", materiasPrimas);
        request.setAttribute("trabajadores", trabajadores);
    }
}

