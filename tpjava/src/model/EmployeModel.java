// EmployeModel.java
package model;

import java.util.List;

import dao.EmployeDAOimpl;
import model.Employe.Role;
import model.Employe.Poste;

public class EmployeModel {
    private EmployeDAOimpl dao;

    public EmployeModel(EmployeDAOimpl dao) {
        this.dao = dao;
    }

    // Logique métier pour ajouter un employé
    public boolean add(String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        if (salaire <= 0) {
            System.out.println("Le salaire doit être supérieur à 0 !");
            return false;
        }

        if (email == null || !email.contains("@")) {
            System.out.println("L'email n'est pas valide !");
            return false;
        }

        Employe nouvelEmploye = new Employe(nom, prenom, email, telephone, salaire, role, poste);
        dao.add(nouvelEmploye);
        return true;
    }
    
    public List<Employe> findAll() {
        EmployeDAOimpl dao = new EmployeDAOimpl();
        return dao.findAll();
    }

}
