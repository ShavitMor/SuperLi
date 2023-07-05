package GUI;

import GUI.Controller.EmployeeUIController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class EmployeeUI extends JFrame{
    private JPanel buttonPanel;
    private JButton addConstraintButton;
    private JButton showFutureShiftsButton;
    private JButton addCancelationsButton;
    private JButton addSpecialEventButton;
    private JButton showFutureShipmentsButton;
    private JButton logoutButton;
    private String id;
    private EmployeeService employeeService;


    public EmployeeUI(String id) {
        // Create the main frame
        this.id=id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(new BorderLayout());
        employeeService=new EmployeeService();
       // JLabel label = new JLabel("Hello "+(String) employeeService.getWorkerName(id).ReturnValue+"!");
        JLabel label=new JLabel("Hello "+ (String) employeeService.getWorkerName(id).ReturnValue);
        label.setPreferredSize(new Dimension(100, 30));

// Create the panel for the label
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(label);

// Add the label panel to the top of the frame
        add(labelPanel, BorderLayout.NORTH);
        // Create the button panel to hold the buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        // Create the buttons
        addConstraintButton = new JButton("Add Constraint");
        showFutureShiftsButton = new JButton("Show Future Shifts");
        addCancelationsButton = new JButton("Add Cancellations");
        addSpecialEventButton = new JButton("Add Special Event");
        showFutureShipmentsButton = new JButton("Show Future Shipments");
        logoutButton = new JButton("Logout");

        // Set the size of the buttons
        Dimension buttonSize = new Dimension(200, 30);
        addConstraintButton.setPreferredSize(buttonSize);
        showFutureShiftsButton.setPreferredSize(buttonSize);
        addCancelationsButton.setPreferredSize(buttonSize);
        addSpecialEventButton.setPreferredSize(buttonSize);
        showFutureShipmentsButton.setPreferredSize(buttonSize);


        // Add the buttons to the button panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(addConstraintButton, constraints);
        buttonPanel.add(showFutureShiftsButton, constraints);
        buttonPanel.add(addCancelationsButton, constraints);
        buttonPanel.add(addSpecialEventButton, constraints);
        buttonPanel.add(showFutureShipmentsButton, constraints);

        // Add the button panel to the frame's content pane
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Create a separate panel for the logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        EmployeeUIController employeeUIController=new EmployeeUIController(id,this);
        // Add the logout panel to the frame's content pane at the bottom
        getContentPane().add(logoutPanel, BorderLayout.SOUTH);

        // Display the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create and show the Employee UI
        EmployeeUI employeeUI = new EmployeeUI("1");
    }

    public JButton getAddConstraintButton() {
        return addConstraintButton;
    }

    public JButton getShowFutureShiftsButton() {
        return showFutureShiftsButton;
    }

    public JButton getAddCancelationsButton() {
        return addCancelationsButton;
    }

    public JButton getAddSpecialEventButton() {
        return addSpecialEventButton;
    }
    public JButton getShowFutureShipmentsButton(){return showFutureShipmentsButton;}

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
