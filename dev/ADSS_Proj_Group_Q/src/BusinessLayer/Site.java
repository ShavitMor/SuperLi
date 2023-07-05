package BusinessLayer;

import DataLayer.DTOs.SiteDTO;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class Site extends Observable {
    String name;
    String address;
    String contactName;
    String contactPhone;
    Coordinate location;
    SiteType type;
    boolean isActive = true;


    public Site(String name, String address, String cName, String cPhone, int xCord, int yCord, SiteType type) {
        this.name = name;
        this.address = address;
        this.contactName = cName;
        this.contactPhone = cPhone;
        location = new Coordinate(xCord,yCord);
        this.type = type;
    }

    class Coordinate{
        int x;
        int y;

        public Coordinate(int x,int y){
            this.x =x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }


        public double getDistance(Coordinate other){
            int sumX = (x- other.x);
            sumX *= sumX;
            int sumY = (y - other.y);
            sumY *= sumY;
            int sum = sumX+sumY;
            return Math.sqrt(sum);
        }
        public double getTimeToOtherAsDouble(Coordinate other){
            int TruckSpeed = 60;
            return getDistance(other)/TruckSpeed;
        }

        public LocalTime TimeToOther(Coordinate other){
            int TruckSpeed = 60;
            double driveTime  = getDistance(other)/TruckSpeed;
            int hours = 0;
            while(driveTime > 1){
                hours +=1;
                driveTime -=1;
            }
            driveTime *= 60;
            return LocalTime.of(hours,(int)Math.round(driveTime));
        }
    }

    public enum SiteType{
        LogisticCenter,
        Branch,
        Supplier,
        Else;

        public boolean isSupplier(){
            return this==Supplier;
        }
        public boolean isBranch(){
            return this==Branch;
        }
        public boolean isLogisticCenter(){
            return this == LogisticCenter;
        }


    }

    public Site(String name, String address, String contactName,String contactPhone){
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Site(String name, String address, String contactName,String contactPhone, int x,int y){
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        location = new Coordinate(x,y);
    }

    public Site(SiteDTO siteDTO){
        name=siteDTO.name;
        address=siteDTO.address;
        contactName=siteDTO.contactName;
        contactPhone=siteDTO.contactPhone;
        location = new Coordinate(siteDTO.x,siteDTO.y);
        this.type = getTypeFromString(siteDTO.type);
        isActive =siteDTO.active;
    }


    /**
     * Complete constructor for site in v2- should be used!
     * @param name
     * @param address
     * @param contactName
     * @param contactPhone
     * @param coordinate
     * @param type
     */
    public Site(String name, String address, String contactName,String contactPhone,Coordinate coordinate,SiteType type){
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        location = coordinate;
        this.type = type;
    }

    public boolean isActive(){
        return this.isActive;
    }

    public boolean deactivateSite(){
        this.isActive = false;
        return isActive;
    }

    protected Site() {
    }


    void setAllInfo(String name, String address, String contactName,String contactPhone){
        String oldName = this.name;
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        setChanged();
        notifyObservers(oldName);
    }

    void setAllInfo(String name, String address, String contactName,String contactPhone,int x,int y,SiteType type){
        String oName = this.name;
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        location.setX(x);
        location.setY(y);
        this.type = type;
        setChanged();
        notifyObservers(oName);
    }

    boolean setName(String newName){
        String oName = this.name;
        this.name = newName;
        setChanged();
        notifyObservers(oName);
        return true;
    }
    String getName(){
        return name;
    }
    boolean setAddress(String newAddress){
        this.address = newAddress;
        setChanged();
        notifyObservers(name);
        return true;
    }
    String getAddress(){
        return address;
    }

    boolean setContactName(String contactName){
        if (!isValidName(contactName)){
            return false;
        }
        this.contactName = contactName;
        setChanged();
        notifyObservers(name);
        return true;
    }
    String getContactName(){
        return contactName;
    }
    boolean setContactPhone(String newContactPhone){
        if (!isValidPhoneNumber(newContactPhone)){
            return false;
        }
        this.contactPhone = newContactPhone;
        setChanged();
        notifyObservers(name);
        return true;
    }
    public String getContactPhone(){
        return contactPhone;
    }

    public void setLocation(int x, int y) {
        this.location = new Coordinate(x,y);
        setChanged();
        notifyObservers(name);
    }
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Coordinate getLocation() {
        return location;
    }

    public SiteType getType() {
        return type;
    }

    public void setType(SiteType type) {
        this.type = type;
        setChanged();
        notifyObservers(name);
    }

    public static SiteType getTypeFromString(String type){
        type = type.toUpperCase();
        switch (type){
            case "BRANCH":
                return SiteType.Branch;
            case "SUPPLIER":
                return SiteType.Supplier;
            case "LOGISTICCTR","LOGISTICCENTER","LOGISTIC CENTER":
                return SiteType.LogisticCenter;
            default:
                return SiteType.Else;
        }
    }
    public void setTypeFromString(String type){
        setType(getTypeFromString(type));
        setChanged();
        notifyObservers(name);
    }
    public LocalTime getDriveTimeTo(Site other){
        return location.TimeToOther(other.getLocation());
    }

    @Override
    public String toString() {
        String nullVal = "Unassigned";
        String isActiveString = !isActive() ? "\t*** Site is Inactive! ***\n" : "";
        return String.format("Site Name:\t %s \n Type:\t %s \n Address:\t %s\n Contact Name:\t %s\n Contact Phone:\t %s\n Location: \t %d,%d\n%s",
                Objects.toString(name,nullVal),type.toString(),Objects.toString(address,nullVal),
                Objects.toString(contactName,nullVal),Objects.toString(contactPhone,nullVal),location.getX(),location.getY(),isActiveString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return name.equals(site.name) && address.equals(site.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    boolean isValidName(String name){
        if (name.isBlank()){
            return false;
        }
        return name.matches("[a-zA-Z0-9 ]+");
    }

    boolean isValidPhoneNumber(String number){
        if (number.isBlank() || number.length()<3){
            return false;
        }
        for (int i = 0; i<number.length();i++){
            if((number.charAt(i) <48 || number.charAt(i)>57)){
                return false;
            }
        }
        return true;
    }

    public boolean isValidSite(){
        if (this == null){
            return false;
        }
        return  !(name == null || name.trim().isEmpty()|| address == null || address.trim().isEmpty()
                || contactName == null || contactName.trim().isEmpty()
                || contactPhone == null || contactPhone.trim().isEmpty());
    }

    public void clearInfo(){
        name = null;
        address = null;
        contactName = null;
        contactPhone = null;
        setChanged();
        notifyObservers(name);
    }
}


