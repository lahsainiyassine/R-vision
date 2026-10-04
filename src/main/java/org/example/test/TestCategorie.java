package org.example.test;

import org.example.entities.Categorie;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestCategorie {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Categorie c = new Categorie("Ordinateurs");
        session.save(c); // Hibernate prépare le SQL INSERT

        tx.commit(); // Validation en BDD
        session.close();

        System.out.println("✅ Catégorie insérée avec l'ID : " + c.getId());
        HibernateUtil.shutdown();
    }
}
