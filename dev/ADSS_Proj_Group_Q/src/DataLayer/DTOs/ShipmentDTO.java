package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShipmentDTO extends DTO{
    public String id;
    public LocalDate shippingDate;
    public LocalTime shippingStartTime;
    public String truck;
    public String driver;
    public String zone;
    public String status;
    public double totalDeliveryWeight;
    public ShipmentDTO(DataMapper controller,String id,LocalDate lc,LocalTime lt,String tr,String dr,String mzone,String st) {
        super(controller);
        this.id=id;
        shippingDate=lc;
        shippingStartTime=lt;
        truck=tr;
        driver=dr;
        zone=mzone;
        status=st;
    }

}
