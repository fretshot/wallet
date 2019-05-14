package com.infamous_software.wallet;

import java.io.Serializable;

public class gasto implements Serializable {

    private Integer id;
    private Integer dia;
    private Integer mes;
    private Integer año;
    private Integer comida;
    private Integer transporte;
    private Integer entretenimiento;
    private Integer otros;


    public gasto(Integer id, Integer dia, Integer mes, Integer año, Integer comida, Integer transporte, Integer entretenimiento, Integer otros) {
        this.id = id;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.comida = comida;
        this.transporte = transporte;
        this.entretenimiento = entretenimiento;
        this.otros = otros;
    }

    public gasto(){

    }
    public Integer getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAño() {
        return año;
    }

    public Integer getComida() {
        return comida;
    }

    public Integer getTransporte() {
        return transporte;
    }

    public Integer getEntretenimiento() {
        return entretenimiento;
    }

    public Integer getOtros() {
        return otros;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public void setComida(Integer comida) {
        this.comida = comida;
    }

    public void setTransporte(Integer transporte) {
        this.transporte = transporte;
    }

    public void setEntretenimiento(Integer entretenimiento) {
        this.entretenimiento = entretenimiento;
    }

    public void setOtros(Integer otros) {
        this.otros = otros;
    }

}
