package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class DriversNeededDTO extends DTO{


    public LocalDate date;
    public String shiftType;
    public int amount;

    public DriversNeededDTO(DataMapper controller, LocalDate date, String shiftType, int amount){
        super(controller);
        this.date=date;
        this.shiftType=shiftType;
        this.amount=amount;
    }
}
