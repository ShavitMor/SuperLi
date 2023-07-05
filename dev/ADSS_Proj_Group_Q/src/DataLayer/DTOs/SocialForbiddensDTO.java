package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class SocialForbiddensDTO extends DTO {
    public LocalDate date;
    public String empId;
    public int amount;
    public SocialForbiddensDTO(DataMapper controller,LocalDate lc,String empId,int amount) {
        super(controller);
        date=lc;
        this.empId=empId;
        this.amount=amount;
    }
}
