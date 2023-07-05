package PresentationLayer.GUI.TruckGUI.AddEditTruckGUI;

import ServiceLayer.Response;
import ServiceLayer.TruckService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTruckController implements ActionListener {

    EditTruckView view;
    TruckModel model;
    TruckService ss = new TruckService();
    public EditTruckController(String truckId, EditTruckView editView) {
        model = new TruckModel(getTruckModelData(truckId));
        view = editView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JButton){
            JButton b = (JButton)o;
            switch (b.getName()){
                case "licenceB":
                    setLicenceNumber();
                    break;
                case "modelB":
                    setModel();
                    break;
                case "weightB":
                    setWeight();
                    break;
                case "mWeightB":
                    setMaxWeight();
                    break;
                case "coolingB":
                    setCooling();
                    break;
            }
        }
    }

    private void setCooling() {
       Response res =  ss.switchTempControl(model.Id);
       if(res.isErrorResponse()){
           JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
       }
       else{
           JOptionPane.showMessageDialog(null, "Truck " +model.Id +" Temp. Control changed!" , "Truck changed!", JOptionPane.INFORMATION_MESSAGE);
           model.switchTempControl();
           view.updateData();
       }
    }

    private void setLicenceNumber() {
        String newLicence = JOptionPane.showInputDialog("Enter new Licence Number", model.licenceNumber);
        if(newLicence == null){
            return;
        }
        Response res = ss.editLicenceNumber(model.Id, newLicence);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Truck " +model.Id +" Licence Number changed!" , "Truck changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateLicenceNumber(newLicence);
            view.updateData();

        }
    }

    private void setModel() {
        String newAddress = JOptionPane.showInputDialog("Enter Model:", model.model);
        if(newAddress == null){
            return;
        }
        Response res = ss.editModel(model.Id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Truck " +model.Id +" Model changed!" , "Truck changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateModel(newAddress);
            view.updateData();
        }
    }

    private void setWeight() {
        String newAddress = JOptionPane.showInputDialog("Enter Weight(KG):", model.fWeight);
        if(newAddress == null){
            return;
        }
        Response res = ss.editTruckWeight(model.Id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Truck " +model.Id +" weight changed!" , "Truck changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateWeight(newAddress);
            view.updateData();
        }
    }

    private void setMaxWeight() {
        String newAddress = JOptionPane.showInputDialog("Enter Max Carry Weight(KG) name:", model.fMaxWeight);
        if(newAddress == null){
            return;
        }
        Response res = ss.editTruckCarryWeight(model.Id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Truck " +model.Id +" max carry weight changed!" , "Truck changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateModel(newAddress);
            view.updateData();
        }
    }




    private String[] getTruckModelData(String truckId){
        Response res = ss.getTruckModelData(truckId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Getting Truck", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return null;
        }
        return (String[])res.ReturnValue;
    }

    public TruckModel getModel (){
        return model;
    }


}
