package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class RolesNeededDTO extends DTO{

    public LocalDate date;
    public String shiftType;
    public String branch;

    public String employeeType;
    public int amount;

public RolesNeededDTO(DataMapper controller,LocalDate date,String shiftType,String branch,String employeeType,int amount){
    super(controller);
    this.date=date;
    this.shiftType=shiftType;
    this.branch=branch;
    this.employeeType=employeeType;
    this.amount=amount;
}


}
