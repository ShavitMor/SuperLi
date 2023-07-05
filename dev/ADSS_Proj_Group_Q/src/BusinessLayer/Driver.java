package BusinessLayer;

import DataLayer.DriverMapper;

import java.time.LocalDate;

public class Driver extends Employee implements IDriver{

    LicenceType licenceType;
    String licenceNumber;
    boolean tempControlledLicence;
    private DriverMapper driverMapper;



    /**
     * @param newLicenceType
     * @return
     */

    public Driver(String licenceNumber, String newLicenceType, boolean tempControlledLicence, String mid, String mname, String mbankCompany, int mbankBranch, int mbankId, double msalary, LocalDate mstartDate, String mterms, String state){
        super(mid,mname, mbankCompany, mbankBranch, mbankId, msalary, mstartDate, mterms, state);
        this.licenceNumber = licenceNumber;
        this.licenceType = getLisenceTypeByName(newLicenceType);
        this.tempControlledLicence = tempControlledLicence;
        addRole("d");
        driverMapper=new DriverMapper();
    }

    public Driver(Employee employee,String licenceNumber, String newLicenceType,boolean tempControlledLicence){
        super(employee.id,employee.name, employee.bankCompany, employee.bankBranch, employee.bankId, employee.salary, employee.startDate, employee.terms, employee.personalState);
        this.licenceNumber = licenceNumber;
        this.licenceType = getLisenceTypeByName(newLicenceType);
        this.tempControlledLicence = tempControlledLicence;
        roles=employee.roles;
        isActive=employee.isActive;
        isLoggedIn=employee.isLoggedIn;
        driverMapper=new DriverMapper();
    }


    public LicenceType getLisenceTypeByName (String newLicenceType){


        switch (newLicenceType){
            case "B":
                licenceType = LicenceType.B;
                break;
            case "C":
                licenceType = LicenceType.C;
                break;
            case "C1":
                licenceType = LicenceType.C1;
                break;

        }
        if(licenceType==(null)){
            throw new IllegalArgumentException("bad license type");

        }
        return licenceType;

    }



    public boolean setLicenceType(String type) {



        if(driverMapper.updateDriver(id,"licence_type", type)) {
            licenceType =getLisenceTypeByName(type);
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public LicenceType getLicenceType() {

        return licenceType;

    }

    /**
     * @param newLicenceNumber
     * @return
     */
    @Override
    public boolean setLicenceNumber(String newLicenceNumber) {
        if (isValidLicenceNumber(newLicenceNumber)){
            String oldLnum = licenceNumber;
            if(driverMapper.updateDriver(id,"licence_number",newLicenceNumber)) {
                licenceNumber = newLicenceNumber;
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setChangedToDelete(){

    }

    /**
     * @return
     */
    @Override
    public int getMaxDriveWeight() {
        return licenceType.getWeightLimit();
    }

    @Override
    public boolean isCanDriveTempControlled() {
        return tempControlledLicence;
    }
    public boolean isTempControlMatch(boolean truckTempControl){
        return !truckTempControl||this.tempControlledLicence==truckTempControl;
    }


    public String getRequiredLicenceType(double weight){
        return LicenceType.getRequiredLicenceByWeight(weight).toString();
    }

    public static boolean isValidLicenceNumber(String newLicenceNumber){
        if (newLicenceNumber.length()!=7){
            return false;
        }
        for (char c:
                newLicenceNumber.toCharArray()) {
            if (c>57 || c<48){
                return false;
            }
        }
        return true;
    }

    public void setTempControlled(boolean bool){
        if(driverMapper.updateDriver(id,"temp_controlled_licence",bool)) {
            tempControlledLicence = bool;
            notifyObservers(tempControlledLicence);
        }


    }

    public void switchTempControl(){
        if(driverMapper.updateDriver(id,"temp_controlled_licence",!tempControlledLicence))
            tempControlledLicence = !tempControlledLicence;
    }

    @Override
    public double calculateSalary(int month){
        double hours=ShippingManager.getInstance().getMonthlyDriveHoursOfDriver(this,month);
        return hours*salary+salaryBonus;
    }
    @Override
    public String toString(){
        return super.toString()+" licence Type: "+licenceType+" licence Number: "+licenceNumber;
    }
    public String toStringShort(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s\t Id: %s\tLicenceType: %s\t TempControl: %b\tLicenceNumber: %s\n",
                getName(),getId(),getLicenceType().toString(),isCanDriveTempControlled(),getLicenceNumber()));
        return sb.toString();
    }

    public boolean isDriverCanDrive(LicenceType required){
        return required.getWeightLimit() <= getLicenceType().getWeightLimit();
    }

    public String toStringShorter(){
        return String.format("%s -L.N: %s Type: %s",getName(),getLicenceNumber(),getLicenceType().toString());
    }

}
