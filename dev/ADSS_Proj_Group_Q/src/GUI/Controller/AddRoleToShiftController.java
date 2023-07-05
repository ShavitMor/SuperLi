package GUI.Controller;

import GUI.AddRoleToShiftUI;
import Model.ShiftModel;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRoleToShiftController {
    private ShiftService shiftService;
    public AddRoleToShiftController(AddRoleToShiftUI addRoleToShiftUI, ShiftModel shiftModel){
        shiftService=new ShiftService();
        addRoleToShiftUI.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role= (String) addRoleToShiftUI.getRoleComboBox().getSelectedItem();
                Integer amount= (Integer) addRoleToShiftUI.getAmount().getSelectedItem();
                role=getRole(role);
                Response response = shiftService.addRoleToShift(shiftModel.getDate().getYear(),shiftModel.getDate().getMonth().getValue(),shiftModel.getDate().getDayOfMonth(),shiftModel.getShiftType().substring(0,1),role,amount,shiftModel.getBranch());
                if(response.isErrorResponse())
                    JOptionPane.showMessageDialog(addRoleToShiftUI,response.ErrorMsg);
                else {
                    JOptionPane.showMessageDialog(addRoleToShiftUI,"role added");
                    addRoleToShiftUI.dispose();
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
        return "bad for "+role;
    }
}
