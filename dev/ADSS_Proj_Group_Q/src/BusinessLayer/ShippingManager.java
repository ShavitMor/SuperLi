package BusinessLayer;

import DataLayer.DTOs.ItemAndWeightDTO;
import DataLayer.DTOs.ShipmentDTO;
import DataLayer.DTOs.ShipmentDestAndAmountsDTO;
import DataLayer.DTOs.siteOrderDTO;
import DataLayer.ItemAndWeightMapper;
import DataLayer.ShipmentDestAndAmountsMapper;
import DataLayer.ShipmentMapper;
import DataLayer.siteOrderMapper;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShippingManager implements Observer {
    private static int currId = 1;
    ArrayList<Shipment> errorShipment;
    ArrayList<Shipment> activeShipments;
    ArrayList<Shipment> completedShipments;
    private TruckManager tm;
    Hashtable<Integer,Integer> shipmentIdToList;
    private static ShippingManager instance;

    private static ShipmentMapper sMapper = new ShipmentMapper();
    private static ShipmentDestAndAmountsMapper dMapper = new ShipmentDestAndAmountsMapper();
    private static ItemAndWeightMapper wMapper = new ItemAndWeightMapper();
    private static siteOrderMapper soMapper = new siteOrderMapper();


    final static int errListId = 1;
    final static int activeListId = 2;
    final static int compListId = 3;
    final String nullVal = "Unassigned";


    public ShippingManager(){
        errorShipment = new ArrayList<>();
        activeShipments = new ArrayList<>();
        completedShipments = new ArrayList<>();
        shipmentIdToList = new Hashtable<>();
        tm = TruckManager.getInstance();
        instance=this;
    }
    public static ShippingManager getInstance(){
        if(instance==null)
            instance=new ShippingManager();
        return instance;
    }

    private static String toStringShipmentTable(ArrayList<Shipment> toString){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < toString.size(); i++) {
            sb.append(String.format("Entry number: %d\t\n %s",i,toString.get(i).toString()));
        }
        return sb.toString();
    }
    private String toStringShipmentTableOfStatus(ArrayList<Shipment> toString,IShipment.ShipmentStatus status){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < toString.size(); i++) {
            if(getShipment(i).status ==IShipment.ShipmentStatus.DriverError)
            sb.append(String.format("Entry number: %d\t\n %s",i,toString.get(i).toString()));
        }
        return sb.toString();
    }

    public String errorShipmentsToString(){
        return toStringShipmentTable(errorShipment);
    }
    public String activeShipmentsToString(){
        return toStringShipmentTable(activeShipments);
    }
    public String compeletedShipmentsToString(){
        return toStringShipmentTable(completedShipments);
    }

    public Shipment addNewShipment(){
        Shipment shipment =new Shipment(currId);
        shipmentIdToList.put(shipment.id,errListId);
        shipment.addObserver(this);
        errorShipment.add(shipment);
        switchShipmentToMatchingList(shipment);
        insertNewShipment(shipment);
        currId++;
        return shipment;
    }

    public boolean deleteShipment(int id) throws Exception {
        Shipment shipment = getShipment(id);
        if (!isShipmentDeleteable(id)){
            throw new IShipment.InvalidShipmentEditingException("",shipment.status);
        }
        if(activeShipments.remove(shipment) || errorShipment.remove(shipment)){
            shipmentIdToList.remove(shipment);
            sMapper.removeShipment(""+id);
            return true;
        }
        return false;
    }
    public Shipment getShipment(int id) {
        if (id<=0){
            throw new IllegalArgumentException("id cannot be 0 or lower");
        }
        for (Shipment s: errorShipment){
            if (s.id == id){
                return s;
            }
        }
        for (Shipment s: activeShipments){
            if (s.id == id){
                return s;
            }
        }
        for (Shipment s: completedShipments){
            if (s.id == id){
                return s;
            }
        }
        throw new IllegalArgumentException("no shipment with id: "+id + " was found");
    }


    public int getAmountOfCompletedShipments(){
        return completedShipments.size();
    }
    public int getAmountOfActiveShipments(){
        return activeShipments.size();
    }
    public int getErrorsAmount(){
        return errorShipment.size();
    }

    private boolean switchShipmentToMatchingList(Shipment ship){
        sMapper.updateShipment(String.valueOf(ship.id),"status", ship.getStatus().toString());
        if (ship.status == IShipment.ShipmentStatus.Done){
            if(!completedShipments.contains(ship)){
                return activeShipments.remove(ship) && completedShipments.add(ship);
            }
        }
        int locationIndex = checkShipmentList(ship);
        switch (ship.getStatus()){
            case DriverError,TruckError,ShipmentError,SiteError,ItemError:
                return switchShipmentToListByIndex(locationIndex,1,ship);
            case Done:
                return switchShipmentToListByIndex(locationIndex,3,ship);
            default:
                return switchShipmentToListByIndex(locationIndex,2,ship);
        }
    }

    private int checkShipmentList(Shipment ship){
        if (errorShipment.contains(ship)){
            return errListId;
        }
        if (activeShipments.contains(ship)){
            return activeListId;
        }
        if (completedShipments.contains(ship)){
            return compListId;
        }
        return 0;
    }

    private boolean switchShipmentToListByIndex(int source, int dest,Shipment ship){
        if (source == dest){
            return true;
        }
        shipmentIdToList.replace(ship.id,source,dest);
        return removeFromShipmentListByIndex(source,ship) && insertToShipmentListByIndex(dest,ship);
    }

    private boolean insertToShipmentListByIndex(int index,Shipment ship){
        switch (index){
            case errListId:
                return errorShipment.add(ship);
            case  activeListId:
                return activeShipments.add(ship);
            case  compListId:
                return completedShipments.add(ship);
        }
        return false;
    }
    private boolean removeFromShipmentListByIndex(int index,Shipment ship){
        switch (index){
            case errListId:
                return errorShipment.remove(ship);
            case activeListId:
                return activeShipments.remove(ship);
            case  compListId:
                return completedShipments.remove(ship);
        }
        return false;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an {@code Observable} object's
     * {@code notifyObservers} method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Shipment){
            if(arg instanceof IShipment.ShipmentStatus ){
                update((Shipment) o);
            }
            if(arg instanceof Site){
                update((Shipment) o,(Site)arg);
            }
            update((Shipment) o);
        }
        if (o instanceof Truck){
            if (arg == null){
                updateDelete((Truck)o);
            }
            update((Truck) o,(String) arg);
        }
        if(o instanceof Driver){
            if (arg == null){
                updateDelete((Driver)o);
            }
            update((Driver) o,(String) arg);
        }
        if(o instanceof Site){
            if (arg == null){
                updateDelete((Site)o);
            }
            update((Site) o,(String) arg);
        }

    }

    private void updateDelete(Truck d){
        for (Shipment s:errorShipment){
            if(s.getTruck().equals(d)){
                s.setTruck(null);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"truckId",s.getTruck().getId());
            }
        }
        for (Shipment s:activeShipments){
            if(s.getTruck().equals(d) && !s.isShipmentInProgress()){
                s.setTruck(null);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"truckId",s.getTruck().getId());
            }
        }
    }
    private void updateDelete(Driver d){
        for (Shipment s:errorShipment){
            if(s.getDriver().equals(d)){
                s.setDriver(null);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"driverId",s.getDriver().getId());

            }
        }
        for (Shipment s:activeShipments){
            if(s.getDriver().equals(d) && !s.isShipmentInProgress()){
                s.setDriver(null);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"driverId",s.getDriver().getId());

            }
        }
    }
    private void updateDelete(Site s){
        for (Shipment ship: errorShipment){
            int order = ship.invManager.getShippingOrder().indexOf(s);
            if(ship.hasDestination(s)){
                ship.removeDestination(s);
                dMapper.removeShipmentDest(String.valueOf(ship.getId()),String.valueOf(order));
            }
        }
        for (Shipment ship: activeShipments){
            int order = ship.invManager.getShippingOrder().indexOf(s);
            if(ship.hasDestination(s)){
                ship.removeDestination(s);
                dMapper.removeShipmentDest(String.valueOf(ship.getId()),String.valueOf(order));
            }
        }
    }

    private boolean update(Shipment ship, Site toRemove){
       return soMapper.removeSite(String.valueOf(ship.getId()),String.valueOf(SiteManager.getInstance().getSiteIDFromName(toRemove.getName())));

    }


    private void updateSitesOrder(Shipment ship){
        for (Site s : ship.invManager.getShippingOrder()){
            soMapper.updateSite(String.valueOf(ship.getId()),
                    String.valueOf(SiteManager.getInstance().getSiteIDFromName(s.getName())),ship.invManager.getShippingOrder().indexOf(s));
        }
    }

    private void update(Site site, String oldName){
        for (Shipment s:errorShipment){
            if(s.hasDestination(site) ){
                s.updateSiteInfo(site,oldName);
                switchShipmentToMatchingList(s);
            }
        }
        for (Shipment s:activeShipments){
            if(s.hasDestination(site) ){
                s.updateSiteInfo(site,oldName);
                switchShipmentToMatchingList(s);
            }
        }
    }

    private void update(Truck t,String oldLnum){
        for (Shipment s:errorShipment){
            if(s.getTruck().getLicenceNum().equals(oldLnum) ){
                s.setTruck(t);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"truckId",s.getTruck().getId());
            }
        }
        for (Shipment s:activeShipments){
            if(s.getTruck().getLicenceNum().equals(oldLnum) && !s.isShipmentInProgress()){
                s.setTruck(t);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"truckId",s.getTruck().getId());
            }
        }
    }
    private void update(Driver d, String oldLnum){
        for (Shipment s:errorShipment){
            if(s.getDriver().getLicenceNumber().equals(oldLnum)){
                s.setDriver(d);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"driverId",s.getDriver().getId());
            }
        }
        for (Shipment s:activeShipments){
            if(s.getDriver().getLicenceNumber().equals(oldLnum) && !s.isShipmentInProgress()){
                s.setDriver(d);
                switchShipmentToMatchingList(s);
                sMapper.updateShipment(String.valueOf(s.id),"driverId",s.getDriver().getId());
            }
        }
    }
    private void update(Shipment ship){
        sMapper.updateShipment(String.valueOf(ship.id),"status",getShipment(ship.id).getStatus().toString());
        switchShipmentToMatchingList(ship);
    }

    public int getCompletedShipmentId(int parseInt) {
        return completedShipments.get(parseInt).getId();
    }


    public String stringifyCompletedShipmentsByDate(LocalDate date){
        StringBuilder sb = new StringBuilder();
        for(Shipment s: completedShipments){
            if (s.shippingDate == date){
                sb.append("Entry number: " + getShipmentIndexInList(s,completedShipments )+"\n");
                sb.append(s.toString());
            }
        }
        return sb.toString();
    }
    public String stringifyActiveShipmentsByDate(LocalDate date){
        StringBuilder sb = new StringBuilder();
        for(Shipment s: activeShipments){
            if (s.shippingDate == date){
                sb.append("Entry number: " + getShipmentIndexInList(s,activeShipments )+"\n");
                sb.append(s.toString());
            }
        }
        return sb.toString();
    }

    public String stringifyErrorShipmentsByDate(LocalDate date){
        StringBuilder sb = new StringBuilder();
        for(Shipment s: errorShipment){
            if (s.shippingDate == date){
                sb.append("Entry number: " + getShipmentIndexInList(s,errorShipment )+"\n");
                sb.append(s.toString());
            }
        }
        return sb.toString();
    }

    private int getShipmentIndexInList(Shipment shipment, List<Shipment> list){
        return list.indexOf(shipment);
    }

    public String getFullInfoOfShipmentById(int id) throws Exception {
        return getShipment(id).fullInfoString();
    }

    public boolean isShipmentDeleteable(int shipmentId) throws Exception {
        return getShipment(shipmentId).status.isViableToEdit();
    }
    public void setShipmentDate(int id, LocalDate date)  {
        getShipment(id).setShippingDate(date);
        sMapper.updateShipment(String.valueOf(id),"date", Date.valueOf(getShipment(id).getShippingDate()));

    }
    public void setShipmentTime(int id, LocalTime parse)  {
        getShipment(id).setShippingStartTime(parse);
        sMapper.updateShipment(String.valueOf(id),"StartTime", Time.valueOf(getShipment(id).getShippingStartTime()));

    }
    public String stringifyShippingDateTime(int id){
        Shipment s = getShipment(id);
        return String.format("Date: %s  \t Time: %s",
                Objects.toString(s.getShippingDate(),nullVal),Objects.toString(s.shippingStartTime,nullVal));
    }
    public String getDriverString(int id) {
        if (getShipment(id).getDriver() == null){
            return nullVal;
        }
        return getShipment(id).getDriver().toStringShort();
    }

    public String getTruckString(int id){
        return Objects.toString(getShipment(id).getTruck(),nullVal);
    }

    public boolean isDriverCanDrive(int id,IDriver driver){
        return getShipment(id).isDriverCanDrive(driver);
    }



    public void setShipmentTruck(int id, int truckIndex) {
        Truck truck = tm.getTruckByID(truckIndex);
        Shipment shipment = getShipment(id);
        truck.addObserver(this);
        shipment.setTruck(truck);
        sMapper.updateShipment(""+id, "truckId",getShipment(id).getTruck().getId());
    }

    public void removeShipmentTruck(int sId){
        getShipment(sId).setTruck(null);
    }

    public int getAmountOfTrucks() {
        return tm.getAmountOfTrucks();
    }

    public String getTruckList() {
        return tm.toString();
    }

    public void setTruckByLicenceNumber(int id, String truckLicence) {
        setShipmentTruck(id,tm.getTruckIdByLicence(truckLicence));
    }

    public String getSourceString(int id){
        if(getShipment(id).isSourceNull()){
            return "Unassigned";
        }
        return getShipment(id).getSource().toString();
    }

    public void setShipmentSource(int id, String cName) {
        Site s = SiteManager.getInstance().getSite(cName);

        if(getShipment(id)!= null && ((getShipment(id).hasSource() && s.getName() != getShipment(id).getSource().getName())) || !getShipment(id).hasSource() ) {
            getShipment(id).invManager.setSource(s);
            s.addObserver(this);
            soMapper.insert(String.valueOf(id), String.valueOf(SiteManager.getInstance().getSiteIDFromName(cName)), 0);
        }
        checkAndSetStatusErrors(id);
    }
    public boolean addShipmentDestination (int id, String cName)  {
        Site s = SiteManager.getInstance().getSite(cName);
        getShipment(id).invManager.addSite(s);
        int order = getShipment(id).invManager.getShippingOrder().size();
        s.addObserver(this);
        boolean b = soMapper.insert(String.valueOf(id),String.valueOf(SiteManager.getInstance().getSiteIDFromName(cName)),order-1);;
        checkAndSetStatusErrors(id);
        return b;

    }


    public int getDestinationsAmount(int sId){
        return getShipment(sId).getDestinationsAmount();
    }

    public String getShipmentDestinationsString(int sId){
        return getShipment(sId).toStringDestinations();
    }


    public String getShipmentDestinationsShortString(int sId){
        return getShipment(sId).toShortStringDestinations();
    }

    public Site getShipmentDestination(int sId, int dId)  {
        return getShipment(sId).getDestination(dId);
    }

    public String getShipmentDestinationString(int sId, int dId){
        return getShipment(sId).getDestination(dId).toString();
    }


    public void deleteDestination(int sId, int order){
        Site s = getShipment(sId).getDestination(order);
        getShipment(sId).invManager.removeSite(s);
        int size = getShipment(sId).invManager.getShippingOrder().size();
        soMapper.removeSite(String.valueOf(sId),String.valueOf(SiteManager.getInstance().getSiteIDFromName(s.getName())));
        for (Site x :getShipment(sId).invManager.getShippingOrder()){
            soMapper.update(String.valueOf(sId),"order",getShipment(sId).invManager.getShippingOrder().indexOf(x));
        }
        checkAndSetStatusErrors(sId);
    }

    public boolean isTruckOverweight(int sId){
        return getShipment(sId).isTotalWeightOK();
    }

    public boolean advanceShipmentStatus(int sId) {
        getShipment(sId).advanceShipment();
        switchShipmentToMatchingList(getShipment(sId));
        return sMapper.updateShipment(String.valueOf(sId), "status", getShipment(sId).getStatus().toString());
    }
    public boolean isShipmentInProgress(int sId){
        return getShipment(sId).isShipmentInProgress();
    }
    public boolean isShipmentDone(int sId){
        return getShipment(sId).isShipmentDone();
    }
    public boolean isShipmentError(int sId){
        return getShipment(sId).isShipmentError();
    }


    public void clearDestinationItems(int sId, int destOrder){
        getShipment(sId).clearItemsForDestination(destOrder);
        dMapper.removeShipmentDest(String.valueOf(sId), String.valueOf(destOrder));
    }



    public boolean removeItem(int sId, int destOrder, String itemName) throws Exception{
        boolean b = getShipment(sId).removeItem(destOrder,itemName);
       return dMapper.removeShipmentDestItem(String.valueOf(sId), String.valueOf(destOrder), itemName);

    }

    public int addCleanShipment() {
        return addNewShipment().getId();
    }

    public int getErrorShipmentId(int parseInt) {
        return errorShipment.get(parseInt).getId();
    }
    public int getActiveShipmentId(int parseInt) {
        return activeShipments.get(parseInt).getId();
    }

    public void setShipmentZone(int sId, String userIn)  {
        getShipment(sId).setZoneFromString(userIn);
        sMapper.updateShipment(String.valueOf(sId),"zone", getShipment(sId).getZone().toString());
    }


    public boolean validateDestNameAndAddress(int parseInt, int parseInt1) {
        return getShipment(parseInt).getDestination(parseInt1).isValidSite();
    }

    public String destinationItemsToString(int parseInt, int parseInt1) {
        return getShipment(parseInt).getSiteString(parseInt1);
    }

    public boolean isDestinationItem(int parseInt, int parseInt1, String itemName) {
        return getShipment(parseInt).invManager.isShipmentContainsItem(itemName);
    }

    public String itemInfoToString(int parseInt, int parseInt1, String itemName) {
        return getShipment(parseInt).invManager.shipmentItemString(itemName);
    }



    public boolean setItemWeight(int sId, String itemName, double parseDouble) {
        getShipment(sId).invManager.setItemWeight(itemName,parseDouble);
        wMapper.updateItemsWeight(itemName, "weight", parseDouble);
        return true;
    }

    public void setShipmentDriver(int parseInt, String driverID) {
        getShipment(parseInt).setDriver(EmployeeController.getInstance().getDriver(driverID));
        sMapper.update(String.valueOf(parseInt),"driverId", driverID);
    }

    private boolean insertShipment(Shipment s){
        return sMapper.insert(String.valueOf(s.getId()),s.getShippingDate(),s.getShippingStartTime(), s.getTruck().getLicenceNum(),
                s.getDriver().getId(),s.getZone().toString(),s.status.toString() );
    }
    private boolean insertCleanShipment(Shipment s){
        return sMapper.insert(String.valueOf(s.getId()),LocalDate.of(0,1,1),LocalTime.of(0,0), null,
                null,s.getZone().toString(),s.status.toString() );
    }
    private boolean insertNewShipment(Shipment s){
        return sMapper.insert(String.valueOf(s.getId()),null,null, null,
                null,s.getZone().toString(),s.status.toString());
    }
    public List<Shipment> getShipmentAmountForSiteOnDate(Site site, LocalDate date){
        List<Shipment> res = new ArrayList<>();
        for (Shipment s: activeShipments){
            if (s.invManager.getShippingOrder().contains(site) && s.getShippingDate().equals(date)){
                res.add(s);
            }
        }
        return res;
    }
    public double getMonthlyDriveHoursOfDriver(Driver d,int month){
        double workHours = 0;
        for (Shipment s: completedShipments){
            if (s.getDriver()==d && s.getShippingDate().getMonthValue() == month){
                workHours += s.getShipmentTimeAsDouble();
            }
        }
        return workHours;
    }

    public List<Driver> getAvailableDriversList(Shipment s){
        try{
            return ShiftController.getInstance().availableDrivers(s.getRequiredLicence(),s.getTruck().isTempratureControlled(),s.getShippingDate(),s.getShipmentTime());
        }catch (Exception ex){}
        return new ArrayList<>();
    }
    public String getAvailableDriverId(int entryNum, Shipment s){
        List<Driver> drivers = getAvailableDriversList(s);
        return drivers.get(entryNum).getLicenceNumber();
    }
    public void setShipmentDriverFromListByEntry(int entryNum, Shipment s){
        List<Driver> drivers = getAvailableDriversList(s);
        setShipmentDriver(s.id,drivers.get(entryNum).getId());
    }
    public void clearForTest(){
        sMapper.deleteAll();
        dMapper.deleteAll();
        wMapper.deleteAll();
        soMapper.deleteAll();
        currId = 1;
    }
    public void updateShipmentSiteError(LocalDate lc,Site site){
        for (int i=1;i<this.currId;i++) {
            getShipment(i).checkAndSetErrorStatus();
        }
    }

    public LocalTime getArrivalTimeToBranch(Shipment ship, Site site){
        return ship.getTimeToSite(site);
    }

    public String getSiteDeliveryStringOnDate(Site s, LocalDate date){
        for (Shipment ship: activeShipments){
            if (ship.getShippingDate().equals(date) && ship.hasDestination(s)){
                return ship.getSiteDeliveryString(s);
            }
        }
        return "";
    }


    public void removeDriver(int sId) {
        setShipmentDriver(sId,null);
    }

    public String getShipmentSourceAndDestinationsInfo(int sId) {
        StringBuilder sb = new StringBuilder();
        Shipment ship = getShipment(sId);
        double weight = ship.getTruck() != null ? ship.getTruck().getMaxCarryWeight() : 0;
        sb.append(ship.invManager.ToStringShort(weight));
        return sb.toString();
    }

    public String addDestGetOrder(int sId, int destId) {
        Site s = SiteManager.getInstance().getSite(destId);
        addShipmentDestination(sId,s.getName());
        checkAndSetStatusErrors(sId);
        return ""+getShipment(sId).invManager.shippingOrder.indexOf(s);
    }

    public String getShipmentShortDestinationsOnlyString(int sId) {
        StringBuilder sb = new StringBuilder();
        Shipment ship = getShipment(sId);
        for (int i = 1; i < ship.invManager.getShippingOrder().size();i++){
            Site s = ship.invManager.getShippingOrder().get(i);
            sb.append(ship.invManager.siteShippingToString(s));
        }
        return sb.toString();
    }

    public boolean isShipmentHasDestInOrder(int sId, int destOrder) {
        return getShipment(sId).invManager.getShippingOrder().size() > destOrder && destOrder > 0;
    }

    public void addNewItemToShipment(int sId, String itemName, double weight) {
        getShipment(sId).addNewItem(itemName, weight);
        wMapper.insert(itemName,weight, String.valueOf(sId));
        checkAndSetStatusErrors(sId);
    }

    public void removeItemFromShipment(int sId, String toRemove) {
        boolean b = getShipment(sId).removeItem(toRemove);
        wMapper.deleteShipmentItem(String.valueOf(sId),toRemove);
        for (Site s: getShipment(sId).invManager.getShippingOrder())
        dMapper.removeShipmentDestItem(String.valueOf(sId),
                String.valueOf(getShipment(sId).invManager.getShippingOrder().indexOf(s)), toRemove);
        checkAndSetStatusErrors(sId);
    }

    public void clearShipmentItems(int sId) {
        for(String item: getShipment(sId).invManager.itemToWeightTable.keySet()){
            removeItemFromShipment(sId,item);
        }

    }

    public String getDestItemsString(int sId, int destOrder) {
        double max = getShipment(sId).getTruck() == null ? 0 : getShipment(sId).getTruck().getMaxCarryWeight();
        return getShipment(sId).invManager.getFullSiteString(destOrder,max);
    }

    public int getShipmentWaitingForDriverAmount() {
        int amount = 0;
        for (Shipment s: errorShipment){
            if (s.getStatus() == IShipment.ShipmentStatus.DriverError){
                amount++;
            }
        }
        return amount;
    }

    public String getShipmentWaitingForDriverString() {
        return toStringShipmentTableOfStatus(errorShipment, IShipment.ShipmentStatus.DriverError);

    }

    public String getAvailableDriversString(int sId) {
        Shipment s = getShipment(sId);
        boolean b = s.getTruck() == null  ?  false : s.getTruck().isTempratureControlled();
        List<Driver> drivers = ShiftController.getInstance().availableDrivers(
                s.getRequiredLicence(),b,s.getShippingDate(),s.getShippingStartTime());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < drivers.size(); i++) {
            sb.append(String.format("%d. %s",i,drivers.get(i).toString()));
        }
        if(drivers.size() == 0){
            return "No available drivers right now, please wait for more drivers to assign their shifts.";
        }
        return sb.toString();
    }

    public void setShipmentDriverByEntry(int sId, int driverEntryNum) {
        Shipment s = getShipment(sId);
        boolean tempControl = s.getTruck() == null ? false : s.getTruck().isTempratureControlled();
        List<Driver> drivers = ShiftController.getInstance().availableDrivers(
                s.getRequiredLicence(),tempControl,s.getShippingDate(),s.getShippingStartTime());
        setShipmentDriver(sId,drivers.get(driverEntryNum).getId());
    }

    public void setShipmentDriverById(int sId, String driverId) {
        Shipment s = getShipment(sId);
        boolean tempControl = s.getTruck() == null ? false : s.getTruck().isTempratureControlled();
        List<Driver> drivers = ShiftController.getInstance().availableDrivers(
                s.getRequiredLicence(),tempControl,s.getShippingDate(),s.getShippingStartTime());
        if(!drivers.contains(EmployeeController.getInstance().getDriver(driverId))){
            throw new IllegalArgumentException("Driver with ID " + driverId + " is not available");
        }
        setShipmentDriver(sId,driverId);
    }

    public void addItemsToDeliver(int sId, int destOrder, String item, int amount) {
        if (!getShipment(sId).invManager.isShipmentContainsItem(item)){
          throw new NoSuchElementException(String.format("Item %s is not part of this shipment, please add item to shipment",item));
        }
       if(dMapper.insertDestItemAndAmount(String.valueOf(sId),String.valueOf(destOrder),item,-amount)){
           getShipment(sId).addItemDrop(destOrder,item,amount);
       }
       else {
           throw new RuntimeException("Error adding new item!");
       }
        checkAndSetStatusErrors(sId);
    }

    public void addItemsToPickUp(int sId, int destOrder, String item, int amount) {
        if (!getShipment(sId).invManager.isShipmentContainsItem(item)){
            throw new NoSuchElementException(String.format("Item %s is not part of this shipment, please add item to shipment",item));
        }
        if(dMapper.insertDestItemAndAmount(String.valueOf(sId),String.valueOf(destOrder),item,amount)){
            getShipment(sId).addItemPickUp(destOrder,item,amount);
        }
        checkAndSetStatusErrors(sId);
    }

    public void removeItemFromDest(int sId, int destOrder, String toRemove) {
        if (!getShipment(sId).invManager.isShipmentContainsItem(toRemove)){
            throw new NoSuchElementException(String.format("Item %s is not part of this shipment",toRemove));
        }
        getShipment(sId).removeItem(destOrder,toRemove);
        dMapper.removeShipmentDestItem(String.valueOf(sId),String.valueOf(destOrder),toRemove);
        checkAndSetStatusErrors(sId);
    }

    public String getDestinationString(int sId, int destOrder) {
        Shipment ship = getShipment(sId);
        Site s =ship.invManager.getSite(destOrder);
        double maxTruck = ship.getTruck() == null ? 0 : ship.getTruck().getMaxCarryWeight();
        double maxDriver = ship.getDriver() == null ? 0 : ship.getDriver().getMaxDriveWeight();
        return ship.invManager.siteShippingToStringWithInfo(s,maxTruck,maxDriver);

    }

    public String getShipmentItems(int sId) {
        return getShipment(sId).getItemsString();
    }

    public boolean isItemInShipment(int sId, String itemName) {
        return getShipment(sId).invManager.isShipmentContainsItem(itemName);
    }

    public String getAllActiveSitesString() {
        return SiteManager.getInstance().toStringByActive(true);
    }

    public String getSiteIdByName(String siteName) {
        return "" + SiteManager.getInstance().getSiteIDFromName(siteName);
    }

    public Site getSite(int siteId) {
        return SiteManager.getInstance().getSite(siteId);
    }

    public String getLogisticCentersString() {
        return SiteManager.getInstance().toStringByType("LOGISTICCTR");
    }

    public boolean setShipmentSourceById(int shipmentId, int siteId) {
        setShipmentSource(shipmentId,SiteManager.getInstance().getSite(siteId).getName());
        return true;
    }

    public boolean isSourceNull(int sId) {
        return getShipment(sId).isSourceNull();
    }

    public String getShipmentTotalWeightReport(int sId) {
        Shipment s = getShipment(sId);
        double truckMax = s.getTruck() == null ? 0 : s.getTruck().getMaxCarryWeight();
        double driverMax = s.getDriver() == null? 0 : s.getDriver().getMaxDriveWeight();
        return s.invManager.getWeightReport(truckMax,driverMax);
    }

    public void replaceSitesOrder(int shipmentId, int destOrder1, int destOrder2) {
        if (!soMapper.switchOrder(String.valueOf(shipmentId),destOrder1,destOrder2)){
            getShipment(shipmentId).invManager.switchSitesOrder(destOrder1,destOrder2);
            checkAndSetStatusErrors(shipmentId);
        }
        throw new RuntimeException("Error while replacing sites");
    }

    public void switchShipmentDestination(int shipmentId, int destOrder, int siteId) {
       if( soMapper.updateSite(String.valueOf(shipmentId),String.valueOf(siteId),destOrder)){
           Shipment s = getShipment(shipmentId);
           s.invManager.setSiteToPlace(SiteManager.getInstance().getSite(siteId),destOrder );
           checkAndSetStatusErrors(shipmentId);
       }
        throw new RuntimeException("Error while replacing sites");
    }

    public void loadShipments() {
        int max = 0;
        List<ShipmentDTO> ships = sMapper.getShipments();
        List<ItemAndWeightDTO> weights = wMapper.getAllItemsAndWeight();
        for (ShipmentDTO s: ships){
            HashMap<String,Integer> orders = soMapper.getOrderOfShipment(s.id);
            Shipment ship = Shipment.FromDTO(s.id,s.shippingDate,s.shippingStartTime,s.status,s.zone,s.truck,s.driver);
            ship.addObserver(this);
            if (s.status.contains("Error")){
                errorShipment.add(ship);
            } else if (s.status.contains("Done")) {
                completedShipments.add(ship);
            }else {
                activeShipments.add(ship);
            }
            if(s.truck!=null){
                Truck t = TruckManager.getInstance().getTruckByID(Integer.valueOf(s.truck));
                t.addObserver(this);
            }
            if(Integer.valueOf(s.id) >= max){
                max = Integer.valueOf(s.id) +1;
            }
            if(s.driver!=null){
                Driver d = EmployeeController.getInstance().getDriver(s.driver);
                d.addObserver(this);
            }
            ship.addItemsFromDTO(weights);
            ship.addDestinationsFromDTO(orders);
            for (Site site: ship.invManager.getShippingOrder()){
                HashMap<String,Integer> dests = dMapper.getShipmentDestAmount(s.id,
                        String.valueOf(ship.invManager.getShippingOrder().indexOf(site)));
                ship.addItemsFromDTO(site,dests);
                site.addObserver(this);
            }
            switchShipmentToMatchingList(ship);
            checkAndSetStatusErrors(ship.id);
              currId = max;
        }
    }

    public boolean checkAndSetStatusErrors(int shipmentId){
        return getShipment(shipmentId).checkAndSetErrorStatus();
    }

    public int getTotalShipmentsAmount() {
        return errorShipment.size() + activeShipments.size() + completedShipments.size();
    }

    public String getCurrentListedItemsString(int shipmentId) {
        Shipment s = getShipment(shipmentId);
        return s.getItemsStringWithAmount();
    }

    public void removeDestination(int shipmentId, int destOrder) {
        clearDestinationItems(shipmentId,destOrder);
        getShipment(shipmentId).removeDestination(getShipment(shipmentId).getDestination(destOrder));
    }

    public String[][] getShipmentsTable(String filter, String date) {
        boolean notFilterByDate = date.isBlank();
        LocalDate lDate= notFilterByDate? null : LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));//"yyyy-MM-dd"
        ArrayList <Shipment> list = new ArrayList<>();
        switch(filter){
            case "Error":
                list = notFilterByDate? errorShipment : getErrorShipmentOfDate(lDate);
                break;
            case "Active":
                list = notFilterByDate? activeShipments : getActiveShipmentOfDate(lDate);
                break;
            case "Done":
                list = notFilterByDate? completedShipments : getCompletedShipmentOfDate(lDate);
                break;
            default:
                for(Shipment s: notFilterByDate?  errorShipment : getErrorShipmentOfDate(lDate)){
                    list.add(s);
                }
                for(Shipment s: notFilterByDate? activeShipments : getActiveShipmentOfDate(lDate)){
                    list.add(s);
                }
                for(Shipment s: notFilterByDate? completedShipments : getCompletedShipmentOfDate(lDate)){
                    list.add(s);
                }
                list.sort(new Comparator<Shipment>() {
                    @Override
                    public int compare(Shipment o1, Shipment o2) {
                        return o1.getId()-o2.getId();
                    }
                });
                break;
        }
        String[][] arr = new String[list.size()][4];
        int curr = 0;
        for(Shipment s: list){
            arr[curr] = getShipmentInfo(s);
            curr++;
        }
        return arr;
    }

    private ArrayList<Shipment> getErrorShipmentOfDate(LocalDate lDate) {
        ArrayList <Shipment> res = new ArrayList<>();
        for (Shipment s: errorShipment){
            if (s.getShippingDate()!= null && s.getShippingDate().compareTo( lDate) == 0){
                res.add(s);
            }
        }
        return res;
    }

    private ArrayList<Shipment> getActiveShipmentOfDate(LocalDate lDate) {
        ArrayList <Shipment> res = new ArrayList<>();
        for (Shipment s: activeShipments){
            if (s.getShippingDate() == lDate){
                res.add(s);
            }
        }
        return res;
    }

    private ArrayList<Shipment> getCompletedShipmentOfDate(LocalDate lDate) {
        ArrayList <Shipment> res = new ArrayList<>();
        for (Shipment s: activeShipments){
            if (s.getShippingDate() == lDate){
                res.add(s);
            }
        }
        return res;
    }

    private String[] getShipmentInfo(Shipment s) {
        String[] arr= {String.valueOf(s.getId()),s.zone.toString(), s.getShippingDate()==null ? nullVal : s.getShippingDate().toString(),
            s.getDriver()==null ? nullVal : s.getDriver().getName()+" : "+s.getDriver().getId(),
                s.getTruck()==null? nullVal : s.getTruck().getModel() +" : "+ s.getTruck().getLicenceNum(),s.getStatus().toString()};
        return arr;
    }

    public String[] getShipmentModelData(String sId) {
        Shipment s = getShipment(Integer.valueOf(sId));
        String[] arr = {sId,s.getShippingDate()==null ? nullVal : s.getShippingDate().toString(),s.getShippingStartTime()==null ? nullVal : s.getShippingStartTime().toString(),
                s.getTruck()==null ? nullVal : tm.truckShorterString(s.getTruck().getId()),s.getDriver()==null ? nullVal : s.getDriver().toStringShort(),
                s.getZone().toString(),s.getStatus().toString(),getShipmentSourceAndDestinationsInfo(s.getId())} ;
        return arr;
    }

    public String getStatusMsg(String id) {
        String out;
        Shipment s = getShipment(Integer.valueOf(id));
        if(s.getStatus().isErrorStatus()){
            out = "***Warning - There are Errors in this Shipment! It Cannot be launched!***";
        } else if (s.getStatus()== IShipment.ShipmentStatus.Done) {
            out = "Shipment is completed";
        } else if (s.getStatus() == IShipment.ShipmentStatus.StandBy) {
            out = "Shipment is ready for launch!";
        }
        else {
            out = "Shipment currently in progress!";
        }
        return out;
    }

    public String getShipmentStatus(String id) {
        return getShipment(Integer.valueOf(id)).getStatus().toString();
    }

    public String getTruckInfo(String tId) {
        return tm.truckShortString(Integer.parseInt(tId));
    }

    public String[][] getTrucksArrays() {
        return tm.getTruckArrays();

    }

    public String getTruckShortInfo(String tId) {
        return tm.truckShorterString(Integer.parseInt(tId));
    }

    public String getDriverShortInfo(String sId) {
        Shipment s = getShipment(Integer.parseInt(sId));
        return s.getDriver()==null ?"Unassigned" :  s.getDriver().toStringShorter();
    }

    public String[][] getAvailableDriverArrays(int sId) {
        List<Driver> list= getAvailableDriversList( getShipment(sId));
        if(list.size()==0){
            throw new NoSuchElementException("No drivers currently available for this shipment!");
        }
        String[][] out = new String[2][list.size()];
        for (int i = 0; i < list.size(); i++) {
            out[0][i] = list.get(i).toStringShort();
            out[1][i] = list.get(i).getId();
        }
        return out;
    }

    public String[][] getShipmentItemInfo(int sId) {
        Shipment s = getShipment(sId);
        return s.invManager.getItemsInfo();
    }

    public String[][] getShipmentRouteInfo(int sId) {
        Shipment s = getShipment(sId);
        return s.invManager.getRouteInfo();
    }

    public String[][] getSourcesInfo() {
        HashMap<Integer,Site> sources = SiteManager.getInstance().getActiveSitesByType(Site.SiteType.LogisticCenter);
        String[][] out = new String[2][sources.size()];
        int counter = 0;
        for (Integer i: sources.keySet()){
            Site s = sources.get(i);
            out[0][counter] = s.toString() ;
            out[1] [counter] = SiteManager.getInstance().getSiteIDFromName(s.getName())+"";
            counter++;
        }
        return out;
    }

    public String[][] getDestsInfo(int sId) {
        if(!getShipment(sId).hasSource()){
            throw new RuntimeException("Please add Source before adding destinations!");
        }
        HashMap<Integer,Site> sources = SiteManager.getInstance().getActiveSites();
        for(Site s: getShipment(sId).invManager.getShippingOrder()){
            sources.remove(SiteManager.getInstance().getSiteIDFromName(s.getName()));
        }
        String[][] out = new String[2][sources.size()];
        int counter = 0;
        for (Integer i: sources.keySet()){
            Site s = sources.get(i);
            out[0][counter] = s.toString() ;
            out[1] [counter] = SiteManager.getInstance().getSiteIDFromName(s.getName())+"";
            counter++;
        }
        return out;
    }

    public void deleteItemFromShipment(int sId,String item){
        Shipment s =getShipment(sId);
        s.invManager.removeFromAll(item);
        wMapper.deleteShipmentItem(String.valueOf(sId),item);
        for (Site site: getShipment(sId).invManager.getShippingOrder())
            dMapper.removeShipmentDestItem(String.valueOf(sId),
                    String.valueOf(getShipment(sId).invManager.getShippingOrder().indexOf(site)), item);
        checkAndSetStatusErrors(sId);
    }

    public String[] getDestInfo(Integer sId, int order) {
        Shipment ship = getShipment(sId);
        Site s = getShipment(sId).invManager.shippingOrder.get(order);
        boolean truckB = ship.getTruck() == null;
        boolean driveB = ship.getDriver() == null;

        String [] arr = {s.getName(),s.getType().toString(),s.getAddress(),
                getShipment(sId).invManager.getErrorsAtSite(order, truckB? 0 :ship.getTruck().getMaxCarryWeight(),
                        truckB? 0 :ship.getTruck().getWeight(),driveB? 0 :ship.getDriver().getMaxDriveWeight() )};
        return arr;
    }

    public String[][] getSiteItemsInfo(Integer valueOf, int parseInt) {
        return getShipment(valueOf).invManager.getSiteItemsInfo(parseInt);
    }

    public String[] getShipmentSizeArr(Integer valueOf) {
        String[] arr= new String[getShipment(valueOf).invManager.shippingOrder.size()];
        for (int i = 1; i < arr.length; i++) {
            arr[i] = ""+i;
        }
        return arr;
    }

    public String[] getItemsArr(int parseInt,int order) {
        String[] arr= new String[getShipment(parseInt).invManager.itemToWeightTable.keySet().size()];
        int counter = 0;
        for (String item: getShipment(parseInt).invManager.itemToWeightTable.keySet()){
            if(!getShipment(parseInt).invManager.isSiteGetItem(order,item)){
                arr[counter] = item;
                counter++;
            }
        }
        return arr;

    }
}