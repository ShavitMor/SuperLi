package GUI;

import BusinessLayer.SiteManager;
import GUI.Controller.AddShiftController;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class AddShiftWindow extends JFrame {
    private JFrame frame;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> shiftTypeComboBox;
    private JComboBox<String> branchComboBox;
    private JComboBox<String> shiftTimeComboBox;
    private JComboBox<String> periodComboBox;
    private JButton addButton;
    SiteService siteService;

    public AddShiftWindow() {
        frame = new JFrame("Shift Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Create labels
        JLabel yearLabel = new JLabel("Year:");
        JLabel monthLabel = new JLabel("Month:");
        JLabel dayLabel = new JLabel("Day:");
        JLabel shiftTypeLabel = new JLabel("Shift Type:");
        JLabel branchLabel = new JLabel("Branch:");
        JLabel shiftTimeLabel = new JLabel("Start Time:");
        JLabel periodLabel = new JLabel("Period:");
        siteService=new SiteService(SiteManager.getInstance());
        // Create combo boxes
        yearComboBox = new JComboBox<>(getYearValues());
        monthComboBox = new JComboBox<>(getMonthValues());
        dayComboBox = new JComboBox<>(getDayValues());
        shiftTypeComboBox = new JComboBox<>(getShiftTypeValues());
        branchComboBox = new JComboBox<>(getBranchValues());
        shiftTimeComboBox = new JComboBox<>(getShiftTimeValues());
        periodComboBox = new JComboBox<>(getPeriodValues());

        // Create add button
         addButton = new JButton("Add");
        // Create panel to hold components
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(yearLabel);
        panel.add(yearComboBox);
        panel.add(monthLabel);
        panel.add(monthComboBox);
        panel.add(dayLabel);
        panel.add(dayComboBox);
        panel.add(shiftTypeLabel);
        panel.add(shiftTypeComboBox);
        panel.add(branchLabel);
        panel.add(branchComboBox);
        panel.add(shiftTimeLabel);
        panel.add(shiftTimeComboBox);
        panel.add(periodLabel);
        panel.add(periodComboBox);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);
        AddShiftController addShiftController=new AddShiftController(this);
        // Add panel to frame
        frame.getContentPane().add(panel);

        // Display the frame
        frame.setVisible(true);
    }

    private String[] getYearValues() {
        // Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Create an array of two years: current year and next year
        String[] yearValues = new String[2];
        yearValues[0] = String.valueOf(currentYear);
        yearValues[1] = String.valueOf(currentYear + 1);

        return yearValues;
    }

    private String[] getMonthValues() {
        // Create an array of month values from 1 to 12
        String[] monthValues = new String[12];
        for (int i = 0; i < 12; i++) {
            monthValues[i] = String.valueOf(i + 1);
        }

        return monthValues;
    }

    private String[] getDayValues() {
        // Create an array of day values from 1 to 31
        String[] dayValues = new String[31];
        for (int i = 0; i < 31; i++) {
            dayValues[i] = String.valueOf(i + 1);
        }

        return dayValues;
    }

    private String[] getShiftTypeValues() {
        // Create an array of shift type values: Morning and Evening
        String[] shiftTypeValues = {"Morning", "Evening"};

        return shiftTypeValues;
    }

    private String[] getBranchValues() {
        // Create an array of branch values
        // Replace with your actual branch values
        ArrayList<String> branchList = (ArrayList<String>) siteService.getAllBranchesNames().ReturnValue;
        String[] branchValues = branchList.toArray(new String[branchList.size()]);


        return branchValues;
    }

    private String[] getShiftTimeValues() {
        // Create an array of shift time values from 0 to 23
        String[] shiftTimeValues = new String[24];
        for (int i = 0; i < 24; i++) {
            shiftTimeValues[i] = String.valueOf(i);
        }

        return shiftTimeValues;
    }

    private String[] getPeriodValues() {
        // Create an array of period values from 1 to 8
        String[] periodValues = new String[8];
        for (int i = 0; i < 8; i++) {
            periodValues[i] = String.valueOf(i + 1);
        }

        return periodValues;
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

    public JComboBox<String> getShiftTypeComboBox() {
        return shiftTypeComboBox;
    }

    public JComboBox<String> getBranchComboBox() {
        return branchComboBox;
    }

    public JComboBox<String> getShiftTimeComboBox() {
        return shiftTimeComboBox;
    }

    public JComboBox<String> getPeriodComboBox() {
        return periodComboBox;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddShiftWindow();
            }
        });
    }
}
