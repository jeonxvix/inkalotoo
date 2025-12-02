/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "billetera_usuario")
public class BilleteraUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_billetera")
    private Integer idBilletera;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "saldo_actual", nullable = false)
    private BigDecimal saldoActual;

    public BilleteraUsuario() {
        this.saldoActual = BigDecimal.ZERO;
    }

    public Integer getIdBilletera() {
        return idBilletera;
    }
    public void setIdBilletera(Integer idBilletera) {
        this.idBilletera = idBilletera;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }
    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }
}
