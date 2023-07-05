package GUI.Controller;

import GUI.AddConstraintWindow;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddConstraintController {
    private ShiftService shiftService;
    public AddConstraintController(AddConstraintWindow addConstraintWindow){
        shiftService=new ShiftService();
        addConstraintWindow.getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected values from the combo boxes
                int year = Integer.parseInt((String) addConstraintWindow.getYearComboBox().getSelectedItem());
                int month = Integer.parseInt((String) addConstraintWindow.getMonthComboBox().getSelectedItem());
                int day = Integer.parseInt((String) addConstraintWindow.getDayComboBox().getSelectedItem());
                String shiftType = (String) addConstraintWindow.getShiftTypeComboBox().getSelectedItem();
                String id= addConstraintWindow.getId();
                Response res = shiftService.AddConstraint(id, year, month, day, shiftType.charAt(0) + "");
                if (!res.isErrorResponse()) {
                    addConstraintWindow.dispose();
                } else {
                    JOptionPane.showMessageDialog(addConstraintWindow, res.ErrorMsg);
                }
            }
        });
    }
}
