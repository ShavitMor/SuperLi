package PresentationLayer.GUI.SiteGUI;

import BusinessLayer.SiteManager;
import PresentationLayer.GUI.SiteGUI.AddSiteGUI.AddSiteView;
import PresentationLayer.GUI.SiteGUI.EditSiteGUI.EditSiteView;
import ServiceLayer.Response;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SiteManagerViewController implements ActionListener {
    JButton addNewB;
    JButton returnB;
    JButton nameSearchB;
    JButton idSearchB;
    SiteManagerView view;
    SiteService ss = new SiteService(SiteManager.getInstance());
    JFrame toReturn;

    public SiteManagerViewController(JButton addNewB, JButton returnB, JButton nsb, JButton isb ,SiteManagerView v,JFrame toReturn) {
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
            addNewSite();
        }
        else if(o == returnB){
            view.dispose();
            toReturn.setVisible(true);
        }
        else if(o == nameSearchB){
            String siteName = JOptionPane.showInputDialog("Enter site name to search for:", "");
            if(siteName == null){
                return;
            }
            Response res = ss.getSiteByName(siteName);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                EditSiteView siteView = new EditSiteView(res.ReturnValue.toString(),this);
                siteView.setVisible(true);
            }

        }
        if(o == idSearchB){
            String siteId = JOptionPane.showInputDialog("Enter site ID to search for:", "");
            if(siteId == null){
                return;
            }
            Response res = ss.getSiteByID(siteId);
            if(res.isErrorResponse()){
                JOptionPane.showMessageDialog(null, "Error:" + res.ErrorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            else {
                EditSiteView siteView = new EditSiteView(siteId,this);
                siteView.setVisible(true);
            }
        }

    }

    public void runEditSite(Object value) {
        EditSiteView siteView = new EditSiteView(value.toString(),this);
        siteView.setVisible(true);
    }

    public void addNewSite() {
        AddSiteView siteView = new AddSiteView(this);
        siteView.setVisible(true);
    }

    public void updateView() {
        view.updateTableInfoAndComboBox();
    }

}
