package BusinessLayer;

import DataLayer.DTOs.ItemAndWeightDTO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.*;

public class Shipment extends Observable implements IShipment {
    int id;
    LocalDate shippingDate;
    LocalTime shippingStartTime;
    ITruck truck;
    Driver driver;
    ShippingZone zone;
    ShipmentInventoryManager invManager;
    ShipmentStatus status;

    public Shipment(int id){
        this.id = id;
        zone = ShippingZone.Center;
        status = ShipmentStatus.StandBy;
        invManager = new ShipmentInventoryManager();
        checkAndSetErrorStatus();
    }
    private Shipment(int id,LocalDate date, LocalTime time){
        this.id = id;
        shippingDate = date;
        shippingStartTime = time;
        zone = ShippingZone.Center;
        status = ShipmentStatus.StandBy;
        invManager = new ShipmentInventoryManager();
        checkAndSetErrorStatus();
    }

    private Shipment(Integer id, LocalDate shippingDate, LocalTime shippingStartTime, ShipmentStatus statusFromString,
                    ShippingZone zoneFromString, Truck truckByID, Driver driver) {
        this.id = id;
        this.shippingDate = shippingDate;
        this.shippingStartTime = shippingStartTime;
        this.status = statusFromString;
        this.zone = zoneFromString;
        this.truck = truckByID;
        this.driver = driver;
        invManager = new ShipmentInventoryManager();
    }

    public static Shipment getNotNullTimes (int id){
        return new Shipment(id,LocalDate.of(0,1,1),LocalTime.of(0,0));
    }

    public Shipment(int id,LocalDate ld,LocalTime lt,Truck truck,Driver driver,Site source,ShipmentInventoryManager SIM){
        this.id = id;
        zone = ShippingZone.Center;
        status = ShipmentStatus.StandBy;
        shippingDate = ld;
        shippingStartTime = lt;
        this.truck = truck;
        this.driver = driver;
        invManager = SIM;
        checkAndSetErrorStatus();
    }

    public static Shipment FromDTO(String id, LocalDate shippingDate, LocalTime shippingStartTime,
                                   String status, String zone, String truck, String driver) {
        Truck t = truck == null ? null : TruckManager.getInstance().getTruckByID(Integer.valueOf(truck));
        return new Shipment(Integer.valueOf(id),shippingDate,shippingStartTime,getStatusFromString(status)
                ,getZoneFromString(zone),t,EmployeeController.getInstance().getDriver(driver));
    }

    public static ShipmentStatus getStatusFromString(String status){
        switch (status.toLowerCase()){
            case ("done") :
                return ShipmentStatus.Done;
            case ("inprogress"):
                return ShipmentStatus.InProgress;
            case ("siteerror") :
                return ShipmentStatus.SiteError;
            case ("shipmenterror"):
                return ShipmentStatus.ShipmentError;
            case ("drivererror") :
                return ShipmentStatus.DriverError;
            case ("truckerror") :
                return ShipmentStatus.TruckError;
            case ("itemerror") :
                return ShipmentStatus.ItemError;
            default:
                return ShipmentStatus.StandBy;

        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setShippingDate(LocalDate newDate) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        setDriver(null);
        if(shippingDate!=null&&getShippingStartTime()!=null)
            ShiftController.getInstance().removeDriverNeededToShift(getShippingDate(),getShippingStartTime());
        shippingDate = newDate;
        if(shippingDate!=null&&shippingStartTime!=null) {
            ShiftController.getInstance().addDriverNeededToShift(getShippingDate(), shippingStartTime);

        }
        checkAndSetErrorStatus();
    }

    @Override
    public LocalDate getShippingDate() {
        return shippingDate;
    }

    @Override
    public void setShippingStartTime(LocalTime newStartTime) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        if(shippingStartTime!=null&&getShippingStartTime()!=null)
            try{
                ShiftController.getInstance().removeDriverNeededToShift(getShippingDate(),getShippingStartTime());
            }catch (Exception ex){}
        setDriver( null);
        shippingStartTime = newStartTime;
        if(shippingDate!=null&&shippingStartTime!=null)
            ShiftController.getInstance().addDriverNeededToShift(shippingDate,shippingStartTime);


        checkAndSetErrorStatus();
    }

    @Override
    public LocalTime getShippingStartTime() {
        return shippingStartTime;
    }

    @Override
    public ITruck getTruck() {
        return truck;
    }

    @Override
    public void setTruck(ITruck newTruck) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        truck = newTruck;
        checkAndSetErrorStatus();
    }

    @Override
    public void setDriver(Driver newDriver)  {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        if(driver!=null&&getShippingDate()!=null&getShippingStartTime() !=null && driver != newDriver){
            ShiftController.getInstance().removeDriverFromShift(driver.getId(),getShippingDate(),getShippingStartTime());
            ShiftController.getInstance().addDriverNeededToShift(getShippingDate(),getShippingStartTime());
        }
        driver = newDriver;
        if(driver!=null){
            ShiftController.getInstance().addDriverToShift(newDriver.id,shippingDate,shippingStartTime);
            ShiftController.getInstance().removeDriverNeededToShift(shippingDate,shippingStartTime);
        }
        checkAndSetErrorStatus();
    }



    @Override
    public Driver getDriver() {
        return driver;
    }

    public ShippingZone getZone(){
        return zone;
    }

    public void setZone(ShippingZone zone) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        this.zone = zone;
    }

    @Override
    public void setSource(Site newSource) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.setSource(newSource);
        checkAndSetErrorStatus();
    }

    @Override
    public Site getSource() {

        return !hasSource() ? null :  invManager.getSite(0);
    }


    @Override
    public boolean setSourceContact(String ContactName, String ContactPhone) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        boolean ans =  invManager.getSite(0).setContactName(ContactName) && invManager.getSite(0).setContactPhone(ContactPhone);
        checkAndSetErrorStatus();
        return ans;
    }
    public boolean setSourceName(String name) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        boolean ans = invManager.getSite(0).setName(name);
        checkAndSetErrorStatus();
        return ans;
    }
    public boolean setSourceAddress(String address) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        boolean ans = invManager.getSite(0).setAddress(address);
        checkAndSetErrorStatus();
        return ans;
    }
    public boolean setSourceContactName(String ContactName) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        boolean ans = invManager.getSite(0).setContactName(ContactName);
        checkAndSetErrorStatus();
        return ans;
    }
    public boolean setSourceContactPhone(String ContactPhone) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        boolean ans = invManager.getSite(0).setContactPhone(ContactPhone);
        checkAndSetErrorStatus();
        return ans;
    }



    public String toShortStringDestinations(){
        return invManager.toShortString();
    }
    public String toStringDestinations(){
        return invManager.toString();
    }

    @Override
    public void addDestination(Site newDest) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addSite(newDest);
    }

    public Site addNewDestination(Site newDest) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addSite(newDest);
        checkAndSetErrorStatus();
        return newDest;
    }

    public List<Driver> getMatchingDrivers(LocalDate date, IDriver.LicenceType type, boolean tempControl,LocalTime hour){
        return ShiftController.getInstance().availableDrivers(type,tempControl,date,hour);
    }

    public void requestDriver(){
        ShiftController.getInstance().addDriverNeededToShift(getShippingDate(),getShippingStartTime());
    }

    public void cancelDriverRequest(LocalDate date,LocalTime lc){
        ShiftController.getInstance().removeDriverNeededToShift(date,lc);
    }
    @Override
    public void removeDestination(Site toRemove) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.removeSite(toRemove);
        setChanged();
        notifyObservers(toRemove);
        checkAndSetErrorStatus();
    }
    public boolean removeUnViableDestination(){
        return invManager.removeUnViableDestination();
    }

    public int addEmptyDestination(){
       int index =  invManager.getShippingOrder().size()-1;
       invManager.addSite(new Site());
       return index;
    }
    @Override
    public void editDestinationInfo(int id,String name, String address, String contactName, String contactPhone) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.getSite(id).setAllInfo(name,address,contactName,contactPhone);
        checkAndSetErrorStatus();
    }

    public void clearItemsForDestination(int destId){
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.clearDestinationDelivery(invManager.getSite(destId));
        checkAndSetErrorStatus();
    }
    @Override
    public boolean addItem(int destId, String itemName, int amount, double itemWeight) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addItemPickUp(invManager.getSite(destId),itemName,amount,itemWeight);
        checkAndSetErrorStatus();
        return true;
    }
    public boolean addExistingItem(int destId, String itemName, int amount) throws Exception {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addExistingItemPickUp(invManager.getSite(destId),itemName,amount);
        checkAndSetErrorStatus();
        return true;
    }


    @Override
    public boolean removeItem(int destId, String itemName) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.removeItem(invManager.getSite(destId),itemName);
        checkAndSetErrorStatus();
        return true;
    }
    public boolean removeItem(String itemName) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.removeItemWithoutChecks(itemName);
        checkAndSetErrorStatus();
        return true;
    }



    @Override
    public boolean isTotalWeightOK() {
        return invManager.isWeightOKThroughAll(truck.getMaxCarryWeight()) < 0;
    }

    @Override
    public boolean isDriverCanDrive(IDriver driver) {
        if(driver == null | truck ==null ){
            return false;
        }
        if (invManager.isWeightOKThroughAll(driver.getLicenceType().getWeightLimit() - truck.getWeight()) > 0){
            return false;
        }
        if(truck.isTempratureControlled() && !driver.isCanDriveTempControlled()){
            return false;
        }
        return true;
    }

    public void setZoneFromString(String zoneFromString) {
        switch (zoneFromString){
            case "S":
                zone = ShippingZone.South;
                break;
            case "N":
                zone = ShippingZone.North;
                break;
            case "E":
                zone = ShippingZone.East;
                break;
            case "W":
                zone = ShippingZone.West;
                break;
            case "C":
                zone = ShippingZone.Center;
                break;
            default:
                throw new IllegalArgumentException("Invalid string to get zone");
        }
        checkAndSetErrorStatus();
    }

    private static ShippingZone getZoneFromString(String zoneFromString) {
        switch (zoneFromString.toLowerCase()){
            case "south":
                return ShippingZone.South;
            case "north":
                return ShippingZone.North;
            case "east":
                return ShippingZone.East;
            case "west":
                return ShippingZone.West;
            case "center":
                return ShippingZone.Center;
            default:
                throw new IllegalArgumentException("Invalid string to get zone");
        }
    }

    @Override
    public boolean advanceShipment()  {
        status = status.advanceShipmentStatus();
        return true;
    }


    @Override
    public ShipmentStatus getStatus() {
        return status;
    }

    public Site getDestination(int dId) {
        return invManager.getSite(dId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final String nullVal = "Unassigned";
        String dName  = nullVal;
        if (driver!= null){
            dName = driver.name;
        }
         return String.format("ID: %d\t Zone: %s\t date:%s\t driver:%s\t status: %s\n"

                ,id,zone.toString(),Objects.toString(getShippingDate(),nullVal), dName,Objects.toString(getStatus(),nullVal));
    }
    public String fullInfoString(){
        final String nullVal = "***Unassigned***";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Shipment Id: %d\nStatus: %s\n", id,status));
        sb.append(String.format("Date and start time: %s\t at %s\n",
                Objects.toString(shippingDate,nullVal),Objects.toString(shippingStartTime,nullVal)));
        sb.append("Zone:\t" + zone.toString()+"\n");
        if (truck==null){
            sb.append("Truck : " +nullVal +"\n");
        }
        else{
            sb.append(String.format("Truck: %s \t Plate number: %s\tMax Carry Weight %1.3fkg\n",
                    truck.getModel(),truck.getLicenceNum(),truck.getMaxCarryWeight()));
            if(!isTotalWeightOK()){
                sb.append("***WARNING:current delivery weight is greater than chosen truck max carry weight!***\n");
            }
        }
        sb.append(String.format("Driver: %s\n",Objects.toString(driver,nullVal)));
        if(driver!=null && !isDriverCanDrive(driver)){
            sb.append("***WARNING:current driver is not allowed to carry out this delivery!***\n");
        }
        String sourceInfo = nullVal;
        double maxTruck = truck!=null ?truck.getMaxCarryWeight() :  0;
        double truckWeight = truck!=null ?truck.getWeight() :  0;
        double maxDriver = driver!=null ? driver.getMaxDriveWeight() : 0;
        if (invManager.getShippingOrder().size() > 0 && invManager.getSite(0) != null){
            sourceInfo = invManager.siteShippingToStringWithInfo(invManager.getSite(0),
                    maxTruck, maxDriver-truckWeight);
        }
        sb.append(String.format("Source: %s\n",sourceInfo));
        sb.append("Destinations:\n");
        for(Site d: invManager.getShippingOrder()){
            if (!invManager.isSiteSource(d)){
                sb.append(invManager.siteShippingToStringWithInfo(d, maxTruck, maxDriver-truckWeight));
            }
            if(invManager.getShippingOrder().size()==0){
                sb.append("\nWARNING-No sites are assigned to this shipment!\n");
            }
        }
        if(status.isErrorStatus()){
            sb.append("\nWARNING: there are errors in current shipment, cannot launch shipment!\n");
        }
        return sb.toString();
    }

    /**
     * checks and sets status to an error status
     * @return true - if status is an error status
     * */
    public boolean checkAndSetErrorStatus(){
        if (status == ShipmentStatus.Done || status == ShipmentStatus.InProgress){
            return false;
        }
        if(!isShipmentDataValid()){
            if(status!=ShipmentStatus.ShipmentError){
                status = status.setShipmentError();
                setChanged();
                notifyObservers(status);
            }
            return true;
        }
        if(!invManager.areOtherSitesThanSource()){
            status = status.setSiteError();
            setChanged();
            notifyObservers(status);
            return true;
        }
        if(!invManager.areAllItemsShippedInOrder()){
            if(status!=ShipmentStatus.ItemError){
                status = status.setItemError();
                setChanged();
                notifyObservers(status);
            }
            return true;
        }
        if(!isTotalWeightOK()){
            if(status!=ShipmentStatus.TruckError){
                status = status.setTruckError();
                setChanged();
                notifyObservers(status);
            }
            return true;
        }
        if(!isDriverCanDrive(driver)){
            if(status!=ShipmentStatus.DriverError){
                status = status.setDriverError();
                setChanged();
                notifyObservers(status);
            }
            return true;
        }
        for (Site s: invManager.getShippingOrder()){
            if(getShippingDate()!=null&&s.getType()== Site.SiteType.Branch&&!ShiftController.getInstance().isStoreKeeperInDay(s.name ,getShippingDate())){
                status = status.setSiteError();
                setChanged();
                notifyObservers(status);
                return true;
            }
        }
        status = ShipmentStatus.StandBy;
        setChangedAndNotify();
        return false;
    }

    public boolean isShipmentDataValid(){
        if (shippingDate == null || shippingStartTime == null || driver == null || truck ==null) {
            return false;
        }
        return invManager.isValidShipmentData();
    }



    public int getDestinationsAmount() {
        return invManager.shippingOrder.size();
    }


    int getDestinationIndex(Site d){
        return invManager.getShippingOrder().indexOf(d);
    }

    public boolean isShipmentInProgress() {
        return status == ShipmentStatus.InProgress;
    }
    public boolean isShipmentDone() {
        return status == ShipmentStatus.Done;
    }
    public boolean isShipmentError() {
        return status.isErrorStatus();
    }


    private void setChangedAndNotify(){
        setChanged();
        notifyObservers();
    }

    public String getSiteString(int order){
        return invManager.siteShippingToStringWithInfo(getDestination(order),truck.getMaxCarryWeight(),driver.getMaxDriveWeight()-truck.getWeight());
    }

    public void callRemoveDriverFromShift(LocalDate date, LocalTime time){
        ShiftController.getInstance().removeDriverFromShift(driver.getId(),date,time);
        ShiftController.getInstance().addDriverNeededToShift(shippingDate,shippingStartTime);
    }

    public void callAddDriverToShift(LocalDate date, LocalTime time){
        ShiftController.getInstance().addDriverToShift(driver.getId(),date,time);
    }

    public double getShipmentTimeAsDouble(){
        double res = 0;
        Site.Coordinate prev = null;
        if(invManager.shippingOrder.size() <1){
            return 0;
        }
        Site.Coordinate origin = invManager.getShippingOrder().get(0).getLocation();
        for (Site s : invManager.getShippingOrder()){
            if (prev != null){
                res += s.getLocation().getTimeToOtherAsDouble(prev);
            }
            prev = s.getLocation();
        }
        res += prev.getTimeToOtherAsDouble(origin);
        return res;
    }
    public LocalTime getShipmentTime(){
        double time  = getShipmentTimeAsDouble();
        int hours = 0;
        while (time > 1){
            time--;
            hours++;
        }
        time *= 60;
        return LocalTime.of(hours,(int)time);
    }

    public IDriver.LicenceType getRequiredLicence(){
        double weight = truck == null ? 0 : truck.getWeight();
        return IDriver.LicenceType.getRequiredLicenceByWeight(invManager.getMaxShipmentWeight(weight));
    }


    public boolean hasDestination(Site s) {
        for (Site site: invManager.getShippingOrder()){
            if (site.equals(s)){
                return true;
            }
        }
        return false;
    }

    public void updateSiteInfo(Site newSite, String oldName){
        invManager.updateSite(newSite,oldName);
    }

    public LocalTime getTimeToSite(Site s){
        if (!invManager.shippingOrder.contains(s)){
            throw new IllegalArgumentException("Site is not in shipment");
        }
        LocalTime startTime = shippingStartTime;
        double timeToSite = invManager.getShippingDriveTimeToSite(s);
        int hours = 0;
        while(timeToSite > 1){
            hours++;
            timeToSite--;
        }
        int fHours = startTime.getHour()+hours;
        int fMinutes = startTime.getMinute() + (int)(timeToSite * 60);
        if(fMinutes>59){
            fMinutes-=60;
            fHours +=1;
        }
        if(fHours > 23){
            fHours -= 24;
        }
        return LocalTime.of(fHours,fMinutes);
    }

    public String getSiteDeliveryString(Site s){
        if (!invManager.shippingOrder.contains(s)){
            throw new IllegalArgumentException("Site is not in shipment");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Delivery to " + s.getName() + ": \n");
        sb.append("Expected arrival time: \t" +getTimeToSite(s).toString()+"\n\n");
        sb.append(invManager.siteItemsToString(s));
        return sb.toString();
    }

    public void addNewItem(String itemName, double weight) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addDeliverableItem(itemName,weight);
        checkAndSetErrorStatus();
    }
    private void initAddItem(String itemName, double weight){
        invManager.addDeliverableItem(itemName,weight);
        checkAndSetErrorStatus();
    }

    public void addItemDrop(int destOrder, String item, int amount) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addItemDrop(destOrder,item,amount);
        checkAndSetErrorStatus();
    }

    public void addItemPickUp(int destOrder, String item, int amount) {
        if(!status.isViableToEdit()){
            throw new InvalidShipmentEditingException("",status);
        }
        invManager.addItemPickUp(destOrder,item,amount);
        checkAndSetErrorStatus();
    }

    public String getItemsString() {
        return invManager.ItemsToString();
    }

    public boolean isSourceNull() {
        return invManager.isSourceNull();
    }

    public void addItemsFromDTO(List<ItemAndWeightDTO> weights) {
        for(ItemAndWeightDTO item: weights){
            if(Integer.valueOf(item.shipmentId) == this.id){
                initAddItem(item.item,item.weight);
            }
        }
    }

    public void addDestinationsFromDTO(HashMap<String, Integer> orders) {
        Hashtable<Integer,Site> siteOrder = new Hashtable<>();
        int max = -1;
        for (String siteId: orders.keySet() ){
            Site s = SiteManager.getInstance().getSite(Integer.valueOf(siteId));
            int index = orders.get(siteId);
            siteOrder.put(index,s);
            if(index > max){
                max = index;
            }
        }
        ArrayList<Site> shippingOrder = new ArrayList<>();
        for(int i = 0;i<max+1;i++){
            shippingOrder.add(siteOrder.get(i));
        }
        invManager.setOrder(shippingOrder);
    }

    public void addItemsFromDTO(Site s,HashMap<String, Integer> dests) {
        int order = invManager.getShippingOrder().indexOf(s);
        for (String item: dests.keySet()){
            invManager.addItem(order,item,dests.get(item));
        }
    }

    public String getItemsStringWithAmount() {
        return invManager.getItemsStringWithAmount();
    }

    public boolean hasSource() {
         return invManager.shippingOrder.size()>0 && invManager.isSiteSource(invManager.shippingOrder.get(0));
    }
}
