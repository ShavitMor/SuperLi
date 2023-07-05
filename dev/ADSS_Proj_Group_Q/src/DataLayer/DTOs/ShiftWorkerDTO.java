package DataLayer.DTOs;

import DataLayer.DataMapper;


import java.time.LocalDate;


public class ShiftWorkerDTO extends DTO{
    public LocalDate shiftDate;
    public String shiftTime;
    public String branch;
    public String id;
    public String role;

    public ShiftWorkerDTO(DataMapper controller, LocalDate lc, String shiftTime, String branch, String mid,String role) {
        super(controller);
        shiftDate=lc;
        this.shiftTime=shiftTime;
        this.branch=branch;
        this.id=mid;
        this.role=role;
    }


    }


