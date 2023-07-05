package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import javax.swing.table.DefaultTableModel;

public class DestinationModel extends DefaultTableModel {

    String sId, order;
    String sName;
    String sType;
    String sAddress;
    String errLabel;
    static final Object [] headers = {"Item:", "Picked/Shipped:","Amount at Arrival:","Amount After Leave:"};
    Object [][] routeSum;

    public DestinationModel(String sId,String order, String[][] siteItems,String[] siteInfo){
        super(siteItems,headers);
        routeSum = siteItems;
        this.sId =sId;
        this.order = order;
        sName = siteInfo[0];
        sType = siteInfo[1];
        sAddress = siteInfo[2];
        errLabel = siteInfo[3];

    }

    public void updateOrder(String x){
        order = x;
    }

    public boolean isCellEditable(int row,int column){
        return false;
    }
}
