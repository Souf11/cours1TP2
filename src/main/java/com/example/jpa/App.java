package com.example.jpa;

import com.example.jpa.entities.Salle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionPU");

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();

                Salle salle = new Salle("Salle A", 10);
                em.persist(salle);

                tx.commit();

                System.out.println("Salle créée avec succès !");
                System.out.println(salle);

            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                e.printStackTrace();
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }

            if (emf.isOpen()) {
                emf.close();
            }
        }
    }
}