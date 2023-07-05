package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory;

import BusinessLayer.ShippingManager;
import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.EditShipmentView;
import PresentationLayer.GUI.Utils.ChooseFromComboPane;
import ServiceLayer.Response;
import ServiceLayer.ShippingService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DestinationController implements ActionListener {

    String sId,order;
    DestinationModel model;
    DestinationView view;
    ShippingService ss = new ShippingService(ShippingManager.getInstance());
    ShipmentDestInventoryView toUpdate;

    public DestinationController(ShipmentDestInventoryView tp,DestinationView v, String sId,String order) {
        this.order =order;
        toUpdate =tp;
        this.view = v;
        this.sId =sId;
        String[][] items = getSiteItemsInfo();
        String [] siteInfo = getSiteInfo();
        model = new DestinationModel(sId,order,items,siteInfo);
    }

    private String[] getSiteInfo() {
        Response res = ss.getDestInfo(sId,order);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);

        }
        return res.isErrorResponse() ? new String[4] : ((String[])res.ReturnValue);
    }

    public String[][] getSiteItemsInfo() {
        Response res = ss.getSiteItemInfo(sId,order);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);

        }
        return res.isErrorResponse() ? new String[0][4] : ((String[][])res.ReturnValue);
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
                updateAll();

                break;
            case "removeB":
                removeDest();

                break;

        }
    }


    private void addNewItem() {
        Response res = ss.getItemArr(sId,order);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] data = (String[])res.ReturnValue;
        if(data.length == 0){
            JOptionPane.showMessageDialog(null, "No items to add!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data,data, view);

        choose.run((x)->addItemToShipment(x),"Select item");
    }

    private void addItemToShipment(String x) {
        String amount = JOptionPane.showInputDialog(view,"Enter Requested amount: (Negative for shipping, Positive for picking up","Set Item Amount",JOptionPane.WARNING_MESSAGE);
        if(amount == null || amount == "0"){
            return;
        }
        Response res = ss.addItemPickOrDrop(sId,order,x,amount);
        if(res.isErrorResponse() ){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{;
            updateAll();
            JOptionPane.showMessageDialog(null, "Item "+x+" added!", "Added item to shipment!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setOrder() {
        Response res = ss.getShipmentSizeArr(sId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] data = (String[])res.ReturnValue;
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data,data, view);
        choose.run((x)->flipSiteOrder(x), "select new order");
    }

    private void flipSiteOrder(String x) {
        Response res = x== null ?  null : ss.replaceSitesOrder(sId,order, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            model.updateOrder(x);
            updateAll();
            JOptionPane.showMessageDialog(null, "Order changed to "+x+"!", "Site order changed!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeDest() {
        int op = JOptionPane.showConfirmDialog(view,"Remove this site from Shipment?","Removing Destination",JOptionPane.YES_NO_OPTION);
        if(Integer.valueOf(order)==0){
            JOptionPane.showMessageDialog(null, "Error: cannot remove source!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (op == JOptionPane.YES_OPTION ){
            Response res = ss.removeDestination(sId,order);
            if(order =="0"){
                JOptionPane.showMessageDialog(null, "Error: cannot remove source!", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Destination removed!" , "Shipment destination removed!", JOptionPane.INFORMATION_MESSAGE);
                toUpdate.updateTableInfo();
                view.dispose();
            }
        }
    }

    public DestinationModel getModel(){
        return model;
    }


    private void updateAll(){
        updateInfo();
    }

    public void updateInfo() {
        order = model.order;
        setErrorLabel(view.errorLabel);
        model.setDataVector(getSiteItemsInfo(),model.headers);
        model.fireTableDataChanged();
        view.updateTableInfo();
        toUpdate.updateTableInfo();
    }

    public void setErrorLabel(JLabel errorLabel) {
        //TODO
    }

    public void renEditItem(Object item,Object amount) {
        int chosen = new EditItemOptionPane((String)item,(String)amount).run();
        switch (chosen) {
            case 0:
                deleteItem((String) item);
                break;
        }
    }


    private void deleteItem(String item) {
        int delete = JOptionPane.showConfirmDialog(view,"remove "+item+" from this site?","Removing item",JOptionPane.YES_NO_OPTION);
        if(delete != JOptionPane.YES_OPTION){
            return;
        }
        Response res = ss.removeItemFromDest(sId,order,item);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Item " +item +" removed!" , "Shipment item removed!", JOptionPane.INFORMATION_MESSAGE);
            updateAll();
        }
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
            JLabel label2 = new JLabel("Amount:" );

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
