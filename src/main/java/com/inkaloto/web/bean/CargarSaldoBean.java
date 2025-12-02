/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.MovimientoBilleteraService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;

@Named("cargarSaldoBean")
@RequestScoped
public class CargarSaldoBean implements Serializable {

    private BigDecimal monto;
    private String metodo;

    private String mensajeExito;
    private String mensajeError;

    @Inject
    private LoginBean loginBean;

    private final MovimientoBilleteraService movimientoService = new MovimientoBilleteraService();

    public String confirmarRecarga() {
        mensajeExito = null;
        mensajeError = null;

        try {
            Usuario usuario = loginBean.getUsuarioLogueado();
            if (usuario == null) {
                mensajeError = "Tu sesión ha expirado. Inicia sesión nuevamente.";
                return "login?faces-redirect=true";
            }

            MovimientoBilletera mov =
                    movimientoService.registrarRecarga(usuario, monto, metodo);

            // Opcional: mensaje flash si te quedas en la misma página
            mensajeExito = "Recarga realizada con éxito. Código: " + mov.getCodigoMovimiento();

            // Redirigir al comprobante
            return "comprobanteRecarga.xhtml?id=" + mov.getIdMovimiento() + "&faces-redirect=true";

        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
            return null;

        } catch (Exception e) {
            mensajeError = "Ocurrió un error al procesar la recarga.";
            return null;
        }
    }

    // Getters y setters

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getMensajeExito() { return mensajeExito; }
    public void setMensajeExito(String mensajeExito) { this.mensajeExito = mensajeExito; }

    public String getMensajeError() { return mensajeError; }
    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }
}
