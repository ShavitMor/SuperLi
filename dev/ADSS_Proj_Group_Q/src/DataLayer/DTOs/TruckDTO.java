package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TruckDTO extends DTO {

    public String id;
    public String licenceNumber;
    public String model;
    public double weight;
    public double maxCarryWeight;
    public boolean tempControlled;

    public TruckDTO(DataMapper controller, String licenceNumber, String model, double truckWeight, double maxCarryWeight, boolean tempControlled,String id) {
        super(controller);
        this.id=id;
        this.licenceNumber = licenceNumber;
        this.model = model;
        this.weight = truckWeight;
        this.maxCarryWeight = maxCarryWeight;
        this.tempControlled = tempControlled;
}




}
