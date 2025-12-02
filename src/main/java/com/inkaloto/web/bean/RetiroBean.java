
package com.inkaloto.web.bean;

import com.inkaloto.dao.MovimientoBilleteraDAO;
import com.inkaloto.dao.NotificacionDAO;
import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Notificacion;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.BilleteraService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Named("retiroBean")
@RequestScoped
public class RetiroBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private BigDecimal monto;
    private String metodo;   // CUENTA_BANCARIA / BILLETERA_DIGITAL
    private String destino;  // CCI, nro de billetera, etc.

    private String mensajeError;
    private String mensajeExito;

    // ===== Campos para COMPROBANTE de retiro =====
    private String comprobanteCodigo;
    private String comprobanteFecha;
    private BigDecimal comprobanteMonto;

    public String solicitarRetiro() {

        mensajeError = null;
        mensajeExito = null;
        comprobanteCodigo = null;
        comprobanteFecha = null;
        comprobanteMonto = null;

        Usuario u = loginBean.getUsuarioLogueado();
        if (u == null) {
            mensajeError = "Debes iniciar sesión para retirar tus premios.";
            return null;
        }

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            mensajeError = "Ingresa un monto válido para retirar.";
            return null;
        }

        if (metodo == null || metodo.isEmpty()) {
            mensajeError = "Selecciona un método de retiro.";
            return null;
        }

        if (destino == null || destino.trim().isEmpty()) {
            mensajeError = "Ingresa el destino del retiro (cuenta, billetera, etc.).";
            return null;
        }

        try {
            BilleteraService billeteraService = new BilleteraService();
            BilleteraUsuario billetera = billeteraService.obtenerPorUsuario(u);

            if (billetera == null) {
                mensajeError = "No se encontró una billetera para tu usuario.";
                return null;
            }

            BigDecimal saldoActual = billetera.getSaldoActual() == null
                    ? BigDecimal.ZERO
                    : billetera.getSaldoActual();

            if (saldoActual.compareTo(monto) < 0) {
                mensajeError = "No tienes saldo suficiente para este retiro.";
                return null;
            }

            // 1) Descontar saldo
            billeteraService.restarSaldo(billetera, monto);

            // 2) Registrar movimiento RETIRO
            MovimientoBilletera mov = new MovimientoBilletera();
            mov.setBilletera(billetera);
            mov.setTipoMovimiento(MovimientoBilletera.TipoMovimiento.RETIRO);
            mov.setMonto(monto);
            mov.setSigno(-1);
            // podemos usar BILLETERA_DIGITAL como método genérico
            mov.setMetodoPago(MovimientoBilletera.MetodoPago.BILLETERA_DIGITAL);

            String codigo = "RET-" + System.currentTimeMillis();
            mov.setCodigoMovimiento(codigo);
            mov.setDescripcion("Retiro de premios a " + metodo + " (" + destino + ")");

            MovimientoBilleteraDAO movDAO = new MovimientoBilleteraDAO();
            movDAO.guardar(mov);

            // ===== Llenar datos del COMPROBANTE =====
            this.comprobanteCodigo = mov.getCodigoMovimiento();
            this.comprobanteMonto = mov.getMonto();
            if (mov.getFechaMovimiento() != null) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                this.comprobanteFecha = mov.getFechaMovimiento().format(fmt);
            } else {
                this.comprobanteFecha = "";
            }

            // ===== NUEVO: Crear NOTIFICACIÓN por retiro =====
            try {
                NotificacionDAO notiDAO = new NotificacionDAO();
                Notificacion n = new Notificacion();
                n.setUsuario(u);
                n.setTipoJuego(null); // no pertenece a juego
                n.setCodigoJugada(codigo); // reutilizamos el código del retiro
                n.setTitulo("Retiro registrado");
                n.setMensaje("Se realizó un retiro de S/ " + this.comprobanteMonto +
                        " hacia " + metodo + " (" + destino + ").");
                notiDAO.guardar(n);
            } catch (Exception e) {
                System.out.println("ERROR al generar notificación de retiro: " + e.getMessage());
                e.printStackTrace();
            }
            // ===============================================

            mensajeExito = "Se registró tu retiro por S/ " + monto + ". " +
                    "Lo verás en la sección Movimientos y en Notificaciones.";

            // limpiar formulario (pero dejamos el comprobante visible)
            monto = null;
            destino = null;
            metodo = null;

        } catch (Exception e) {
            e.printStackTrace();
            mensajeError = "Ocurrió un error al procesar el retiro: " + e.getMessage();
        }

        return null;
    }

    // ===== Getters / setters =====

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    // === Getters del comprobante ===
    public String getComprobanteCodigo() {
        return comprobanteCodigo;
    }

    public String getComprobanteFecha() {
        return comprobanteFecha;
    }

    public BigDecimal getComprobanteMonto() {
        return comprobanteMonto;
    }
}
