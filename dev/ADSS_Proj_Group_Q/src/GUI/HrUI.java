package GUI;

import BusinessLayer.LoadData;
import GUI.Controller.HruiController;
import ServiceLayer.Response;
import ServiceLayer.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HrUI extends JFrame {
    private JButton manageWorkersButton;
    private JButton manageShiftsButton;
    private JButton logoutButton;

    public HrUI(String id) {
        // Set up the JFrame
        setTitle("HR UI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the buttons
        manageWorkersButton = new JButton("Manage Workers");
        manageShiftsButton = new JButton("Manage Shifts");
        logoutButton = new JButton("Logout");

        // Create the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(manageWorkersButton);
        panel.add(manageShiftsButton);
        panel.add(logoutButton);
        HruiController hruiController=new HruiController(this,id);
        // Add the panel to the JFrame
        add(panel);
        // Set the JFrame visible
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           HrUI shiftUI = new HrUI("1");
            loadData();
            shiftUI.setVisible(true);
        });

    }
    private static void loadData(){
        Response res;
        res = ServiceFactory.getInstance().loadData();
        if (res.isErrorResponse()) {
            System.out.println(res);
            res = ServiceFactory.getInstance().loadData();
            if (res.isErrorResponse()) {
                System.out.println(res);
            }
        }
    }
    public JButton getManageWorkersButton() {
        return manageWorkersButton;
    }

    public JButton getManageShiftsButton() {
        return manageShiftsButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
