package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


public class ShiftDTO extends DTO {
    public LocalDate date;
    public LocalTime begin;
    public int shiftTime;
    public LocalTime end;
    public String shiftType;
    public String branch;
    public String shiftManagerID;
    public boolean isPublished;


    public ShiftDTO(DataMapper controller, LocalDate date, LocalTime begin, int shiftTime, LocalTime end, String shiftType, String branch, String shiftManagerID, boolean isPublished) {
        super(controller);
        this.date = date;
        this.begin = begin;
        this.shiftTime = shiftTime;
        this.end = end;
        this.shiftType = shiftType;
        this.branch = branch;
        this.shiftManagerID = shiftManagerID;
        this.isPublished = isPublished;
    }




}

