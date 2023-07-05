package GUI;

import BusinessLayer.Employee;
import BusinessLayer.EmployeeController;
import GUI.Controller.EmployeeWindowController;
import Model.EmployeeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeWindow extends JFrame {
    private EmployeeModel employee;
    private JPanel buttonPanel;
    private JButton updateStateButton;
    private JButton addRoleButton ;
    private JButton setBonusButton ;
    private JButton setSalaryButton ;
    private JButton setTermsButton ;
    private JButton setBankButton;
    private JButton backButton;
    private JComboBox<Integer> month;
    private JLabel monthSalaryValue;

private JLabel name,idValueLabel,bankCom,bankBranch,bankId,roles,salary,salaryBonus,terms,isActive,stateLabel;

    private JButton restrictButton;
    public EmployeeWindow() {

    }
    public JButton getUpdateStateButton() {
        return updateStateButton;
    }

    public JButton getAddRoleButton() {
        return addRoleButton;
    }

    public JButton getSetBonusButton() {
        return setBonusButton;
    }

    public JButton getSetSalaryButton() {
        return setSalaryButton;
    }

    public JButton getRestrictButton() {
        return restrictButton;
    }

    public JButton getSetTermsButton() {
        return setTermsButton;
    }

    public JButton getSetBankButton() {
        return setBankButton;
    }
    public JButton getBackButton() {
        return backButton;
    }

    public JComboBox<Integer> getMonth() {
        return month;
    }

    public void setMonthSalaryValue(String monthSalaryValue) {
        this.monthSalaryValue.setText(monthSalaryValue);
    }

    public EmployeeWindow(EmployeeModel employeeModel) {


        setTitle("Employee Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(333, 555);
        setLocationRelativeTo(null);
        employee = employeeModel;

// Employee details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
         idValueLabel=new JLabel(employee.getId());
        detailsPanel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx++;
        detailsPanel.add(idValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx++;
        name=new JLabel(employee.getName());
        detailsPanel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Bank company:"), gbc);
        gbc.gridx++;
         bankCom=new JLabel(employee.getBankCompany());
        detailsPanel.add(bankCom, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Bank branch:"), gbc);
        gbc.gridx++;
         bankBranch=new JLabel(String.valueOf(employee.getBankBranch()));
        detailsPanel.add(bankBranch, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Bank ID:"), gbc);
        gbc.gridx++;
         bankId=new JLabel(String.valueOf(employee.getBankId()));
        detailsPanel.add(bankId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Salary:"), gbc);
        gbc.gridx++;
         salary=new JLabel(String.valueOf(employee.getSalary()));
        detailsPanel.add(salary, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Salary Bonus:"), gbc);
        gbc.gridx++;
         salaryBonus=new JLabel(String.valueOf(employee.getSalaryBonus()));
        detailsPanel.add(salaryBonus, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Start Date:"), gbc);
        gbc.gridx++;
        detailsPanel.add(new JLabel(employee.getMstartDate()), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Terms:"), gbc);
        gbc.gridx++;
         terms=new JLabel(employee.getMterms());
        detailsPanel.add(terms, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Roles:"), gbc);
        gbc.gridx++;
         roles=new JLabel(employee.getRoles());
        detailsPanel.add(roles, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Active:"), gbc);
        gbc.gridx++;
         isActive=new JLabel(employee.getIsActive());
        detailsPanel.add(isActive, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(new JLabel("Personal State:"), gbc);
        gbc.gridx++;
         stateLabel=new JLabel(employeeModel.getPersonalstate());

        detailsPanel.add(stateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        month=new JComboBox<>();
        for(int i=1;i<13;i++)
            month.addItem(i);
        detailsPanel.add(month);
        monthSalaryValue=new JLabel("month salary");
        gbc.gridx++;
        detailsPanel.add(monthSalaryValue);

        // Buttons
         buttonPanel = new JPanel(new GridLayout(2, 3));
         updateStateButton = new JButton("Update State");
         addRoleButton = new JButton("Add Role");
         setBonusButton = new JButton("Set Bonus");
         setSalaryButton = new JButton("Set Salary");
         setTermsButton = new JButton("Set Terms");
         setBankButton = new JButton("Set Bank");
         restrictButton =new JButton("Restrict");
         backButton=new JButton("back");

        buttonPanel.add(updateStateButton);
        buttonPanel.add(addRoleButton);
        buttonPanel.add(setBonusButton);
        buttonPanel.add(setSalaryButton);
        buttonPanel.add(setTermsButton);
        buttonPanel.add(setBankButton);

        buttonPanel.add(restrictButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(detailsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        EmployeeWindowController employeeWindowController=new EmployeeWindowController(this,employee);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
     EmployeeWindow manageEmpUI = new EmployeeWindow(new EmployeeModel("a","a","a",1,1,1,1,"a","a","a","a","a","a"));
                manageEmpUI.setVisible(true);
            }
        });
    }
    public void updateLabels(EmployeeModel employee) {
        idValueLabel.setText(String.valueOf(employee.getId()));
        name.setText(employee.getName());
        bankCom.setText(employee.getBankCompany());
        bankBranch.setText(String.valueOf(employee.getBankBranch()));
        bankId.setText(String.valueOf(employee.getBankId()));
        salary.setText(String.valueOf(employee.getSalary()));
        terms.setText(employee.getTerms());
        stateLabel.setText(employee.getPersonalstate());
        roles.setText(employee.getRoles());
        salaryBonus.setText(employee.getSalaryBonus()+"");

    }


}
