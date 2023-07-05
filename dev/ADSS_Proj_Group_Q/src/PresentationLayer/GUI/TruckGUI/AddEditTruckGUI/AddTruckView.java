package PresentationLayer.GUI.TruckGUI.AddEditTruckGUI;

import PresentationLayer.GUI.TruckGUI.TruckManagerController;
import PresentationLayer.GUI.Utils.LabelAndTextboxJPane;
import PresentationLayer.GUI.Utils.StringInputValidator;
import PresentationLayer.GUI.Utils.VoidStringUpdater;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTruckView extends JFrame{

    LabelAndTextboxJPane licencePane;
    LabelAndTextboxJPane modelPane;
    LabelAndTextboxJPane weightPane;
    LabelAndTextboxJPane mWeightPane;
    JComboBox coolingBox;

    JButton addTruckB;

    TruckModel model ;
    AddTruckController controller;

    public AddTruckView(TruckManagerController smvc) {
        setTitle("Add new Truck:");
        setBounds(30,30,350,450);
        setLayout(new BorderLayout());

        // Create the title label and add it to the top panel
        JLabel titleLabel = new JLabel("Enter Info of New Truck:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),Font.PLAIN,18));
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Create the center panel with the form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6,1));

        //LabelTextPanes
        licencePane = new LabelAndTextboxJPane("Licence Number:");
        modelPane = new LabelAndTextboxJPane("Model:");
        weightPane = new LabelAndTextboxJPane("Truck Weight:");
        mWeightPane = new LabelAndTextboxJPane("Max Carry Weight:");

        //Pane for Cooling Capacity Type
        JPanel coolingPanel = new JPanel(null);
        JLabel coolingLabel = new JLabel("Temp. Control:");
        coolingBox = new JComboBox<>(TruckModel.tempContOptions);
        coolingPanel.add(coolingLabel);
        coolingLabel.setBounds(5,0,100,30);
        coolingPanel.add(coolingBox);
        coolingBox.setBounds(130,0, 150,30);

        //Pane for button
        JPanel addTruckPane = new JPanel(null);
        addTruckB = new JButton("+");
        addTruckPane.add(addTruckB);
        addTruckB.setBounds(100,0,105,40);


        //Add all comps to Frame
        centerPanel.add(licencePane);
        centerPanel.add(modelPane);
        centerPanel.add(weightPane);
        centerPanel.add(mWeightPane);
        centerPanel.add(coolingPanel);
        centerPanel.add(addTruckPane);

        add(centerPanel);

        //setControllerAndModel
        model = new TruckModel();
        controller = new AddTruckController(this,model,smvc);

        //Set action listeners
        addDocumentListenersToTextBoxes(licencePane,((x)->{return controller.isValidLicenceNumber(x);}),((x)-> {model.updateLicenceNumber(x);}));
        addDocumentListenersToTextBoxes(modelPane,((x)->{return true;}),((x)-> {model.updateModel(x);}));
        addDocumentListenersToTextBoxes(weightPane,((x)->{return controller.isValidWeight(x);}),((x)-> {model.updateWeight(x);}));
        addDocumentListenersToTextBoxes(mWeightPane,((x)->{return controller.isValidMaxWieght(x);}),((x)-> {model.updateMaxWeight(x);}));
        coolingBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateType(coolingBox.getSelectedItem().toString());
            }
        });
        addTruckB.addActionListener(controller);
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
