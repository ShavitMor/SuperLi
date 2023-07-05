package GUI;

import GUI.Controller.DriverViewController;
import GUI.Controller.EmployeeWindowController;
import Model.DriverModel;
import Model.EmployeeModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverViewWindow extends JFrame {
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel bankCompanyLabel;
    private JLabel bankBranchLabel;
    private JLabel bankIdLabel;
    private JLabel salaryLabel;
    private JLabel termsLabel;
    private JLabel stateLabel;
    private JLabel licenceTypeLabel;
    private JLabel licenceNumberLabel;
    private JLabel tempControlledLicenceLabel;
    private JLabel bonusLabel;
    private JComboBox<Integer> month;

    private JLabel idValueLabel;
    private JLabel nameValueLabel;
    private JLabel bankCompanyValueLabel;
    private JLabel bankBranchValueLabel;
    private JLabel bankIdValueLabel;
    private JLabel salaryValueLabel;
    private JLabel termsValueLabel;
    private JLabel stateValueLabel;
    private JLabel licenceTypeValueLabel;
    private JLabel licenceNumberValueLabel;
    private JLabel tempControlledLicenceValueLabel;
    private JLabel monthSalary;
    private JLabel bonusValue;

    private JButton editLicenceTypeButton;
    private JButton editLicenceNumberButton;
    private JButton editTempControlledLicenceButton;

    private JButton updateStateButton;

    private DriverModel driver;


    public JButton getEditLicenceTypeButton() {
        return editLicenceTypeButton;
    }

    public JButton getEditLicenceNumberButton() {
        return editLicenceNumberButton;
    }

    public JButton getEditTempControlledLicenceButton() {
        return editTempControlledLicenceButton;
    }

    public JButton getUpdateStateButton() {
        return updateStateButton;
    }

    public JButton getSetBonusButton() {
        return setBonusButton;
    }

    public JButton getSetSalaryButton() {
        return setSalaryButton;
    }

    public JButton getSetTermsButton() {
        return setTermsButton;
    }

    public JButton getSetBankButton() {
        return setBankButton;
    }
    public JComboBox<Integer> getMonth() {
        return month;
    }

    public void setMonthSalaryValue(String monthSalaryValue) {
        this.monthSalary.setText(monthSalaryValue);
    }
    private JButton setBonusButton;
    private JButton setSalaryButton;
    private JButton setTermsButton;
    private JButton setBankButton;

    public DriverViewWindow(DriverModel driverModel) {
        setTitle("Driver Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("View Driver");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        driver=driverModel;
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        add(titlePanel,BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 4, 4, 4);
        JPanel formPanel = new JPanel(new GridLayout(13,2));
        idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("arial",Font.BOLD,16));
        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("arial",Font.BOLD,16));
        bankCompanyLabel = new JLabel("Bank Company:");
        bankCompanyLabel.setFont(new Font("arial",Font.BOLD,16));
        bankBranchLabel = new JLabel("Bank Branch:");
        bankBranchLabel.setFont(new Font("arial",Font.BOLD,16));
        bankIdLabel = new JLabel("Bank ID:");
        bankIdLabel.setFont(new Font("arial",Font.BOLD,16));
        salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("arial",Font.BOLD,16));
        termsLabel = new JLabel("Terms:");
        termsLabel.setFont(new Font("arial",Font.BOLD,16));
        stateLabel = new JLabel("State:");
        stateLabel.setFont(new Font("arial",Font.BOLD,16));
        licenceTypeLabel = new JLabel("License Type:");
        licenceTypeLabel.setFont(new Font("arial",Font.BOLD,16));
        licenceNumberLabel = new JLabel("License Number:");
        licenceNumberLabel.setFont(new Font("arial",Font.BOLD,16));
        tempControlledLicenceLabel = new JLabel("Temp Controlled License:");
        tempControlledLicenceLabel.setFont(new Font("arial",Font.BOLD,16));
        bonusLabel = new JLabel("Bonus:");
        bonusLabel.setFont(new Font("arial",Font.BOLD,16));


        month=new JComboBox<>();
        for(int i=1;i<13;i++)
            month.addItem(i);

        idValueLabel = new JLabel(driverModel.getId());
        idValueLabel.setFont(new Font("arial",Font.BOLD,14));
        nameValueLabel = new JLabel(driverModel.getName());
        nameValueLabel.setFont(new Font("arial",Font.BOLD,14));
        bankCompanyValueLabel = new JLabel(driverModel.getBankCompany());
        bankCompanyValueLabel.setFont(new Font("arial",Font.BOLD,14));
        bankBranchValueLabel = new JLabel(String.valueOf(driverModel.getBankBranch()));
        bankBranchValueLabel.setFont(new Font("arial",Font.BOLD,14));
        bankIdValueLabel = new JLabel(String.valueOf(driverModel.getBankId()));
        bankIdValueLabel.setFont(new Font("arial",Font.BOLD,14));
        salaryValueLabel = new JLabel(String.valueOf(driverModel.getSalary()));
        salaryValueLabel.setFont(new Font("arial",Font.BOLD,14));
        termsValueLabel = new JLabel(driverModel.getTerms());
        termsValueLabel.setFont(new Font("arial",Font.BOLD,14));
        stateValueLabel = new JLabel(driverModel.getPersonalstate());
        stateValueLabel.setFont(new Font("arial",Font.BOLD,14));
        licenceTypeValueLabel = new JLabel(driverModel.getLicenseType());
        licenceTypeValueLabel.setFont(new Font("arial",Font.BOLD,14));
        licenceNumberValueLabel = new JLabel(driverModel.getLicenseNumber());
        licenceNumberValueLabel.setFont(new Font("arial",Font.BOLD,14));
        tempControlledLicenceValueLabel = new JLabel(driverModel.getTempControl());
        tempControlledLicenceValueLabel.setFont(new Font("arial",Font.BOLD,14));
        bonusValue = new JLabel(driverModel.getSalaryBonus()+"");
        bonusValue.setFont(new Font("arial",Font.BOLD,14));
        monthSalary=new JLabel("month salary");


        formPanel.add(idLabel,gbc);
        gbc.gridx++;
        formPanel.add(idValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(nameLabel,gbc);
        gbc.gridx++;
        formPanel.add(nameValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(bankCompanyLabel,gbc);
        gbc.gridx++;
        formPanel.add(bankCompanyValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(bankBranchLabel,gbc);
        gbc.gridx++;
        formPanel.add(bankBranchValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(bankIdLabel,gbc);
        gbc.gridx++;
        formPanel.add(bankIdValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(salaryLabel,gbc);
        gbc.gridx++;
        formPanel.add(salaryValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(bonusLabel,gbc);
        gbc.gridx++;
        formPanel.add(bonusValue,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(termsLabel,gbc);
        gbc.gridx++;
        formPanel.add(termsValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(stateLabel,gbc);
        gbc.gridx++;
        formPanel.add(stateValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(licenceTypeLabel,gbc);
        gbc.gridx++;
        formPanel.add(licenceTypeValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(licenceNumberLabel,gbc);
        gbc.gridx++;
        formPanel.add(licenceNumberValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(tempControlledLicenceLabel,gbc);
        gbc.gridx++;
        formPanel.add(tempControlledLicenceValueLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        formPanel.add(month,gbc);
        gbc.gridx++;
        formPanel.add(monthSalary);
        formPanel.setPreferredSize(new Dimension(getWidth(),500));
        add(formPanel,BorderLayout.CENTER);
        JPanel formPanel1 = new JPanel(new GridLayout(2, 4));
        formPanel1.setPreferredSize(new Dimension(getWidth(),100));
        GridBagConstraints gbcFormPanel1 = new GridBagConstraints();
        gbcFormPanel1.anchor = GridBagConstraints.WEST;
        gbcFormPanel1.gridx = 0;
        gbcFormPanel1.gridy = 0;
        gbcFormPanel1.insets = new Insets(2, 2, 2, 2);
        editLicenceTypeButton = new JButton("Edit \nLicence Type");
        editLicenceTypeButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        editLicenceNumberButton = new JButton("Edit \nLicence Number");
        editLicenceNumberButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        editTempControlledLicenceButton = new JButton("Edit Temp \nControlled Licence");
        editTempControlledLicenceButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        updateStateButton = new JButton("Update State");
        updateStateButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));

        setBonusButton = new JButton("Set Bonus");
        setBonusButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        setSalaryButton = new JButton("Set Salary");
        setSalaryButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        setTermsButton = new JButton("Set Terms");
        setTermsButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        setBankButton = new JButton("Set Bank");
        setBankButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        formPanel1.add(updateStateButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(setBonusButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(setSalaryButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(setTermsButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(setBankButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(editLicenceTypeButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(editLicenceNumberButton,gbcFormPanel1);
        gbcFormPanel1.gridy++;
        formPanel1.add(editTempControlledLicenceButton,gbcFormPanel1);

        DriverViewController driverWindowController=new DriverViewController(this,driver);

        add(formPanel1,BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                DriverViewWindow manageEmpUI = new DriverViewWindow(new DriverModel("a","a","a",1,1,1,1,"a","a","a","a","a","a","M","s","a"));
                manageEmpUI.setVisible(true);
            }
        });
    }
    public void updateLabels(DriverModel employee) {
        idValueLabel.setText(String.valueOf(employee.getId()));
        nameValueLabel.setText(employee.getName());
        bankCompanyValueLabel.setText(employee.getBankCompany());
        bankBranchValueLabel.setText(String.valueOf(employee.getBankBranch()));
        bankIdValueLabel.setText(String.valueOf(employee.getBankId()));
        salaryValueLabel.setText(String.valueOf(employee.getSalary()));
        termsValueLabel.setText(employee.getTerms());
        stateValueLabel.setText(employee.getPersonalstate());
        licenceNumberValueLabel.setText(employee.getLicenseNumber());
        licenceTypeValueLabel.setText(employee.getLicenseType());
        tempControlledLicenceValueLabel.setText(employee.getTempControl());
        bonusValue.setText(employee.getSalaryBonus()+"");

    }
}
