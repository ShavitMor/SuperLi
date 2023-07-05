package GUI.Controller;
import GUI.*;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class EmployeeUIController {
    private EmployeeUI employeeUI;
    private EmployeeService employeeService;
    private ShiftService shiftService;
    private String id;

    public EmployeeUIController(String id,EmployeeUI employeeUI) {
        this.employeeUI = employeeUI;
        this.employeeService = new EmployeeService();
        this.shiftService = new ShiftService();
        this.id=id;

        // Add action listeners to the buttons
        employeeUI.getAddConstraintButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddConstraintWindow addConstraintWindow=new AddConstraintWindow(id);
                addConstraintWindow.setVisible(true);
            }
        });

        employeeUI.getShowFutureShipmentsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame showFutureShiftsFrame = new JFrame("Future Shipments");
                showFutureShiftsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                showFutureShiftsFrame.setSize(300, 200);
                showFutureShiftsFrame.setLayout(new BorderLayout());
                String[] futureShips;
                // Create a sample list of future shifts
                Response res = shiftService.getFutureShipmentsForEmp(id);
                if(!res.isErrorResponse()){
                    ArrayList<String> shipmentsList = (ArrayList<String>) res.ReturnValue;
                    futureShips = shipmentsList.toArray(new String[shipmentsList.size()]);
                    // Create a JList to display the future shifts
                    JList<String> shipmentList = new JList<>(futureShips);
                    // Create a scroll pane for the JList
                    JScrollPane scrollPane = new JScrollPane(shipmentList);
                    // Add the scroll pane to the frame
                    showFutureShiftsFrame.add(scrollPane, BorderLayout.CENTER);
                    // Display the show future shifts frame
                    showFutureShiftsFrame.setVisible(true);
                }
                else{

                    JOptionPane.showMessageDialog(employeeUI, res.ErrorMsg);


                }
            }
        });

        employeeUI.getAddCancelationsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                AddCancelationsWindow addCancelationsWindow=new AddCancelationsWindow(id);
                addCancelationsWindow.setVisible(true);
            }
        });

        employeeUI.getAddSpecialEventButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddSpecialEventWindow addSpecialEventWindow=new AddSpecialEventWindow(id);
                addSpecialEventWindow.setVisible(true);
            }
        });

        employeeUI.getLogoutButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Response res = employeeService.logout(id);
                if(!res.isErrorResponse()) {
                    LoginUI loginUI = new LoginUI();
                   loginUI.setVisible(true);
                    employeeUI.dispose();

                }
                else
                    JOptionPane.showMessageDialog(employeeUI, res.ErrorMsg);
            }
        });
        employeeUI.getShowFutureShiftsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame showFutureShipmentsFrame = new JFrame("Future shifts");
                showFutureShipmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                showFutureShipmentsFrame.setSize(300, 200);
                showFutureShipmentsFrame.setLayout(new BorderLayout());

                // Create a sample list of future shipments
                Response res = employeeService.showShifts(id);
                if(res.isValueResponse()) {
                    ArrayList<String> branchList = (ArrayList<String>) res.ReturnValue;
                    String[] futureShipments = branchList.toArray(new String[branchList.size()]);

                    // Create a JList to display the future shipments
                    JList<String> shipmentsList = new JList<>(futureShipments);

                    // Create a scroll pane for the JList
                    JScrollPane scrollPane = new JScrollPane(shipmentsList);

                    // Add the scroll pane to the frame
                    showFutureShipmentsFrame.add(scrollPane, BorderLayout.CENTER);

                    // Display the show future shipments frame
                    showFutureShipmentsFrame.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(employeeUI, res.ErrorMsg);
            }
        });
    }

}
