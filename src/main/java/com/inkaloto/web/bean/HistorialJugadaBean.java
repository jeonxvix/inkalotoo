
package com.inkaloto.web.bean;

import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.servicio.JugadaService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("historialJugadaBean")
@RequestScoped
public class HistorialJugadaBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private List<Jugada> jugadas = new ArrayList<>();

    private final JugadaService jugadaService = new JugadaService();

    @PostConstruct
    public void init() {
        Usuario u = loginBean.getUsuarioLogueado();
        if (u == null) {
            System.out.println("HistorialJugadaBean: usuario no logueado.");
            return;
        }

        System.out.println("HistorialJugadaBean: cargando jugadas para usuario ID = " + u.getIdUsuario());
        jugadas = jugadaService.listarPorUsuario(u);
        System.out.println("HistorialJugadaBean: jugadas encontradas = " +
                (jugadas != null ? jugadas.size() : 0));
    }

    public List<Jugada> getJugadas() {
        return jugadas;
    }
}