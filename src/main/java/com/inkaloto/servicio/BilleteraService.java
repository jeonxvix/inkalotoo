
package com.inkaloto.servicio;

import com.inkaloto.dao.BilleteraDAO;
import com.inkaloto.dao.MovimientoBilleteraDAO;
import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Usuario;

import java.math.BigDecimal;

public class BilleteraService {

    private final BilleteraDAO billeteraDAO = new BilleteraDAO();
    private final MovimientoBilleteraDAO movimientoDAO = new MovimientoBilleteraDAO();

    public void crearBilleteraParaUsuario(Usuario usuario) {
        BilleteraUsuario existente = billeteraDAO.obtenerPorUsuario(usuario);
        if (existente != null) {
            return;
        }
        BilleteraUsuario b = new BilleteraUsuario();
        b.setUsuario(usuario);
        if (b.getSaldoActual() == null) {
            b.setSaldoActual(BigDecimal.ZERO);
        }
        billeteraDAO.crear(b);
    }

    public BilleteraUsuario obtenerPorUsuario(Usuario usuario) {
        return billeteraDAO.obtenerPorUsuario(usuario);
    }

    public void sumarSaldo(BilleteraUsuario billetera, BigDecimal monto) {
        if (billetera == null) return;
        BigDecimal actual = billetera.getSaldoActual() == null
                ? BigDecimal.ZERO
                : billetera.getSaldoActual();
        billetera.setSaldoActual(actual.add(monto));
        billeteraDAO.actualizar(billetera);
    }

    public void restarSaldo(BilleteraUsuario billetera, BigDecimal monto) {
        if (billetera == null) return;
        BigDecimal actual = billetera.getSaldoActual() == null
                ? BigDecimal.ZERO
                : billetera.getSaldoActual();
        billetera.setSaldoActual(actual.subtract(monto));
        billeteraDAO.actualizar(billetera);
    }

    // ================================================================
    //   REGISTRAR APUESTA DE BINGO (ya existía – SIN CAMBIOS)
    // ================================================================
    public MovimientoBilletera registrarApuestaBingo(Usuario usuario,
                                                     BigDecimal monto,
                                                     String codigoMovimiento,
                                                     String descripcion) {

        BilleteraUsuario billetera = billeteraDAO.obtenerPorUsuario(usuario);
        if (billetera == null) {
            throw new IllegalStateException("El usuario no tiene billetera asociada.");
        }

        BigDecimal saldoActual = billetera.getSaldoActual() == null
                ? BigDecimal.ZERO
                : billetera.getSaldoActual();

        if (saldoActual.compareTo(monto) < 0) {
            throw new IllegalStateException("Saldo insuficiente para la apuesta.");
        }

        BigDecimal nuevoSaldo = saldoActual.subtract(monto);
        billetera.setSaldoActual(nuevoSaldo);
        billeteraDAO.actualizar(billetera);

        MovimientoBilletera mov = new MovimientoBilletera();
        mov.setBilletera(billetera);
        mov.setTipoMovimiento(MovimientoBilletera.TipoMovimiento.APUESTA);
        mov.setMonto(monto);
        mov.setSigno(-1);
        mov.setMetodoPago(null);
        mov.setCodigoMovimiento(codigoMovimiento);
        mov.setDescripcion(descripcion);

        movimientoDAO.guardar(mov);

        return mov;
    }

    public MovimientoBilletera registrarApuestaBingo(Usuario usuario,
                                                     double montoDouble,
                                                     String codigoMovimiento,
                                                     String descripcion) {
        BigDecimal monto = BigDecimal.valueOf(montoDouble);
        return registrarApuestaBingo(usuario, monto, codigoMovimiento, descripcion);
    }


    // ================================================================
    //   ? NUEVO: REGISTRA PREMIO DE BINGO
    // ================================================================
    public MovimientoBilletera registrarPremioBingo(Usuario usuario,
                                                    BigDecimal monto,
                                                    String codigoMovimiento,
                                                    String descripcion) {

        BilleteraUsuario billetera = billeteraDAO.obtenerPorUsuario(usuario);
        if (billetera == null) {
            throw new IllegalStateException("El usuario no tiene billetera asociada.");
        }

        BigDecimal saldoActual = billetera.getSaldoActual() == null
                ? BigDecimal.ZERO
                : billetera.getSaldoActual();

        BigDecimal nuevoSaldo = saldoActual.add(monto);
        billetera.setSaldoActual(nuevoSaldo);
        billeteraDAO.actualizar(billetera);

        MovimientoBilletera mov = new MovimientoBilletera();
        mov.setBilletera(billetera);
        mov.setTipoMovimiento(MovimientoBilletera.TipoMovimiento.PREMIO);
        mov.setMonto(monto);
        mov.setSigno(1);
        mov.setMetodoPago(null);
        mov.setCodigoMovimiento(codigoMovimiento);
        mov.setDescripcion(descripcion);

        movimientoDAO.guardar(mov);

        return mov;
    }
}