package PresentationLayer.GUI.ShipmentsGUI;

import BusinessLayer.ShippingManager;
import PresentationLayer.GUI.Utils.DatePicker;
import PresentationLayer.GUI.Utils.TimePicker;
import ServiceLayer.ShippingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class ShipmentManagerView extends JFrame {

    Component[] comps;
    ShipmentManagerModel model ;
    ShipmentsViewController controller;
    ShippingService ss = new ShippingService(ShippingManager.getInstance());
    JTable shipmentTable;
    JTextField dateField;
    boolean viewInactive = false;
    private final int offset = 30;
    JFrame toReturn;

    public ShipmentManagerView(JFrame home){
        super("Shipping Manager");
        toReturn = home;
        this.setLayout(null);
        this.setBounds(200,200,450,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new ShipmentManagerModel(getShipmentsInfo(true));
        setComps();
        setButtonsActionListener();
        setCompsBounds();
        setAllVisible(comps);
    }



    public static void run(JFrame toReturn){
        new ShipmentManagerView(toReturn).setVisible(true);
    }
    private void setComps() {
        JComboBox filterBar = new JComboBox(model.shipmentStatusTypes);
        filterBar.addActionListener(controller);
        JButton addShipmentB = new JButton("+");
        JButton searchNameB = new JButton("Filter Date");
        JButton searchIdB = new JButton("Search by ID");
        JButton returnB  = new JButton("Main Menu");
        shipmentTable = new JTable(model);
        JScrollPane tablePane = new JScrollPane(shipmentTable);
        shipmentTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        JLabel filterLabel = new JLabel("Filter by Status:");
        shipmentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    int row = shipmentTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object value = shipmentTable.getValueAt(row, 0);
                        handleTableValueClick(value);
                    }
                }
            }
        });
        controller = new ShipmentsViewController(addShipmentB,returnB,searchNameB,searchIdB,this,toReturn);
        shipmentTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        JCheckBox showInactive = new JCheckBox();
        showInactive.addActionListener(controller);
        this.viewInactive = showInactive.isSelected();
        JLabel inactiveL = new JLabel("Filter By Date?:") ;
        dateField = new JTextField(LocalDate.now().toString());
        dateField.setEditable(false);
        DatePicker dp = new DatePicker();
        dp.connectButtonAndTextBox(searchNameB,dateField,this,((x)->{
            updateTableInfo(x);
        }));

        Component[] arr = {filterBar,shipmentTable,addShipmentB,returnB,tablePane,searchNameB,searchIdB, filterLabel,showInactive,inactiveL,dateField};
        comps = arr;
    }

    private void setCompsBounds(){
        comps[0].setBounds(50-offset,520,120,30);
        comps[2].setBounds(405-offset,505,45,45);
        comps[3].setBounds(50-offset,5,100,20);
        comps[4].setBounds(50-offset,30,400,470);
        comps[6].setBounds(172-offset,530,125,20);
        comps[5].setBounds(300-offset,530,100,20);
        comps[7].setBounds(50-offset,500, 100, 20);
        comps[8].setBounds(300-offset,505,20,20);
        comps[9].setBounds(210-offset,505,100,20);
        comps[10].setBounds(320-offset,505,80,20);


    }

    private void setAllVisible(Component[] comps){
        for (Component comp : comps){
            if(!(comp instanceof JTable)){
                this.add(comp);
            }
            comp.setVisible(true);
        }
    }

    private void setButtonsActionListener(){
        for (Component comp: comps){
            if(comp instanceof JButton){
                ((JButton) comp).addActionListener(controller);
            }
        }
        setComboBox();
    }

    public String[][] getShipmentsInfo(boolean startUp){
        String dateFilter = viewInactive ? dateField.getText() : "";
       return(String[][]) ss.getShipmentsTable(
               startUp ? "None" : ((JComboBox)comps[0]).getSelectedItem().toString(),dateFilter).ReturnValue;
    }

    private void handleTableValueClick(Object value) {
        controller.runEditShipment(value);
    }

    private void setComboBox(){
        ((JComboBox) comps[0]).addActionListener(controller);
    }

    public void updateTableInfo() {
        model.setDataVector(getShipmentsInfo(false),ShipmentManagerModel.headers);
        model.fireTableDataChanged();
        shipmentTable.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public void updateTableInfo(String x) {
        updateTableInfo();
    }

    public void updateTableInfoAndComboBox() {
        ((JComboBox) comps[0]).setSelectedIndex(0);
        updateTableInfo();
    }
}
