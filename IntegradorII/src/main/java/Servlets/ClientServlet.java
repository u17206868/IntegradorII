package Servlets;

import DAO.ClienteDAO;
import Entidad.Cliente;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    public ClientServlet() {
        super();
        Conexion conexion = new Conexion();
        Connection connection = conexion.conectar();
        this.clienteDAO = new ClienteDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "report":
                    generarInformePDF(request, response);
                    break;
                default:
                    listarClientes(request, response);
                    break;
            }
        } catch (SQLException | DocumentException e) {
            throw new ServletException(e);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "add":
                    agregarCliente(request, response);
                    break;
                case "update":
                    modificarCliente(request, response);
                    break;
                case "delete":
                    eliminarCliente(request, response);
                    break;
                default:
                    doGet(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void agregarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String dni = request.getParameter("dni");

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setApellido(apellido);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setDireccion(direccion);
        nuevoCliente.setDni(dni);

        if (clienteDAO.registrarCliente(nuevoCliente)) {
            response.sendRedirect("ClientServlet");
        } else {
            request.setAttribute("error", "Error al agregar el cliente");
            request.getRequestDispatcher("clientes.jsp").forward(request, response);
        }
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String dni = request.getParameter("dni");

        Cliente clienteModificado = new Cliente();
        clienteModificado.setIdCliente(idCliente);
        clienteModificado.setNombre(nombre);
        clienteModificado.setApellido(apellido);
        clienteModificado.setCorreo(correo);
        clienteModificado.setTelefono(telefono);
        clienteModificado.setDireccion(direccion);
        clienteModificado.setDni(dni);

        if (clienteDAO.actualizarCliente(clienteModificado)) {
            response.sendRedirect("ClientServlet");
        } else {
            request.setAttribute("error", "Error al modificar el cliente");
            request.getRequestDispatcher("clientes.jsp").forward(request, response);
        }
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

        if (clienteDAO.eliminarCliente(idCliente)) {
            response.sendRedirect("ClientServlet");
        } else {
            request.setAttribute("error", "Error al eliminar el cliente");
            request.getRequestDispatcher("clientes.jsp").forward(request, response);
        }
    }

    private void generarInformePDF(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, IOException {
        List<Cliente> clientes = clienteDAO.obtenerClientes();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeClientes.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Informe de Clientes"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);  // Siete columnas para los campos de la entidad Cliente
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("ID Cliente", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Nombre", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Apellido", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DNI", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Correo", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Teléfono", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Dirección", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (Cliente cliente : clientes) {
            table.addCell(Integer.toString(cliente.getIdCliente()));
            table.addCell(cliente.getNombre());
            table.addCell(cliente.getApellido());
            table.addCell(cliente.getDni());
            table.addCell(cliente.getCorreo());
            table.addCell(cliente.getTelefono());
            table.addCell(cliente.getDireccion());
        }

        document.add(table);
        document.close();
    }
}
