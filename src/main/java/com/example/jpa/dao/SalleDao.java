package com.example.jpa.dao;

import com.example.jpa.entities.Salle;
import javax.persistence.EntityManager;
import java.util.List;

public class SalleDao extends GenericDao<Salle> {

    public SalleDao(EntityManager manager) {
        super(Salle.class, manager);
    }

    public List<Salle> findByCapaciteMin(int capaciteMin) {
        String jpql = "SELECT s FROM Salle s WHERE s.capacite >= :capaciteMin";

        return em.createQuery(jpql, Salle.class)
                .setParameter("capaciteMin", capaciteMin)
                .getResultList();
    }

    public List<Salle> rechercherGrandesSalles(int minCapacite) {
        String jpql = "SELECT s FROM Salle s WHERE s.capacite > :minCapacite";

        return em.createQuery(jpql, Salle.class)
                .setParameter("minCapacite", minCapacite)
                .getResultList();
    }
}
