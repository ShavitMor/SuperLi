package GUI.Controller;

import GUI.*;
import PresentationLayer.MannageEmpUI;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HruiController {
    EmployeeService employeeService;
    public HruiController(HrUI hrUI, String id){
        employeeService=new EmployeeService();
        hrUI.getManageWorkersButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle the Manage Workers button click
                ManageEmpUI manageEmpUI=new ManageEmpUI();
                manageEmpUI.setVisible(true);
                hrUI.dispose();
            }
        });

        hrUI.getManageShiftsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to handle the Manage Shifts button click
                GUI.ShiftUI shiftUI=new ShiftUI();
                shiftUI.setVisible(true);
                hrUI.dispose();
            }
        });
        hrUI.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response res = employeeService.logout(id);
                if(res.isErrorResponse()){
                    LoginUI loginUI=new LoginUI();
                    loginUI.setVisible(true);
                    hrUI.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(hrUI ,"user logged out");
                    LoginUI loginUI=new LoginUI();
                    loginUI.setVisible(true);
                    hrUI.dispose();
                }

            }
        });
    }
}
