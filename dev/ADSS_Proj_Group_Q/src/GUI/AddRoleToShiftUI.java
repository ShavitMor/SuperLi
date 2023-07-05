package GUI;

import GUI.Controller.AddRoleToShiftController;
import Model.ShiftModel;

import javax.swing.*;

public class AddRoleToShiftUI extends JFrame {
    private  JComboBox<String> roleComboBox;
    private JComboBox<Integer> amount;
    private JButton addButton;
    public AddRoleToShiftUI(ShiftModel shiftModel){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);

        // Create the label for role selection
        JLabel roleLabel = new JLabel("Role:");
        JLabel amountLabel=new JLabel("amount:");
        // Create the role combo box
        roleComboBox = new JComboBox<>();
        // Add your role options to the combo box, for example:
        roleComboBox.addItem("Shift manager");
        roleComboBox.addItem("Sales man");
        roleComboBox.addItem("Store keeper");
        roleComboBox.addItem("Security");
        roleComboBox.addItem("Clean");
        roleComboBox.addItem("Usher");
        roleComboBox.addItem("General");
        amount=new JComboBox<>();
        for(int i=1;i<=10;i++)
            amount.addItem(i);
        // Create an "Add" button
       addButton = new JButton("Add");

        // Create a panel to hold the labels, combo box, and button
        JPanel panel = new JPanel();
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(amountLabel);
        panel.add(amount);
        panel.add(addButton);

        // Add the panel to the roleView frame
        getContentPane().add(panel);
        AddRoleToShiftController addRoleToShiftController=new AddRoleToShiftController(this,shiftModel);
        // Display the roleView frame
        setVisible(true);
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JComboBox<Integer> getAmount() {
        return amount;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
