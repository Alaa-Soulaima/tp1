// Connexion.java
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String URL = "jdbc:mysql://localhost:3306/Employe";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection conn = null;

    public static Connection getConnexion() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion établie avec succès !");
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'établissement de la connexion : " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Connexion fermée avec succès !");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
