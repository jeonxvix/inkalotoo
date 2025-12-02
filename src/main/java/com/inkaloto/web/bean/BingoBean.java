
package com.inkaloto.web.bean;

import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.BilleteraService;
import com.inkaloto.servicio.JugadaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;

@Named("bingoBean")
@RequestScoped
public class BingoBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private BigDecimal precio = new BigDecimal("1.00");
    private int cantidad = 1;

    private String mensajeError;
    private String mensajeExito;

    private int cartonesDisponibles = 0;

    // CAMPOS PARA EL BINGO
    private String numerosSacadosJS = "[]";  // JSON array vacío
    private String resultadoJS = "{}";       // JSON object vacío
    private int[] numerosSeleccionados = new int[25];
    private boolean cartonComprado = false;
    private boolean juegoActivo = false;
    private int numeroActual = 0;

    // NUEVO ? guardar la jugada actual
    private Jugada ultimaJugada;

    // =============================
    //   COMPRAR CARTÓN BINGO
    // =============================
    public String comprarCarton() {
        mensajeError = null;
        mensajeExito = null;

        Usuario u = loginBean.getUsuarioLogueado();
        if (u == null) {
            mensajeError = "Debes iniciar sesión para comprar cartones.";
            return null;
        }

        if (precio == null || cantidad <= 0) {
            mensajeError = "Selecciona un precio válido y una cantidad mayor a 0.";
            return null;
        }

        BigDecimal total = precio.multiply(BigDecimal.valueOf(cantidad));

        try {
            System.out.println("=== INICIANDO COMPRA BINGO ===");
            System.out.println("Usuario: " + u.getNombres());
            System.out.println("Total: " + total);

            BilleteraService billeteraService = new BilleteraService();
            JugadaService jugadaService = new JugadaService();

            String codigo = "BINGO-" + System.currentTimeMillis();
            String desc = "Compra de " + cantidad + " cartón(es) de bingo";

            System.out.println("Registrando apuesta...");

            // 1) Descontar saldo y crear movimiento APUESTA
            MovimientoBilletera movApuesta =
                    billeteraService.registrarApuestaBingo(u, total, codigo, desc);

            System.out.println("Apuesta registrada: " + movApuesta.getIdMovimiento());

            System.out.println("Registrando jugada...");

            // 2) Registrar jugada en la tabla JUGADA (devuelve Jugada)
            ultimaJugada = jugadaService.registrarJugadaBingo(
                    u,
                    total,
                    codigo,
                    desc,
                    movApuesta
            );

            System.out.println("Jugada registrada exitosamente. ID jugada = "
                    + (ultimaJugada != null ? ultimaJugada.getIdJugada() : "null"));

            // 3) Aumentar cartones disponibles (solo UI)
            cartonesDisponibles += cantidad;

            // 4) Marcar como comprado
            cartonComprado = true;

            mensajeExito = "Compra realizada. Se descontó S/ " + total
                    + ". Cartones disponibles: " + cartonesDisponibles;

            System.out.println("=== COMPRA EXITOSA ===");

        } catch (IllegalStateException e) {
            System.out.println("ERROR IllegalState: " + e.getMessage());
            mensajeError = e.getMessage();
        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
            mensajeError = "Ocurrió un error al procesar la compra: " + e.getMessage();
        }

        return null;
    }


    // =============================
    //   LÓGICA DEL JUEGO
    // =============================

    public void iniciarJuego() {
        juegoActivo = true;
        numerosSacadosJS = "[]";
        resultadoJS = "{\"estado\": \"iniciado\"}";
    }

    public void sacarNumero() {
        if (juegoActivo) {
            numeroActual = (int) (Math.random() * 75) + 1;
            // Aquí agregarías el número a numerosSacadosJS
        }
    }


    // =============================
    //        GETTERS / SETTERS
    // =============================

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getTotal() {
        if (precio == null || cantidad <= 0) return BigDecimal.ZERO;
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    public String getMensajeError() { return mensajeError; }
    public String getMensajeExito() { return mensajeExito; }

    public int getCartonesDisponibles() { return cartonesDisponibles; }
    public void setCartonesDisponibles(int cartonesDisponibles) {
        this.cartonesDisponibles = cartonesDisponibles;
    }

    public String getNumerosSacadosJS() {
        return numerosSacadosJS;
    }

    public void setNumerosSacadosJS(String numerosSacadosJS) {
        this.numerosSacadosJS = numerosSacadosJS;
    }

    public String getResultadoJS() {
        return resultadoJS;
    }

    public void setResultadoJS(String resultadoJS) {
        this.resultadoJS = resultadoJS;
    }

    public int[] getNumerosSeleccionados() {
        return numerosSeleccionados;
    }

    public void setNumerosSeleccionados(int[] numerosSeleccionados) {
        this.numerosSeleccionados = numerosSeleccionados;
    }

    public boolean isCartonComprado() {
        return cartonComprado;
    }

    public void setCartonComprado(boolean cartonComprado) {
        this.cartonComprado = cartonComprado;
    }

    public boolean isJuegoActivo() {
        return juegoActivo;
    }

    public void setJuegoActivo(boolean juegoActivo) {
        this.juegoActivo = juegoActivo;
    }

    public int getNumeroActual() {
        return numeroActual;
    }

    public void setNumeroActual(int numeroActual) {
        this.numeroActual = numeroActual;
    }

    public String getNumerosCartonJS() {
        return java.util.Arrays.toString(numerosSeleccionados);
    }

    public Jugada getUltimaJugada() {
        return ultimaJugada;
    }

    public void setUltimaJugada(Jugada ultimaJugada) {
        this.ultimaJugada = ultimaJugada;
    }
}