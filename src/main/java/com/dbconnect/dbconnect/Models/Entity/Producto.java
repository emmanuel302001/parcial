package com.dbconnect.dbconnect.Models.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotEmpty
    private String Nombre;

    @NotEmpty
    private String Descripcion;

    private Long ValorUni;

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
