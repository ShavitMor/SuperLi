package PresentationLayer.GUI.TruckGUI.AddEditTruckGUI;

import BusinessLayer.TruckManager;
import PresentationLayer.GUI.TruckGUI.TruckManagerController;
import ServiceLayer.Response;

import ServiceLayer.TruckService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTruckController implements ActionListener {
    AddTruckView view;
    TruckModel model;
    TruckService ss = new TruckService(TruckManager.getInstance());
    TruckManagerController smvc;

    public AddTruckController(AddTruckView view, TruckModel model, TruckManagerController smvc){
        this.view = view;
        this.model = model;
        this.smvc = smvc;
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            addNewTruck();
        }
    }

    private void addNewTruck() {
        Response res = ss.addNewTruck(model.licenceNumber, model.model, model.fWeight, model.fMaxWeight, model.TempControlled);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "New Truck added successfully!", "Truck Added!", JOptionPane.INFORMATION_MESSAGE);
            smvc.updateView();
            view.dispose();
        }
    }


    public boolean isValidLicenceNumber(String x) {
        Response res = ss.isTruckNumberGood(x);
        return res.isErrorResponse() ? false : (boolean) (res.ReturnValue) ? true : false;
    }

    public boolean isValidWeight(String x) {
        Response res = ss.isTruckWeightValid(x);
        return !res.isErrorResponse();
    }

    public boolean isValidMaxWieght(String x) {
        Response res = ss.isTruckCarryWeightValid(x);
        return !res.isErrorResponse();
    }
}
