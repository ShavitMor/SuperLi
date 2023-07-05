package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import BusinessLayer.ShippingManager;
import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.EditShipmentView;
import PresentationLayer.GUI.Utils.ChooseFromComboPane;
import PresentationLayer.GUI.Utils.VoidStringUpdater;
import ServiceLayer.Response;
import ServiceLayer.ShippingService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipmentsInvDestController implements ActionListener {

    String sId;
    ShipmentDestInventoryModel model;
    ShipmentDestInventoryView view;
    ShippingService ss = new ShippingService(ShippingManager.getInstance());
    EditShipmentView toUpdate;

    public ShipmentsInvDestController(EditShipmentView tp,ShipmentDestInventoryView v, String sId) {
        toUpdate =tp;
        this.view = v;
        this.sId =sId;
        String[][] dests = getRouteInfo();
        String[][] items = getItemsInfo();
        model = new ShipmentDestInventoryModel(sId,dests,items);
    }

    public String[][] getItemsInfo() {
        Response res = ss.getItemInfo(sId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);

        }
        return res.isErrorResponse() ? new String[0][4] : ((String[][])res.ReturnValue);
    }

    public String[][] getRouteInfo() {
        Response res = ss.getRouteInfo(sId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);

        }
        return res.isErrorResponse() ? new String[0][5] : ((String[][])res.ReturnValue);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        switch (((JButton)o).getName()){
            case "AddItemB":
                addNewItem();
                break;
            case "AddDestB":
                addNewDest();
                break;
            case "sourceB":
                setSource();
                break;

        }
        updateAll();
    }

    private void setSource() {
        Response res = ss.getSourcesInfo();
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[][] data = (String[][])res.ReturnValue;
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data[0],data[1], view);
        choose.run((x)->setSourceIn(x),"Select Source");
    }

    private void setSourceIn(String x) {
        Response res = x== null ?  null : ss.setShipmentSourceById(sId, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            updateAll();
        }
    }

    private void addNewDest() {
        Response res = ss.getDestsInfo(sId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[][] data = (String[][])res.ReturnValue;
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data[0],data[1], view);
        choose.run((x)->setDestIn(x), "Select site to add");
    }

    private void setDestIn(String x) {
        Response res = x== null ?  null : ss.addDestGetOrder(sId, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            updateAll();
        }
    }

    private void addNewItem() {
        String itemName = JOptionPane.showInputDialog("Enter Item name:");
        String weight = JOptionPane.showInputDialog("Enter single Unit Weight (KG):");
        Response res = ss.addNewItemToShipment(sId,itemName,weight);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Item " +itemName +" added!" , "Shipment item added!", JOptionPane.INFORMATION_MESSAGE);
            updateAll();
        }
        
        
    }

    public ShipmentDestInventoryModel getModel(){
        return model;
    }

    public void runEditItem(Object value,Object weight) {
        int chosen = new EditItemOptionPane((String)value,(String)weight).run();
        switch (chosen) {
            case 0:
                deleteItem((String) value);
                break;
        }
    }


    private void deleteItem(String value) {

        int delete = JOptionPane.showConfirmDialog(view,"Delete "+value+" from this shipment?","Removing item",JOptionPane.YES_NO_OPTION);
        if(delete != JOptionPane.YES_OPTION){
            return;
        }
        Response res = ss.removeItemFromShipment(sId,value);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Item " +value +" removed!" , "Shipment item removed!", JOptionPane.INFORMATION_MESSAGE);
            updateAll();
        }
    }
    public void runEditDest(Object value) {
        DestinationView view = new DestinationView(this.view, sId, value.toString());
        view.setVisible(true);
    }



    public void updateView() {
        view.updateTableInfoAndComboBox();
    }

    public void updateAll(){
        updateItemTableInfo();
        updateRouteTableInfo();
    }

    public void updateItemTableInfo() {
        model.itemsModel.setDataVector(getItemsInfo(),model.itemsModel.headers);
        model.itemsModel.fireTableDataChanged();
        toUpdate.updateData();
    }
    public void updateRouteTableInfo() {
        model.destModel.setDataVector(getRouteInfo(),model.destModel.headers);
        model.destModel.fireTableDataChanged();
        view.updateRouteTableInfo();
        toUpdate.updateData();
    }

    public void setErrorLabel(JLabel errorLabel) {
        //TODO
    }

    class EditItemOptionPane {
        String[] options = {"Delete", "Cancel"};
        JPanel panel;
        String item, Weight;
        public EditItemOptionPane(String item, String Weight) {
            this.item = item;
            this.Weight = Weight;
            // Create the labels
            JLabel label1 = new JLabel(item+":");
            JLabel label2 = new JLabel("Unit Weight: "+Weight );

            // Create a JPanel to hold the labels
            panel = new JPanel();
            panel.add(label1);
            panel.add(label2);

            // Show the option dialog with labels and buttons
        }
        public int run(){
            int result = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Edit Item: "+item,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Handle the selected option
            if (result >= 0 && result < options.length) {
                return result;
            }
            return 2;
        }
    }
}
