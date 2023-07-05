package PresentationLayer.GUI.SiteGUI.EditSiteGUI;

import BusinessLayer.SiteManager;
import ServiceLayer.Response;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditSiteController implements ActionListener {

    EditSiteView view;
    EditSiteModel model;
    SiteService ss = new SiteService(SiteManager.getInstance());
    public EditSiteController(String siteId, EditSiteView editSiteView) {
        model = new EditSiteModel(getSiteModelData(siteId));
        view = editSiteView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JButton){
            JButton b = (JButton)o;
            switch (b.getName()){
                case "sAB":
                    setAddress();
                    break;
                case "sCNB":
                    setContactName();
                    break;
                case "sCPB":
                    setContactPhone();
                    break;
                case "sLB":
                    setLocation();
                    break;
                case "dSB":
                    deactivateSite();
                    break;
            }
        }
    }

    private void setAddress() {
        String newAddress = JOptionPane.showInputDialog("Enter new Address", model.address);
        if(newAddress == null){
            return;
        }
        Response res = ss.setSiteAddress(model.id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Site " +model.id +" address changed!" , "Site changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateAddress(newAddress);
            view.updateData();

        }
    }

    private void setContactName() {
        String newAddress = JOptionPane.showInputDialog("Enter Contact name:", model.contactName);
        if(newAddress == null){
            return;
        }
        Response res = ss.setSiteContactName(model.id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Site " +model.id +" contact changed!" , "Site changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateContactName(newAddress);
            view.updateData();
        }
    }

    private void setContactPhone() {
        String newAddress = JOptionPane.showInputDialog("Enter Contact phone:", model.contactPhone);
        if(newAddress == null){
            return;
        }
        Response res = ss.setSiteContactPhone(model.id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Site " +model.id +" contact changed!" , "Site changed!", JOptionPane.INFORMATION_MESSAGE);
            model.updateContactPhone(newAddress);
            view.updateData();
        }
    }

    private void setLocation() {
        setLocationOptionPane op = new setLocationOptionPane();
        String newAddress = op.run();
        if(newAddress == null){
            return;
        }
        Response res = ss.setSiteCoordinate(model.id, newAddress);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Site " +model.id +" Loacation changed!" , "Site changed!", JOptionPane.INFORMATION_MESSAGE);
            String[] cords = newAddress.split(",");
            model.updateX(cords[0]);
            model.updateY(cords[1]);
            view.updateData();
        }
        
    }

    private void deactivateSite() {
        int x = JOptionPane.showConfirmDialog(null, "Deactivated site cannot be modified and cannot be used for deliveries!\nDeactivate this site?",
                "Deactivate Site", JOptionPane.YES_NO_OPTION);
        if(x == JOptionPane.YES_OPTION){
            Response res = ss.deactivateSite(model.id);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Getting Site", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Site " +model.id +" deactivated!" , "Site Deactivated!", JOptionPane.INFORMATION_MESSAGE);
                model.updateActive();
                view.updateData();
                view.dispose();
            }
        }
    }

    private String[] getSiteModelData (String siteId){
        Response res = ss.getSiteModelData(siteId);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Getting Site", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return null;
        }
        return (String[])res.ReturnValue;
    }

    public EditSiteModel getModel (){
        return model;
    }


    class setLocationOptionPane{
        JTextField textField1;
        JTextField textField2;
        JPanel panel;
        public setLocationOptionPane(){
            textField1 = new JTextField(10);
            textField2 = new JTextField(10);

            panel = new JPanel();
            panel.add(new JLabel("X:"));
            panel.add(textField1);
            panel.add(new JLabel("Y:"));
            panel.add(textField2);
        }

        public String run(){
            String res = null;
            int option = JOptionPane.showOptionDialog(null, panel, "Enter Site's new Coordinate:",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                String text1 = textField1.getText();
                String text2 = textField2.getText();
                res = text1+","+text2;
            }
            return res;
        }


    }



}
