package Entidad;

public class DetalleVenta {
    private int idDetalle;
    private int idVenta;
    private int idProducto;
    private String producto;
    private int cantidad;
    private int tiempo;
    private double precio;
    private String estado; // Nuevo campo

    // Constructor vacío
    public DetalleVenta() {}

    // Constructor completo
    public DetalleVenta(int idDetalle, int idVenta, int idProducto, String producto, int cantidad, int tiempo, double precio, String estado) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.tiempo = tiempo;
        this.precio = precio;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
