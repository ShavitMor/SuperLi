package GUI;

import GUI.Controller.StoreManagerController;
import ServiceLayer.Response;
import ServiceLayer.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerWindow extends JFrame {
    JButton manageShippingButton;
    JButton manageHRButton;

    public JButton getManageShippingButton() {
        return manageShippingButton;
    }

    public JButton getManageHRButton() {
        return manageHRButton;
    }

    public StoreManagerWindow() {
        setTitle("StoreManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Create components
        JLabel titleLabel = new JLabel("Hello Store Manager! Choose which actions you want to do:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        manageShippingButton = new JButton("Manage Shipping");
        manageHRButton = new JButton("Manage HR");

        // Create a panel and set layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components to the panel
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(manageShippingButton);
        buttonPanel.add(manageHRButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add panel to the frame
        add(panel);

        StoreManagerController storeManagerController = new StoreManagerController(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loadData();
                new StoreManagerWindow();
            }
        });
    }

    private static void loadData() {
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

}
