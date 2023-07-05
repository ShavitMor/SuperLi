package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import javax.swing.table.DefaultTableModel;

public class ShipmentDestInventoryModel {

    String sId;
    ShipmentDestinationsModel destModel;
    ShipmentItemsModel itemsModel;

    public ShipmentDestInventoryModel(String sId,String[][] dests,String[][] items){
        this.sId = sId;
        destModel = new ShipmentDestinationsModel(dests);
        itemsModel = new ShipmentItemsModel(items);
    }

    class ShipmentDestinationsModel extends DefaultTableModel{
        static final Object [] headers = {"Order","Site Name:","Type","Picked/Dropped","Weight at Site"};
        Object [][] routeSum;

        public ShipmentDestinationsModel(String[][] dests){
            super(dests,headers);
            routeSum = dests;
        }

        public boolean isCellEditable(int row,int column){
            return false;
        }
    }
    class ShipmentItemsModel extends DefaultTableModel{
        static final Object [] headers = {"Item","Unit Weight:","Total Shipped","Total Picked"};
        Object [][] itemsSum;

        public ShipmentItemsModel(String[][] items){
            super(items,headers);
            itemsSum = items;
        }

        public boolean isCellEditable(int row,int column){
            return false;
        }
    }
}
