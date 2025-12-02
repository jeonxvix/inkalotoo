/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.dao.MovimientoBilleteraDAO;
import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.BilleteraService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("historialPremiosBean")
@RequestScoped
public class HistorialPremiosBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private final BilleteraService billeteraService = new BilleteraService();
    private final MovimientoBilleteraDAO movimientoDAO = new MovimientoBilleteraDAO();

    public List<MovimientoBilletera> getPremios() {
        Usuario usuario = loginBean.getUsuarioLogueado();
        if (usuario == null) {
            return Collections.emptyList();
        }

        BilleteraUsuario billetera = billeteraService.obtenerPorUsuario(usuario);
        if (billetera == null) {
            return Collections.emptyList();
        }

        // Solo movimientos tipo PREMIO
        return movimientoDAO.listarPremios(billetera);
    }
}
