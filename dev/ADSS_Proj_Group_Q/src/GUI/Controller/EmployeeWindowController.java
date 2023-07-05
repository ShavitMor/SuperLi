package GUI.Controller;

import GUI.EmployeeWindow;
import GUI.ManageEmpUI;
import GUI.ShiftViewWindow;
import Model.EmployeeModel;
import Model.ShiftModel;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EmployeeWindowController {
        private EmployeeService employeeService;


    public EmployeeWindowController(EmployeeWindow employeeWindow, EmployeeModel employeeModel) {
        employeeService = new EmployeeService();
        employeeWindow.getUpdateStateButton().addActionListener(new ActionListener() {
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
                        Response res = employeeService.updateState(employeeModel.getId(), newState);
                        if (!res.isErrorResponse()) {
                            employeeModel.setState(newState);
                            employeeWindow.updateLabels(employeeModel);
                        } else {
                            JOptionPane.showMessageDialog(employeeWindow, res.ErrorMsg);
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

        employeeWindow.getAddRoleButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for adding a role
                JFrame addRoleFrame = new JFrame("Add Role");
                addRoleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addRoleFrame.setSize(300, 200);
                addRoleFrame.setLocationRelativeTo(null);

                // Panel for user input
                JPanel inputPanel = new JPanel(new FlowLayout());

                // Text field for entering the new role
                String[] roleOptions = {"ShiftManager", "SalesMan", "StoreKeeper", "Security", "Clean", "Usher", "General", "Driver"};
                JComboBox<String> roleComboBox = new JComboBox<>(roleOptions);
                inputPanel.add(roleComboBox);

                // OK button to add the role
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newRole = roleComboBox.getSelectedItem().toString();
                        String nRole = getCodeFromFullName(newRole);
                        Response res = employeeService.addRoleToEmployee(employeeModel.getId(), nRole);
                        if (!res.isErrorResponse()) {
                            employeeModel.addRole(newRole);
                            employeeWindow.updateLabels(employeeModel);

                        } else {
                            JOptionPane.showMessageDialog(employeeWindow, res.ErrorMsg);
                        }
                        addRoleFrame.dispose();
                    }
                });
                inputPanel.add(okButton);
                addRoleFrame.add(inputPanel);
                addRoleFrame.setVisible(true);
            }
        });

        employeeWindow.getSetBonusButton().addActionListener(new ActionListener() {
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

                        Response res = employeeService.setBonus(employeeModel.getId(), Integer.parseInt(newBonus));
                        if (!res.isErrorResponse()) {
                            employeeModel.setSalaryBonus(Integer.parseInt(newBonus));
                            employeeWindow.updateLabels(employeeModel);

                        } else {
                            JOptionPane.showMessageDialog(employeeWindow, res.ErrorMsg);
                        }


                        setBonusFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setBonusFrame.add(inputPanel);
                setBonusFrame.setVisible(true);
            }
        });

        employeeWindow.getSetSalaryButton().addActionListener(new ActionListener() {
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

                        Response res = employeeService.setSalary(employeeModel.getId(), Integer.parseInt(newSalary));
                        if (!res.isErrorResponse()) {
                            employeeModel.setSalary(Double.parseDouble(newSalary));
                            employeeWindow.updateLabels(employeeModel);

                        } else {
                            JOptionPane.showMessageDialog(employeeWindow, res.ErrorMsg);
                        }

                        setSalaryFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setSalaryFrame.add(inputPanel);
                setSalaryFrame.setVisible(true);
            }
        });

        employeeWindow.getSetTermsButton().addActionListener(new ActionListener() {
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
                        Response res = employeeService.setTerms(employeeModel.getId(), newTerms);
                        if (res.isErrorResponse())
                            JOptionPane.showMessageDialog(setTermsFrame, res.ErrorMsg);
                        else {
                            employeeModel.setTerms(newTerms);
                            employeeWindow.updateLabels(employeeModel);

                            setTermsFrame.dispose();
                        }

                    }
                });
                inputPanel.add(okButton);

                setTermsFrame.add(inputPanel);
                setTermsFrame.setVisible(true);
            }
        });
        employeeWindow.getSetBankButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame for setting the bank
                JFrame setBankFrame = new JFrame("Set Bank");
                setBankFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBankFrame.setSize(300, 200);
                setBankFrame.setLocationRelativeTo(null);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.insets = new Insets(5, 5, 5, 5);

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
                        Response res = employeeService.setBank(employeeModel.getId(), newBankCompany, newBankBranch, newBankID);

                        if (!res.isErrorResponse()) {
                            employeeModel.setBankBranch(newBankBranch);
                            employeeModel.setBankId(newBankID);
                            employeeModel.setBankCompany(newBankCompany);
                            employeeWindow.updateLabels(employeeModel);

                        } else {
                            JOptionPane.showMessageDialog(employeeWindow, res.ErrorMsg);
                        }
                        setBankFrame.dispose();
                    }
                });
                inputPanel.add(okButton);

                setBankFrame.add(inputPanel);
                setBankFrame.setVisible(true);
            }
        });
        employeeWindow.getRestrictButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame restrictFrame = new JFrame();
                restrictFrame.setSize(300, 300);
                restrictFrame.setLayout(new BorderLayout());
                JLabel yearLabel = new JLabel("Year:");
                JLabel monthLabel = new JLabel("Month:");
                JLabel dayLabel = new JLabel("Day:");
                JComboBox<String> yearComboBox = new JComboBox<>();
                JButton add = new JButton("Restrict");
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = currentYear; i <= currentYear + 2; i++) {
                    yearComboBox.addItem(String.valueOf(i));
                }

                JComboBox<String> monthComboBox = new JComboBox<>();
                for (int i = 1; i <= 12; i++) {
                    monthComboBox.addItem(String.valueOf(i));
                }

                JComboBox<String> dayComboBox = new JComboBox<>();
                for (int i = 1; i <= 31; i++) {
                    dayComboBox.addItem(String.valueOf(i));
                }
                JLabel shiftTypeLabel = new JLabel("Shift Type:");
                JComboBox shiftTypeComboBox = new JComboBox<>();
                shiftTypeComboBox.addItem("Morning");
                shiftTypeComboBox.addItem("Evening");

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.insets = new Insets(5, 5, 5, 5);
                jPanel.add(yearLabel, constraints);
                constraints.gridx++;
                jPanel.add(yearComboBox, constraints);
                constraints.gridx = 0;
                constraints.gridy++;
                jPanel.add(monthLabel, constraints);
                constraints.gridx++;
                jPanel.add(monthComboBox, constraints);
                constraints.gridx = 0;
                constraints.gridy++;
                jPanel.add(dayLabel, constraints);
                constraints.gridx++;
                jPanel.add(dayComboBox, constraints);
                constraints.gridx = 0;
                constraints.gridy++;
                jPanel.add(shiftTypeLabel, constraints);
                constraints.gridx++;
                jPanel.add(shiftTypeComboBox, constraints);
                JPanel buttonPanel = new JPanel();
                jPanel.setLayout(new GridBagLayout());
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
                        int month = Integer.parseInt((String) monthComboBox.getSelectedItem());
                        int day = Integer.parseInt((String) dayComboBox.getSelectedItem());
                        String type = ((String) shiftTypeComboBox.getSelectedItem()).substring(0, 1);
                        Response response = employeeService.restrictEmployee(year, month, day, type, employeeModel.getId());
                        if (response.isErrorResponse())
                            JOptionPane.showMessageDialog(restrictFrame, response.ErrorMsg);
                        else {
                            JOptionPane.showMessageDialog(restrictFrame, "employee restricted");
                            restrictFrame.dispose();

                        }
                    }

                });
                buttonPanel.add(add);
                restrictFrame.add(jPanel, BorderLayout.CENTER);
                restrictFrame.add(buttonPanel, BorderLayout.SOUTH);
                restrictFrame.setVisible(true);
            }
        });
        employeeWindow.getMonth().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int month= (int) employeeWindow.getMonth().getSelectedItem();
                Response response = employeeService.getMonthSalary(employeeModel.getId(),month);
                if(response.isErrorResponse())
                    JOptionPane.showMessageDialog(employeeWindow,response.ErrorMsg);
                else
                    employeeWindow.setMonthSalaryValue(String.valueOf(response.ReturnValue));
            }
        });
        employeeWindow.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageEmpUI manageEmpUI=new ManageEmpUI();
                manageEmpUI.setVisible(true);
                employeeWindow.dispose();
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

