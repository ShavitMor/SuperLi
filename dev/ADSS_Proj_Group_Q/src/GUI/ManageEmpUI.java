package GUI;

import GUI.Controller.ManageEmpUIController;
import Model.DriverModel;
import Model.EmployeeModel;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;


import javax.swing.*;
import java.awt.*;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

public class ManageEmpUI extends JFrame {
    private List<String> employeeList;
    private JList<String> employeeJList;
    public JButton addWorkerButton;
    public EmployeeService employeeService;
    public JButton addDriverButton;

    public JButton backButton;
    public JButton getBackButton() {
        return backButton;
    }

    public List<String> getEmployeeList() {
        return employeeList;
    }

    public JList<String> getEmployeeJList() {
        return employeeJList;
    }

    public JButton getAddWorkerButton() {
        return addWorkerButton;
    }

    public JButton getAddDriverButton() {
        return addDriverButton;
    }

    public ManageEmpUI() {
        employeeService=new EmployeeService();
        employeeList = new ArrayList<>();
        addWorkerButton = new JButton("Add New Worker");
        addDriverButton = new JButton("Add Driver");
        backButton=new JButton("back");
        ManageEmpUIController manageEmpUIController=new ManageEmpUIController(this);

        employeeList=manageEmpUIController.getEmployessIdName();
        List<String> employeeList1=new ArrayList<>();
        for(String id :employeeList) {
            employeeList1.add(id +": " +manageEmpUIController.getEmployesName(id));
        }


        setTitle("Manage Employees");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Employee list
        DefaultListModel<String> employeeListModel = new DefaultListModel<>();
        for (String employee : employeeList1) {
            employeeListModel.addElement(employee);
        }

        employeeJList = new JList<>(employeeListModel);
        employeeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JScrollPane employeeScrollPane = new JScrollPane(employeeJList);
        getEmployeeJList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String id = getEmployeeJList().getSelectedValue();
                int num=id.indexOf(":");
                id=id.substring(0,num);
                String[] details=new String[13];
                Response resDetails=employeeService.getEmployeeDetails(id);
                try{
                    if(!resDetails.isErrorResponse()){
                        details=(String[])resDetails.ReturnValue;
                        EmployeeModel selectedEmployee=new EmployeeModel(details[0],details[1],details[2],
                                Integer.parseInt(details[3]),Integer.parseInt(details[4]),
                                Double.parseDouble(details[5]),Double.parseDouble(details[6]),
                                details[7],details[8],
                                details[9],details[10],details[11],details[12]);

                        Response driverLicenceRes=employeeService.getDriverLicense(details[0]);
                        try {
                            if(!driverLicenceRes.isErrorResponse()){
                                if(driverLicenceRes.ReturnValue==null){
                                    EmployeeWindow employeeWindow = new EmployeeWindow(selectedEmployee);
                                    employeeWindow.setVisible(true);
                                    dispose();
                                }
                                else{
                                    Response Res=employeeService.getDriverDetails(id);
                                    try{
                                        String[] detailsDriver;
                                        if(!resDetails.isErrorResponse()){
                                            detailsDriver=(String[]) Res.ReturnValue;

                                        DriverModel selectedDriver=new DriverModel(details[0],details[1],details[2],
                                                Integer.parseInt(details[3]),Integer.parseInt(details[4]),
                                                Double.parseDouble(details[5]),Double.parseDouble(details[6]),
                                                details[7],details[8],
                                                details[9],details[10],details[11],details[12],detailsDriver[0],
                                                detailsDriver[1],detailsDriver[2]);


                                        DriverViewWindow driverViewWindow=new DriverViewWindow(selectedDriver);
                                            driverViewWindow.setVisible(true);
                                            dispose();
                                        }
                                        else{
                                            System.out.println(Res.ErrorMsg);

                                        }
                                    }
                                    catch (Exception ex){
                                        System.out.println(ex.getMessage());
                                    }

                                }
                            }
                        }
                        catch(Exception ex){
                            System.out.println(ex.getMessage());
                        }


                    }
                    else{
                        System.out.println(resDetails.ErrorMsg);
                    }
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }



            }
        });
        // Buttons

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addWorkerButton);
        buttonPanel.add(addDriverButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(employeeScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ManageEmpUI manageEmpUI = new ManageEmpUI();
                manageEmpUI.setVisible(true);
            }
        });
    }
}
