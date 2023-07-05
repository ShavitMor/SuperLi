package PresentationLayer.GUI.SiteGUI.EditSiteGUI;

import PresentationLayer.GUI.SiteGUI.SiteManagerView;
import PresentationLayer.GUI.SiteGUI.SiteManagerViewController;
import PresentationLayer.GUI.Utils.DoubleLabelAndButtonPane;

import javax.swing.*;
import java.awt.*;

public class EditSiteView extends JFrame {

    JButton setAddressB;
    JButton setContactNameB;
    JButton setContactPhoneB;
    JButton setLocationB;
    JButton deleteSite;


    DoubleLabelAndButtonPane addressPane ;
    DoubleLabelAndButtonPane cNamePane;
    DoubleLabelAndButtonPane cPhonePane ;
    DoubleLabelAndButtonPane locationPane ;
    DoubleLabelAndButtonPane activePane;
    EditSiteModel model;
    EditSiteController con;

    SiteManagerViewController toUpdate;


    public EditSiteView(String siteId, SiteManagerViewController toUpdate){
        this.toUpdate = toUpdate;
        con = new EditSiteController(siteId,this);
        model = con.getModel();
        setTitle("Editing site "+ siteId +":");
        setBounds(30,30,400,450);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Editing Site "+siteId +": "+model.name);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),Font.BOLD,18));
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Create the center panel with the form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6,1));
        DoubleLabelAndButtonPane typePane = new DoubleLabelAndButtonPane("Type:",model.type,"");
        typePane.button.setVisible(false);
        addressPane = new DoubleLabelAndButtonPane("Address:",model.address,"set address");
        cNamePane = new DoubleLabelAndButtonPane("Contact Name:",model.contactName,"set contact name");
        cPhonePane = new DoubleLabelAndButtonPane("Contact Phone:",model.contactPhone,"set contact phone");
        locationPane = new DoubleLabelAndButtonPane("Location:",model.xLocation + ","+model.yLocation,"set Location");
        activePane = new DoubleLabelAndButtonPane("Active:",model.isActive,"deactivate site");

        setAddressB = addressPane.getButton();
        setAddressB.setName("sAB");
        setContactNameB = cNamePane.getButton();
        setContactNameB.setName("sCNB");
        setContactPhoneB = cPhonePane.getButton();
        setContactPhoneB.setName("sCPB");
        setLocationB = locationPane.getButton();
        setLocationB.setName("sLB");
        deleteSite = activePane.getButton();
        deleteSite.setName("dSB");

        setAddressB.addActionListener(con);
        setContactNameB.addActionListener(con);
        setContactPhoneB.addActionListener(con);
        setLocationB.addActionListener(con);
        deleteSite.addActionListener(con);
        centerPanel.add(typePane);
        centerPanel.add(addressPane);
        centerPanel.add(cNamePane);
        centerPanel.add(cPhonePane);
        centerPanel.add(locationPane);
        centerPanel.add(activePane);
        add(centerPanel);
    }

    public void updateData() {
        addressPane.secondLabel.setText(model.address);
        cNamePane.secondLabel.setText(model.contactName);
        cPhonePane.secondLabel.setText(model.contactPhone);
        locationPane.secondLabel.setText(model.xLocation + ","+model.yLocation);
        toUpdate.updateView();
    }
}
