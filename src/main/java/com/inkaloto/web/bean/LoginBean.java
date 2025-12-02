/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.web.bean;

import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.UsuarioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String numeroDocumento;
    private String password;

    private Usuario usuarioLogueado;
    private String mensajeError;

    private final UsuarioService usuarioService = new UsuarioService();

    public String iniciarSesion() {
        mensajeError = null;

        Usuario u = usuarioService.login(numeroDocumento, password);

        if (u != null) {
            this.usuarioLogueado = u;
            // Redirigir a "Mi Cuenta" (luego ajustamos el nombre de la página)
            return "micuenta?faces-redirect=true";
        } else {
            mensajeError = "Documento o contraseña incorrectos.";
            return null; // permanece en la misma página
        }
    }

public String logout() {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/login.xhtml?faces-redirect=true";
}



    // ==== Getters y Setters ====

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
