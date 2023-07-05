package PresentationLayer.GUI.ShipmentsGUI;

import BusinessLayer.ShippingManager;
import PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI.EditShipmentView;
import PresentationLayer.GUI.Utils.DatePicker;
import PresentationLayer.GUI.Utils.TimePicker;
import ServiceLayer.Response;
import ServiceLayer.ShippingService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipmentsViewController implements ActionListener {
    JButton addNewB;
    JButton returnB;
    JButton nameSearchB;
    JButton idSearchB;
    ShipmentManagerView view;
    ShippingService ss = new ShippingService(ShippingManager.getInstance());
    JFrame toReturn;

    public ShipmentsViewController(JButton addNewB, JButton returnB, JButton nsb, JButton isb ,ShipmentManagerView v,JFrame toReturn) {
        this.addNewB = addNewB;
        this.returnB = returnB;
        this.nameSearchB = nsb;
        this.idSearchB = isb;
        this.view = v;
        this.toReturn = toReturn;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JComboBox){
            view.updateTableInfo();
        }
        if(o instanceof JCheckBox){
            view.viewInactive = !view.viewInactive;
            view.updateTableInfo();
        }
        if(o == addNewB){
            addNewShipment();
        }
        else if(o == returnB){
            view.dispose();
            toReturn.setVisible(true);
        }
        if(o == idSearchB){
            String shipmentId = JOptionPane.showInputDialog("Enter shipment ID to search for:", "");
            if(shipmentId == null){
                return;
            }
            Response res = ss.getShipmentById(shipmentId);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                EditShipmentView shipView = new EditShipmentView(shipmentId,this);
                shipView.setVisible(true);
            }
        }

    }

    public void runEditShipment(Object value) {
        EditShipmentView shipView = new EditShipmentView(value.toString(),this);
        shipView.setVisible(true);
    }

    public void addNewShipment() {
        Response res = ss.addCleanShipment();
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            EditShipmentView shipView = new EditShipmentView(((Integer)res.ReturnValue).toString(),this);
            shipView.setVisible(true);
            view.updateTableInfo();
        }

    }

    public void updateView() {
        view.updateTableInfoAndComboBox();
    }
}
