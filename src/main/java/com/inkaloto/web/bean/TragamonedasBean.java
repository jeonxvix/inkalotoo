/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.BilleteraService;
import com.inkaloto.servicio.JugadaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;

@Named("tragamonedasBean")
@RequestScoped
public class TragamonedasBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    // Configuración del juego
    private final BigDecimal COSTO_JUEGO      = new BigDecimal("1.00");
    private final BigDecimal PREMIO_NORMAL   = new BigDecimal("5.00");
    private final BigDecimal PREMIO_ESTRELLA = new BigDecimal("10.00");

    // Mensajes UI
    private String mensajeError;
    private String mensajeExito;

    // ? (signo de interrogación en emoji)  U+2754
    private String r1 = "\u2754";
    private String r2 = "\u2754";
    private String r3 = "\u2754";

    /*
      EMOJIS EN SECUENCIAS UNICODE:
      ?  U+1F352  ->  \uD83C\uDF52
      ?  U+2B50   ->  \u2B50
      ?  U+1F514  ->  \uD83D\uDD14
      ?  U+1F34B  ->  \uD83C\uDF4B
      ?  U+1F340  ->  \uD83C\uDF40
    */
    private final String[] simbolos = {
            "\uD83C\uDF52", // ?
            "\u2B50",       // ?
            "\uD83D\uDD14", // ?
            "\uD83C\uDF4B", // ?
            "\uD83C\uDF40"  // ?
    };

    public String jugar() {

        mensajeError = null;
        mensajeExito = null;

        Usuario u = loginBean.getUsuarioLogueado();
        if (u == null) {
            mensajeError = "Debes iniciar sesión para jugar.";
            return null;
        }

        try {
            BilleteraService billeteraService = new BilleteraService();
            JugadaService jugadaService = new JugadaService();

            String codigo = "SLOT-" + System.currentTimeMillis();
            String desc   = "Jugada de tragamonedas";

            // 1) Descontar saldo y registrar movimiento APUESTA
            MovimientoBilletera movApuesta =
                    billeteraService.registrarApuestaBingo(
                            u,
                            COSTO_JUEGO,
                            codigo,
                            desc
                    );

            // 2) Generar resultado aleatorio
            Random rnd = new Random();
            r1 = simbolos[rnd.nextInt(simbolos.length)];
            r2 = simbolos[rnd.nextInt(simbolos.length)];
            r3 = simbolos[rnd.nextInt(simbolos.length)];

            String detalle = r1 + "-" + r2 + "-" + r3;

            // 3) Registrar jugada TRAGAMONEDAS
            Jugada jugada = jugadaService.registrarJugadaTragamonedas(
                    u,
                    COSTO_JUEGO,
                    codigo,
                    detalle,
                    movApuesta
            );

            // 4) Evaluar si ganó (los 3 símbolos iguales)
            if (r1.equals(r2) && r2.equals(r3)) {

                // Premio especial si son 3 ?, en código \u2B50
                BigDecimal premio =
                        r1.equals("\u2B50") ? PREMIO_ESTRELLA : PREMIO_NORMAL;

                String codigoPremio = codigo + "-P";

                MovimientoBilletera movPremio =
                        billeteraService.registrarPremioBingo(
                                u,
                                premio,
                                codigoPremio,
                                "Premio tragamonedas"
                        );

                jugadaService.registrarPremioBingo(jugada, movPremio, premio);

                mensajeExito = "¡GANASTE! Premio: S/ " + premio + " — Resultado: " + detalle;

            } else {
                mensajeExito = "Sin premio esta vez. Resultado: " + detalle;
            }

        } catch (IllegalStateException e) {
            mensajeError = e.getMessage(); // saldo insuficiente, etc.
        } catch (Exception e) {
            mensajeError = "Error en el juego: " + e.getMessage();
            e.printStackTrace();
        }

        return null;
    }

    // ===== Getters para la vista =====

    public String getMensajeError() {
        return mensajeError;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public String getR1() {
        return r1;
    }

    public String getR2() {
        return r2;
    }

    public String getR3() {
        return r3;
    }

    public BigDecimal getCostoJuego() {
        return COSTO_JUEGO;
    }

    public BigDecimal getPremioNormal() {
        return PREMIO_NORMAL;
    }

    public BigDecimal getPremioEstrella() {
        return PREMIO_ESTRELLA;
    }
}
