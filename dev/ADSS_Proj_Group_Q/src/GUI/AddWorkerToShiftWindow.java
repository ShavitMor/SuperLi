package GUI;

import GUI.Controller.AddWorkerToShiftController;
import Model.ShiftModel;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddWorkerToShiftWindow extends JFrame {

    private JComboBox<String> roleComboBox;
    private JComboBox<String> employeeIdComboBox;
    private JButton addButton;
    public AddWorkerToShiftWindow(ShiftModel shiftModel){
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
       roleComboBox = new JComboBox<>();
        employeeIdComboBox = new JComboBox<>();
        roleComboBox.addItem("Shift manager");
        roleComboBox.addItem("Sales man");
        roleComboBox.addItem("Store keeper");
        roleComboBox.addItem("Security");
        roleComboBox.addItem("Clean");
        roleComboBox.addItem("Usher");
        roleComboBox.addItem("General");

        // Create an "Add" button
        addButton = new JButton("Add");

        // Create a panel to hold the combo boxes and button
        JPanel panel = new JPanel();
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(new JLabel("Employee ID:"));
        panel.add(employeeIdComboBox);
        panel.add(addButton);
        AddWorkerToShiftController addWorkerToShiftController=new AddWorkerToShiftController(this,shiftModel);
        // Add the panel to the workerView frame
        getContentPane().add(panel);

        // Display the workerView frame
        setVisible(true);

    }
    private String getRole(String role){
        switch (role) {
            case ("Shift manager"):
                return "shm";
            case ("Sales man"):
                return "sm";
            case ("Store keeper"):
                return "sk";
            case ("Security"):
                return "se";
            case ("Clean"):
                return "c";
            case ("Usher"):
                return "u";
            case ("General"):
                return "g";
        }
        return null;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JComboBox<String> getEmployeeIdComboBox() {
        return employeeIdComboBox;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
