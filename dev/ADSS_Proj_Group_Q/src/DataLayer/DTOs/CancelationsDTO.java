package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class CancelationsDTO extends DTO{

    public LocalDate date;
    public String shiftType;
    public String branch;
    public int productID;
    public int amount;


    public CancelationsDTO(DataMapper controller,LocalDate date,String shiftType,String branch,int productID,int amount){
        super(controller);
        this.date=date;
        this.shiftType=shiftType;
        this.branch=branch;
        this.productID=productID;
        this.amount=amount;
    }



}
