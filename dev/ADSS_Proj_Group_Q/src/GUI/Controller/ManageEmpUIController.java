package GUI.Controller;

import DataLayer.EmployeeMapper;
import DataLayer.EmployeeRolesMapper;
import GUI.*;
import Model.EmployeeModel;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ManageEmpUIController {
    EmployeeService employeeService=new EmployeeService();
    public ManageEmpUIController(ManageEmpUI manageEmpUI){




    manageEmpUI.addWorkerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            RegisterUI registerUI=new RegisterUI();
            registerUI.setVisible(true);
            manageEmpUI.dispose();

        }
    });

        manageEmpUI.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HrUI hrui=new HrUI((String) employeeService.getHumanResourceManagerId().ReturnValue);
                hrui.setVisible(true);
                manageEmpUI.dispose();

            }
        });

        manageEmpUI.addDriverButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            AddNewDriverWindow addNewDriverWindow=new AddNewDriverWindow();
            addNewDriverWindow.setVisible(true);
            manageEmpUI.dispose();
        }
    });



    }

    public ArrayList<String> getEmployessIdName() {
        Response res = employeeService.getWorkersIdName();
        ArrayList<String> falser=new ArrayList<>();
        try {
            if (!res.isErrorResponse()) {
                return (ArrayList<String>) res.ReturnValue;
            } else {
                falser.add(null);
                return falser;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return falser;
    }

     public String getEmployesName(String id) {
        Response res = employeeService.getWorkerName(id);
        try {
            if (!res.isErrorResponse()) {
                return (String)res.ReturnValue;
            } else {
              return "";
            }
        } catch (Exception ex) {
        }
        return " ";
    }
}
