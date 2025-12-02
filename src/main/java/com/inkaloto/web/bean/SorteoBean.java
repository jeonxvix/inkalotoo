/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.modelo.Sorteo;
import com.inkaloto.servicio.BilleteraService;
import com.inkaloto.servicio.JugadaService;
import com.inkaloto.servicio.SorteoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Named("sorteoBean")
@RequestScoped
public class SorteoBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private BigDecimal precio = new BigDecimal("2.00");

    // id del sorteo actual
    private Integer idSorteoActual;

    // FECHA del sorteo (cadena yyyy-MM-dd desde el input date)
    private String fechaSorteo;

    private String mensajeError;
    private String mensajeExito;

    private Jugada ultimaJugada;
    private String numerosGanadores;

    // Números que ingresa (o autogenera) el usuario
    private Integer n1, n2, n3, n4, n5, n6;

    // Texto de la jugada actual, ej: "5-12-23-31-38-44"
    private String numerosJugadaActual;

    // =========================
    //  Participar en el sorteo
    // =========================
    public String participarEnSorteo() {

        mensajeError = null;
        mensajeExito = null;
        // Reiniciamos los ganadores al comprar un boleto nuevo
        numerosGanadores = null;

        Usuario u = loginBean.getUsuarioLogueado();
        if (u == null) {
            mensajeError = "Debes iniciar sesión para participar en el sorteo.";
            return null;
        }

        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            mensajeError = "Precio de sorteo inválido.";
            return null;
        }

        // 1) Validar FECHA (no puede ser pasada)
        if (fechaSorteo == null || fechaSorteo.isBlank()) {
            mensajeError = "Selecciona la fecha del sorteo.";
            return null;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaSel;

        try {
            fechaSel = LocalDate.parse(fechaSorteo); // yyyy-MM-dd
        } catch (DateTimeParseException e) {
            mensajeError = "Fecha de sorteo inválida.";
            return null;
        }

        if (fechaSel.isBefore(hoy)) {
            mensajeError = "La fecha del sorteo debe ser hoy o una fecha futura.";
            return null;
        }

        // 2) Validar que los 6 números estén llenos
        if (n1 == null || n2 == null || n3 == null ||
            n4 == null || n5 == null || n6 == null) {
            mensajeError = "Completa los 6 números del sorteo.";
            return null;
        }

        // Construir el texto de la jugada
        numerosJugadaActual = n1 + "-" + n2 + "-" + n3 + "-" + n4 + "-" + n5 + "-" + n6;

        BigDecimal total = precio;

        try {
            System.out.println("=== INICIANDO PARTICIPACIÓN EN SORTEO ===");
            System.out.println("Usuario: " + u.getNombres());
            System.out.println("Total: " + total);
            System.out.println("Números jugados: " + numerosJugadaActual);
            System.out.println("Fecha sorteo: " + fechaSorteo);

            BilleteraService billeteraService = new BilleteraService();
            JugadaService jugadaService = new JugadaService();
            SorteoService sorteoService = new SorteoService();

            // 0) Obtenemos (o creamos) el sorteo PROGRAMADO actual
            Sorteo sorteoActual = sorteoService.obtenerOcrearSorteoActual();
            idSorteoActual = sorteoActual.getIdSorteo();
            System.out.println("Usando sorteo con id = " + idSorteoActual);

            String codigo = "SORTEO-" + System.currentTimeMillis();
            String descMovimiento = "Participación en sorteo";
            String detalleJugada = numerosJugadaActual; // se guarda en jugada.detalle_jugada

            // 1) Descontar saldo y crear movimiento APUESTA
            MovimientoBilletera movApuesta =
                    billeteraService.registrarApuestaBingo(u, total, codigo, descMovimiento);

            System.out.println("Apuesta sorteo registrada: " + movApuesta.getIdMovimiento());

            // 2) Registrar jugada en la tabla JUGADA asociada al sorteo
            ultimaJugada = jugadaService.registrarJugadaSorteo(
                    u,
                    total,
                    codigo,
                    detalleJugada,
                    movApuesta,
                    idSorteoActual
            );

            System.out.println("Jugada SORTEO registrada, id=" +
                    (ultimaJugada != null ? ultimaJugada.getIdJugada() : "null"));

            mensajeExito = "Te has inscrito en el sorteo. ¡Mucha suerte!";

            System.out.println("=== PARTICIPACIÓN EN SORTEO EXITOSA ===");

        } catch (IllegalStateException e) {
            System.out.println("ERROR saldo/apuesta sorteo: " + e.getMessage());
            mensajeError = e.getMessage();
        } catch (Exception e) {
            System.out.println("ERROR GENERAL sorteo: " + e.getMessage());
            e.printStackTrace();
            mensajeError = "Ocurrió un error al participar: " + e.getMessage();
        }

        return null;
    }

    // =========================
    //   Ejecutar el sorteo
    // =========================
    public String ejecutarSorteo() {
        mensajeError = null;
        mensajeExito = null;

        try {
            SorteoService sorteoService = new SorteoService();
            JugadaService jugadaService = new JugadaService();

            Sorteo sorteo;
            if (idSorteoActual != null) {
                sorteo = sorteoService.ejecutarSorteo(idSorteoActual);
            } else {
                sorteo = sorteoService.ejecutarSorteo(null);
                idSorteoActual = sorteo.getIdSorteo();
            }

            numerosGanadores = sorteo.getNumerosGanadores();
            mensajeExito = "Sorteo ejecutado. Números ganadores: " + numerosGanadores;

            System.out.println("Sorteo ejecutado. Ganadores: " + numerosGanadores);

            // ? Volver a cargar la última jugada del usuario para
            // que "Tu jugada" se siga mostrando después de ejecutar
            Usuario u = loginBean.getUsuarioLogueado();
            if (u != null) {
                List<Jugada> jugadas = jugadaService.listarPorUsuario(u);
                if (jugadas != null && !jugadas.isEmpty()) {
                    Jugada j = jugadas.get(0); // la más reciente (ya viene ordenada desc)
                    ultimaJugada = j;
                    numerosJugadaActual = j.getDetalleJugada();
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR al ejecutar sorteo: " + e.getMessage());
            e.printStackTrace();
            mensajeError = "Ocurrió un error al ejecutar el sorteo: " + e.getMessage();
        }

        return null;
    }

    // Getters / setters

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getIdSorteoActual() { return idSorteoActual; }
    public void setIdSorteoActual(Integer idSorteoActual) { this.idSorteoActual = idSorteoActual; }

    public String getFechaSorteo() { return fechaSorteo; }
    public void setFechaSorteo(String fechaSorteo) { this.fechaSorteo = fechaSorteo; }

    public String getMensajeError() { return mensajeError; }
    public String getMensajeExito() { return mensajeExito; }

    public Jugada getUltimaJugada() { return ultimaJugada; }
    public void setUltimaJugada(Jugada ultimaJugada) { this.ultimaJugada = ultimaJugada; }

    public String getNumerosGanadores() { return numerosGanadores; }
    public void setNumerosGanadores(String numerosGanadores) { this.numerosGanadores = numerosGanadores; }

    public Integer getN1() { return n1; }
    public void setN1(Integer n1) { this.n1 = n1; }

    public Integer getN2() { return n2; }
    public void setN2(Integer n2) { this.n2 = n2; }

    public Integer getN3() { return n3; }
    public void setN3(Integer n3) { this.n3 = n3; }

    public Integer getN4() { return n4; }
    public void setN4(Integer n4) { this.n4 = n4; }

    public Integer getN5() { return n5; }
    public void setN5(Integer n5) { this.n5 = n5; }

    public Integer getN6() { return n6; }
    public void setN6(Integer n6) { this.n6 = n6; }

    public String getNumerosJugadaActual() { return numerosJugadaActual; }
    public void setNumerosJugadaActual(String numerosJugadaActual) { this.numerosJugadaActual = numerosJugadaActual; }
}
