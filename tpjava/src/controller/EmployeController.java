// EmployeController.java
package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import dao.EmployeDAOimpl;
import model.Employe;
import model.Employe.Poste;
import model.Employe.Role;
import model.EmployeModel;
import view.EmployeView;

public class EmployeController {
    private EmployeModel model;
    private EmployeView view;

    public EmployeController(EmployeModel model, EmployeView view) {
        this.model = model;
        this.view = view;
        this.view.btnAjouter.addActionListener(e -> addEmploye());
        this.view.btnModifier.addActionListener(e -> updateEmploye());
        this.view.btnAfficher.addActionListener(e -> afficherEmploye());
        this.view.btnSupprimer.addActionListener(e -> supprimerEmploye());
    }

    private void addEmploye() {
        String nom = view.getNom();
        String prenom = view.getPrenom();
        String email = view.getEmail();
        String telephone = view.getTelephone();
        double salaire;
        try {
            salaire = view.getSalaire();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Salaire invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Poste poste = view.getPoste();
        Role role = view.getRole();

        boolean add = model.add(nom, prenom, email, telephone, salaire, role, poste);
        if (add) {
            JOptionPane.showMessageDialog(view, "Employé ajouté avec succès !");
        } else {
            JOptionPane.showMessageDialog(view, "Échec de l'ajout de l'employé.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmploye() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un employé à modifier.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) view.table.getValueAt(selectedRow, 0);
        String nom = view.getNom();
        String prenom = view.getPrenom();
        String email = view.getEmail();
        String telephone = view.getTelephone();
        double salaire;
        try {
            salaire = view.getSalaire();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Salaire invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Poste poste = view.getPoste();
        Role role = view.getRole();

        Employe employe = new Employe(nom, prenom, email, telephone, salaire, role, poste);
        EmployeDAOimpl employeImpl = new EmployeDAOimpl();
        employeImpl.update(employe, id);

        JOptionPane.showMessageDialog(view, "Employé modifié avec succès !");
    }

    public void afficherEmploye() {
        List<Employe> employes = model.findAll();  // Vérifiez que la méthode findAll() existe dans EmployeModel
        DefaultTableModel tableModel = (DefaultTableModel) view.table.getModel();
        tableModel.setRowCount(0);  // Réinitialiser les lignes existantes

        for (Employe employe : employes) {
            tableModel.addRow(new Object[]{
                employe.getId(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getEmail(),
                employe.getTelephone(),
                employe.getSalaire(),
                employe.getRole(),
                employe.getPoste()
            });
        }
    }


    public void supprimerEmploye() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un employé à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) view.table.getValueAt(selectedRow, 0);
        EmployeDAOimpl employerDAOimpl = new EmployeDAOimpl();

        int confirmation = JOptionPane.showConfirmDialog(view, "Voulez-vous vraiment supprimer cet employé ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            employerDAOimpl.delete(id);
            JOptionPane.showMessageDialog(view, "Employé supprimé avec succès !");
            afficherEmploye();
        }
    }
}
