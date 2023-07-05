package DataLayer.DTOs;

import DataLayer.DataMapper;

public class ShipmentDestAndAmountsDTO extends DTO{
    public String shipmentId;
    public String siteId;
    public String itemId;
    public int itemAmount;
    public ShipmentDestAndAmountsDTO(DataMapper controller,String shId,String sId,String iId,int amount) {
        super(controller);
        shipmentId=shId;
        siteId=sId;
        itemId=iId;
        itemAmount=amount;
    }

}
