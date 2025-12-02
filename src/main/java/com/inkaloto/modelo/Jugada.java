/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;  // ? CAMBIA de LocalDateTime a Date

@Entity
@Table(name = "jugada")
public class Jugada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugada")
    private Integer idJugada;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Por ahora lo usamos como entero (id del juego BINGO)
    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @Column(name = "id_sorteo")
    private Integer idSorteo; // lo dejamos null para bingo online simple

    @Column(name = "codigo_jugada", nullable = false, length = 30)
    private String codigoJugada;

    @Column(name = "detalle_jugada", nullable = false, length = 255)
    private String detalleJugada;

    @Column(name = "monto_apostado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoApostado;

    @Column(name = "gano", nullable = false)
    private boolean gano;

    @Column(name = "monto_ganado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoGanado;

    @ManyToOne
    @JoinColumn(name = "id_mov_apuesta", nullable = false)
    private MovimientoBilletera movimientoApuesta;

    @ManyToOne
    @JoinColumn(name = "id_mov_premio")
    private MovimientoBilletera movimientoPremio;

    @Column(name = "fecha_jugada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)  // ? AGREGA esta anotación
    private Date fechaJugada;  // ? CAMBIA a Date

    public Jugada() {
        this.fechaJugada = new Date();  // ? new Date() en lugar de LocalDateTime.now()
        this.gano = false;
        this.montoGanado = BigDecimal.ZERO;
    }

    // ===== GETTERS / SETTERS =====
    public Integer getIdJugada() { return idJugada; }
    public void setIdJugada(Integer idJugada) { this.idJugada = idJugada; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Integer getIdJuego() { return idJuego; }
    public void setIdJuego(Integer idJuego) { this.idJuego = idJuego; }

    public Integer getIdSorteo() { return idSorteo; }
    public void setIdSorteo(Integer idSorteo) { this.idSorteo = idSorteo; }

    public String getCodigoJugada() { return codigoJugada; }
    public void setCodigoJugada(String codigoJugada) { this.codigoJugada = codigoJugada; }

    public String getDetalleJugada() { return detalleJugada; }
    public void setDetalleJugada(String detalleJugada) { this.detalleJugada = detalleJugada; }

    public BigDecimal getMontoApostado() { return montoApostado; }
    public void setMontoApostado(BigDecimal montoApostado) { this.montoApostado = montoApostado; }

    public boolean isGano() { return gano; }
    public void setGano(boolean gano) { this.gano = gano; }

    public BigDecimal getMontoGanado() { return montoGanado; }
    public void setMontoGanado(BigDecimal montoGanado) { this.montoGanado = montoGanado; }

    public MovimientoBilletera getMovimientoApuesta() { return movimientoApuesta; }
    public void setMovimientoApuesta(MovimientoBilletera movimientoApuesta) { this.movimientoApuesta = movimientoApuesta; }

    public MovimientoBilletera getMovimientoPremio() { return movimientoPremio; }
    public void setMovimientoPremio(MovimientoBilletera movimientoPremio) { this.movimientoPremio = movimientoPremio; }

    // GETTER/SETTER para Date (REEMPLAZA los de LocalDateTime)
    public Date getFechaJugada() {
        return fechaJugada;
    }

    public void setFechaJugada(Date fechaJugada) {
        this.fechaJugada = fechaJugada;
    }
}