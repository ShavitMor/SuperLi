package Model;

public class DriverModel extends EmployeeModel{
    String licenseType;
    String LicenseNumber;
    String tempControl;
    public DriverModel(String mid, String mname, String mbankCompany, int mbankBranch, int mbankId, double msalary, double salaryBonus, String mstartDate, String mterms, String roles, String isActive, String personalstate, String isLoggedIn,String licenseType,String licenseNumber,String tempControl) {
        super(mid, mname, mbankCompany, mbankBranch, mbankId, msalary, salaryBonus, mstartDate, mterms, roles, isActive, personalstate, isLoggedIn);
        this.licenseType=licenseType;
        this.LicenseNumber=licenseNumber;
        this.tempControl=tempControl;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public String getTempControl() {
        return tempControl;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public void setTempControl(String tempControl) {
        this.tempControl = tempControl;
    }
}
