package Servlets;

import DAO.OrdenProduccionDAO;
import DAO.OrdenSelladoDAO;
import DAO.UsuarioDAO;
import Entidad.OrdenProduccion;
import Entidad.OrdenSellado;
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

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

@WebServlet("/OrdenSelladoServlet")
public class OrdenSelladoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdenSelladoDAO ordenSelladoDAO;
    private UsuarioDAO usuarioDAO;
    private OrdenProduccionDAO ordenProduccionDAO;

    public OrdenSelladoServlet() {
        super();
        Conexion conexion = new Conexion();
        this.ordenSelladoDAO = new OrdenSelladoDAO(conexion);
        this.usuarioDAO = new UsuarioDAO(conexion);
        this.ordenProduccionDAO = new OrdenProduccionDAO(conexion);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!validarSesion(request, response)) return;

        String action = request.getParameter("action");
        if (action == null) action = "list";

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String rol = usuario.getRol();
        Integer idUsuario = usuario.getIdUsuario();

        try {
            switch (action) {
                case "report":
                    generarInformePDF(request, response);
                    break;
                default:
                    if ("Administrador".equals(rol)) {
                        cargarDatosAdministrador(request);
                    } else if ("Trabajador".equals(rol)) {
                        cargarDatosTrabajador(request, idUsuario);
                    }
                    String vista = "Administrador".equals(rol) ? "ordensellado.jsp" : "SelladoTT.jsp";
                    request.getRequestDispatcher(vista).forward(request, response);
                    break;
            }
        } catch (SQLException | DocumentException e) {
            manejarError(request, response, "Error al cargar las órdenes de sellado", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "insert":
                    procesarOrdenSellado(request, "insertar");
                    break;
                case "update":
                    procesarOrdenSellado(request, "actualizar");
                    break;
                case "delete":
                    eliminarOrdenSellado(request);
                    break;
                case "complete":
                    completarOrden(request);
                    break;
                default:
                    doGet(request, response);
                    break;
            }
            response.sendRedirect("OrdenSelladoServlet");
        } catch (Exception e) {
            manejarError(request, response, "Error al procesar la acción: " + action, e);
        }
    }

    private void generarInformePDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        List<OrdenSellado> ordenes = ordenSelladoDAO.obtenerOrdenesPorFecha(fechaInicio, fechaFin);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeOrdenesSellado.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Informe de Órdenes de Sellado"));
        document.add(new Paragraph("Fecha Inicio: " + fechaInicio));
        document.add(new Paragraph("Fecha Fin: " + fechaFin));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(8);  // Ocho columnas para los campos de la entidad OrdenSellado
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("ID Orden Sellado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ID Usuario", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Medida Bolsa", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cantidad a Sellar", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha Orden", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Estado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tiempo Estimado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ID Orden", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (OrdenSellado orden : ordenes) {
            table.addCell(Integer.toString(orden.getIdOrdenSellado()));
            table.addCell(Integer.toString(orden.getIdUsuario()));
            table.addCell(orden.getMedidaBolsa());
            table.addCell(Integer.toString(orden.getCantidadASellar()));
            table.addCell(orden.getFechaOrden());
            table.addCell(orden.getEstado());
            table.addCell(Integer.toString(orden.getTiempoEstimado()));
            table.addCell(Integer.toString(orden.getIdOrden()));
        }

        document.add(table);
        document.close();
    }

    private boolean validarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return false;
        }
        return true;
    }

        private void cargarDatosAdministrador(HttpServletRequest request) throws SQLException {
        List<OrdenSellado> ordenesSellados = ordenSelladoDAO.listarOrdenesSellado();
        List<Usuario> trabajadores = usuarioDAO.obtenerTrabajadores();
        List<OrdenProduccion> listaOrdenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccionCompletadas(); // Usamos el nuevo método aquí

        request.setAttribute("ordenesSellado", ordenesSellados);
        request.setAttribute("trabajadores", trabajadores);
        request.setAttribute("listaOrdenesProduccion", listaOrdenesProduccion);
    }

    private void cargarDatosTrabajador(HttpServletRequest request, int idUsuario) throws SQLException {
        List<OrdenSellado> ordenesSellados = ordenSelladoDAO.obtenerOrdenesSelladoPorTrabajador(idUsuario);
        request.setAttribute("ordenesSellado", ordenesSellados);
    }

    private void procesarOrdenSellado(HttpServletRequest request, String accion) throws SQLException {
        OrdenSellado ordenSellado = construirOrdenSelladoDesdeRequest(request);

        if ("insertar".equals(accion)) {
            ordenSellado.setEstado("Pendiente");
            ordenSelladoDAO.insertarOrdenSellado(ordenSellado);
        } else if ("actualizar".equals(accion)) {
            ordenSelladoDAO.actualizarOrdenSellado(ordenSellado);
        }
    }

    private OrdenSellado construirOrdenSelladoDesdeRequest(HttpServletRequest request) {
        OrdenSellado ordenSellado = new OrdenSellado();
        ordenSellado.setIdOrdenSellado(parseIntOrDefault(request.getParameter("idOrdenSellado"), 0));
        ordenSellado.setIdUsuario(parseIntOrDefault(request.getParameter("trabajador"), 0));
        ordenSellado.setMedidaBolsa(request.getParameter("medidaBolsa"));
        ordenSellado.setCantidadASellar(parseIntOrDefault(request.getParameter("cantidadASellar"), 0));
        ordenSellado.setFechaOrden(request.getParameter("fechaOrden"));
        ordenSellado.setTiempoEstimado(parseIntOrDefault(request.getParameter("tiempoEstimado"), 0));
        ordenSellado.setEstado(request.getParameter("estado"));
        ordenSellado.setIdOrden(parseIntOrDefault(request.getParameter("idOrden"), 0));
        return ordenSellado;
    }

    private void eliminarOrdenSellado(HttpServletRequest request) throws SQLException {
        int idOrdenSellado = parseIntOrDefault(request.getParameter("idOrdenSellado"), 0);
        ordenSelladoDAO.eliminarOrdenSellado(idOrdenSellado);
    }

    private void completarOrden(HttpServletRequest request) throws SQLException, ParseException {
        int idOrden = parseIntOrDefault(request.getParameter("idOrden"), 0);
        OrdenSellado orden = ordenSelladoDAO.obtenerOrdenPorId(idOrden);
        if (orden != null) {
            orden.setEstado("Completada");
            ordenSelladoDAO.actualizarOrdenSellado(orden);
        }
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response, String mensaje, Exception e) throws ServletException, IOException {
        e.printStackTrace(); // Cambiar a un logger en producción
        request.setAttribute("error", mensaje + ": " + e.getMessage());
        request.getRequestDispatcher("ordensellado.jsp").forward(request, response);
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
