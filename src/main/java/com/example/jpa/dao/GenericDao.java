package com.example.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GenericDao<T> {

    private final Class<T> type;
    protected final EntityManager em;

    public GenericDao(Class<T> type, EntityManager em) {
        this.type = type;
        this.em = em;
    }

    public void create(T object) {
        executeInTransaction(() -> em.persist(object));
    }

    public T find(Object id) {
        return em.find(type, id);
    }

    public T update(T object) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            T result = em.merge(object);
            transaction.commit();
            return result;
        } catch (RuntimeException ex) {
            rollbackIfNeeded(transaction);
            throw ex;
        }
    }

    public void delete(T object) {
        executeInTransaction(() -> {
            T managedObject = em.contains(object)
                    ? object
                    : em.merge(object);

            em.remove(managedObject);
        });
    }

    public List<T> findAll() {
        String jpql = "SELECT item FROM "
                + type.getSimpleName() + " item";

        return em.createQuery(jpql, type).getResultList();
    }

    private void executeInTransaction(Runnable operation) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            operation.run();
            transaction.commit();
        } catch (RuntimeException ex) {
            rollbackIfNeeded(transaction);
            throw ex;
        }
    }

    private void rollbackIfNeeded(EntityTransaction transaction) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }
}