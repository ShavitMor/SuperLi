package PresentationLayer.GUI.TruckGUI;

import BusinessLayer.TruckManager;
import ServiceLayer.TruckService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TruckManagerView extends JFrame {

    Component[] comps;
    TruckManagerModel model;
    TruckManagerController controller;
    TruckService service = new TruckService(TruckManager.getInstance());
    JTable truckTable;
    boolean viewInactive = false;
    private final int offset = 30;
    JFrame toReturn;

    public TruckManagerView(JFrame home) {
        super("Truck Manager");
        toReturn = home;
        this.setLayout(null);
        this.setBounds(200, 200, 550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new TruckManagerModel(getTruckInfo(true));
        setComps();
        setButtonsActionListener();
        setCompsBounds();
        setAllVisible(comps);
    }

    public static void run(JFrame toReturn) {
        new TruckManagerView(toReturn).setVisible(true);
    }

    private void setComps() {
        JComboBox filterBar = new JComboBox(model.minimumLicenceTypes);
        filterBar.addActionListener(controller);
        JButton addTruckB = new JButton("+");
        JButton searchByLicence = new JButton("Search by licence");
        JButton searchIdB = new JButton("Search by ID");
        JButton returnB = new JButton("Main Menu");
        truckTable = new JTable(model);
        JScrollPane tablePane = new JScrollPane(truckTable);
        truckTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        JLabel filterLabel = new JLabel("Filter-Required licence:");
        truckTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    int row = truckTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object value = truckTable.getValueAt(row, 0);
                        handleTableValueClick(value); // Call your function here
                    }
                }
            }
        });
        controller = new TruckManagerController(addTruckB, returnB, searchByLicence, searchIdB, this, toReturn);
        truckTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        JCheckBox showInactive = new JCheckBox();
        showInactive.addActionListener(controller);
        this.viewInactive = showInactive.isSelected();
        JLabel inactiveL = new JLabel("Only Temp. Control?");

        Component[] arr = {filterBar, truckTable, addTruckB, returnB, tablePane, searchByLicence, searchIdB, filterLabel, showInactive, inactiveL};
        comps = arr;
    }

    private void setCompsBounds() {
        comps[0].setBounds(50 - offset, 520, 120, 30);
        comps[2].setBounds(490 - offset, 505, 60, 45);
        comps[3].setBounds(50 - offset, 5, 100, 20);
        comps[4].setBounds(50 - offset, 30, 500, 470);
        comps[5].setBounds(325 - offset, 505, 160, 20);
        comps[6].setBounds(325 - offset, 530, 160, 20);
        comps[7].setBounds(50 - offset, 500, 150, 20);
        comps[8].setBounds(225 - offset, 522, 40, 40);
        comps[9].setBounds(200 - offset, 500, 150, 20);

    }

    private void setAllVisible(Component[] comps) {
        for (Component comp : comps) {
            if (!(comp instanceof JTable)) {
                this.add(comp);
            }
            comp.setVisible(true);
        }
    }

    private void setButtonsActionListener() {
        for (Component comp : comps) {
            if (comp instanceof JButton) {
                ((JButton) comp).addActionListener(controller);
            }
        }
        setComboBox();
    }

    public String[][] getTruckInfo(boolean startUp) {
        return (String[][]) service.getTruckTableInfo(
                startUp ? "None" : ((JComboBox) comps[0]).getSelectedItem().toString(), viewInactive + "").ReturnValue;
    }

    private void handleTableValueClick(Object value) {
        // Perform your desired action here
        controller.runEditTruck(value);
    }

    private void setComboBox() {
        ((JComboBox) comps[0]).addActionListener(controller);
    }

    public void updateTableInfo() {
        model.setDataVector(getTruckInfo(false), TruckManagerModel.headers);
        model.fireTableDataChanged();
        truckTable.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public void updateTableInfoAndComboBox() {
        ((JComboBox) comps[0]).setSelectedIndex(0);
        updateTableInfo();
    }
}
