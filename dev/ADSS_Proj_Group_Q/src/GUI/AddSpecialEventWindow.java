package GUI;

import BusinessLayer.SiteManager;
import GUI.Controller.AddSpecialEventController;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;
import ServiceLayer.SiteService;
import com.sun.source.tree.NewArrayTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddSpecialEventWindow extends JFrame {
    private String id;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> branchComboBox;
    private JTextField eventTextField;
    private JButton addButton;
    private  JComboBox<String> shiftTypeComboBox;
    private SiteService siteService;

    public AddSpecialEventWindow(String mid){
        siteService=new SiteService(SiteManager.getInstance());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());
        this.id=mid;
        // Create the panel to hold the components
        JPanel addShiftEventPanel = new JPanel();
        addShiftEventPanel.setLayout(new GridBagLayout());

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
        // Add branch options to the combo box
        // Replace the following line with the actual logic to populate the branch options
        List<String> branches= (List<String>) siteService.getAllBranchesNames().ReturnValue;
        for(String branch :branches){
            branchComboBox.addItem(branch);
        }

        JLabel eventLabel = new JLabel("Event:");
        eventTextField = new JTextField(30);
        JLabel shiftTypeLabel = new JLabel("Shift Type:");
        String[] shiftTypes = {"Morning", "Evening"};
        shiftTypeComboBox = new JComboBox<>(shiftTypes);
        addButton = new JButton("Add");

        // Set the constraints for the components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the components to the panel
        addShiftEventPanel.add(yearLabel, constraints);
        addShiftEventPanel.add(yearComboBox, constraints);
        addShiftEventPanel.add(monthLabel, constraints);
        addShiftEventPanel.add(monthComboBox, constraints);
        addShiftEventPanel.add(dayLabel, constraints);
        addShiftEventPanel.add(dayComboBox, constraints);
        addShiftEventPanel.add(branchLabel, constraints);
        addShiftEventPanel.add(branchComboBox, constraints);
        addShiftEventPanel.add(eventLabel, constraints);
        addShiftEventPanel.add(eventTextField, constraints);
        addShiftEventPanel.add(shiftTypeLabel, constraints);
        addShiftEventPanel.add(shiftTypeComboBox, constraints);

        // Add the Add button with updated constraints
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        addShiftEventPanel.add(addButton, constraints);

        // Update the size of the Add button
        addButton.setPreferredSize(new Dimension(80, 30));

        // Create a scroll pane for the panel
        JScrollPane scrollPane = new JScrollPane(addShiftEventPanel);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
        // Display the add shift event frame
        AddSpecialEventController addSpecialEventController=new AddSpecialEventController(this);
        setVisible(true);
    }

    public String getId() {
        return id;
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

    public JTextField getEventTextField() {
        return eventTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JComboBox<String> getShiftTypeComboBox() {
        return shiftTypeComboBox;
    }
}
