/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_juego")
    private TipoJuego tipoJuego; // BINGO / SORTEO / SLOTS (puede ser null)

    @Column(name = "codigo_jugada")
    private String codigoJugada;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_notificacion", nullable = false)
    private LocalDateTime fechaNotificacion;

    @Column(name = "leido", nullable = false)
    private boolean leido;

    @Column(name = "eliminado", nullable = false)
    private boolean eliminado;

    public enum TipoJuego {
        BINGO, SORTEO, SLOTS
    }

    public Notificacion() {
        this.fechaNotificacion = LocalDateTime.now();
        this.leido = false;
        this.eliminado = false;
    }

    // ===== Getters / Setters =====
    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoJuego getTipoJuego() {
        return tipoJuego;
    }

    public void setTipoJuego(TipoJuego tipoJuego) {
        this.tipoJuego = tipoJuego;
    }

    public String getCodigoJugada() {
        return codigoJugada;
    }

    public void setCodigoJugada(String codigoJugada) {
        this.codigoJugada = codigoJugada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(LocalDateTime fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
