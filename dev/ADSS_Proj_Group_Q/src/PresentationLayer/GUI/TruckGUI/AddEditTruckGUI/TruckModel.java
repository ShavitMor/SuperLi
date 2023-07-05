package PresentationLayer.GUI.TruckGUI.AddEditTruckGUI;

public class TruckModel {
    String Id;
    String licenceNumber;
    String model;
    String fWeight;
    String fMaxWeight;
    String TempControlled = "true";
    public static String[] tempContOptions = {"Yes","No"};

    public TruckModel(){}
    public TruckModel(String[] truckInfo){
        Id  = truckInfo[0];
        licenceNumber = truckInfo[1];
        model = truckInfo[2];
        fWeight= truckInfo[3];
        fMaxWeight = truckInfo[4];
        TempControlled = truckInfo[5];
    }

    public void updateLicenceNumber(String text){
        licenceNumber = text;
    }
    public void updateModel(String text){
        model = text;
    }
    public void updateWeight(String text){
        fWeight = text;
    }
    public void updateMaxWeight(String text){
        fMaxWeight = text;
    }

    public void updateType(String toString) {
        TempControlled = toString.equals("Yes") ? "true" : "false";
    }

    public void switchTempControl(){
       TempControlled =  TempControlled.equals("true") ? "false" : "true";
    }
}
