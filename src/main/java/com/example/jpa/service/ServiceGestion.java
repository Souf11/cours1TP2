package com.example.jpa.service;

import com.example.jpa.dao.SalleDao;
import com.example.jpa.dao.UtilisateurDao;
import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ServiceGestion {

    private final EntityManagerFactory factory;
    private final EntityManager manager;

    private final SalleDao salleDao;
    private final UtilisateurDao utilisateurDao;

    public ServiceGestion() {
        factory = Persistence.createEntityManagerFactory("gestionPU");
        manager = factory.createEntityManager();

        salleDao = new SalleDao(manager);
        utilisateurDao = new UtilisateurDao(manager);
    }

    public void ajouterSalle(Salle salle) {
        salleDao.create(salle);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.create(utilisateur);
    }

    public List<Salle> listerSalles() {
        return salleDao.findAll();
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurDao.findAll();
    }

    public List<Utilisateur> utilisateursParSalle(String nomSalle) {
        return utilisateurDao.findBySalleNom(nomSalle);
    }

    public List<Salle> rechercherGrandesSalles(int capaciteMin) {
        return salleDao.rechercherGrandesSalles(capaciteMin);
    }

    public List<Utilisateur> rechercherUtilisateurs(String nom) {
        return utilisateurDao.rechercherParNom(nom);
    }

    public void fermer() {
        if (manager.isOpen()) {
            manager.close();
        }

        if (factory.isOpen()) {
            factory.close();
        }
    }
}