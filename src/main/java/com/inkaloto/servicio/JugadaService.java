package com.inkaloto.servicio;

import com.inkaloto.dao.JugadaDAO;
import com.inkaloto.dao.NotificacionDAO;
import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Notificacion;
import com.inkaloto.modelo.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class JugadaService {

    private final JugadaDAO jugadaDAO = new JugadaDAO();
    private final NotificacionDAO notificacionDAO = new NotificacionDAO();

    // IDs del juego
    private static final int ID_JUEGO_BINGO         = 1;
    private static final int ID_JUEGO_SORTEO        = 2;
    private static final int ID_JUEGO_TRAGAMONEDAS  = 3;

    // ==========================================================
    //   JUGADAS — BINGO
    // ==========================================================
    public Jugada registrarJugadaBingo(Usuario usuario,
                                       BigDecimal montoApostado,
                                       String codigoJugada,
                                       String detalleJugada,
                                       MovimientoBilletera movApuesta) {

        if (usuario == null || movApuesta == null) return null;

        try {
            Jugada j = new Jugada();
            j.setUsuario(usuario);
            j.setIdJuego(ID_JUEGO_BINGO);
            j.setIdSorteo(null);
            j.setCodigoJugada(codigoJugada);
            j.setDetalleJugada(detalleJugada);
            j.setMontoApostado(montoApostado);
            j.setMovimientoApuesta(movApuesta);
            j.setMovimientoPremio(null);

            jugadaDAO.guardar(j);
            return j;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================================
    //   JUGADAS — SORTEO
    // ==========================================================
    public Jugada registrarJugadaSorteo(Usuario usuario,
                                        BigDecimal montoApostado,
                                        String codigoJugada,
                                        String detalleJugada,
                                        MovimientoBilletera movApuesta,
                                        Integer idSorteo) {

        if (usuario == null || movApuesta == null) return null;

        try {
            Jugada j = new Jugada();
            j.setUsuario(usuario);
            j.setIdJuego(ID_JUEGO_SORTEO);
            j.setIdSorteo(idSorteo);
            j.setCodigoJugada(codigoJugada);
            j.setDetalleJugada(detalleJugada);
            j.setMontoApostado(montoApostado);
            j.setMovimientoApuesta(movApuesta);
            j.setMovimientoPremio(null);

            jugadaDAO.guardar(j);
            return j;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================================
    //   JUGADAS — TRAGAMONEDAS
    // ==========================================================
    public Jugada registrarJugadaTragamonedas(Usuario usuario,
                                              BigDecimal montoApostado,
                                              String codigoJugada,
                                              String detalleJugada,
                                              MovimientoBilletera movApuesta) {

        if (usuario == null || movApuesta == null) return null;

        try {
            Jugada j = new Jugada();
            j.setUsuario(usuario);
            j.setIdJuego(ID_JUEGO_TRAGAMONEDAS);
            j.setIdSorteo(null);
            j.setCodigoJugada(codigoJugada);
            j.setDetalleJugada(detalleJugada);
            j.setMontoApostado(montoApostado);
            j.setMovimientoApuesta(movApuesta);
            j.setMovimientoPremio(null);

            jugadaDAO.guardar(j);
            return j;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================================
    //   REGISTRAR PREMIO + NOTIFICACIÓN
    // ==========================================================
    public void registrarPremioBingo(Jugada jugada,
                                     MovimientoBilletera movPremio,
                                     BigDecimal montoPremio) {

        if (jugada == null || movPremio == null) return;

        if (montoPremio == null) {
            montoPremio = BigDecimal.ZERO;
        }

        // Actualizar jugada
        jugada.setGano(true);
        jugada.setMontoGanado(montoPremio);
        jugada.setMovimientoPremio(movPremio);

        jugadaDAO.actualizar(jugada);

        // Crear la notificación
        try {
            Notificacion noti = new Notificacion();
            noti.setUsuario(jugada.getUsuario());
            noti.setCodigoJugada(jugada.getCodigoJugada());

            // Tipo de juego
            if (jugada.getIdJuego() == ID_JUEGO_BINGO)
                noti.setTipoJuego(Notificacion.TipoJuego.BINGO);
            else if (jugada.getIdJuego() == ID_JUEGO_SORTEO)
                noti.setTipoJuego(Notificacion.TipoJuego.SORTEO);
            else if (jugada.getIdJuego() == ID_JUEGO_TRAGAMONEDAS)
                noti.setTipoJuego(Notificacion.TipoJuego.SLOTS);

            noti.setTitulo("¡Has ganado un premio!");
            noti.setMensaje("Ganaste S/ " + montoPremio +
                    " (código " + jugada.getCodigoJugada() + ").");

            notificacionDAO.guardar(noti);

        } catch (Exception e) {
            System.out.println("Error al generar notificación: " + e.getMessage());
        }
    }

    // ==========================================================
    //   LISTAR
    // ==========================================================
    public List<Jugada> listarPorUsuario(Usuario usuario) {
        return jugadaDAO.listarPorUsuario(usuario);
    }
}
