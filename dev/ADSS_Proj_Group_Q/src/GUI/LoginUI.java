package GUI;

import BusinessLayer.LoadData;
import Controller.LoginController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private JLabel loginIdLabel, passwordLabel;
    private JTextField loginIdTextField, passwordTextField;
    private JButton registerButton, loginButton;
    private EmployeeService employeeService;
    public LoginUI() {
        // Set up the JFrame
        setTitle("Login UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        Response res = ServiceFactory.getInstance().loadData();
        if(res.isErrorResponse()) {
            JOptionPane.showMessageDialog(this, res.ErrorMsg);
            dispose();
        }
        employeeService=new EmployeeService();


        // Create the components
        loginIdLabel = new JLabel("Login ID:");
        passwordLabel = new JLabel("Password:");
        loginIdTextField = new JTextField(15);
        passwordTextField = new JPasswordField(15);
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        // Create the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(loginIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(loginButton, gbc);
        Response response=employeeService.getShipmentManagerId();
        Response response1=employeeService.getHumanResourceManagerId();
        if(response.ReturnValue!=null&&response1.ReturnValue!=null)
            registerButton.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(registerButton, gbc);
        LoginController loginController = new LoginController(this);
        // Add the panel to the JFrame
        add(panel);
        // Set the JFrame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getLoginIdTextField() {
        return loginIdTextField.getText();
    }

    public String getPasswordTextField() {
        return passwordTextField.getText();
    }
}
