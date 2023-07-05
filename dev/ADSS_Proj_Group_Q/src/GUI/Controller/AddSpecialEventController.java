package GUI.Controller;

import GUI.AddSpecialEventWindow;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSpecialEventController {
    private ShiftService shiftService;
    public AddSpecialEventController(AddSpecialEventWindow addSpecialEventWindow){
        shiftService=new ShiftService();
        addSpecialEventWindow.getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the values from the combo boxes and text field
                int year = Integer.parseInt((String) addSpecialEventWindow.getYearComboBox().getSelectedItem());
                int month = Integer.parseInt((String) addSpecialEventWindow.getMonthComboBox().getSelectedItem());
                int day = Integer.parseInt((String) addSpecialEventWindow.getDayComboBox().getSelectedItem());
                String event = addSpecialEventWindow.getEventTextField().getText();
                String shiftType = (String) addSpecialEventWindow.getShiftTypeComboBox().getSelectedItem();
                String branch = (String) addSpecialEventWindow.getBranchComboBox().getSelectedItem();
                String id=addSpecialEventWindow.getId();
                Response res = shiftService.addShiftEvent(id, year, month, day, shiftType.substring(0, 1), branch, event);
                if (res.isEmpty()) {
                    addSpecialEventWindow.dispose();
                } else {
                    JOptionPane.showMessageDialog(addSpecialEventWindow, res.ErrorMsg);
                }
            }
        });
    }
}
