package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI;

import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.ShipmentDestInventory.ShipmentDestInventoryView;
import PresentationLayer.GUI.Utils.ChooseFromComboPane;
import ServiceLayer.Response;
import ServiceLayer.ShippingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditShipmentController implements ActionListener {

    EditShipmentView view;
    ShipmentModel model;
    ShippingService ss = new ShippingService();
    public EditShipmentController(String sId, EditShipmentView editView) {
        model = new ShipmentModel(getShipmentModelData(sId));
        view = editView;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JButton){
            JButton b = (JButton)o;
            switch (b.getName()){
                case "truckB":
                    setShipmentTruck();
                    break;
                case "driverB":
                    setShipmentDriver();
                    break;
                case "destB":
                    EditDestinations();
                    break;
                case "advanceB":
                    advanceShipmentStatus();
                    break;
                case "deleteB":
                    deleteShipment();
                    break;
            }
        }
        else{
            setShipmentZone(((JComboBox)o).getSelectedItem().toString());
        }
    }

    private void setShipmentZone(String selectedItem) {
        Response res = ss.setShipmentZone(model.id, ""+selectedItem.toUpperCase().charAt(0));
        view.updateData();
    }

    private void deleteShipment() {
        int ans = JOptionPane.showOptionDialog(null,"Delete this Shipment?",
                "Deleting Shipment "+model.id+"...", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,null,null);
        if(ans==JOptionPane.YES_OPTION){
            Response res = ss.deleteShipment(model.id);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Shipment " +model.id +" deleted!" , "Shipment removed!", JOptionPane.INFORMATION_MESSAGE);
                view.updateData();
                view.dispose();
            }
        }
    }

    private void advanceShipmentStatus() {
        Response res = ss.advanceShipmentStatus(model.id);
        Response statusRes = ss.getShipmentStatus(model.id);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else if(statusRes.isErrorResponse()) {
            JOptionPane.showMessageDialog(null, "Error:" + statusRes.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Shipment " +model.id +" status changed!" , "Shipment changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateStatus((String)(statusRes.ReturnValue));
            view.updateData();
        }
    }

    private void EditDestinations() {
        ShipmentDestInventoryView view = new ShipmentDestInventoryView(this.view,model.id);
        view.setVisible(true);
    }

    private void setShipmentDriver() {
        Response res = ss.getAvailableDriversArrays(model.id);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[][] data = (String[][])res.ReturnValue;
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data[0],data[1], model.driver, view);
        choose.run((x)->updateDriverData(x), "Choose Driver:");
    }

    private void updateDriverData(String x) {
        Response res = x== null ? ss.removeDriver(model.id) : ss.setShipmentDriverById(model.id, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            model.updateDriver(x==null?  "Unassigned" : getDriverShortInfo(x));
            view.updateData();
        }
    }

    private String getDriverShortInfo(String x) {
        String info = "ID: "+x;
        Response res = ss.getDriverShortInfo(x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return info;
        }
        info = (String) res.ReturnValue;
        return info;
    }

    private void setShipmentTruck() {
        Response res = ss.getTrucksArrays();
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[][] data = (String[][])res.ReturnValue;
        ChooseFromComboPane choose = new ChooseFromComboPane();
        choose.setData(data[0],data[1], model.truck, view);
        choose.run((x)->updateTruckData(x),"Select Truck:");
    }

    private void updateTruckData(String x) {
        Response res = x== null ? ss.removeTruck(model.id) : ss.setShipmentTruck(model.id, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else{
            model.updateTruck(x==null?  "Unassigned" : getTruckShortInfo(x));
            view.updateData();
        }
    }
    private String getTruckInfo(String tId){
        String info = "";
        Response res = ss.getTruckInfo(tId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return info;
        }
        info = (String) res.ReturnValue;
        return info;
    }

    private String getTruckShortInfo(String tId){
        String info = "ID: "+tId;
        Response res = ss.getTruckShortInfo(tId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return info;
        }
        info = (String) res.ReturnValue;
        return info;
    }


    private String[] getShipmentModelData(String sId){
        Response res = ss.getShipmentModelData(sId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Getting Shipment", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return null;
        }
        return (String[])res.ReturnValue;
    }

    public ShipmentModel getModel (){
        return model;
    }


    public void setStatusLabel(JLabel statusMsg) {
        Response res = ss.getStatusMsg(model.id);
        if (res.isErrorResponse()){
            statusMsg.setText("");
            statusMsg.setVisible(false);
            return;
        }
        String out = (String)res.ReturnValue;
        statusMsg.setVisible(true);
        statusMsg.setText(out);
        statusMsg.setForeground(out.contains("*") ? Color.RED : model.status.equals("Done") ?  Color.GREEN : Color.GREEN );
        return;
    }

    public void setShipmentDate(String x) {
        Response res = ss.setShipmentDate(model.id, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Shipment " +model.id +" date changed!" , "Shipment changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateDate((x));
            view.updateData();
        }
    }
    public void setShipmentTime(String x) {
        Response res = ss.setShipmentTime(model.id, x);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Shipment " +model.id +" Time changed!" , "Shipment changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateTime((x));
            view.updateData();
        }
    }

}
