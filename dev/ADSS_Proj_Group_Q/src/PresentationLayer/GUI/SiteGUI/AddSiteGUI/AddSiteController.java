package PresentationLayer.GUI.SiteGUI.AddSiteGUI;

import BusinessLayer.SiteManager;
import PresentationLayer.GUI.SiteGUI.SiteManagerViewController;
import ServiceLayer.Response;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSiteController implements ActionListener {
    AddSiteView view;
    SiteModel model;
    SiteService ss = new SiteService(SiteManager.getInstance());
    SiteManagerViewController smvc;

    public AddSiteController(AddSiteView view, SiteModel model, SiteManagerViewController smvc){
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
            addNewSite();
        }
    }

    private void addNewSite() {
        Response res = ss.addNewSite(model.name, model.address, model.contactName, model.contactPhone,
                ""+model.xLocation+","+model.yLocation, model.type);
        if(res.isErrorResponse()){
            JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "New Site added successfully!", "Site Added!", JOptionPane.INFORMATION_MESSAGE);
            smvc.updateView();
            view.dispose();
        }
    }

    public boolean isValidName(String x) {
        Response res = ss.isValidNameAndNotTaken(x);
        return res.isErrorResponse() ? false : (boolean) (res.ReturnValue) ? true : false;
    }

    public boolean isValidPhone(String x) {
        Response res = ss.isValidPhone(x);
        return res.isErrorResponse() ? false : (boolean) (res.ReturnValue) ? true : false;
    }

}
