package PresentationLayer.GUI.ShipmentsGUI.EditShipmentGUI;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShipmentModel {

    String id;
    String shippingDate;
    String shippingStartTime;
    String truck;
    String driver;
    String zone;
    String status;
    String totalDeliveredGoods;

    final static String[] zones = {"Center","North","South","East","West"};

    public ShipmentModel(String[] shipInfo){
        id  = shipInfo[0];
        shippingDate = shipInfo[1];
        shippingStartTime = shipInfo[2];
        truck= shipInfo[3];
        driver = shipInfo[4];
        zone = shipInfo[5];
        status= shipInfo[6];
        totalDeliveredGoods = shipInfo[7];
    }
    public String getId(){
        return id;
    }

    public void updateDate(String text){
        shippingDate = text;
    }
    public void updateTime(String text){
        shippingStartTime = text;
    }
    public void updateTruck(String text){
        truck = text;
    }
    public void updateDriver(String text){
        driver = text;
    }

    public void updateZone(String text){
        zone = text;
    }
    public void updateStatus(String text){
        truck = text;
    }
    public void updateItemsSum(String text){
        totalDeliveredGoods = text;
    }
}
