// Main.java
package main;

import dao.EmployeDAOimpl;
import model.EmployeModel;
import view.EmployeView;
import controller.EmployeController;

public class main {
    public static void main(String[] args) {
        EmployeView view = new EmployeView();
        EmployeDAOimpl dao = new EmployeDAOimpl();
        EmployeModel model = new EmployeModel(dao);
        new EmployeController(model, view);

        view.setVisible(true);
    }
}
