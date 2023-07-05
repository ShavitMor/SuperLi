package GUI;

import GUI.Controller.AddNewDriverController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewDriverWindow extends JFrame {
    private JLabel titleLabel;

    private JLabel idLabel;
    private JTextField idTextField;

    private JLabel nameLabel;
    private JTextField nameTextField;

    private JLabel bankCompanyLabel;
    private JTextField bankCompanyTextField;

    private JLabel bankBranchLabel;
    private JComboBox<Integer> bankBranchComboBox;

    private JLabel bankIdLabel;
    private JTextField bankIdTextField;

    private JLabel salaryLabel;
    private JTextField salaryTextField;

    private JLabel termsLabel;
    private JTextField termsTextField;

    private JLabel stateLabel;
    private JTextField stateTextField;

    private JLabel passwordLabel;
    private JTextField passwordTextField;

    private JLabel licenceTypeLabel;
    private JComboBox<String> licenceTypeComboBox;

    private JLabel licenceNumberLabel;
    private JTextField licenceNumberTextField;

    private JLabel tempControlledLicenceLabel;
    private JComboBox<String> tempControlledLicenceComboBox;

    private JButton addDriverButton;
    private JButton backButton;

    public JButton getBackButton() {
        return backButton;
    }

    public AddNewDriverWindow() {
        setTitle("Add New Driver");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Add New Driver");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(14, 2));

        idLabel = new JLabel("ID:");
        idTextField = new JTextField();

        nameLabel = new JLabel("Name:");
        nameTextField = new JTextField();

        bankCompanyLabel = new JLabel("Bank Company:");
        bankCompanyTextField = new JTextField();

        bankBranchLabel = new JLabel("Bank Branch:");
        Integer[] bankBranchOptions = new Integer[100];
        for (int i = 0; i < 100; i++) {
            bankBranchOptions[i] = i + 1;
        }
        bankBranchComboBox = new JComboBox<>(bankBranchOptions);

        bankIdLabel = new JLabel("Bank ID:");
        bankIdTextField = new JTextField();

        salaryLabel = new JLabel("Salary:");
        salaryTextField = new JTextField();

        termsLabel = new JLabel("Terms:");
        termsTextField = new JTextField();

        stateLabel = new JLabel("State:");
        stateTextField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordTextField = new JTextField();

        licenceTypeLabel = new JLabel("License Type:");
        String[] licenceTypeOptions = {"B", "C", "C1"};
        licenceTypeComboBox = new JComboBox<>(licenceTypeOptions);

        licenceNumberLabel = new JLabel("License Number:");
        licenceNumberTextField = new JTextField();

        tempControlledLicenceLabel = new JLabel("Temp Controlled License:");
        String[] tempControlledLicenceOptions = {"true", "false"};
        tempControlledLicenceComboBox = new JComboBox<>(tempControlledLicenceOptions);

        addDriverButton = new JButton("Add New Driver");
        backButton=new JButton("back");

        formPanel.add(idLabel);
        formPanel.add(idTextField);
        formPanel.add(nameLabel);
        formPanel.add(nameTextField);
        formPanel.add(bankCompanyLabel);
        formPanel.add(bankCompanyTextField);
        formPanel.add(bankBranchLabel);
        formPanel.add(bankBranchComboBox);
        formPanel.add(bankIdLabel);
        formPanel.add(bankIdTextField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryTextField);
        formPanel.add(termsLabel);
        formPanel.add(termsTextField);
        formPanel.add(stateLabel);
        formPanel.add(stateTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordTextField);
        formPanel.add(licenceTypeLabel);
        formPanel.add(licenceTypeComboBox);
        formPanel.add(licenceNumberLabel);
        formPanel.add(licenceNumberTextField);
        formPanel.add(tempControlledLicenceLabel);
        formPanel.add(tempControlledLicenceComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addDriverButton);
        buttonPanel.add(backButton);
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        AddNewDriverController addNewDriverController=new AddNewDriverController(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddNewDriverWindow();
            }
        });
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

    public JComboBox<Integer> getBankBranchComboBox() {
        return bankBranchComboBox;
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

    public JComboBox<String> getLicenceTypeComboBox() {
        return licenceTypeComboBox;
    }

    public JTextField getLicenceNumberTextField() {
        return licenceNumberTextField;
    }

    public JComboBox<String> getTempControlledLicenceComboBox() {
        return tempControlledLicenceComboBox;
    }

    public JButton getAddDriverButton() {
        return addDriverButton;
    }
}
