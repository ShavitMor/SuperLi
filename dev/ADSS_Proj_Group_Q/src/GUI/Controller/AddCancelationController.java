package GUI.Controller;

import GUI.AddCancelationsWindow;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCancelationController {
    private ShiftService shiftService;
    public AddCancelationController(AddCancelationsWindow addCancelationsWindow){
        shiftService=new ShiftService();
        addCancelationsWindow.getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the values from the combo boxes
                int year = Integer.parseInt((String) addCancelationsWindow.getYearComboBox().getSelectedItem());
                int month = Integer.parseInt((String) addCancelationsWindow.getMonthComboBox().getSelectedItem());
                int day = Integer.parseInt((String) addCancelationsWindow.getDayComboBox().getSelectedItem());
                String branch = (String) addCancelationsWindow.getBranchComboBox().getSelectedItem();
                int itemId = Integer.parseInt((String) addCancelationsWindow.getItemIdComboBox().getSelectedItem());
                int amount = Integer.parseInt((String) addCancelationsWindow.getAmountComboBox().getSelectedItem());
                String shiftType = (String) addCancelationsWindow.getShiftTypeComboBox().getSelectedItem();
                String id= addCancelationsWindow.getId();
                Response res = shiftService.addShiftCancellation(id, year, month, day, shiftType.charAt(0) + "", branch, itemId, amount);
                if (res.isEmpty()) {
                    addCancelationsWindow.dispose();
                } else {
                    JOptionPane.showMessageDialog(addCancelationsWindow, res.ErrorMsg);
                }
            }
        });

    }
}
