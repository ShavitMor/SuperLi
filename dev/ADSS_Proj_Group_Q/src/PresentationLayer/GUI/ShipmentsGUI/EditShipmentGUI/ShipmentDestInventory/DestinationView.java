package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.EditShipmentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DestinationView extends JFrame {

    DestinationModel model ;
    DestinationController con;
    JLabel orderL;
    JTable routeTable;
    JButton addItemB;
    JButton removeDest;
    JLabel errorLabel;
    private final int offset = 30;
    String sId,order;
    ShipmentDestInventoryView toUpdate;

    public DestinationView(ShipmentDestInventoryView v, String sId,String order){
        super("Shipment " +sId +" destination edit:");
        this.order = order;
        toUpdate = v;
        this.sId = sId;
        this.setLayout(null);
        this.setBounds(200,200,450,600);
        setComps();
    }


    public static void run(String sId,String order,ShipmentDestInventoryView v){
        new DestinationView(v,sId,order).setVisible(true);
    }
    private void setComps() {
        con = new DestinationController(toUpdate,this,sId,order);
        model = con.getModel();
        routeTable = new JTable(model);
        addItemB = new JButton("Add Item");
        removeDest = new JButton("remove Destination");
        JScrollPane routePane = new JScrollPane(routeTable);
        routeTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        routeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    int row = routeTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object item = routeTable.getValueAt(row, 0);
                        Object amount = routeTable.getValueAt(row, 1);

                        handleRouteTableValueClick(item,amount);
                    }
                }
            }
        });
        errorLabel = new JLabel();
        orderL = new JLabel("order");
        setButtonsNames();
        add(routePane);
        JLabel sites = new JLabel("Items of Destination:");
        add(sites);
        sites.setBounds(15,60,150,20);
        routePane.setBounds(15,80,400,250);
        add( addItemB);
        addItemB.setBounds(315,500,100,40);

        add( removeDest);
        removeDest.setBounds(15,500,100,40);
        add( errorLabel);
        errorLabel.setBounds(15,280,150,20);
        addItemB.setVisible(true);;
        removeDest.setVisible(true);;
        addItemB.addActionListener(con);
        removeDest.addActionListener(con);
        errorLabel.setVisible(true);;
        con.setErrorLabel(errorLabel);

    }
    private void handleRouteTableValueClick(Object value,Object value2) {
        con.renEditItem(value,value2);
    }


    public void updateTableInfo() {
        order = model.order;
        orderL.setText(order);

    }


    private void setButtonsNames(){
        removeDest.setName("removeB");
        addItemB.setName("AddItemB");
    }
}
