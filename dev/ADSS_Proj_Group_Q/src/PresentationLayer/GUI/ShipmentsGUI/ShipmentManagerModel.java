package PresentationLayer.GUI.ShipmentsGUI;

import javax.swing.table.DefaultTableModel;

public class ShipmentManagerModel extends DefaultTableModel {
    static final Object [] headers = {"ID","Zone:","Date","Driver:","Truck","Status:"};
    Object [][] shipments;
    String [] shipmentStatusTypes = {"None","Error","Active","Done"};


    public ShipmentManagerModel(Object[][] sites){
        super(sites, headers);
        this.shipments = sites;
    }

    public void updateSitesInfo(Object[][] filteredSites){
        shipments = filteredSites;
    }

    public Object getIDValueAt(int row, int col) {
        if (shipments == null || shipments.length == 0) {
            return "No entries";
        } else {
            return shipments[row][0];
        }
    }
    @Override
    public boolean isCellEditable(int row,int column){
        return false;
    }
}
