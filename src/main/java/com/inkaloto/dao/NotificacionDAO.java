/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.dao;

import com.inkaloto.modelo.Notificacion;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NotificacionDAO {

    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void guardar(Notificacion n) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(n);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Notificacion n) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(n);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Notificacion buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacion.class, id);
        } finally {
            em.close();
        }
    }

    // Lista las no eliminadas de un usuario
    public List<Notificacion> listarActivasPorUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT n FROM Notificacion n " +
                    "WHERE n.usuario = :u AND n.eliminado = false " +
                    "ORDER BY n.fechaNotificacion DESC",
                    Notificacion.class
            ).setParameter("u", usuario)
             .getResultList();
        } finally {
            em.close();
        }
    }

    // Cantidad de no leídas (por si luego quieres usarla en el ?)
    public long contarNoLeidas(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(n) FROM Notificacion n " +
                    "WHERE n.usuario = :u AND n.leido = false AND n.eliminado = false",
                    Long.class
            ).setParameter("u", usuario)
             .getSingleResult();
        } finally {
            em.close();
        }
    }
}
