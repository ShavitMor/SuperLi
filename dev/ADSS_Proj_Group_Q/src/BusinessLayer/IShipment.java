
package BusinessLayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;

public interface IShipment {

    int getId();
    void setShippingDate(LocalDate newDate);
    LocalDate getShippingDate();
    void setShippingStartTime(LocalTime newStartTime);
    LocalTime getShippingStartTime();
    ITruck getTruck();
    void setTruck(ITruck newTruck);
    void setDriver(Driver newDriver) throws Exception;
    Driver getDriver();
    void setSource(Site newSource);
    Site getSource();
    boolean setSourceContact(String ContactName, String ContactPhone);
    void addDestination(Site newDest);
    void removeDestination(Site toRemove);
    void editDestinationInfo(int id,String name, String address, String contactName, String contactPhone);
    boolean addItem(int destID, String itemName,int amount, double itemWeight) throws Exception;
    boolean removeItem(int destId,String itemName) throws Exception;

    boolean isTotalWeightOK() throws Exception;

    boolean isDriverCanDrive(IDriver driver);
    boolean advanceShipment() throws Exception;

    ShipmentStatus getStatus();



    public enum ShippingZone{
        North,
        South,
        East,
        West,
        Center;
    }
    public enum ShipmentStatus{
        TruckError,
        DriverError,
        ShipmentError,
        ItemError,
        SiteError,
        StandBy,
        InProgress,
        Done;


        public ShipmentStatus advanceShipmentStatus()  {
            switch(this){
                case StandBy:
                    return InProgress;
                case InProgress:
                    return Done;
                default:
                    throw new IllegalStateException("cannot advance shipment with status:" + this.toString());
            }
        }
        public boolean isErrorStatus(){
            switch (this){
                case TruckError, ShipmentError,DriverError, SiteError,ItemError:
                    return true;
            }
            return false;
        }

        public boolean isViableToEdit(){
            return this!=InProgress && this!=Done;
        }

        public ShipmentStatus setTruckError(){
            return TruckError;
        }
        public ShipmentStatus setDriverError(){
            return DriverError;
        }
        public ShipmentStatus setShipmentError(){
            return ShipmentError;
        }
        public ShipmentStatus setItemError() { return ItemError;}
        public ShipmentStatus setSiteError() { return SiteError;}
    }
    public class UnfitDriverLicenceType extends RuntimeException{
        public UnfitDriverLicenceType(String msg, String required,String current ){
            super(msg + "\n Required: "+ required + "\t Current: " + current);
        }
    }
    public class TruckOverWeightException extends RuntimeException {
        public TruckOverWeightException(String msg, double maxAllowed) {
            super(msg + "\n Max Weight Allowed:" + maxAllowed);
        }
    }
    public class InvalidShipmentEditingException extends RuntimeException {
        public InvalidShipmentEditingException(String msg, ShipmentStatus status) {
            super("Editing data of shipment with status: "+ status +"is not allowed\n"+msg);
        }
    }
}

