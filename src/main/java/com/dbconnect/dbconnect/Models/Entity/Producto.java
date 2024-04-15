package com.dbconnect.dbconnect.Models.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Column;

@Entity
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @NotEmpty
    @Column(name = "Nombre")
    private String Nombre;

    @NotEmpty
    @Column(name = "Descripcion")
    private String Descripcion;

    @Column(name = "ValorUni")
    private Long ValorUni;

    @Column(name = "Stock")
    private Long Stock;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Long getValorUni() {
        return ValorUni;
    }

    public void setValorUni(Long valorUni) {
        ValorUni = valorUni;
    }

    public Long getStock() {
        return Stock;
    }

    public void setStock(Long stock) {
        Stock = stock;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

}
