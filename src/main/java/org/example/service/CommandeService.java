package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.Commande;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

public class CommandeService extends AbstractFacade<Commande> {

    public CommandeService() {
        super(Commande.class);
    }

    public double getChiffreAffairesTotal() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Double total = session.createQuery(
                    "SELECT SUM(l.quantite * l.prixVente) FROM LigneCommandeProduit l", Double.class
            ).getSingleResult();
            return total != null ? total : 0.0;
        } finally {
            session.close();
        }
    }
}
