/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entidad;
public class OrdenSellado {
    private int idOrdenSellado;
    private int idUsuario;
    private String medidaBolsa;
    private int cantidadASellar;
    private String fechaOrden;
    private String estado;
    private int tiempoEstimado;
    private int idOrden;

 
    // Getters y setters
    public int getIdOrdenSellado() {
        return idOrdenSellado;
    }

    public void setIdOrdenSellado(int idOrdenSellado) {
        this.idOrdenSellado = idOrdenSellado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMedidaBolsa() {
        return medidaBolsa;
    }

    public void setMedidaBolsa(String medidaBolsa) {
        this.medidaBolsa = medidaBolsa;
    }

    public int getCantidadASellar() {
        return cantidadASellar;
    }

    public void setCantidadASellar(int cantidadASellar) {
        this.cantidadASellar = cantidadASellar;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Integer tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    
}
