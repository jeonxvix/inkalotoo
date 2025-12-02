/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.modelo.Usuario;
import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.servicio.BilleteraService;
import com.inkaloto.servicio.UsuarioService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("miCuentaBean")
@RequestScoped
public class MiCuentaBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private final BilleteraService billeteraService = new BilleteraService();
    private final UsuarioService usuarioService = new UsuarioService();

    private List<String> premiosDisponibles = new ArrayList<>();
    private String mensajeExito;
    private String mensajeError;

    public Usuario getUsuario() {
        return loginBean.getUsuarioLogueado();
    }

    public BilleteraUsuario getBilletera() {
        Usuario usuario = getUsuario();
        if (usuario != null && usuario.getIdUsuario() != null) {
            return billeteraService.obtenerPorUsuario(usuario);
        }
        return null;
    }

    public List<String> getPremiosDisponibles() {
        return premiosDisponibles;
    }

    public boolean getUsuarioLogueado() {
        return getUsuario() != null;
    }

    // ? ESTE ES EL MÉTODO QUE JSF ESTÁ BUSCANDO
    public String actualizarPerfil() {
        mensajeExito = null;
        mensajeError = null;

        try {
            Usuario u = getUsuario();
            if (u == null) {
                mensajeError = "Tu sesión ha expirado. Inicia sesión nuevamente.";
                return null;
            }

            usuarioService.actualizarUsuario(u);
            mensajeExito = "Datos actualizados correctamente.";
        } catch (Exception e) {
            mensajeError = "Ocurrió un error al actualizar tus datos.";
        }
        return null; // se queda en la misma página
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
