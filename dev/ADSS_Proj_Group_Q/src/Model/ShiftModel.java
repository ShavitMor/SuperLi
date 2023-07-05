package Model;

import BusinessLayer.Employee;
import BusinessLayer.EmployeeType;
import BusinessLayer.Shift;
import GUI.ShiftViewWindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftModel {
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String shiftType;
    private String branch;

    private HashMap<String, Integer> rolesNeeded;
    private  List<String> workers;
    private List<String> specialEvents;
    private List<String> cancelations;
    private boolean isPublished;
    private ShiftViewWindow shiftViewWindow;

    // Constructor
    public ShiftModel(int year,int month,int day,String shiftType,String branchName,LocalTime begin,LocalTime end,HashMap<String,Integer> rolesNeeded,List<String> workers,List<String> specialEvents, List<String> cancelations,boolean published) {
        this.date = LocalDate.of(year,month,day);
        this.begin = begin;
        this.end = end;
        this.shiftType = shiftType;
        this.branch = branchName;

        this.rolesNeeded = rolesNeeded;

        this.workers = workers;
        this.specialEvents =specialEvents;
        this.cancelations = cancelations;
        this.isPublished = published;
    }

    // Getters and setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public HashMap<String, Integer> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(HashMap<String, Integer> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<String> getWorkers() {
        return workers;
    }

    public void setWorkers( List<String> workers) {
        this.workers = workers;
    }
    public void addNewWorker(String id){
        workers.add(id);
        shiftViewWindow.update(this);
    }

    public List<String> getSpecialEvents() {
        return specialEvents;
    }

    public void setSpecialEvents(ArrayList<String> specialEvents) {
        this.specialEvents = specialEvents;
    }

    public List<String> getCancelations() {
        return cancelations;
    }

    public void setCancelations(List<String> cancelations) {
        this.cancelations = cancelations;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
        shiftViewWindow.update(this);
    }
    public void addListener(ShiftViewWindow shiftViewWindow){
        this.shiftViewWindow=shiftViewWindow;

    }
}
