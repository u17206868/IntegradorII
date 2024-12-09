package Servlets;

import DAO.AlmacenDAO;
import DAO.MovimientoAlmacenDAO;
import DAO.ProductoDAO;
import Entidad.Almacen;
import Entidad.MovimientoAlmacen;
import Entidad.Producto;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/MovimientoAlmacenServlet")
public class MovimientoAlmacenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MovimientoAlmacenDAO movimientoAlmacenDAO;
    private final ProductoDAO productoDAO;
    private final AlmacenDAO almacenDAO;

    public MovimientoAlmacenServlet() {
        super();
        Conexion conexion = new Conexion();
        this.movimientoAlmacenDAO = new MovimientoAlmacenDAO(conexion);
        this.productoDAO = new ProductoDAO(conexion);
        this.almacenDAO = new AlmacenDAO(conexion);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "complete":
                    completarMovimiento(request, response);
                case "update":
                    modificarMovimiento(request, response);
                case "add":
                    agregarMovimiento(request, response);
                case "report":
                    generarInformePDF(request, response);
                    break;
                default:
                    listarMovimientos(request, response);
                    break;
            }
        } catch (SQLException | DocumentException e) {
            throw new ServletException(e);
        }
    }

    private void listarMovimientos(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String rol = usuario.getRol();

        List<Producto> productos = productoDAO.obtenerProductos();
        List<Almacen> almacenes = almacenDAO.listarAlmacenes();
        List<MovimientoAlmacen> movimientosPendientes = movimientoAlmacenDAO.obtenerMovimientosPorEstado("Pendiente");
        List<MovimientoAlmacen> movimientosCompletados = movimientoAlmacenDAO.obtenerMovimientosPorEstado("Completada");

        request.setAttribute("movimientosPendientes", movimientosPendientes);
        request.setAttribute("movimientosCompletados", movimientosCompletados);
        request.setAttribute("productos", productos);
        request.setAttribute("almacenes", almacenes);

        if ("Administrador".equals(rol)) {
            request.getRequestDispatcher("almacen.jsp").forward(request, response);
        } else if ("Trabajador".equals(rol)) {
            request.getRequestDispatcher("almacenTT.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void generarInformePDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        List<MovimientoAlmacen> movimientos = movimientoAlmacenDAO.obtenerMovimientosPorFecha(fechaInicio, fechaFin);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeMovimientos.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Informe de Movimientos de Almacén"));
        document.add(new Paragraph("Fecha Inicio: " + fechaInicio));
        document.add(new Paragraph("Fecha Fin: " + fechaFin));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("ID Movimiento", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Producto", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cantidad", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha de Movimiento", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Almacén", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Estado", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (MovimientoAlmacen movimiento : movimientos) {
            table.addCell(Integer.toString(movimiento.getIdMovimiento()));
            table.addCell(Integer.toString(movimiento.getIdProducto()));
            table.addCell(Double.toString(movimiento.getCantidad()));
            table.addCell(movimiento.getFechaMovimiento());
            table.addCell(Integer.toString(movimiento.getIdAlmacen()));
            table.addCell(movimiento.getEstado());
        }

        document.add(table);
        document.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void agregarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String fechaMovimiento = request.getParameter("fechaMovimiento");
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        String estado = request.getParameter("estado"); // Obtener estado

        MovimientoAlmacen nuevoMovimiento = new MovimientoAlmacen();
        nuevoMovimiento.setIdProducto(idProducto);
        nuevoMovimiento.setCantidad(cantidad);
        nuevoMovimiento.setFechaMovimiento(fechaMovimiento);
        nuevoMovimiento.setIdAlmacen(idAlmacen);
        nuevoMovimiento.setEstado(estado); // Asignar estado

        try {
            movimientoAlmacenDAO.insertarMovimiento(nuevoMovimiento);
            response.sendRedirect("MovimientoAlmacenServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al agregar el movimiento de almacén");
            request.getRequestDispatcher("movimientoAlmacen.jsp").forward(request, response);
        }
    }

    private void modificarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMovimiento = Integer.parseInt(request.getParameter("idMovimiento"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String fechaMovimiento = request.getParameter("fechaMovimiento");
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        String estado = request.getParameter("estado"); // Obtener estado

        MovimientoAlmacen movimientoModificado = new MovimientoAlmacen();
        movimientoModificado.setIdMovimiento(idMovimiento);
        movimientoModificado.setIdProducto(idProducto);
        movimientoModificado.setCantidad(cantidad);
        movimientoModificado.setFechaMovimiento(fechaMovimiento);
        movimientoModificado.setIdAlmacen(idAlmacen);
        movimientoModificado.setEstado(estado); // Asignar estado

        try {
            movimientoAlmacenDAO.modificarMovimiento(movimientoModificado);
            response.sendRedirect("MovimientoAlmacenServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al modificar el movimiento de almacén");
            request.getRequestDispatcher("movimientoAlmacen.jsp").forward(request, response);
        }
    }

    private void eliminarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMovimiento = Integer.parseInt(request.getParameter("idMovimiento"));

        try {
            movimientoAlmacenDAO.eliminarMovimiento(idMovimiento);
            response.sendRedirect("MovimientoAlmacenServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar el movimiento de almacén");
            request.getRequestDispatcher("movimientoAlmacen.jsp").forward(request, response);
        }
    }

        private void completarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMovimiento = Integer.parseInt(request.getParameter("idMovimiento"));

        try {
            movimientoAlmacenDAO.cambiarEstadoMovimiento(idMovimiento, "Completada");
            response.sendRedirect("MovimientoAlmacenServlet");
        } catch (SQLException e) {
            request.setAttribute("error", "Error al completar el movimiento de almacén");
            request.getRequestDispatcher("almacenTT.jsp").forward(request, response);
        }
    }
}
