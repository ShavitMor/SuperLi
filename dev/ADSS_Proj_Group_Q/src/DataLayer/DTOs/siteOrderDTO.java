package DataLayer.DTOs;

import DataLayer.DataMapper;

public class siteOrderDTO extends DTO {
    public String shipmentId;
    public String siteId;
    public int order;
    public siteOrderDTO(DataMapper controller,String shipmentId,String siteId,int place) {
        super(controller);
        this.shipmentId=shipmentId;
        this.siteId=siteId;
        this.order=place;
    }
}
