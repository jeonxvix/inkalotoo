/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sorteo")
public class Sorteo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sorteo")
    private Integer idSorteo;

    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada;

    @Column(name = "numeros_ganadores")
    private String numerosGanadores;

    @Column(name = "estado_sorteo", nullable = false)
    private String estadoSorteo; // PROGRAMADO, EJECUTADO, CANCELADO

    public Sorteo() {
        this.fechaProgramada = LocalDateTime.now();
        this.estadoSorteo = "PROGRAMADO";
    }

    public Integer getIdSorteo() {
        return idSorteo;
    }

    public void setIdSorteo(Integer idSorteo) {
        this.idSorteo = idSorteo;
    }

    public Integer getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(Integer idJuego) {
        this.idJuego = idJuego;
    }

    public LocalDateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getNumerosGanadores() {
        return numerosGanadores;
    }

    public void setNumerosGanadores(String numerosGanadores) {
        this.numerosGanadores = numerosGanadores;
    }

    public String getEstadoSorteo() {
        return estadoSorteo;
    }

    public void setEstadoSorteo(String estadoSorteo) {
        this.estadoSorteo = estadoSorteo;
    }
}
