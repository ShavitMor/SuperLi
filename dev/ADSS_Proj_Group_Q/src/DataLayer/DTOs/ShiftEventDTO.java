package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShiftEventDTO extends DTO{
    public LocalDate date;
    public String time;
    public String branch;
    public String event;
    public ShiftEventDTO(DataMapper controller, LocalDate date, String time, String branch, String event) {
        super(controller);
        this.date=date;
        this.time=time;
        this.branch=branch;
        this.event=event;
    }

}
