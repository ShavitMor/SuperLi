package GUI.Controller;

import GUI.HrUI;
import GUI.LoginUI;
import GUI.ManageEmpUI;
import GUI.RegisterUI;
import PresentationLayer.GUI.MainShippingGUI.MainShippingView;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUIController {
    EmployeeService employeeService;
    public RegisterUIController(RegisterUI registerUI){
        // Add action listeners to the buttons
        employeeService=new EmployeeService();
        registerUI.getRegisterHRButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = registerUI.getIdTextField().getText();
                    String name = registerUI.getNameTextField().getText();
                    String bankCompany = registerUI.getBankCompanyTextField().getText();
                    int bankBranch = Integer.parseInt(registerUI.getBankBranchTextField().getText());
                    int bankId = Integer.parseInt(registerUI.getBankIdTextField().getText());
                    double salary = Double.parseDouble(registerUI.getSalaryTextField().getText());
                    String terms = registerUI.getTermsTextField().getText();
                    String state = registerUI.getStateTextField().getText();
                    String password = registerUI.getPasswordTextField().getText();
                    Response res=employeeService.humanResourceManagerRegister(id,name,bankCompany,bankBranch,bankId,salary,terms,state,password);
                    if(!res.isErrorResponse()){
                        HrUI hrUI = new HrUI(id);
                        hrUI.setVisible(true);
                        JOptionPane.showMessageDialog(registerUI, "HR Registered");
                        employeeService.login(id,password);
                        registerUI.dispose();

                    }
                    else {
                        JOptionPane.showMessageDialog(registerUI, res.ErrorMsg);
                    }

                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(registerUI, ex.getMessage());

                }

            }

        });

        registerUI.getRegisterShipmentManagerButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle the register Shipment Manager button click
                try {


                    String id = registerUI.getIdTextField().getText();
                    String name = registerUI.getNameTextField().getText();
                    String bankCompany = registerUI.getBankCompanyTextField().getText();
                    int bankBranch = Integer.parseInt(registerUI.getBankBranchTextField().getText());
                    int bankId = Integer.parseInt(registerUI.getBankIdTextField().getText());
                    double salary = Double.parseDouble(registerUI.getSalaryTextField().getText());
                    String terms = registerUI.getTermsTextField().getText();
                    String state = registerUI.getStateTextField().getText();
                    String password = registerUI.getPasswordTextField().getText();
                    Response res=employeeService.shipmentManagerRegister(id,name,bankCompany,bankBranch,bankId,salary,terms,state,password);
                    if(res.isErrorResponse())
                        JOptionPane.showMessageDialog(registerUI,res.ErrorMsg);
                    else {
                        MainShippingView.run();
                    }
                }catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(registerUI,"please enter valid data");
                }
            }
        });

        registerUI.getRegisterWorkerButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = registerUI.getIdTextField().getText();
                    String name = registerUI.getNameTextField().getText();
                    String bankCompany = registerUI.getBankCompanyTextField().getText();
                    int bankBranch = Integer.parseInt(registerUI.getBankBranchTextField().getText());
                    int bankId = Integer.parseInt(registerUI.getBankIdTextField().getText());
                    double salary = Double.parseDouble(registerUI.getSalaryTextField().getText());
                    String terms = registerUI.getTermsTextField().getText();
                    String state = registerUI.getStateTextField().getText();
                    String password = registerUI.getPasswordTextField().getText();
                    Response res=employeeService.addNewWorker(id,name,bankCompany,bankBranch,bankId,salary,terms,state,password);
                    if(res.isErrorResponse())
                        JOptionPane.showMessageDialog(registerUI,res.ErrorMsg);
                    else {
                        HrUI hrUI=new HrUI((String) employeeService.getHumanResourceManagerId().ReturnValue);
                        registerUI.dispose();
                    }

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(registerUI,"please enter valid data");
                }
            }
        });

        registerUI.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the RegisterUI window
                ManageEmpUI manageEmpUI;

                manageEmpUI = new ManageEmpUI();
                    manageEmpUI.setVisible(true);
                registerUI.dispose();
            }
        });
    }
}
