package utils;

import Entidad.OrdenProduccion;
import Entidad.VentaConDetalles;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class PDFResource {

    public ByteArrayInputStream generateVentaPDF(HttpServletResponse response,
            List<VentaConDetalles> listVentaConDetalles) throws Exception {
        Document documento = new Document(PageSize.LETTER.rotate());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter.getInstance(documento, stream);

        documento.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        PdfPCell celdaTitulo = new PdfPCell(new Phrase("Lista de ventas", fontTitulo));
        celdaTitulo.setBorder(0);
        celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTitulo.setPadding(10);
        tablaTitulo.addCell(celdaTitulo);
        documento.add(tablaTitulo);

        PdfPTable tabla = new PdfPTable(10);
        tabla.setWidthPercentage(100); 
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        tabla.addCell(Cabecera("ID Venta"));
        tabla.addCell(Cabecera("DNI Cliente"));
        tabla.addCell(Cabecera("Producto"));
        tabla.addCell(Cabecera("Cantidad"));
        tabla.addCell(Cabecera("Precio"));
        tabla.addCell(Cabecera("Subtotal"));
        tabla.addCell(Cabecera("IGV"));
        tabla.addCell(Cabecera("Total"));
        tabla.addCell(Cabecera("Fecha Venta"));
        tabla.addCell(Cabecera("Fecha Registro"));

        for (VentaConDetalles obj : listVentaConDetalles) {
            tabla.addCell(Celda("" + obj.getIdVenta()));
            tabla.addCell(Celda("" + obj.getDniCliente()));
            tabla.addCell(Celda("" + obj.getProducto()));
            tabla.addCell(Celda("" + obj.getCantidad()));
            tabla.addCell(Celda("" + obj.getPrecio()));
            tabla.addCell(Celda("" + obj.getSubtotal()));
            tabla.addCell(Celda("" + obj.getIgv()));
            tabla.addCell(Celda("" + obj.getTotal()));
            tabla.addCell(Celda("" + obj.getFechaVenta()));
            tabla.addCell(Celda("" + obj.getFechaRegistro()));
        }

        documento.add(tabla);
        documento.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

    public ByteArrayInputStream generateProduccionPDF(HttpServletResponse response,
            List<OrdenProduccion> lista) throws Exception {
        Document documento = new Document(PageSize.LETTER.rotate());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter.getInstance(documento, stream);

        documento.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        PdfPCell celdaTitulo = new PdfPCell(new Phrase("Órdenes de Producción", fontTitulo));
        celdaTitulo.setBorder(0);
        celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTitulo.setPadding(10);
        tablaTitulo.addCell(celdaTitulo);
        documento.add(tablaTitulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100); 
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        float[] columnWidths = {1f, 3f, 3f, 2f, 2f, 2f};
        tabla.setWidths(columnWidths);

        tabla.addCell(Cabecera("#"));
        tabla.addCell(Cabecera("Tipo de Material"));
        tabla.addCell(Cabecera("Trabajador"));
        tabla.addCell(Cabecera("Tiempo Estimado (días)"));
        tabla.addCell(Cabecera("Fecha"));
        tabla.addCell(Cabecera("Estado"));

        for (OrdenProduccion obj : lista) {
            tabla.addCell(Celda("" + obj.getIdOrden()));
            tabla.addCell(Celda("" + obj.getNombreMaterial()));
            tabla.addCell(Celda("" + obj.getNombreUsuario()+ " "+ obj.getApellidoUsuario()));
            tabla.addCell(Celda("" + obj.getTiempoEstimado()));
            tabla.addCell(Celda("" + obj.getFecha()));
            tabla.addCell(Celda("" + obj.getEstado()));
        }

        documento.add(tabla);
        documento.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

    public PdfPCell Cabecera(String data) {
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        PdfPCell celda = new PdfPCell(new Phrase(data, fuenteTitulo));
        celda.setBackgroundColor(new BaseColor(75, 155, 218));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);
        return celda;
    }

    public PdfPCell Celda(String data) {
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);
        PdfPCell celda = new PdfPCell(new Phrase(data, fuenteTitulo));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);
        return celda;
    }
}
