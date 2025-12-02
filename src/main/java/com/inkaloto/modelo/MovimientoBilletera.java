
package com.inkaloto.modelo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "movimiento_billetera")
public class MovimientoBilletera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @ManyToOne
    @JoinColumn(name = "id_billetera", nullable = false)
    private BilleteraUsuario billetera;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "signo", nullable = false)
    private Integer signo; // 1 = suma, -1 = resta

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "codigo_movimiento", nullable = false, length = 30)
    private String codigoMovimiento;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    public MovimientoBilletera() {
        this.fechaMovimiento = LocalDateTime.now();
    }

    public enum TipoMovimiento {
        RECARGA, APUESTA, PREMIO, RETIRO, AJUSTE
    }

    public enum MetodoPago {
        TARJETA, YAPE_PLIN, TRANSFERENCIA, BILLETERA_DIGITAL
    }

    // Getters y setters

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public BilleteraUsuario getBilletera() {
        return billetera;
    }

    public void setBilletera(BilleteraUsuario billetera) {
        this.billetera = billetera;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getSigno() {
        return signo;
    }

    public void setSigno(Integer signo) {
        this.signo = signo;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public void setCodigoMovimiento(String codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    // ? NUEVO: fecha formateada para usar en JSF
    public String getFechaFormateada() {
        if (fechaMovimiento == null) return "";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaMovimiento.format(fmt);
    }
}
