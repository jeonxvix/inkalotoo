/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inkaloto.dao;

import com.inkaloto.modelo.Usuario;
import com.inkaloto.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UsuarioDAO {

    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public Usuario buscarPorDocumentoYContrasena(String numeroDocumento, String contrasenaHash) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.numero_documento = :doc AND u.contrasena_hash = :pass",
                Usuario.class
            );
            query.setParameter("doc", numeroDocumento);
            query.setParameter("pass", contrasenaHash);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existeDocumento(String numeroDocumento) {
        EntityManager em = getEntityManager();
        try {
            Long conteo = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.numero_documento = :doc",
                Long.class
            ).setParameter("doc", numeroDocumento)
             .getSingleResult();
            return conteo != null && conteo > 0;
        } finally {
            em.close();
        }
    }

    public boolean existeCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            Long conteo = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo",
                Long.class
            ).setParameter("correo", correo)
             .getSingleResult();
            return conteo != null && conteo > 0;
        } finally {
            em.close();
        }
    }

    public void registrar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // NUEVO: actualizar
    public void actualizar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
