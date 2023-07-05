package PresentationLayer.GUI.SiteGUI.AddSiteGUI;

import PresentationLayer.GUI.Utils.LabelAndTextboxJPane;

public class SiteModel {
    public String name;
    public String address;
    public String contactName;
    public String contactPhone;
    public String xLocation;
    public String yLocation;
    public String type = types[0];
    public static String[] types = {"Logistic Center", "Branch", "Supplier", "Else"};

    public SiteModel(){}
    public SiteModel(String[] input){
        name = input[0];
        address = input[1];
        contactName = input[2];
        contactPhone = input[3];
        xLocation = input[4];
        yLocation = input[5];
        type = input[6];
    }

    public void updateName(String text) {
        this.name = text;
    }

    public void updateAddress(String text) {
        this.address = text;
    }
    public void updateContactName(String text) {
        this.contactName = text;
    }
    public void updateContactPhone(String text) {
        this.contactPhone = text;
    }

    public void updateX(String x) {
        this.xLocation = x;
    }

    public void updateY(String x) {
        this.yLocation = x;
    }

    public void updateType(String toString) {
        this.type = toString;
    }
}
