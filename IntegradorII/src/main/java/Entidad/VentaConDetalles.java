package Entidad;

import java.util.Date;

public class VentaConDetalles {
    private int idVenta;
    private String dniCliente;
    private String producto;
    private int cantidad;
    private double precio;
    private double subtotal;
    private double igv;
    private double total;
    private String fechaVenta;
    private Date fechaRegistro;
    private String estado; // Nuevo campo

    // Getters y setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getIgv() { return igv; }
    public void setIgv(double igv) { this.igv = igv; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
