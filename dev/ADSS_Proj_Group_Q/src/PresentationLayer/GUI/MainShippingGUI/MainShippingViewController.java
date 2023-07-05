package PresentationLayer.GUI.MainShippingGUI;

import PresentationLayer.GUI.ShipmentsGUI.ShipmentManagerView;
import PresentationLayer.GUI.SiteGUI.SiteManagerView;
import PresentationLayer.GUI.TruckGUI.TruckManagerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainShippingViewController implements ActionListener {
    JButton siteB;
    JButton truckB;
    JButton shipB;
    MainShippingView view;

    public MainShippingViewController(JButton siteB, JButton truckB, JButton shipB, MainShippingView shippingView) {
        this.siteB = siteB;
        this.truckB = truckB;
        this.shipB = shipB;
        this.view = shippingView;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object pressed = e.getSource();
        if (pressed == siteB){
            runSiteGui();
        }
        if(pressed == truckB){
            runTruckGui();
        }
        if(pressed == shipB){
            runShippingGui();
        }
    }

    private void runShippingGui() {

        System.out.println("shipGUI");
        ShipmentManagerView.run(view);
        view.dispose();
    }

    private void runTruckGui() {
        System.out.println("truckGUI");
        TruckManagerView.run(view);
        view.dispose();
    }

    private void runSiteGui() {

        System.out.println("siteGUI");
        SiteManagerView.run(view);
        view.dispose();
    }
}
