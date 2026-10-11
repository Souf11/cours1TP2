package com.example.jpa.dao;

import com.example.jpa.entities.Utilisateur;
import javax.persistence.EntityManager;
import java.util.List;

public class UtilisateurDao extends GenericDao<Utilisateur> {

    public UtilisateurDao(EntityManager manager) {
        super(Utilisateur.class, manager);
    }

    public List<Utilisateur> findBySalleNom(String nomSalle) {
        String requete = "SELECT u FROM Utilisateur u "
                + "WHERE u.salle.nom = :nomSalle";

        return em.createQuery(requete, Utilisateur.class)
                .setParameter("nomSalle", nomSalle)
                .getResultList();
    }

    public List<Utilisateur> rechercherParNom(String nom) {
        String requete = "SELECT u FROM Utilisateur u "
                + "WHERE u.nom LIKE :pattern";

        return em.createQuery(requete, Utilisateur.class)
                .setParameter("pattern", "%" + nom + "%")
                .getResultList();
    }
}