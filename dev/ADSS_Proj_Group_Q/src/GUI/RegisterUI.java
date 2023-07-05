package GUI;

import GUI.Controller.RegisterUIController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {
    private JLabel idLabel, nameLabel, bankCompanyLabel, bankBranchLabel, bankIdLabel, salaryLabel, termsLabel, stateLabel, passwordLabel;
    private JTextField idTextField, nameTextField, bankCompanyTextField, bankBranchTextField, bankIdTextField, salaryTextField, termsTextField, stateTextField, passwordTextField;
    private JButton registerHRButton, registerShipmentManagerButton, registerWorkerButton, backButton;
    private EmployeeService employeeService;

    public RegisterUI() {
        // Set up the JFrame
        setTitle("Register UI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        employeeService=new EmployeeService();

        // Create the components
        idLabel = new JLabel("ID:");
        nameLabel = new JLabel("Name:");
        bankCompanyLabel = new JLabel("Bank Company:");
        bankBranchLabel = new JLabel("Bank Branch:");
        bankIdLabel = new JLabel("Bank ID:");
        salaryLabel = new JLabel("Salary:");
        termsLabel = new JLabel("Terms:");
        stateLabel = new JLabel("State:");
        passwordLabel = new JLabel("Password:");

        idTextField = new JTextField(15);
        nameTextField = new JTextField(15);
        bankCompanyTextField = new JTextField(15);
        bankBranchTextField = new JTextField(15);
        bankIdTextField = new JTextField(15);
        salaryTextField = new JTextField(15);
        termsTextField = new JTextField(15);
        stateTextField = new JTextField(15);
        passwordTextField = new JTextField(15);

        registerHRButton = new JButton("Register HR");
        registerShipmentManagerButton = new JButton("Register Shipment Manager");
        registerWorkerButton = new JButton("Register Worker");
        backButton = new JButton("Back");

        // Create the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(bankCompanyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(bankCompanyTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(bankBranchLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(bankBranchTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bankIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(bankIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(salaryTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(termsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(termsTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(stateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(stateTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(passwordTextField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        if(employeeService.getHumanResourceManagerId().ReturnValue!=null)
            registerHRButton.setVisible(false);
        if(employeeService.getShipmentManagerId().ReturnValue!=null)
            registerShipmentManagerButton.setVisible(false);
        buttonPanel.add(registerHRButton);
        buttonPanel.add(registerShipmentManagerButton);
        buttonPanel.add(registerWorkerButton);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Add the back button to the top right corner
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(backButton, gbc);





        // Add the panel to the JFrame
        add(panel);
        RegisterUIController registerUIController=new RegisterUIController(this);
        // Set the JFrame visible
        setVisible(true);
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getBankCompanyTextField() {
        return bankCompanyTextField;
    }

    public JTextField getBankBranchTextField() {
        return bankBranchTextField;
    }

    public JTextField getBankIdTextField() {
        return bankIdTextField;
    }

    public JTextField getSalaryTextField() {
        return salaryTextField;
    }

    public JTextField getTermsTextField() {
        return termsTextField;
    }

    public JTextField getStateTextField() {
        return stateTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public JButton getRegisterHRButton() {
        return registerHRButton;
    }

    public JButton getRegisterShipmentManagerButton() {
        return registerShipmentManagerButton;
    }

    public JButton getRegisterWorkerButton() {
        return registerWorkerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegisterUI();
            }
        });
    }
}
