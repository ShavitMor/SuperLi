package GUI;

import GUI.Controller.ShiftViewWindowController;
import Model.ShiftModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShiftViewWindow extends JFrame {
    private JButton addWorkerButton;
    private JButton addRoleButton;
    private JButton showShiftEventsButton;
    private JButton publishShiftButton;
    private JButton cancellationsButton;
    private JLabel yearValueLabel;
    private JLabel monthValueLabel;
    private JLabel dayValueLabel;
    private JLabel shiftTypeValueLabel;
    private JLabel branchValueLabel;
    private JLabel isPublishedLabelValue;
    private JList<String> workersList;
    public ShiftViewWindow(ShiftModel shiftModel){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        // Create the panel to hold the components
        JPanel viewShiftPanel = new JPanel();
        viewShiftPanel.setLayout(new GridBagLayout());
        // Create the labels for year, month, day, shift type, and branch
        JLabel yearViewLabel = new JLabel("Year:");
        yearValueLabel = new JLabel(String.valueOf(shiftModel.getDate().getYear())); // Replace with actual value
        JLabel monthViewLabel = new JLabel("Month:");
        monthValueLabel = new JLabel(String.valueOf(shiftModel.getDate().getMonth().getValue())); // Replace with actual value
        JLabel dayViewLabel = new JLabel("Day:");
        dayValueLabel = new JLabel(String.valueOf(shiftModel.getDate().getDayOfMonth())); // Replace with actual value
        JLabel shiftTypeViewLabel = new JLabel("Shift Type:");
        shiftTypeValueLabel = new JLabel(shiftModel.getShiftType()); // Replace with actual value
        JLabel branchViewLabel = new JLabel("Branch:");
        branchValueLabel = new JLabel(shiftModel.getBranch()); // Replace with actual value

        // Create the labels for start time and end time
        JLabel startTimeLabel = new JLabel("Start Time:");
        JLabel startTimeValueLabel = new JLabel(String.valueOf(shiftModel.getBegin())); // Replace with actual value
        JLabel endTimeLabel = new JLabel("End Time:");
        JLabel endTimeValueLabel = new JLabel(String.valueOf(shiftModel.getEnd())); // Replace with actual value
        JLabel isPublishedLabel=new JLabel("Published:");
        isPublishedLabelValue=new JLabel(String.valueOf(shiftModel.isPublished()));
        // Create the visible JList on the right side

        List<String> lst= shiftModel.getWorkers();
        DefaultListModel<String> defaultListModel=new DefaultListModel();
        for(String s:lst)
            defaultListModel.addElement(s);
        workersList = new JList<>(defaultListModel);
        JScrollPane visibleListScrollPane = new JScrollPane(workersList);
        visibleListScrollPane.setPreferredSize(new Dimension(150, 0)); // Set the desired width
        visibleListScrollPane.setMinimumSize(new Dimension(150, 0)); // S

        // Create the invisible JList in the middle
        JList<String> invisibleList = new JList<>();
        invisibleList.setVisibleRowCount(4); // Adjust the number of visible rows as needed
        JScrollPane invisibleListScrollPane = new JScrollPane(invisibleList);
        invisibleListScrollPane.setSize(new Dimension(0, 0));
        invisibleListScrollPane.setVisible(false);
        // Create the buttons: Add Worker to Shift, Add Role to Shift, Show Shift Events,
        // Publish Shift, and Cancellations
        addWorkerButton = new JButton("Add Worker to Shift");
        addRoleButton = new JButton("Add Role to Shift");
        showShiftEventsButton = new JButton("Show Shift Events");
        publishShiftButton = new JButton("Publish Shift");
        cancellationsButton = new JButton("Cancellations");

        // Set the constraints for the components
        GridBagConstraints viewConstraints = new GridBagConstraints();
        viewConstraints.gridx = 0;
        viewConstraints.gridy = GridBagConstraints.RELATIVE;
        viewConstraints.anchor = GridBagConstraints.WEST;
        viewConstraints.insets = new Insets(5, 5, 5, 5);

        // Add the components to the panel
        viewShiftPanel.add(yearViewLabel, viewConstraints);
        viewShiftPanel.add(yearValueLabel, viewConstraints);
        viewShiftPanel.add(monthViewLabel, viewConstraints);
        viewShiftPanel.add(monthValueLabel, viewConstraints);
        viewShiftPanel.add(dayViewLabel, viewConstraints);
        viewShiftPanel.add(dayValueLabel, viewConstraints);
        viewShiftPanel.add(shiftTypeViewLabel, viewConstraints);
        viewShiftPanel.add(shiftTypeValueLabel, viewConstraints);
        viewShiftPanel.add(branchViewLabel, viewConstraints);
        viewShiftPanel.add(branchValueLabel, viewConstraints);
        viewShiftPanel.add(startTimeLabel, viewConstraints);
        viewShiftPanel.add(startTimeValueLabel, viewConstraints);
        viewShiftPanel.add(endTimeLabel, viewConstraints);
        viewShiftPanel.add(endTimeValueLabel, viewConstraints);
        viewShiftPanel.add(isPublishedLabel,viewConstraints);
        viewShiftPanel.add(isPublishedLabelValue,viewConstraints);

        // Add the visible list on the right
        viewShiftPanel.add(visibleListScrollPane, viewConstraints);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(addWorkerButton, viewConstraints);
        buttonPanel.add(addRoleButton, viewConstraints);
        buttonPanel.add(showShiftEventsButton, viewConstraints);
        buttonPanel.add(publishShiftButton, viewConstraints);
        buttonPanel.add(cancellationsButton, viewConstraints);

        // Add the invisible list in the middle
        add(buttonPanel, BorderLayout.WEST);
        add(viewShiftPanel,BorderLayout.CENTER);
        add(visibleListScrollPane, BorderLayout.EAST);
        add(invisibleListScrollPane, BorderLayout.SOUTH);
        ShiftViewWindowController shiftViewWindowController=new ShiftViewWindowController(this,shiftModel);
        shiftModel.addListener(this);
        // Display the view shift frame
        setVisible(true);
    }

    public JButton getAddWorkerButton() {
        return addWorkerButton;
    }

    public JButton getAddRoleButton() {
        return addRoleButton;
    }

    public JButton getShowShiftEventsButton() {
        return showShiftEventsButton;
    }

    public JButton getPublishShiftButton() {
        return publishShiftButton;
    }

    public JButton getCancellationsButton() {
        return cancellationsButton;
    }

    public JLabel getYearValueLabel() {
        return yearValueLabel;
    }

    public JLabel getMonthValueLabel() {
        return monthValueLabel;
    }

    public JLabel getDayValueLabel() {
        return dayValueLabel;
    }

    public JLabel getShiftTypeValueLabel() {
        return shiftTypeValueLabel;
    }

    public JLabel getBranchValueLabel() {
        return branchValueLabel;
    }
    public void update(ShiftModel shiftModel){
        isPublishedLabelValue.setText(String.valueOf(shiftModel.isPublished()));
        List<String> lst= shiftModel.getWorkers();
        DefaultListModel<String> defaultListModel=new DefaultListModel();
        for(String s:lst)
            defaultListModel.addElement(s);
        workersList.setModel(defaultListModel);
    }
}
