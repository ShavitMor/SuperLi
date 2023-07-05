package BusinessLayer;

import java.util.Objects;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Truck extends Observable implements ITruck{

    protected int id;
    protected String licenceNumber;
    protected String model;
    protected double weight;
    protected double maxCarryWeight;
    protected boolean tempControlled;

    public Truck(int id,String licenceNumber, String model, double truckWeight, double maxCarryWeight, boolean tempControlled) {
        if (!isValidTruckNum(licenceNumber)){
            throw new IllegalArgumentException("Illegal licence number");
        }
        if(!isValidTruckWeight(truckWeight)){
            throw new IllegalArgumentException("truck weight must be more than 0");
        }
        if(!isValidCarryWeight(maxCarryWeight)){
            throw new IllegalArgumentException("truck carry weight must be more or equal to zero");
        }
        this.licenceNumber = licenceNumber;
        this.model = model;
        this.weight = truckWeight;
        this.maxCarryWeight = maxCarryWeight;
        this.tempControlled = tempControlled;
        this.id = id;

    }
    /**
     * @param newLicenceNum - Truck's licence number
     * @return true if action succeeded, false if failed
     */
    @Override
    public boolean setLicenceNum(String newLicenceNum) {
        Pattern allowed = Pattern.compile("[a-zA-z0-9]");
        Matcher matcher = allowed.matcher(newLicenceNum);
        boolean isNewNumberAllowed = matcher.find();
        if (isNewNumberAllowed){
            String oLnum = licenceNumber;
            licenceNumber = newLicenceNum;
            setChanged();
            notifyObservers(oLnum);
        }
        else
            System.out.println("Please enter valid licence number");
        return isNewNumberAllowed;
    }

    /**
     * @return
     */
    @Override
    public String getLicenceNum() {
        return licenceNumber;
    }

    /**
     * @param newModel
     * @return
     */
    @Override
    public void setModel(String newModel) {
        model = newModel;
        setChanged();
        notifyObservers(licenceNumber);
    }

    /**
     * @return
     */
    @Override
    public String getModel() {
        return model;
    }

    /**
     * @param newWeight
     * @return
     */
    @Override
    public boolean setWeight(double newWeight) {
        if (newWeight<= 0 ){
            System.out.println("Truck weight cannot be negative or zero.");
        }
        else{
            weight = newWeight;
            setChanged();
            notifyObservers(licenceNumber);
        }
        return newWeight>0;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * @param newCarryWeight
     * @return
     */
    @Override
    public boolean setMaxCarryWeight(double newCarryWeight) {
        if (newCarryWeight< 0 ){
            System.out.println("Truck weight cannot be negative.");
        }
        else{
            maxCarryWeight = newCarryWeight;
            setChanged();
            notifyObservers(licenceNumber);
        }
        return newCarryWeight>=0;
    }

    public void setChangedToDelete(){
        setChanged();
    }


    /**
     * @return
     */
    @Override
    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    @Override
    public boolean isTempratureControlled() {
        return tempControlled;
    }

    public boolean isValidTruckNum(String newLicenceNum){
        Pattern allowed = Pattern.compile("[a-zA-z0-9]");
        Matcher matcher = allowed.matcher(newLicenceNum);
        return newLicenceNum!=null & matcher.find();
    }
    public boolean isValidTruckWeight(double weight){
        return weight>0;
    }
    public boolean isValidCarryWeight(double carryWeight){
        return weight>=0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return licenceNumber.equals(truck.licenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenceNumber);
    }
    @Override
    public String toString(){
        if(this !=null){
            return String.format("Licence Number:\t  %s,\nModel: \t %s, \nWeight:\t%3.3f\nMax Carry Weight: \t%3.3f \n TempControl:\t %s",
                    licenceNumber,model,weight,maxCarryWeight,tempControlled?"Yes":"No");
        }
        else{
            return "Unassigned";
        }
    }
    public boolean isTempControlled(){
        return tempControlled;
    }

    public boolean switchTempControl() {
        tempControlled = !tempControlled;
        setChanged();
        notifyObservers(licenceNumber);
        return tempControlled;
    }

    public int getId() {
        return id;
    }
}
