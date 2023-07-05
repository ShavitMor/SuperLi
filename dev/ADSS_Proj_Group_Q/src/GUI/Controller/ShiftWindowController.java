package GUI.Controller;

import GUI.*;
import Model.ShiftModel;
import PresentationLayer.HRUI;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class ShiftWindowController {
    private ShiftService shiftService;
    private EmployeeService employeeService;
    public ShiftWindowController(ShiftUI shiftUI){
        shiftService=new ShiftService();
        employeeService=new EmployeeService();
        shiftUI.getAddNewShiftButton().addActionListener(e -> {
            AddShiftWindow addShiftWindow=new AddShiftWindow();
            addShiftWindow.setVisible(true);
        });
        shiftUI.getViewShiftButton().addActionListener(e -> {
            // Create a new JFrame for the view shift
            int year= Integer.parseInt((String)shiftUI.getYearComboBox().getSelectedItem());
            int month=Integer.parseInt((String) shiftUI.getMonthComboBox().getSelectedItem());
            int day=Integer.parseInt((String)shiftUI.getDayComboBox().getSelectedItem());
            String shiftType= ((String) shiftUI.getShiftTypeComboBox().getSelectedItem()).substring(0,1);
            String branch= (String) shiftUI.getBranchComboBox().getSelectedItem();
            Response res = shiftService.isShiftExist(year,month,day,shiftType,branch);
            if(res.isErrorResponse())
                JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
            if(res.ReturnValue.equals(false))
                JOptionPane.showMessageDialog(shiftUI, "shift doesnt exist in system");
            else {
                Response response=shiftService.getShiftBegin(year,month,day,shiftType,branch);
                if(response.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response1=shiftService.getShiftEnd(year,month,day,shiftType,branch);
                if(response1.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response2=shiftService.getShiftWorkers(year,month,day,shiftType,branch);
                if(response2.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response3 =shiftService.getShiftRolesNeeded(year,month,day,shiftType,branch);
                if(response3.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response4=shiftService.getCancelationsByShift(year,month,day,shiftType,branch);
                if(response4.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response5=shiftService.shiftEvents(year,month,day,shiftType,branch);
                if(response5.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                Response response6 =shiftService.getShiftIsPublished(year,month,day,shiftType,branch);
                if(response6.isErrorResponse())
                    JOptionPane.showMessageDialog(shiftUI, res.ErrorMsg);
                ShiftModel shiftModel=new ShiftModel(year,month,day,(String) shiftUI.getShiftTypeComboBox().getSelectedItem(),branch,(LocalTime) response.ReturnValue,(LocalTime) response1.ReturnValue,(HashMap<String, Integer>) response3.ReturnValue,(List<String>) response2.ReturnValue,(List<String>)response5.ReturnValue,(List<String>)response4.ReturnValue,(boolean) response6.ReturnValue);
                ShiftViewWindow shiftViewWindow = new ShiftViewWindow(shiftModel);
                shiftViewWindow.setVisible(true);
            }
        });
        shiftUI.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HrUI hrui=new HrUI((String) employeeService.getHumanResourceManagerId().ReturnValue);
                hrui.setVisible(true);
                shiftUI.dispose();
            }
        });

    }
}
