package GUI.Controller;

import GUI.AddShiftWindow;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddShiftController {
    private ShiftService shiftService;
    public AddShiftController(AddShiftWindow addShiftWindow){
        shiftService=new ShiftService();
        addShiftWindow.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year= Integer.parseInt((String) addShiftWindow.getYearComboBox().getSelectedItem());
                int month=Integer.parseInt((String) addShiftWindow.getMonthComboBox().getSelectedItem());
                int day=Integer.parseInt((String) addShiftWindow.getDayComboBox().getSelectedItem());
                String shiftType= ((String) addShiftWindow.getShiftTypeComboBox().getSelectedItem()).substring(0,1);
                String branch= (String) addShiftWindow.getBranchComboBox().getSelectedItem();
                int startTime= Integer.parseInt((String) addShiftWindow.getShiftTimeComboBox().getSelectedItem());
                int shiftPeriod= Integer.parseInt((String) addShiftWindow.getPeriodComboBox().getSelectedItem());
                Response res=shiftService.addShift(year,month,day,startTime,shiftPeriod,shiftType,branch);
                if(res.isErrorResponse())
                    JOptionPane.showMessageDialog(addShiftWindow,res.ErrorMsg);
                else {
                    JOptionPane.showMessageDialog(addShiftWindow, "Shift added");
                    addShiftWindow.dispose();
                }


            }
        });;
    }
}
