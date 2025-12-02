
package com.inkaloto.web.bean;

import com.inkaloto.dao.NotificacionDAO;
import com.inkaloto.modelo.Notificacion;
import com.inkaloto.modelo.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("notificacionesBean")
@RequestScoped
public class NotificacionesBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private final NotificacionDAO notificacionDAO = new NotificacionDAO();

    private List<Notificacion> notificaciones;

    @PostConstruct
    public void init() {
        try {
            System.out.println("=== INIT NotificacionesBean ===");

            if (loginBean == null) {
                System.out.println("loginBean es NULL en NotificacionesBean");
                notificaciones = Collections.emptyList();
                return;
            }

            Usuario u = loginBean.getUsuarioLogueado();
            if (u == null) {
                System.out.println("Usuario no logueado en NotificacionesBean");
                notificaciones = Collections.emptyList();
                return;
            }

            System.out.println("Cargando notificaciones para usuario ID = " + u.getIdUsuario());
            notificaciones = notificacionDAO.listarActivasPorUsuario(u);
            System.out.println("Notificaciones encontradas: " + notificaciones.size());

        } catch (Exception e) {
            System.out.println("ERROR en NotificacionesBean.init(): " + e.getMessage());
            e.printStackTrace();
            notificaciones = Collections.emptyList();
        }
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public int getCantidadNoLeidas() {
        if (notificaciones == null) return 0;
        return (int) notificaciones.stream()
                .filter(n -> !n.isLeido() && !n.isEliminado())
                .count();
    }

    public String marcarLeida(Integer id) {
        if (id == null) return null;
        try {
            Notificacion n = notificacionDAO.buscarPorId(id);
            if (n != null && !n.isEliminado()) {
                n.setLeido(true);
                notificacionDAO.actualizar(n);
            }
            init();
        } catch (Exception e) {
            System.out.println("ERROR al marcar notificación como leída: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String eliminar(Integer id) {
        if (id == null) return null;
        try {
            Notificacion n = notificacionDAO.buscarPorId(id);
            if (n != null) {
                n.setEliminado(true);
                notificacionDAO.actualizar(n);
            }
            init();
        } catch (Exception e) {
            System.out.println("ERROR al eliminar notificación: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
