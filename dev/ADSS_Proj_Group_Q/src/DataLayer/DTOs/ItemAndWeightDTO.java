package DataLayer.DTOs;

import DataLayer.DataMapper;

public class ItemAndWeightDTO extends DTO{

    public String item;
    public double weight;
    public String shipmentId;

    public ItemAndWeightDTO(DataMapper controller, String item, double weight,String shipmentId){
        super(controller);
        this.item=item;
        this.weight=weight;
        this.shipmentId=shipmentId;

    }
}
