package Servlets;

import DAO.DetalleVentaDAO;
import DAO.OrdenProduccionDAO;
import DAO.UsuarioDAO;
import DAO.VentaDAO;
import Entidad.DetalleVenta;
import Entidad.OrdenProduccion;
import Entidad.Usuario;
import Entidad.Venta;
import Entidad.VentaConDetalles;
import conexion.Conexion;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.CorreoUtil;
import utils.PDFResource;

public class UtilServlet extends HttpServlet {

    private OrdenProduccionDAO ordenProduccionDAO;
    private UsuarioDAO usuarioDAO;
    private VentaDAO ventaDAO;
    private DetalleVentaDAO detalleVentaDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Conexion conexion = new Conexion();
        ordenProduccionDAO = new OrdenProduccionDAO(conexion);
        usuarioDAO = new UsuarioDAO(conexion);
        ventaDAO = new VentaDAO(conexion.conectar());
        detalleVentaDAO = new DetalleVentaDAO(conexion.conectar());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        switch (action) {
            case "ordenProd_exportar_pdf":
                OrdenProdExportarPdf(request, response);
                break;
            case "venta_exportar_pdf":
                VentasProdExportarPdf(request, response);
                break;
            case "enviarCorreo_ordenProd":
                EnviarCorreo_ordenProd(request, response);
                break;
        }
    }

    protected void EnviarCorreo_ordenProd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = sdf.format(new Date());

        String nombreArchivo = "orden_produccion_" + fechaHora + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

        try {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            String rol = usuario.getRol();
            Integer idUsuario = usuario.getIdUsuario();
            List<OrdenProduccion> ordenesProduccion = new ArrayList<>();

            if ("Administrador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccion();
            } else if ("Trabajador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccionPorTrabajador(idUsuario);
            }

            PDFResource pdfResource = new PDFResource();
            ByteArrayInputStream pdfStream = pdfResource.generateProduccionPDF(response, ordenesProduccion);

            String correoDestino = request.getParameter("correo");
            String asunto = "Orden de Producción";
            String cuerpo = "Estimado,\n\nSe adjunta el archivo PDF con las órdenes de producción.\n\nSaludos.";

            byte[] pdfBytes = pdfStream.readAllBytes();

            CorreoUtil correoUtil = new CorreoUtil();
            String result = correoUtil.EnviarCorreoConAdjunto(asunto, cuerpo, correoDestino, false,
                    pdfBytes, nombreArchivo);

            response.sendRedirect("OrdenProduccionServlet");

            response.getOutputStream().flush();
            response.getOutputStream().close();
            pdfStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al generar el PDF o enviar el correo", e);
        }
    }

    protected void OrdenProdExportarPdf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = sdf.format(new Date());

        String nombreArchivo = "orden_produccion_" + fechaHora + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

        try {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            String rol = usuario.getRol();
            Integer idUsuario = usuario.getIdUsuario();
            List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
            if ("Administrador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccion();
            } else if ("Trabajador".equals(rol)) {
                ordenesProduccion = ordenProduccionDAO.obtenerOrdenesProduccionPorTrabajador(idUsuario);
            }

            PDFResource pdfResource = new PDFResource();

                ByteArrayInputStream pdfStream = pdfResource.generateProduccionPDF(response, ordenesProduccion);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = pdfStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            response.getOutputStream().flush();
            response.getOutputStream().close();
            pdfStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al generar el PDF", e);
        }
    }

    protected void VentasProdExportarPdf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = sdf.format(new Date());

        String nombreArchivo = "venta_" + fechaHora + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

        try {
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

            PDFResource pdfResource = new PDFResource();

            ByteArrayInputStream pdfStream = pdfResource.generateVentaPDF(response, listVentaConDetalles);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = pdfStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            response.getOutputStream().flush();
            response.getOutputStream().close();
            pdfStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al generar el PDF", e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
