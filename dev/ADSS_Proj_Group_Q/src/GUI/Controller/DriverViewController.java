package GUI.Controller;

import GUI.DriverViewWindow;
import GUI.EmployeeWindow;
import GUI.ShiftViewWindow;
import Model.DriverModel;
import Model.EmployeeModel;
import Model.ShiftModel;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DriverViewController {
    EmployeeService employeeService;

    public DriverViewController(DriverViewWindow driverWindow, DriverModel driverModel){

        employeeService=new EmployeeService();
        driverWindow.getUpdateStateButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the employee's state
                JFrame updateStateFrame = new JFrame("Update State");
                updateStateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                updateStateFrame.setSize(300, 150);
                updateStateFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new state
                JTextField stateTextField = new JTextField(20);
                inputPanel.add(stateTextField);

                // OK button to update the state
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newState = stateTextField.getText();
                        // Update the label value with the entered state
                        Response res=employeeService.updateState(driverModel.getId(),newState);
                        if(!res.isErrorResponse()){
                            driverModel.setState(newState);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        // Close the frame
                        updateStateFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                updateStateFrame.add(inputPanel);
                updateStateFrame.setVisible(true);
            }
        });


        driverWindow.getSetBonusButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the bonus
                JFrame setBonusFrame = new JFrame("Set Bonus");
                setBonusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBonusFrame.setSize(300, 200);
                setBonusFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new bonus
                JTextField bonusTextField = new JTextField(20);
                inputPanel.add(bonusTextField);

                // OK button to set the bonus
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newBonus = bonusTextField.getText();

                        Response res=employeeService.setBonus(driverModel.getId(),Integer.parseInt(newBonus));
                        if(!res.isErrorResponse()){
                            driverModel.setSalaryBonus(Integer.parseInt(newBonus));
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }


                        setBonusFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setBonusFrame.add(inputPanel);
                setBonusFrame.setVisible(true);
            }
        });

        driverWindow.getSetSalaryButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the salary
                JFrame setSalaryFrame = new JFrame("Set Salary");
                setSalaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSalaryFrame.setSize(300, 200);
                setSalaryFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new salary
                JTextField salaryTextField = new JTextField(20);
                inputPanel.add(salaryTextField);

                // OK button to set the salary
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newSalary = salaryTextField.getText();
                        driverWindow.updateLabels(driverModel);

                        Response res=employeeService.setSalary(driverModel.getId(),Integer.parseInt(newSalary));
                        if(!res.isErrorResponse()){
                            driverModel.setSalary(Integer.parseInt(newSalary));
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }

                        setSalaryFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setSalaryFrame.add(inputPanel);
                setSalaryFrame.setVisible(true);
            }
        });

        driverWindow.getSetTermsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the terms
                JFrame setTermsFrame = new JFrame("Set Terms");
                setTermsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setTermsFrame.setSize(300, 200);
                setTermsFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new terms
                JTextField termsTextField = new JTextField(20);
                inputPanel.add(termsTextField);

                // OK button to set the terms
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newTerms = termsTextField.getText();
                        Response res=employeeService.setTerms(driverModel.getId(),newTerms);
                        if(!res.isErrorResponse()){
                            driverModel.setTerms(newTerms);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        setTermsFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setTermsFrame.add(inputPanel);
                setTermsFrame.setVisible(true);
            }
        });
        driverWindow.getSetBankButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the bank
                JFrame setBankFrame = new JFrame("Set Bank");
                setBankFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBankFrame.setSize(300, 200);
                setBankFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

                // Text field for entering the new bank company
                JTextField bankCompanyTextField = new JTextField(20);
                inputPanel.add(new JLabel("Bank Company:"));
                inputPanel.add(bankCompanyTextField);

                // Text field for entering the new bank branch
                JTextField bankBranchTextField = new JTextField(20);
                inputPanel.add(new JLabel("Bank Branch:"));
                inputPanel.add(bankBranchTextField);

                // Text field for entering the new bank ID
                JTextField bankIDTextField = new JTextField(20);
                inputPanel.add(new JLabel("Bank ID:"));
                inputPanel.add(bankIDTextField);

                // OK button to set the bank
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newBankCompany = bankCompanyTextField.getText();
                        int newBankBranch = Integer.parseInt(bankBranchTextField.getText());
                        int newBankID = Integer.parseInt(bankIDTextField.getText());
                        Response res=employeeService.setBank(driverModel.getId(),newBankCompany,newBankBranch,newBankID);

                        if(!res.isErrorResponse()){
                            driverModel.setBankBranch(newBankBranch);
                            driverModel.setBankId(newBankID);
                            driverModel.setBankCompany(newBankCompany);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        setBankFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setBankFrame.add(inputPanel);
                setBankFrame.setVisible(true);
            }
        });

        driverWindow.getEditLicenceNumberButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the terms
                JFrame setTermsFrame = new JFrame("Set Licence Number");
                setTermsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setTermsFrame.setSize(300, 200);
                setTermsFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new terms
                JTextField termsTextField = new JTextField(20);
                inputPanel.add(termsTextField);

                // OK button to set the terms
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newLicenceNumber = termsTextField.getText();
                        Response res=employeeService.setLicenceNumberString(driverModel.getId(),newLicenceNumber);
                        if(!res.isErrorResponse()){
                            driverModel.setLicenseNumber(newLicenceNumber);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        setTermsFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setTermsFrame.add(inputPanel);
                setTermsFrame.setVisible(true);
            }
        });

        driverWindow.getEditLicenceTypeButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the terms
                JFrame setTermsFrame = new JFrame("Set Licence Type");
                setTermsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setTermsFrame.setSize(300, 200);
                setTermsFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new terms
                JTextField termsTextField = new JTextField(20);
                inputPanel.add(termsTextField);

                // OK button to set the terms
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newLicenceType = termsTextField.getText();
                        Response res=employeeService.setLisenceType(driverModel.getId(),newLicenceType);
                        if(!res.isErrorResponse()){
                            driverModel.setLicenseType(newLicenceType);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        setTermsFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setTermsFrame.add(inputPanel);
                setTermsFrame.setVisible(true);
            }
        });

        driverWindow.getEditTempControlledLicenceButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the terms
                JFrame setTermsFrame = new JFrame("Set Temp Controlled Licence");
                setTermsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setTermsFrame.setSize(300, 200);
                setTermsFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new terms
                String[] tempControlledLicenceOptions = {"true", "false"};
                JComboBox<String> tempControlledLicenceComboBox=new JComboBox<>(tempControlledLicenceOptions);

                inputPanel.add(tempControlledLicenceComboBox);

                // OK button to set the terms
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newTempControlled = (String) tempControlledLicenceComboBox.getSelectedItem();
                        Response res=employeeService.setTempControlled(driverModel.getId(),newTempControlled);
                        if(!res.isErrorResponse()){
                            driverModel.setTempControl(newTempControlled);
                            driverWindow.updateLabels(driverModel);
                        }
                        else{
                            JOptionPane.showMessageDialog(driverWindow,res.ErrorMsg);
                        }
                        setTermsFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setTermsFrame.add(inputPanel);
                setTermsFrame.setVisible(true);
            }
        });
        driverWindow.getMonth().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int month= (int) driverWindow.getMonth().getSelectedItem();
                Response response = employeeService.getMonthSalary(driverModel.getId(),month);
                if(response.isErrorResponse())
                    JOptionPane.showMessageDialog(driverWindow,response.ErrorMsg);
                else
                    driverWindow.setMonthSalaryValue(String.valueOf(response.ReturnValue));
            }
        });
    }
    public static String getCodeFromFullName(String fullName) {
        switch (fullName) {
            case "ShiftManager":
                return "shm";
            case "SalesMan":
                return "sm";
            case "StoreKeeper":
                return "sk";
            case "Security":
                return "se";
            case "Clean":
                return "c";
            case "Usher":
                return "u";
            case "General":
                return "g";
            case "Driver":
                return "d";
            default:
                return "Invalid code";
        }
    }

}

