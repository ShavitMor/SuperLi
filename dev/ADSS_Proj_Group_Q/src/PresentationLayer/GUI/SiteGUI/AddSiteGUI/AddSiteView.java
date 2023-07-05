package PresentationLayer.GUI.SiteGUI.AddSiteGUI;

import BusinessLayer.SiteManager;
import PresentationLayer.GUI.SiteGUI.SiteManagerViewController;
import PresentationLayer.GUI.Utils.StringInputValidator;
import PresentationLayer.GUI.Utils.LabelAndTextboxJPane;
import PresentationLayer.GUI.Utils.VoidStringUpdater;
import ServiceLayer.SiteService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSiteView extends JFrame{

    LabelAndTextboxJPane namePane;
    LabelAndTextboxJPane addressPane;
    LabelAndTextboxJPane cNamePane;
    LabelAndTextboxJPane cPhonePane;
    LabelAndTextboxJPane xPane;
    LabelAndTextboxJPane yPane;
    JComboBox typeBox;

    JButton addSiteB;

    SiteModel model ;
    AddSiteController controller;
    SiteService ss = new SiteService(SiteManager.getInstance());

    public AddSiteView(SiteManagerViewController smvc) {
        setTitle("Add new Site:");
        setBounds(30,30,350,450);
        setLayout(new BorderLayout());

        // Create the title label and add it to the top panel
        JLabel titleLabel = new JLabel("Enter Info of New Site:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),Font.PLAIN,18));
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Create the center panel with the form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7,1));

        //LabelTextPanes
        namePane = new LabelAndTextboxJPane("Name:");
        addressPane = new LabelAndTextboxJPane("Address:");
        cNamePane = new LabelAndTextboxJPane("Contact Name:");
        cPhonePane = new LabelAndTextboxJPane("ContactPhone:");
        //Pane for x y values:
        JPanel xyPane = new JPanel(null);
        xyPane.setBounds(LabelAndTextboxJPane.defaultPanePos);
        JLabel locLabel = new JLabel("Location:");
        locLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xyPane.add(locLabel);
        locLabel.setBounds(-15,5,100,30);
        JPanel xyInput = new JPanel(new GridLayout(1,2));
        xPane = new LabelAndTextboxJPane("X:");
        xPane.label.setBounds(10,0,40,30);
        xPane.inputField.setBounds(25,0,38,30);
        xPane.inputField.setEditable(true);
        yPane = new LabelAndTextboxJPane("Y:");
        yPane.label.setBounds(10,0,40,30);
        yPane.inputField.setBounds(25,0,38,30);
        yPane.inputField.setEditable(true);
        xyInput.add(xPane);
        xyInput.add(yPane);
        xyInput.setBounds(150,0,120,30);
        xyPane.add(xyInput);

        //Pane for Site Type
        JPanel siteTypePane = new JPanel(null);
        JLabel siteTypeLabel = new JLabel("Site Type:");
        typeBox = new JComboBox<>(SiteModel.types);
        siteTypePane.add(siteTypeLabel);
        siteTypeLabel.setBounds(5,0,100,30);
        siteTypePane.add(typeBox);
        typeBox.setBounds(130,0, 150,30);

        //Pane for button
        JPanel addSiteButtonPane = new JPanel(null);
        addSiteB = new JButton("+");
        addSiteButtonPane.add(addSiteB);
        addSiteB.setBounds(100,0,105,40);


        //Add all comps to Frame
        centerPanel.add(namePane);
        centerPanel.add(addressPane);
        centerPanel.add(cNamePane);
        centerPanel.add(cPhonePane);
        centerPanel.add(xyPane);
        centerPanel.add(siteTypePane);
        centerPanel.add(addSiteButtonPane);

        add(centerPanel);

        //setControllerAndModel
        model = new SiteModel();
        controller = new AddSiteController(this,model,smvc);

        //Set action listeners
        addDocumentListenersToTextBoxes(namePane,((x)->{return controller.isValidName(x);}),((x)-> {model.updateName(x);}));
        addDocumentListenersToTextBoxes(addressPane,((x)->{return true;}),((x)-> {model.updateAddress(x);}));
        addDocumentListenersToTextBoxes(cNamePane,((x)->{return true;}),((x)-> {model.updateContactName(x);}));
        addDocumentListenersToTextBoxes(cPhonePane,((x)->{return controller.isValidPhone(x);}),((x)-> {model.updateContactPhone(x);}));
        addDocumentListenersToTextBoxes(xPane,((x)->{return x.matches("\\d+");}),((x)-> {model.updateX(x);}));
        addDocumentListenersToTextBoxes(yPane,((x)->{return x.matches("\\d+");}),((x)-> {model.updateY(x);}));
        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateType(typeBox.getSelectedItem().toString());
            }
        });
        addSiteB.addActionListener(controller);
    }


    public void addDocumentListenersToTextBoxes(LabelAndTextboxJPane lTP, StringInputValidator ivi, VoidStringUpdater vsu){
        lTP.setTextBoxDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }
        });
    }


}
