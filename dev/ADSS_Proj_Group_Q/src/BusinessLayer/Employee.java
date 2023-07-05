package BusinessLayer;

import DataLayer.DTOs.EmployeeDTO;
import DataLayer.EmployeeMapper;
import DataLayer.EmployeeRolesMapper;

import java.util.*;
import java.time.*;

public class Employee extends Observable {
    //------------------------fields----------------------------
    public String id;
    protected String name;
    public String bankCompany;
    public int bankBranch;
    public int bankId;
    public double salary; //per hour
    public double salaryBonus;
    public LocalDate startDate;
    public String terms; //tnaey aasaka
    public List<EmployeeType> roles;
    protected boolean isActive;
    public String personalState;
    protected boolean isLoggedIn;
    private EmployeeMapper employeeMapper;
    private EmployeeRolesMapper employeeRolesMapper;

    //------------------------fields----------------------------


    //------------------------constructor----------------------------
    public Employee(String mid, String mname, String mbankCompany, int mbankBranch, int mbankId, double msalary, LocalDate mstartDate, String mterms, String state) {
        id = mid;
        name = mname;
        bankCompany = mbankCompany;
        bankBranch = mbankBranch;
        bankId = mbankId;
        salary = msalary;
        salaryBonus = 0;
        startDate = mstartDate;
        terms = mterms;
        roles = new ArrayList<>();
        isActive = true;
        personalState = state;
        isLoggedIn = false;
        employeeMapper = new EmployeeMapper();
        employeeRolesMapper = new EmployeeRolesMapper();
    }

    public Employee(EmployeeDTO employeeDTO) {
        employeeMapper = new EmployeeMapper();
        employeeRolesMapper = new EmployeeRolesMapper();
        id = employeeDTO.id;
        name = employeeDTO.name;
        bankCompany = employeeDTO.bankCompany;
        bankBranch = employeeDTO.bankBranch;
        bankId = employeeDTO.bankId;
        salary = employeeDTO.salary;
        salaryBonus = employeeDTO.salaryBonus;
        startDate = employeeDTO.startDate;
        terms = employeeDTO.terms;
        if ((!employeeRolesMapper.getEmployeeRoles(id).isEmpty()) && (employeeRolesMapper.getEmployeeRoles(id).contains("hr") || employeeRolesMapper.getEmployeeRoles(id).contains("sMan")))
            roles = new ArrayList<>();
        else {
            List<EmployeeType> curr = employeeRolesMapper.getEmployeeRoles(id).stream().map((String x) -> EmployeeController.getInstance().gEmployeeType(x)).toList();
            roles=new ArrayList<>();
            roles.addAll(curr);
        }
        isActive = employeeDTO.isActive;
        personalState = employeeDTO.personalState;
        isLoggedIn = employeeDTO.isLoggedIn;
    }
    //------------------------constructor----------------------------

    //------------------------functions----------------------------


    protected String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void addRole(String role) {
        role = role.toLowerCase();
        switch (role) {
            case ("shm"):
                addRole(EmployeeType.ShiftManager);
                break;
            case ("sm"):
                addRole(EmployeeType.SalesMan);
                break;
            case ("se"):
                addRole(EmployeeType.Security);
                break;
            case ("sk"):
                addRole(EmployeeType.StoreKeeper);
                break;
            case ("u"):
                addRole(EmployeeType.Usher);
                break;
            case ("c"):
                addRole(EmployeeType.Clean);
                break;
            case ("g"):
                addRole(EmployeeType.General);
                break;
            case ("d"):
                addRole(EmployeeType.Driver);
                break;

        }

    }

    private void addRole(EmployeeType employeeType) {
        //add role to worker
        if (roles.contains(employeeType))
            throw new IllegalArgumentException("worker already has this role");
        if (employeeRolesMapper.addRoleToEmp(id, employeeType.toString()))
            roles.add(employeeType);
    }

    protected double calculateSalary(int month) {
        //caculate worker salary
        return ShiftController.getInstance().getHours(this, month) * salary + salaryBonus;

    }


    public String toString() {
        return "id:" + id + " name: " + name + " bank Company:" + bankCompany + " bank branch: " + bankBranch + " bank id: " + bankId + " salary per hour: " + salary+" start date: " + startDate+" state: "+personalState + " terms: " + terms + " is active: " + isActive;
    }

    //------------------------functions----------------------------
    public String getDetAndRoles() {
        String ret = id + " " + name + " ";
        for (EmployeeType str : roles) {
            ret = ret + str.name() + ", ";
        }
        ret = ret.substring(0, ret.length() - 1);
        return ret;
    }

    public boolean isContainRole(EmployeeType type) {
        //return if employee can work at that role
        return roles.contains(type);
    }

    protected void setSalaryBonus(int bonus) {
        if (employeeMapper.updateEmployee(id, "salary_bonus", bonus))
            salaryBonus = bonus;
    }

    protected void setActive(boolean isA) {
        //turns the worker into not active worker
        if (!isActive)
            throw new IllegalArgumentException("worker alreay isnt active");
        if (employeeMapper.updateEmployee(id, "is_logged_in", isA)) {
            isActive = isA;
        }
    }

    public List<EmployeeType> getEmployeeTypes() {
        return roles;
    }

    public boolean equals(Employee other) {
        return other.getId() == id;
    }


    protected String login() {
        if (!isActive)
            throw new IllegalArgumentException("your account isnot active anymore");
        if (isLoggedIn)
            throw new IllegalArgumentException("your account is already logged in");
        if (employeeMapper.updateEmployee(id, "is_logged_in", true)) {
            isLoggedIn = true;
            return "Logged-In Succesfully";
        }
        return " ";
    }

    protected void logout() {
        //logout from system
        if (!isLoggedIn)
            throw new IllegalArgumentException("your account is already logged out");
        if (employeeMapper.updateEmployee(id, "is_logged_in", false)) {
            isLoggedIn = false;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setPersonalState(String personalState) {
        if (employeeMapper.updateEmployee(id, "personal_state", personalState))
            this.personalState = personalState;
    }

    public String getPersonalState() {
        return personalState;
    }


    public void setBank(String bankCompany, int bankBranch, int bankId) {
        if (employeeMapper.updateEmployee(id, "bank_company", bankCompany) &&
                employeeMapper.updateEmployee(id, "bank_branch", bankBranch) &&
                employeeMapper.updateEmployee(id, "bank_id", bankId)) {

            this.bankCompany = bankCompany;
            this.bankBranch = bankBranch;
            this.bankId = bankId;
        }

    }

    public void setTerms(String terms){
        if(employeeMapper.updateEmployee(id,"terms",terms)){
            this.terms=terms;
        }
    }

    public void setSalary(int salary){
        if(employeeMapper.updateEmployee(id,"salary",salary)){
            this.salary=salary;
        }
    }


    public IDriver.LicenceType getLicenceType() {
        return null;
    }

}
