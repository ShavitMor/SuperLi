package PresentationLayer.GUI.TruckGUI;


import BusinessLayer.TruckManager;
import ServiceLayer.Response;
import ServiceLayer.TruckService;
import PresentationLayer.GUI.TruckGUI.AddEditTruckGUI.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TruckManagerController implements ActionListener {
    JButton addNewB;
    JButton returnB;
    JButton licenceSearchB;
    JButton idSearchB;
    TruckManagerView view;
    TruckService ss = new TruckService(TruckManager.getInstance());
    JFrame toReturn;

    public TruckManagerController(JButton addNewB, JButton returnB, JButton nsb, JButton isb ,TruckManagerView v,JFrame toReturn) {
        this.addNewB = addNewB;
        this.returnB = returnB;
        this.licenceSearchB = nsb;
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
            addNewTruck();
        }
        else if(o == returnB){
            view.dispose();
            toReturn.setVisible(true);
        }
        else if(o == licenceSearchB){
            String licenceNum = JOptionPane.showInputDialog("Enter Licence number to search for:", "");
            if(licenceNum == null){
                return;
            }
            Response res = ss.getTruckIdByLicence(licenceNum);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                EditTruckView truckView = new EditTruckView(res.ReturnValue.toString(),this);
                truckView.setVisible(true);
            }

        }
        if(o == idSearchB){
            String truckId = JOptionPane.showInputDialog("Enter Truck ID to search for:", "");
            if(truckId == null){
                return;
            }
            Response res = ss.getTruckbyId(truckId);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                EditTruckView truckView = new EditTruckView(truckId,this);
                truckView.setVisible(true);
            }
        }

    }

    public void runEditTruck(Object value) {
        EditTruckView truckView = new EditTruckView(value.toString(),this);
        truckView.setVisible(true);
    }

    public void addNewTruck() {
        AddTruckView truckView = new AddTruckView(this);
        truckView.setVisible(true);
    }

    public void updateView() {
        view.updateTableInfoAndComboBox();
    }
}
