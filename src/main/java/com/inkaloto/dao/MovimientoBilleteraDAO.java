
package com.inkaloto.dao;

import com.inkaloto.modelo.BilleteraUsuario;
import com.inkaloto.modelo.MovimientoBilletera;
import com.inkaloto.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class MovimientoBilleteraDAO {

    private EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void guardar(MovimientoBilletera mov) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mov);
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

    public MovimientoBilletera buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimientoBilletera.class, id);
        } finally {
            em.close();
        }
    }

    /** Lista TODOS los movimientos de una billetera (recarga, retiro, apuestas, premios, etc.) */
    public List<MovimientoBilletera> listarPorBilletera(BilleteraUsuario billetera) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT m FROM MovimientoBilletera m " +
                    "WHERE m.billetera = :b " +
                    "ORDER BY m.fechaMovimiento DESC",
                    MovimientoBilletera.class
            )
            .setParameter("b", billetera)
            .getResultList();
        } finally {
            em.close();
        }
    }

    /** SOLO RECARGA y RETIRO para el historial de transacciones */
    public List<MovimientoBilletera> listarSoloRecargaYRetiro(BilleteraUsuario billetera) {
        EntityManager em = getEntityManager();
        try {
            List<MovimientoBilletera> lista = em.createQuery(
                    "SELECT m FROM MovimientoBilletera m " +
                    "WHERE m.billetera = :b " +
                    "AND (m.tipoMovimiento = :recarga OR m.tipoMovimiento = :retiro) " +
                    "ORDER BY m.fechaMovimiento DESC",
                    MovimientoBilletera.class
            )
            .setParameter("b", billetera)
            .setParameter("recarga", MovimientoBilletera.TipoMovimiento.RECARGA)
            .setParameter("retiro", MovimientoBilletera.TipoMovimiento.RETIRO)
            .getResultList();

            System.out.println("Movimientos RECARGA/RETIRO encontrados: " + lista.size());
            return lista;

        } finally {
            em.close();
        }
    }
// ? NUEVO: SOLO PREMIOS (para historial de premios)
    public List<MovimientoBilletera> listarPremios(BilleteraUsuario billetera) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT m FROM MovimientoBilletera m " +
                    "WHERE m.billetera = :b " +
                    "AND m.tipoMovimiento = :premio " +
                    "ORDER BY m.fechaMovimiento DESC",
                    MovimientoBilletera.class
            )
            .setParameter("b", billetera)
            .setParameter("premio", MovimientoBilletera.TipoMovimiento.PREMIO)
            .getResultList();
        } finally {
            em.close();
        }
    }
}
