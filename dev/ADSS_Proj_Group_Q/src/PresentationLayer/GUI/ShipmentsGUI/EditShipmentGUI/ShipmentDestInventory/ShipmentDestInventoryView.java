package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.EditShipmentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShipmentDestInventoryView extends JFrame {

    ShipmentDestInventoryModel model ;
    ShipmentsInvDestController con;
    JTable routeTable;
    JTable itemTable;
    JButton setSourceB;
    JButton addDestB;
    JButton addItemB;
    JLabel errorLabel;
    private final int offset = 30;
    String sId;
    EditShipmentView toUpdate;

    public ShipmentDestInventoryView(EditShipmentView v, String sId){
        super("Shipment " +sId +" Route:");
        toUpdate = v;
        this.sId = sId;
        this.setLayout(null);
        this.setBounds(200,200,450,600);
        setComps();
    }


    public static void run(String sId,EditShipmentView v){
        new ShipmentDestInventoryView(v,sId).setVisible(true);
    }
    private void setComps() {
        con = new ShipmentsInvDestController(toUpdate,this,sId);
        model = con.getModel();
        routeTable = new JTable(model.destModel);
        itemTable = new JTable(model.itemsModel);
        setSourceB = new JButton("Set Source");
        addDestB = new JButton("Add Destination");
        addItemB = new JButton("Add Item");
        JScrollPane routePane = new JScrollPane(routeTable);
        JScrollPane itemPane = new JScrollPane(itemTable);
        routeTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        itemTable.setPreferredScrollableViewportSize(new Dimension(300, 150));
        routeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    int row = routeTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object value = routeTable.getValueAt(row, 0);
                        handleRouteTableValueClick(value);
                    }
                }
            }
        });
        itemTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    int row = itemTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object itemName = itemTable.getValueAt(row, 0);
                        Object weight = itemTable.getValueAt(row, 1);
                        handleItemTableValueClick(itemName,weight);
                    }
                }
            }
        });
        routeTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        errorLabel = new JLabel();
        setButtonsNames();
        add(routePane);
        JLabel sites = new JLabel("Source and Destinations:");
        add(sites);
        sites.setBounds(15,10,150,20);
        routePane.setBounds(15,30,400,250);
        add(itemPane);
        JLabel itemsL = new JLabel("Items in Shipment:");
        add(itemsL);
        itemsL.setBounds(15,300,150,20);
        itemPane.setBounds(15,320,400,150);
        add( setSourceB);
        setSourceB.setBounds(315,500,100,40);
        add( addDestB);
        addDestB.setBounds(135,500,150,40);
        add( addItemB);
        addItemB.setBounds(15,500,100,40);
        add( errorLabel);
        errorLabel.setBounds(15,280,150,20);
        routePane.setVisible(true);
        itemPane.setVisible(true);;
        setSourceB.setVisible(true);;
        addDestB.setVisible(true);;
        addItemB.setVisible(true);;
        setSourceB.addActionListener(con);
        addDestB.addActionListener(con);
        addItemB.addActionListener(con);
        errorLabel.setVisible(true);;
        con.setErrorLabel(errorLabel);

    }

    private void handleItemTableValueClick(Object value,Object weight) {
        con.runEditItem(value,weight);
    }
    private void handleRouteTableValueClick(Object value) {
        con.runEditDest(value);
    }

    public void updateRouteTableInfo() {
        routeTable.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public void updateTableInfo() {
        con.updateRouteTableInfo();
        con.updateItemTableInfo();
    }

    public void updateTableInfoAndComboBox() {
        updateTableInfo();
    }

    private void setButtonsNames(){
        setSourceB.setName("sourceB");
        addDestB.setName("AddDestB");
        addItemB.setName("AddItemB");
    }
}
