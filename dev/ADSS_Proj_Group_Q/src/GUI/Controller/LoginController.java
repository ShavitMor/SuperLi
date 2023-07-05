package Controller;

import GUI.EmployeeUI;
import GUI.HrUI;
import GUI.LoginUI;
import GUI.RegisterUI;
import PresentationLayer.GUI.MainShippingGUI.MainShippingView;
import PresentationLayer.HRUI;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginUI loginUI;
    private EmployeeService employeeService;

    public LoginController(LoginUI loginUI) {
        this.loginUI = loginUI;
        employeeService = new EmployeeService();

        // Add action listeners to the buttons
        loginUI.getRegisterButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle the register button click
                RegisterUI registerUI = new RegisterUI();
                registerUI.setVisible(true);
                loginUI.dispose();
            }
        });

        loginUI.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle the login button click
                try {
                    String id = loginUI.getLoginIdTextField();
                    String password = loginUI.getPasswordTextField();
                    Response res = employeeService.login(id, password);
                    if (!res.isErrorResponse()) {
                        if (employeeService.getHumanResourceManagerId().ReturnValue!=null&&employeeService.getHumanResourceManagerId().ReturnValue.equals(id)) {
                            HrUI hrUI = new HrUI(id);
                            hrUI.setVisible(true);
                            loginUI.dispose();
                        } else {
                            if (employeeService.getShipmentManagerId().ReturnValue!=null&&employeeService.getShipmentManagerId().ReturnValue.equals(id)) {
                                MainShippingView.run();
                            } else {
                                EmployeeUI empUI = new EmployeeUI(id);
                                empUI.setVisible(true);
                                loginUI.dispose();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginUI, res.ErrorMsg);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginUI, ex.getMessage());
                }
            }
        });
    }

}
