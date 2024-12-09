package Entidad;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private String tipo;
    private double cantidad;
    private int almacen;

    // Getters y setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public int getAlmacen() { return almacen; }
    public void setAlmacen(int almacen) { this.almacen = almacen; }
}
