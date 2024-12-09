package Entidad;

import java.util.Date;

public class Venta {
    private int idVenta;
    private int idCliente;
    private String dniCliente;
    private String fechaVenta;
    private double total;
    private double subtotal;
    private double igv;
    private Date fechaRegistro;
    private String estado; // Nuevo campo

    // Constructor vacío
    public Venta() {}

    // Constructor completo
    public Venta(int idVenta, int idCliente, String dniCliente, String fechaVenta, double total, double subtotal, double igv, Date fechaRegistro, String estado) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.dniCliente = dniCliente;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.subtotal = subtotal;
        this.igv = igv;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
