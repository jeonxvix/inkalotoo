/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.servicio;

import com.inkaloto.dao.MovimientoBilleteraDAO;
import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MovimientoBilleteraService {

    private final MovimientoBilleteraDAO movimientoDAO = new MovimientoBilleteraDAO();
    private final BilleteraService billeteraService = new BilleteraService();

    public MovimientoBilletera registrarRecarga(Usuario usuario, BigDecimal monto, String metodoStr) {
        if (usuario == null) {
            throw new IllegalStateException("No hay usuario logueado.");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }

        // Obtener o crear billetera
        BilleteraUsuario billetera = billeteraService.obtenerPorUsuario(usuario);
        if (billetera == null) {
            billeteraService.crearBilleteraParaUsuario(usuario);
            billetera = billeteraService.obtenerPorUsuario(usuario);
        }

        // Crear movimiento
        MovimientoBilletera mov = new MovimientoBilletera();
        mov.setBilletera(billetera);
        mov.setTipoMovimiento(MovimientoBilletera.TipoMovimiento.RECARGA);
        mov.setMonto(monto);
        mov.setSigno(1); // suma
        mov.setCodigoMovimiento(generarCodigoRecarga());
        mov.setDescripcion("Recarga de saldo vía " + metodoStr);
        mov.setFechaMovimiento(LocalDateTime.now());

        try {
            MovimientoBilletera.MetodoPago metodo =
                    MovimientoBilletera.MetodoPago.valueOf(metodoStr);
            mov.setMetodoPago(metodo);
        } catch (IllegalArgumentException e) {
            mov.setMetodoPago(null);
        }

        // Guardar movimiento
        movimientoDAO.guardar(mov);

        // Actualizar saldo
        billeteraService.sumarSaldo(billetera, monto);

        // devolvemos el movimiento para el comprobante
        return mov;
    }

    private String generarCodigoRecarga() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
