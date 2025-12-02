/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.dao;

import com.inkaloto.modelo.Sorteo;
import com.inkaloto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SorteoDAO {

    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public Sorteo obtenerPorId(Integer idSorteo) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sorteo.class, idSorteo);
        } finally {
            em.close();
        }
    }

    public void crear(Sorteo sorteo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sorteo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Sorteo sorteo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sorteo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Devuelve el último sorteo PROGRAMADO para ese juego, o null si no hay.
     */
    public Sorteo buscarUltimoProgramadoPorJuego(Integer idJuego) {
        EntityManager em = getEntityManager();
        try {
            List<Sorteo> lista = em.createQuery(
                    "SELECT s FROM Sorteo s " +
                    "WHERE s.idJuego = :idJuego AND s.estadoSorteo = 'PROGRAMADO' " +
                    "ORDER BY s.fechaProgramada DESC",
                    Sorteo.class
            ).setParameter("idJuego", idJuego)
             .setMaxResults(1)
             .getResultList();

            return lista.isEmpty() ? null : lista.get(0);

        } finally {
            em.close();
        }
    }
}

