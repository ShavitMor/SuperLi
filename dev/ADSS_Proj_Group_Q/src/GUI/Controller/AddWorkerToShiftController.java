package GUI.Controller;

import GUI.AddWorkerToShiftWindow;
import Model.ShiftModel;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddWorkerToShiftController {
    private ShiftService shiftService;
    public AddWorkerToShiftController(AddWorkerToShiftWindow addWorkerToShiftWindow, ShiftModel shiftModel){
        shiftService=new ShiftService();
        addWorkerToShiftWindow.getRoleComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected role from the combo box
                String selectedRole = (String) addWorkerToShiftWindow.getRoleComboBox().getSelectedItem();
                selectedRole=getRole(selectedRole);
               addWorkerToShiftWindow.getEmployeeIdComboBox().removeAllItems();
                Response response = shiftService.showAvailableWorkersForShiftByRole(shiftModel.getDate().getYear(),shiftModel.getDate().getMonth().getValue(),shiftModel.getDate().getDayOfMonth(),shiftModel.getShiftType().substring(0,1),selectedRole);
                if(response.isErrorResponse())
                    JOptionPane.showMessageDialog(addWorkerToShiftWindow,response.ErrorMsg);
                else {
                    List<String> workersByRole= (List<String>) response.ReturnValue;
                    for(String st:workersByRole)
                        addWorkerToShiftWindow.getEmployeeIdComboBox().addItem(st);
                }
            }
        });
        addWorkerToShiftWindow.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addWorkerToShiftWindow.getEmployeeIdComboBox()==null)
                    JOptionPane.showMessageDialog(addWorkerToShiftWindow,"please choose worker");
                else {
                    String selectedRole = (String) addWorkerToShiftWindow.getRoleComboBox().getSelectedItem();
                    selectedRole=getRole(selectedRole);
                    String empId= (String) addWorkerToShiftWindow.getEmployeeIdComboBox().getSelectedItem();
                    Response response = shiftService.addWorkerToShift(shiftModel.getDate().getYear(),shiftModel.getDate().getMonth().getValue(),shiftModel.getDate().getDayOfMonth(),shiftModel.getShiftType().substring(0,1),empId,selectedRole,shiftModel.getBranch());
                    if(response.isErrorResponse()){
                        JOptionPane.showMessageDialog(addWorkerToShiftWindow,response.ErrorMsg);
                    }
                    else {
                        shiftModel.addNewWorker(empId);
                        addWorkerToShiftWindow.dispose();
                    }
                }
            }
        });
    }

    private String getRole(String role){
        switch (role) {
            case ("Shift manager"):
                return "shm";
            case ("Sales man"):
                return "sm";
            case ("Store keeper"):
                return "sk";
            case ("Security"):
                return "se";
            case ("Clean"):
                return "c";
            case ("Usher"):
                return "u";
            case ("General"):
                return "g";
        }
        return null;
    }
}
