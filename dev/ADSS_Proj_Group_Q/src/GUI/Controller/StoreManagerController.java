package GUI.Controller;

import GUI.HrUI;
import GUI.StoreManagerWindow;
import PresentationLayer.GUI.MainShippingGUI.MainShippingView;
import ServiceLayer.EmployeeService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerController {
    private EmployeeService employeeService;
    public StoreManagerController(StoreManagerWindow storeManagerWindow){
        employeeService= new EmployeeService();

        // Add action listeners to buttons
        storeManagerWindow.getManageShippingButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle manageShippingButton click event
                MainShippingView.run();
                storeManagerWindow.dispose();
            }
        });

        storeManagerWindow.getManageHRButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle manageHRButton click event
                HrUI hrui = new HrUI((String) employeeService.getHumanResourceManagerId().ReturnValue);
                hrui.setVisible(true);
                storeManagerWindow.dispose();

            }
        });


    }

}
