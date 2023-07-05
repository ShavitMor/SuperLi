package GUI;

import GUI.Controller.AddConstraintController;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


public class AddConstraintWindow extends JFrame {
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> shiftTypeComboBox;
    private String id;
    private JButton addButton;

    public AddConstraintWindow(String id){
        this.id=id;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Create the panel to hold the components
        JPanel addConstraintPanel = new JPanel();
        addConstraintPanel.setLayout(new GridBagLayout());

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
        addConstraintPanel.add(yearLabel, constraints);
        addConstraintPanel.add(yearComboBox, constraints);
        addConstraintPanel.add(monthLabel, constraints);
        addConstraintPanel.add(monthComboBox, constraints);
        addConstraintPanel.add(dayLabel, constraints);
        addConstraintPanel.add(dayComboBox, constraints);
        addConstraintPanel.add(shiftTypeLabel, constraints);
        addConstraintPanel.add(shiftTypeComboBox, constraints);

        // Add the Add button with updated constraints
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        addConstraintPanel.add(addButton, constraints);

        // Update the size of the Add button
        addButton.setPreferredSize(new Dimension(80, 30));

        // Create a scroll pane for the panel
        JScrollPane scrollPane = new JScrollPane(addConstraintPanel);
        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
        AddConstraintController addConstraintController=new AddConstraintController(this);
        // Display the add constraint frame
        setVisible(true);
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

    public String getId() {
        return id;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
