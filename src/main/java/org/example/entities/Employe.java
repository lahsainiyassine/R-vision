package org.example.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employes")
public class Employe extends User {

    private String matricule;

    public Employe() {
        super();
    }

    public Employe(String email, String password, String matricule) {
        super(email, password);
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", matricule='" + matricule + '\'' +
                '}';
    }
}
