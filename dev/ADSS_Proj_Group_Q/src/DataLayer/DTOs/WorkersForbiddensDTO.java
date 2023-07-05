package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.time.LocalDate;

public class WorkersForbiddensDTO extends DTO{
    public LocalDate date;
    public String time;
    public String id;
    public WorkersForbiddensDTO(DataMapper controller,LocalDate lc,String time,String id) {
        super(controller);
        date=lc;
        this.time=time;
        this.id=id;
    }

}
