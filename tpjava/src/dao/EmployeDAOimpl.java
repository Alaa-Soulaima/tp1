// EmployeDAOimpl.java
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Employe;
import model.Employe.Poste;
import model.Employe.Role;

public class EmployeDAOimpl implements EmployeDAOI {
    private Connection conn;

    public EmployeDAOimpl() {
        this.conn = Connexion.getConnexion();
    }

    @Override
    public void add(Employe E) {
        String query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, E.getNom());
            stmt.setString(2, E.getPrenom());
            stmt.setString(3, E.getEmail());
            stmt.setString(4, E.getTelephone());
            stmt.setDouble(5, E.getSalaire());
            stmt.setString(6, E.getRole().name());
            stmt.setString(7, E.getPoste().name());
            stmt.executeUpdate();
            System.out.println("Employé ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'employé : " + e.getMessage());
        }
    }

    @Override
    public Employe findById(int employeId) {
        String query = "SELECT * FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDouble("salaire"),
                    Role.valueOf(rs.getString("role")),
                    Poste.valueOf(rs.getString("poste"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'employé par ID : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Employe> findAll() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM Employe";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employes.add(new Employe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDouble("salaire"),
                    Role.valueOf(rs.getString("role")),
                    Poste.valueOf(rs.getString("poste"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les employés : " + e.getMessage());
        }
        return employes;
    }

    @Override
    public void update(Employe E, int id) {
        String query = "UPDATE Employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, E.getNom());
            stmt.setString(2, E.getPrenom());
            stmt.setString(3, E.getEmail());
            stmt.setString(4, E.getTelephone());
            stmt.setDouble(5, E.getSalaire());
            stmt.setString(6, E.getRole().name());
            stmt.setString(7, E.getPoste().name());
            stmt.setInt(8, id);
            stmt.executeUpdate();
            System.out.println("Employé modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'employé : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Employé supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'employé : " + e.getMessage());
        }
    }

    @Override
    public List<Role> findAllRoles() {
        return Arrays.asList(Role.values());
    }

    @Override
    public List<Poste> findAllPostes() {
        return Arrays.asList(Poste.values());
    }

    @Override
    public void updateSpecificField(int id, String fieldName, Object newValue) {
        String query = "UPDATE Employe SET " + fieldName + " = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, newValue);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Champ mis à jour avec succès pour l'ID : " + id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour d'un champ : " + e.getMessage());
        }
    }
}
