package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class DriversShiftsDTO extends DTO{
    public LocalDate date;
    public String time;
    public String id;

    public DriversShiftsDTO(DataMapper controller,LocalDate date1,String time1,String mid) {
        super(controller);
        date=date1;
        time=time1;
        id=mid;
    }

}
