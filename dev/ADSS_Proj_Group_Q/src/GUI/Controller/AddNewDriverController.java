package GUI.Controller;

import GUI.AddNewDriverWindow;
import GUI.HrUI;
import GUI.ManageEmpUI;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewDriverController {
    EmployeeService employeeService=new EmployeeService();
    public AddNewDriverController(AddNewDriverWindow addNewDriverWindow){
        addNewDriverWindow.getAddDriverButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = addNewDriverWindow.getIdTextField().getText();
                    String name = addNewDriverWindow.getNameTextField().getText();
                    String bankCompany = addNewDriverWindow.getBankCompanyTextField().getText();
                    int bankBranch = (Integer)addNewDriverWindow.getBankBranchComboBox().getSelectedItem();
                    int bankId = Integer.parseInt(addNewDriverWindow.getBankIdTextField().getText());
                    double salary = Double.parseDouble(addNewDriverWindow.getSalaryTextField().getText());
                    String terms = addNewDriverWindow.getTermsTextField().getText();
                    String state = addNewDriverWindow.getStateTextField().getText();
                    String password = addNewDriverWindow.getPasswordTextField().getText();
                    String licenseType= (String) addNewDriverWindow.getLicenceTypeComboBox().getSelectedItem();
                    String licenseNumber=addNewDriverWindow.getLicenceNumberTextField().getText();
                    String licenseTempControl= (String) addNewDriverWindow.getTempControlledLicenceComboBox().getSelectedItem();
                    Response response = employeeService.addNewDriver(id,name,bankCompany,bankBranch,bankId,salary,terms,state,password,licenseType,licenseNumber,licenseTempControl);
                    if(response.isErrorResponse())
                        JOptionPane.showMessageDialog(addNewDriverWindow,response.ErrorMsg);
                    else {
                        JOptionPane.showMessageDialog(addNewDriverWindow,"driver added!");
                        HrUI hrUI=new HrUI((String) employeeService.getHumanResourceManagerId().ReturnValue);
                        hrUI.setVisible(true);
                        addNewDriverWindow.dispose();
                    }
                }catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(addNewDriverWindow,"please enter valid data");
                }
            }
        });
        addNewDriverWindow.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageEmpUI empUI=new ManageEmpUI();
                empUI.setVisible(true);
                addNewDriverWindow.dispose();

            }
        });
        }

}
