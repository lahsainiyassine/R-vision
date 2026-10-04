package org.example.test;

import org.example.entities.*;
import org.example.service.CategorieService;
import org.example.service.CommandeService;
import org.example.service.LigneCommandeProduitService;
import org.example.service.ProduitService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TestComplet {

    public static void main(String[] args) {
        System.out.println("========== 1. Test Generic DAO & Service CRUD (Categorie) ==========");
        CategorieService categorieService = new CategorieService();
        Categorie catElectronique = new Categorie("Electronique");
        Categorie catBureautique = new Categorie("Bureautique");

        categorieService.create(catElectronique);
        categorieService.create(catBureautique);
        System.out.println("Catégories créées : " + catElectronique.getId() + ", " + catBureautique.getId());

        List<Categorie> allCategories = categorieService.findAll();
        System.out.println("Nombre total de catégories : " + allCategories.size());

        System.out.println("\n========== 2. Test Entités Produit & Relations ==========");
        ProduitService produitService = new ProduitService();

        Produit p1 = new Produit("PC Portable Dell", 8500.0, new Date(), catElectronique);
        Produit p2 = new Produit("Souris Sans Fil", 150.0, new Date(), catElectronique);
        Produit p3 = new Produit("Chaise Ergonomique", 1200.0, new Date(), catBureautique);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        System.out.println("\n========== 3. Test Criteria API (Moteur de Recherche Dynamique) ==========");
        List<Produit> searchResults = produitService.search(catElectronique, 100.0, 9000.0, "portable");
        System.out.println("Résultats de recherche pour 'portable' dans Electronique :");
        for (Produit p : searchResults) {
            System.out.println(" - " + p.getNom() + " (" + p.getPrix() + " DH)");
        }

        System.out.println("\n========== 4. Test Pagination ==========");
        List<Produit> page1 = produitService.findPage(1, 2);
        System.out.println("Produits - Page 1 (taille 2) :");
        for (Produit p : page1) {
            System.out.println(" - " + p.getNom());
        }

        System.out.println("\n========== 5. Test Héritage JPA (@Inheritance JOINED) ==========");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Client client = new Client("client1@example.com", "pass123", "Alami", "Yassine");
        Employe employe = new Employe("employe1@example.com", "pass456", "EMP-2026-001");
        session.save(client);
        session.save(employe);

        System.out.println("\n========== 6. Test N:N avec Entité Associative (LigneCommandeProduit) ==========");
        Commande commande = new Commande(new Date(), client);
        session.save(commande);

        LigneCommandeProduit lcp1 = new LigneCommandeProduit(commande, p1, 2, 8500.0);
        LigneCommandeProduit lcp2 = new LigneCommandeProduit(commande, p2, 5, 150.0);
        session.save(lcp1);
        session.save(lcp2);

        tx.commit();
        session.close();

        System.out.println("\n========== 7. Test Calcul du Chiffre d'Affaires Global (HQL) ==========");
        CommandeService commandeService = new CommandeService();
        double caTotal = commandeService.getChiffreAffairesTotal();
        System.out.println("Chiffre d'Affaires Total : " + caTotal + " DH");

        HibernateUtil.shutdown();
        System.out.println("\n✅ Tous les tests sont terminés avec succès !");
    }
}
