package com.example.credibanco.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
public class TransaccionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idTransaccion;
    private LocalDateTime fechaTransaccion;
    private String estado;
    private String precioTran;
    @ManyToOne
    @JoinColumn(name = "tarjeta")
    private TarjetaModel tarjetaModel;

    public TransaccionModel(){
        this.fechaTransaccion = LocalDateTime.now();
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDateTime fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TarjetaModel getTarjetaModel() {
        return tarjetaModel;
    }

    public void setTarjetaModel(TarjetaModel tarjetaModel) {
        this.tarjetaModel = tarjetaModel;
    }

    public String getPrecioTran() {
        return precioTran;
    }

    public void setPrecioTran(String precioTran) {
        this.precioTran = precioTran;
    }


}
