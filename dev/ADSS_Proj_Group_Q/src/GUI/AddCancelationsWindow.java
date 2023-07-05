package GUI;

import BusinessLayer.SiteManager;
import GUI.Controller.AddCancelationController;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class AddCancelationsWindow extends JFrame {
    private String id;
    private JButton addButton;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> branchComboBox;
    private JComboBox<String> itemIdComboBox;
    private JComboBox<String> amountComboBox;
    private JComboBox<String> shiftTypeComboBox;
    private SiteService siteService;

    public AddCancelationsWindow(String mid){
        siteService=new SiteService(SiteManager.getInstance());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        // Create the panel to hold the components
        JPanel addCancellationPanel = new JPanel();
        addCancellationPanel.setLayout(new GridBagLayout());
        this.id=mid;
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
        java.util.List<String> branches= (List<String>) siteService.getAllBranchesNames().ReturnValue;
        for(String branch :branches){
            branchComboBox.addItem(branch);
        }
        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdComboBox = new JComboBox<>();
        for (int i = 1; i <= 50; i++) {
            itemIdComboBox.addItem(String.valueOf(i));
        }

        JLabel amountLabel = new JLabel("Amount:");
     amountComboBox = new JComboBox<>();
        for (int i = 1; i <= 50; i++) {
            amountComboBox.addItem(String.valueOf(i));
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
        addCancellationPanel.add(yearLabel, constraints);
        addCancellationPanel.add(yearComboBox, constraints);
        addCancellationPanel.add(monthLabel, constraints);
        addCancellationPanel.add(monthComboBox, constraints);
        addCancellationPanel.add(dayLabel, constraints);
        addCancellationPanel.add(dayComboBox, constraints);
        addCancellationPanel.add(branchLabel, constraints);
        addCancellationPanel.add(branchComboBox, constraints);
        addCancellationPanel.add(itemIdLabel, constraints);
        addCancellationPanel.add(itemIdComboBox, constraints);
        addCancellationPanel.add(amountLabel, constraints);
        addCancellationPanel.add(amountComboBox, constraints);
        addCancellationPanel.add(shiftTypeLabel, constraints);
        addCancellationPanel.add(shiftTypeComboBox, constraints);

        // Add the Add button with updated constraints
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        addCancellationPanel.add(addButton, constraints);

        // Update the size of the Add button
        addButton.setPreferredSize(new Dimension(80, 30));

        // Create a scroll pane for the panel
        JScrollPane scrollPane = new JScrollPane(addCancellationPanel);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
        AddCancelationController addCancelationController=new AddCancelationController(this);
        // Display the add cancellation frame
        setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
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

    public JComboBox<String> getItemIdComboBox() {
        return itemIdComboBox;
    }

    public JComboBox<String> getAmountComboBox() {
        return amountComboBox;
    }

    public JComboBox<String> getShiftTypeComboBox() {
        return shiftTypeComboBox;
    }
}
