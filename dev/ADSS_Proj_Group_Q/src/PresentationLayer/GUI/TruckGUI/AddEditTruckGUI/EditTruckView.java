package PresentationLayer.GUI.TruckGUI.AddEditTruckGUI;

import PresentationLayer.GUI.TruckGUI.TruckManagerController;
import PresentationLayer.GUI.Utils.DoubleLabelAndButtonPane;

import javax.swing.*;
import java.awt.*;

public class EditTruckView extends JFrame {

    JButton setLicenceNumberB;
    JButton setModelB;
    JButton setWeightB;
    JButton setMaxWeightB;
    JButton setCoolingB;


    DoubleLabelAndButtonPane lNumberPane;
    DoubleLabelAndButtonPane modelPane;
    DoubleLabelAndButtonPane weightPane;
    DoubleLabelAndButtonPane maxWeightPane;
    DoubleLabelAndButtonPane coolingPane;
    TruckModel model;
    EditTruckController con;

    TruckManagerController toUpdate;


    public EditTruckView(String truckId, TruckManagerController toUpdate){
        this.toUpdate = toUpdate;
        con = new EditTruckController(truckId,this);
        model = con.getModel();
        setTitle("Editing Truck "+ truckId +":");
        setBounds(30,30,400,450);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Editing Truck "+truckId +": ");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),Font.BOLD,18));
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Create the center panel with the form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6,1));
        coolingPane = new DoubleLabelAndButtonPane("Temp. Control:",model.TempControlled,"set Temp.Control");
        lNumberPane = new DoubleLabelAndButtonPane("Licence Number:",model.licenceNumber,"set Licence");
        modelPane = new DoubleLabelAndButtonPane("Model:",model.model,"set model");
        weightPane = new DoubleLabelAndButtonPane("Weight (KG):",model.fWeight,"set Weight");
        maxWeightPane = new DoubleLabelAndButtonPane("Max Carry Weight:",model.fMaxWeight,"set Carry weight");

        setLicenceNumberB = lNumberPane.getButton();
        setLicenceNumberB.setName("licenceB");
        setModelB = modelPane.getButton();
        setModelB.setName("modelB");
        setWeightB = weightPane.getButton();
        setWeightB.setName("weightB");
        setMaxWeightB = maxWeightPane.getButton();
        setMaxWeightB.setName("mWeightB");
        setCoolingB = coolingPane.getButton();
        setCoolingB.setName("coolingB");

        setLicenceNumberB.addActionListener(con);
        setModelB.addActionListener(con);
        setWeightB.addActionListener(con);
        setMaxWeightB.addActionListener(con);
        setCoolingB.addActionListener(con);
        centerPanel.add(lNumberPane);
        centerPanel.add(modelPane);
        centerPanel.add(weightPane);
        centerPanel.add(maxWeightPane);
        centerPanel.add(coolingPane);
        add(centerPanel);
    }

    public void updateData() {
        lNumberPane.secondLabel.setText(model.licenceNumber);
        modelPane.secondLabel.setText(model.model);
        weightPane.secondLabel.setText(model.fWeight);
        maxWeightPane.secondLabel.setText(model.fMaxWeight);
        coolingPane.secondLabel.setText(model.TempControlled);
        toUpdate.updateView();
    }
}
