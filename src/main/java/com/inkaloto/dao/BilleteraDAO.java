/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.dao;

import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.Usuario;
import com.inkaloto.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


@ApplicationScoped

public class BilleteraDAO {
 
    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }
    
     public BilleteraUsuario buscarPorUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BilleteraUsuario> q = em.createQuery(
                    "SELECT b FROM BilleteraUsuario b WHERE b.usuario = :u",
                    BilleteraUsuario.class
            );
            q.setParameter("u", usuario);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void crear(BilleteraUsuario b) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public BilleteraUsuario obtenerPorUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM BilleteraUsuario b WHERE b.usuario = :u",
                            BilleteraUsuario.class
                    ).setParameter("u", usuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void actualizar(BilleteraUsuario billetera) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(billetera);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
