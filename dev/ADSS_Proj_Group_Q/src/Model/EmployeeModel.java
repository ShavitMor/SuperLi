package Model;


public class EmployeeModel {
    protected String id;
    protected String name;
    protected String bankCompany;
    protected int bankBranch;
    protected int bankId;
    protected double salary; // per hour
    protected double salaryBonus;
    protected String startDate;
    protected String terms;
    protected String state;
    protected String roles;
    protected String isLoggedIn;
    protected String isActive;

    public EmployeeModel(String mid, String mname, String mbankCompany, int mbankBranch, int mbankId, double msalary,
                         double salaryBonus, String mstartDate, String mterms,String roles,String isActive,
                         String personalstate ,String isLoggedIn) {

        id = mid;
        name = mname;
        bankCompany = mbankCompany;
        bankBranch = mbankBranch;
        bankId = mbankId;
        salary = msalary;
        this.salaryBonus = salaryBonus;
        startDate = mstartDate;
        terms = mterms;
        this.roles = roles;
        this.isActive = isActive;
        state=personalstate;
        this.isLoggedIn = isLoggedIn;
    }

    // Getters and setters for the fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCompany() {
        return bankCompany;
    }

    public void setBankCompany(String bankCompany) {
        this.bankCompany = bankCompany;
    }

    public int getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(int bankBranch) {
        this.bankBranch = bankBranch;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalaryBonus() {
        return salaryBonus;
    }

    public void setSalaryBonus(int salaryBonus) {
        this.salaryBonus = salaryBonus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public void addRole(String role){

        roles=roles+", "+role;

    }

    public void setState(String newState) {

        this.state=newState;
        }

    public String getMstartDate() {
        return startDate;
    }

    public String getMterms() {
        return terms;
    }

    public String getRoles() {
        return roles;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getPersonalstate() {
        return state;
    }

    public String getIsLoggedIn() {
        return isLoggedIn;
    }
}
