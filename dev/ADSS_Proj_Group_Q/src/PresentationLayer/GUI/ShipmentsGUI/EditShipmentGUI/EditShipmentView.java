package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI;

import BusinessLayer.Truck;
import PresentationLayer.GUI.ShipmentsGUI.ShipmentsViewController;
import PresentationLayer.GUI.Utils.DatePicker;
import PresentationLayer.GUI.Utils.DoubleLabelAndButtonPane;
import PresentationLayer.GUI.Utils.LabelAndTextboxJPane;
import PresentationLayer.GUI.Utils.TimePicker;

import javax.swing.*;
import java.awt.*;

public class EditShipmentView extends JFrame {

    JLabel titleLabel;
    JButton setDateB;
    JButton setTimeB;
    JButton setTruckB;
    JButton setDriverB;
    JComboBox zoneBox;
    JButton editDestB;
    JButton advanceB;
    JTextArea goodsSum;
    JButton deleteShipB;

    private static final String defaultBText = "Set";

    JPanel dateTimePanel;
    LabelAndTextboxJPane datePane;
    LabelAndTextboxJPane timePane;
    DoubleLabelAndButtonPane truckPane;
    DoubleLabelAndButtonPane driverPane;
    JPanel zonePane;
    JPanel itemsDeliveredPane;
    DoubleLabelAndButtonPane statusPane;
    JPanel buttonsPane;
    JLabel statusNoteL;



    ShipmentModel model;
    EditShipmentController con;
    ShipmentsViewController toUpdate;

    static Font font;


    public EditShipmentView(String shipId, ShipmentsViewController toUpdate){
        this.toUpdate = toUpdate;
        con = new EditShipmentController(shipId,this);
        model = con.getModel();
        setTitle("Editing Truck "+ shipId +":");
        setBounds(30,30,500,600);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Editing Shipment "+shipId +": ");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),Font.BOLD,18));
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        font = new Font(titleLabel.getFont().getFontName(),Font.PLAIN,14);

        // Create the center panel with the form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7,1));
        truckPane = new DoubleLabelAndButtonPane("Truck:",model.truck,"Set Truck");
        setTruckB = truckPane.getButton();
        truckPane.secondLabel.setBounds(110,10,200,30);
        setTruckB.setBounds(350,10,130,30);
        driverPane = new DoubleLabelAndButtonPane("Driver:",model.driver,"Set Driver");
        setDriverB = driverPane.getButton();
        setDriverB.setBounds(350,10,130,30);
        driverPane.secondLabel.setBounds(110,10,200,30);
        statusPane = new DoubleLabelAndButtonPane("Status:", model.status,"");
        statusPane.getButton().setVisible(false);
        statusPane.setBounds(30,30,400,80);
        statusNoteL = new JLabel();
        con.setStatusLabel(statusNoteL);
        statusPane.add(statusNoteL);
        statusNoteL.setBounds(20,20,395,40);
        centerPanel.add(setDateTimePanel());
        centerPanel.add(truckPane);
        centerPanel.add(driverPane);
        centerPanel.add(setZonePanel());
        centerPanel.add(setShippedGoodsPane());
        centerPanel.add(statusPane);
        centerPanel.add(setButtonsPanel());
        add(centerPanel);
        setButtonsNames();
        setTextSizeAndPos();
        new DatePicker().connectButtonAndTextBox(setDateB, datePane.inputField,this,(x) -> con.setShipmentDate(x));
        new TimePicker().connectButtonAndTextBox(setTimeB,timePane.inputField,this,((x) ->{ con.setShipmentTime(x);
            updateData();}));
        setTruckB.addActionListener(con);
        setDriverB.addActionListener(con);
        zoneBox.addActionListener(con);
        editDestB.addActionListener(con);
        advanceB.addActionListener(con);
        deleteShipB.addActionListener(con);

    }

    private JPanel setDateTimePanel(){
        datePane = new LabelAndTextboxJPane("Date:");
        datePane.inputField.setEditable(false);
        datePane.inputField.setVisible(true);
        datePane.inputField.setText(model.shippingDate);
        setDateB = new JButton(defaultBText);
        timePane = new LabelAndTextboxJPane("Time:");
        timePane.inputField.setEditable(false);
        timePane.inputField.setVisible(true);
        timePane.inputField.setText(model.shippingStartTime);
        setTimeB = new JButton(defaultBText);
        dateTimePanel = new JPanel(null);
        dateTimePanel.setBounds(30,30,400,30);
        JLabel dateTimeL = new JLabel("Date and Time:");
        dateTimeL.setFont(font);
        dateTimeL.setHorizontalAlignment(SwingConstants.CENTER);
        dateTimePanel.add(dateTimeL);
        dateTimePanel.add(datePane);
        dateTimePanel.add(setDateB);
        dateTimePanel.add(timePane);
        dateTimePanel.add(setTimeB);
        dateTimeL.setBounds(10,10,140,30);
        datePane.setBounds(130,-5,150,60);
        datePane.label.setBounds(10,10,60,40);
        datePane.inputField.setBounds(50,20,90,30);
        setDateB.setBounds(275,15,30,30);
        int offset =175;
        timePane.setBounds(130+offset,-5,150,60);
        timePane.label.setBounds(9,10,70,40);
        timePane.inputField.setBounds(50,20,90,30);
        setTimeB.setBounds(275+offset,15,30,30);

        return dateTimePanel;
    }

    private void setTextSizeAndPos(){
        datePane.label.setFont(font);
        timePane.label.setFont(font);

    }

    private JPanel setButtonsPanel(){
        buttonsPane = new JPanel(null);
        buttonsPane.setBounds(LabelAndTextboxJPane.defaultPanePos);
        deleteShipB = new JButton("Delete Shipment");
        advanceB = new JButton("Advance Shipment");
        editDestB  = new JButton("Add Items and Destinations");
        buttonsPane.add(deleteShipB);
        buttonsPane.add(advanceB);
        buttonsPane.add(editDestB);
        deleteShipB.setBounds(5,10,130,40);
        advanceB.setBounds(137,10,140,40);
        editDestB.setBounds(280,10,202,40);
        return buttonsPane;
    }

    private JPanel setZonePanel(){
        zonePane = new JPanel(null);
        JLabel zoneL = new JLabel("Zone:");
        zoneBox = new JComboBox(ShipmentModel.zones);
        zonePane.add(zoneL);
        zonePane.add(zoneBox);
        zoneL.setBounds(10,10,140,30);;
        zoneL.setFont(font);
        zoneL.setHorizontalAlignment(SwingConstants.CENTER);
        zoneBox.setBounds(140,10,200,30);
        return zonePane;
    }

    private void setButtonsNames(){
        setDateB.setName("dateB");
        setTimeB.setName("timeB");;
        setTruckB.setName("truckB");;
        setDriverB.setName("driverB");;
        zoneBox.setName("zoneBox");
        editDestB.setName("destB");;
        advanceB.setName("advanceB");;
        deleteShipB.setName("deleteB");
    }

    private JPanel setShippedGoodsPane(){
        itemsDeliveredPane = new JPanel(null);
        itemsDeliveredPane.setBounds(30,30,160,200);
        JLabel goodsL = new JLabel("Summary:");
        String sum = model.totalDeliveredGoods;
        if(sum.isBlank()){
            sum = "***ERROR-No Deliveries in this Shipment!*** ";
        }
        goodsSum = new JTextArea(sum);
        if(model.totalDeliveredGoods.isBlank()){
            goodsSum.setForeground(Color.RED);
        }
        goodsSum.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(goodsSum);

        itemsDeliveredPane.add(goodsL);
        itemsDeliveredPane.add(scrollPane);
        goodsL.setBounds(10,10,140,30);
        goodsL.setFont(font);
        goodsL.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setBounds(130,10, 300,250);
        return itemsDeliveredPane;
    }
    public void updateData() {
        //TODO
        truckPane.secondLabel.setText(model.truck);
        driverPane.secondLabel.setText(model.driver);
        String sum = model.totalDeliveredGoods;
        if(sum.isBlank()){
            sum = "***ERROR-No Deliveries in this Shipment!*** ";
        }
        goodsSum .setText(sum);
        if(model.totalDeliveredGoods.isBlank()){
            goodsSum.setForeground(Color.RED);
        }
        statusPane.secondLabel.setText(model.status);
        con.setStatusLabel(statusNoteL);
        toUpdate.updateView();
    }
}
