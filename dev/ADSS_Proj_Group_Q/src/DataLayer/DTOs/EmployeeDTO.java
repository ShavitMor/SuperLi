package DataLayer.DTOs;

import DataLayer.DataMapper;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDTO extends DTO {
    public String id;
    public String name;
    public String bankCompany;
    public int bankBranch;
    public int bankId;
    public double salary; //per hour
    public double salaryBonus;
    public LocalDate startDate;
    public String terms;//tnaey aasaka
    public boolean isActive;
    public boolean isLoggedIn;
    public String personalState;



    public EmployeeDTO(DataMapper controller, String id, String name, String bankCompany, int bankBranch, int bankId, double salary, double salaryBonus, LocalDate startDate, String terms, boolean isActive, String state, boolean isLoggedIn) {
        super(controller);
        this.id = id;
        this.name = name;
        this.bankCompany = bankCompany;
        this.bankBranch = bankBranch;
        this.bankId = bankId;
        this.salary = salary;
        this.salaryBonus = salaryBonus;
        this.startDate = startDate;
        this.terms = terms;
        this.isActive=isActive;
        this.isLoggedIn=isLoggedIn;
        this.personalState=state;
    }







}
