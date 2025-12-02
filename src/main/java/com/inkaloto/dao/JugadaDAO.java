
package com.inkaloto.dao;

import com.inkaloto.modelo.Jugada;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class JugadaDAO {

    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void guardar(Jugada jugada) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(jugada);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ? NUEVO: para actualizar jugada cuando tenga premio
    public void actualizar(Jugada jugada) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(jugada);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Jugada> listarPorUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            if (usuario == null || usuario.getIdUsuario() == null) {
                System.out.println("listarPorUsuario: usuario nulo o sin id");
                return new ArrayList<>();
            }

            System.out.println("Buscando jugadas para usuario ID: " + usuario.getIdUsuario());

            List<Jugada> resultado = em.createQuery(
                            "SELECT j FROM Jugada j " +
                                    "WHERE j.usuario.idUsuario = :idUsuario " +
                                    "ORDER BY j.fechaJugada DESC",
                            Jugada.class
                    )
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getResultList();

            System.out.println("Jugadas encontradas en BD: " + resultado.size());
            return resultado;

        } catch (Exception e) {
            System.out.println("ERROR en listarPorUsuario: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}