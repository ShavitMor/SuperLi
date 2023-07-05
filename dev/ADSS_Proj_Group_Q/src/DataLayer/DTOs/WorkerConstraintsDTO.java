package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerConstraintsDTO extends DTO{
    public String id;
    public LocalDate date;
    public String time;
    public WorkerConstraintsDTO(DataMapper controller, String id, LocalDate date, String time) {
        super(controller);
        this.id=id;
        this.date=date;
        this.time=time;
    }


}
