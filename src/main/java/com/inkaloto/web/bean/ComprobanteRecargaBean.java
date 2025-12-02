/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.dao.MovimientoBilleteraDAO;
import com.inkaloto.modelo.MovimientoBilletera;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("comprobanteRecargaBean")
@RequestScoped
public class ComprobanteRecargaBean implements Serializable {

    private Integer idMovimiento;
    private MovimientoBilletera movimiento;

    private final MovimientoBilleteraDAO movimientoDAO = new MovimientoBilleteraDAO();

    // Se llamará antes de renderizar la vista
    public void cargar() {
        if (idMovimiento != null) {
            movimiento = movimientoDAO.buscarPorId(idMovimiento);
        }
    }

    // Getters & Setters

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public MovimientoBilletera getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(MovimientoBilletera movimiento) {
        this.movimiento = movimiento;
    }
}
