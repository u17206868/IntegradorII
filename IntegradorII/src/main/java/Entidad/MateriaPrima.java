package Entidad;

public class MateriaPrima {
    private int idMateriaPrima;
    private String nombre;
    private String tipo;
    private double cantidad;
    private String tipo_materia; // Cambiar a tipo_materia para que coincida con la columna de la tabla

    // Getters y Setters
    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo_materia() {
        return tipo_materia;
    }

    public void setTipo_materia(String tipo_materia) {
        this.tipo_materia = tipo_materia;
    }
}
