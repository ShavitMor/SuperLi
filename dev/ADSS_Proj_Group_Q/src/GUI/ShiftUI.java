package GUI;

import BusinessLayer.SiteManager;
import GUI.Controller.ShiftWindowController;
import ServiceLayer.Response;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ShiftUI extends JFrame {
    private JButton addNewShiftButton;
    private JButton viewShiftButton;
    JComboBox<String> yearComboBox;
    JComboBox<String> monthComboBox;
    JComboBox<String> dayComboBox;
    JComboBox<String> branchComboBox;
    JComboBox<String> shiftTypeComboBox;

    public JButton backButton;
    public JButton getBackButton() {
        return backButton;
    }
    SiteService siteService;
    public ShiftUI() {
        super("Shift UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        siteService=new SiteService(SiteManager.getInstance());
        // Create the panel to hold the components
        JPanel shiftPanel = new JPanel();
        shiftPanel.setLayout(new GridBagLayout());

        // Create the labels and combo boxes
        JLabel yearLabel = new JLabel("Year:");
        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 2; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        JLabel monthLabel = new JLabel("Month:");
        monthComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(String.valueOf(i));
        }

        JLabel dayLabel = new JLabel("Day:");
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.valueOf(i));
        }

        JLabel branchLabel = new JLabel("Branch:");
        branchComboBox = new JComboBox<>();
        Response res = siteService.getAllBranchesNames();
        ComboBoxModel<String> boxModel=new DefaultComboBoxModel<>(((ArrayList<String>)res.ReturnValue).toArray(new String[0]));
        branchComboBox.setModel(boxModel);
        if(res.isErrorResponse())
            JOptionPane.showMessageDialog(ShiftUI.this, res.ErrorMsg);

        JLabel shiftTypeLabel = new JLabel("Shift Type:");
        shiftTypeComboBox = new JComboBox<>();
        shiftTypeComboBox.addItem("Morning");
        shiftTypeComboBox.addItem("Evening");

         viewShiftButton = new JButton("View Shift");
         addNewShiftButton = new JButton("Add New Shift");
         backButton=new JButton("back");

        // Set the constraints for the components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the components to the panel
        shiftPanel.add(yearLabel, constraints);
        shiftPanel.add(yearComboBox, constraints);
        shiftPanel.add(monthLabel, constraints);
        shiftPanel.add(monthComboBox, constraints);
        shiftPanel.add(dayLabel, constraints);
        shiftPanel.add(dayComboBox, constraints);
        shiftPanel.add(branchLabel, constraints);
        shiftPanel.add(branchComboBox, constraints);
        shiftPanel.add(shiftTypeLabel, constraints);
        shiftPanel.add(shiftTypeComboBox, constraints);
        shiftPanel.add(viewShiftButton, constraints);
        shiftPanel.add(addNewShiftButton, constraints);
        shiftPanel.add(backButton,constraints);
        ShiftWindowController shiftWindowController=new ShiftWindowController(this);
        // Add the shift panel to the frame
        add(shiftPanel, BorderLayout.CENTER);
    }

    public JButton getAddNewShiftButton() {
        return addNewShiftButton;
    }

    public JButton getViewShiftButton() {
        return viewShiftButton;
    }

    public JComboBox<String> getYearComboBox() {
        return yearComboBox;
    }

    public JComboBox<String> getMonthComboBox() {
        return monthComboBox;
    }

    public JComboBox<String> getDayComboBox() {
        return dayComboBox;
    }

    public JComboBox<String> getBranchComboBox() {
        return branchComboBox;
    }

    public JComboBox<String> getShiftTypeComboBox() {
        return shiftTypeComboBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShiftUI shiftUI = new ShiftUI();
            shiftUI.setVisible(true);
        });
    }
}
