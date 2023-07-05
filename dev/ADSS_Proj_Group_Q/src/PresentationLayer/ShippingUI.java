package PresentationLayer;

import ServiceLayer.Response;
import ServiceLayer.ShippingService;

public class ShippingUI {
    ServiceLayer.ShippingService ss;

    public ShippingUI(ShippingService ss) {
        this.ss = ss;
    }

    public void run() {
        String userIn;
        Response res;
        System.out.println("Manage shipments here:\n");
        while (true) {
            res = ss.getTotalShipmentsAmount();
            if(!res.isErrorResponse()){
                System.out.println(String.format("A total of %d shipments are registered in the system.\n",(int)res.ReturnValue));
            }
            System.out.println("Enter 1 to create new shipment.");
            System.out.println("Enter 2 to search, edit and remove shipments by id.");
            System.out.println("Enter 3 to view, edit and remove active shipments");
            System.out.println("Enter 4 to view, edit and remove shipments with errors");
            System.out.println("Enter 5 to view and search completed shipments");
            System.out.println("Enter X to return\n");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X":
                    return;
                case "1":
                    res = ss.addCleanShipment();
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    String sId = String.valueOf((int) res.ReturnValue);
                    editShipmentUI(sId);
                    break;
                case "2":
                    System.out.println("Enter requested shipment id number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getShipmentById(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editShipmentUI(userIn);
                    break;
                case "3":
                    activeShipmentUI();
                    break;
                case "4":
                    errorShipmentUI();
                    break;
                case "5":
                    completedShipmentsUI();
                    break;
                default:
                    System.out.println("Please enter valid input");
            }
        }
    }

    private void errorShipmentUI(){
        String userIn;
        Response res;
        while(true){
            System.out.println("Enter 1 to select shipment by entry number");
            System.out.println("Enter 2 to view all current listed error shipments");
            System.out.println("Enter 3 to search shipments by date");
            System.out.println("Enter 4 to view all shipments requiring driver");
            System.out.println("Enter X to  return to the Manage shipments PresentationLayer.main Screen");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "1" -> {
                    System.out.println("Enter requested shipment entry number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getErrorShipmentIdByIndex(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editShipmentUI(((Integer) res.ReturnValue).toString());
                }
                case "2" -> {
                    System.out.println("Currently, a total of " + (Integer)ss.getErrorsAmount().ReturnValue + " are listed.");
                    res = ss.getErrorShipmentsToString();
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "3" -> {
                    System.out.println("Enter requested date: (yyyy-MM-dd)");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getErrorShipmentsByDate(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "4" ->{
                    System.out.println("There are" + (Integer)ss.getWaitingForDriverAmount().ReturnValue + "shipments waiting for driver");
                    res = ss.getShipmentWaitingForDriverString();
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "X" -> {
                    return;
                }
                default -> System.out.println("Please enter a valid input");
            }
        }
    }

    private void activeShipmentUI(){
        String userIn;
        Response res;
        while(true){
            System.out.println("Enter 1 to select shipment by entry number");
            System.out.println("Enter 2 to view all current listed active shipments");
            System.out.println("Enter 3 to search shipments by date");
            System.out.println("Enter X to  return to the Manage shipments PresentationLayer.main Screen");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "1" -> {
                    System.out.println("Enter requested shipment entry number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getActiveShipmentIdByIndex(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editShipmentUI(((Integer) res.ReturnValue).toString());
                }
                case "2" -> {
                    System.out.println("Currently, a total of " + (int)ss.getActiveAmount().ReturnValue + " are listed.");
                    res = ss.getActiveShipmentsToString();
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "3" -> {
                    System.out.println("Enter requested date: (yyyy-MM-dd)");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getActiveShipmentsByDate(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "X" -> {
                    return;
                }
                default -> System.out.println("Please enter a valid input");
            }
        }
    }

    private void editShipmentUI(String shipmentId) {
        String userIn;
        Response res;
        while(true){
            System.out.println("Current shipment:");
            res = ss.getFullInfoStringOfShipment(shipmentId);
            System.out.println(res+"\n\n");
            System.out.println("Enter 1 to change the time or date.");
            System.out.println("Enter 2 to change truck.");
            System.out.println("Enter 3 to change shipment zone.");
            System.out.println("Enter 4 to change source, destinations and items delivered.");
            System.out.println("Enter 5 to choose driver.");
            System.out.println("Enter V to launch shipment, or mark it as completed.");
            System.out.println("Enter D to delete shipment.");
            System.out.println("Enter X to return to previous menu\n");
            userIn = MainShippingUI.input.nextLine();
            switch(userIn){
                case "X":
                    return;
                case "D":
                    if(deleteShipmentUI(shipmentId)){
                        return;
                    }
                    break;
                case "V":
                    advanceShipment(shipmentId);
                    break;
                case "1":
                    editShipmentDateAndTime(shipmentId);
                    break;
                case "2":
                    editShipmentTruck(shipmentId);
                    break;
                case "3":
                    setShipmentZone(shipmentId);
                    break;
                case "4":
                    destsAndItemsUI(shipmentId);
                    break;
                case "5":
                    setDriverUI(shipmentId);
                    break;
                default:
                    System.out.println("Please enter a valid value!\n");
            }
        }
    }

    private void setDriverUI(String shipmentId) {
        Response res;
        String userIn;
        while (true){
            System.out.println("Current assigned driver :");
            res = ss.getDriverString(shipmentId);
            System.out.println(res);
            System.out.println();
            System.out.println("Enter 1 to view available drivers.");
            System.out.println("Enter 2 to choose driver by entry number.");
            System.out.println("Enter 3 to choose driver by ID.");
            System.out.println("Enter 4 to remove driver from this shipment.");
            System.out.println("Enter X to return.");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "1":
                    res = ss.getAvailableDriversString(shipmentId);
                    System.out.println(res);
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Enter chosen driver entry number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentDriverByEntry(shipmentId,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                case "3":
                    System.out.println("Enter chosen driver ID:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentDriverById(shipmentId,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                case "4":
                    res = ss.removeDriver(shipmentId);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                default:
                    System.out.println("Please enter valid input!");
            }
        }
    }

    private boolean deleteShipmentUI(String shipmentId){
        Response res;
        String userIn;
        while(true){
            System.out.println("Are you sure you wish to delete this shipment? (Y/N)");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "Y" -> {
                    res = ss.deleteShipment(shipmentId);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                    } else if (!(boolean) res.ReturnValue) {
                        System.out.println("Error while deleting shipment.");
                    } else {
                        System.out.println("shipment removed successfully!");
                    }
                    return true;
                }
                case "N" -> {
                    return false;
                }
                default -> System.out.println("Please enter valid input.");
            }
        }
    }
    private void advanceShipment(String shipmentId){
        Response res;
        res = ss.advanceShipmentStatus(shipmentId);
        if(res.isErrorResponse()){
            printInRedBackGround("\n" +res.toString() + "\n");
            return;
        }
        res = ss.isShipmentInProgress(shipmentId);
        if(res.isErrorResponse()){
            System.out.println(res);
        }
        if((boolean) res.ReturnValue){
            System.out.println("Launching shipment - shipment "+shipmentId + " now in progress!");
            return;
        }
        res = ss.isShipmentDone(shipmentId);
        if(res.isErrorResponse()){
            System.out.println(res);
        }
        if((boolean) res.ReturnValue){
            System.out.println("Shipment " +shipmentId + " has been completed!");
        }
    }
    private void completedShipmentsUI(){
        String userIn;
        Response res;
        while(true){
            System.out.println("Enter 1 to select shipment by entry number");
            System.out.println("Enter 2 to view all current listed completed shipments");
            System.out.println("Enter 3 to search shipments by date");
            System.out.println("Enter X to  return to the Manage shipments PresentationLayer.main Screen");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "1" -> {
                    System.out.println("Enter requested shipment entry number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getCompletedShipmentIdByIndex(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editShipmentUI(((Integer) res.ReturnValue).toString());
                }
                case "2" -> {
                    System.out.println("Currently, a total of " + (int)ss.getAmountOfCompletedShipments().ReturnValue + " are listed.");
                    res = ss.getCompletedShipmentsToString();
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "3" -> {
                    System.out.println("Enter requested date: (yyyy-MM-dd)");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getCompletedShipmentsByDate(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println(res);
                }
                case "X" -> {
                    return;
                }
                default -> System.out.println("Please enter a valid input");
            }
        }
    }
    public void editShipmentDateAndTime(String sId){
        Response res;
        String userIn;
        while (true){
            res = ss.getShipmentDateTimeString(sId);
            if (res.isErrorResponse()){
                System.out.println(res);
            }
            else {
                System.out.println("Current date and time of shipment:");
                System.out.println(res);
            }
            System.out.println("Enter 1 to change date of shipment.");
            System.out.println("Enter 2 to change time of shipment.");
            System.out.println("Enter X to return to previous menu.");
            userIn  = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X" -> {
                    return;
                }
                case "1" -> {
                    System.out.println("Enter requested shipping date: (dd.MM.yyyy)");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentDate(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    printInGreenBackGround("Shipment date changed!");;
                }
                case "2" -> {
                    System.out.println("Enter requested shipping time:(HH:mm)");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentTime(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    printInGreenBackGround("Shipment time changed!");
                }
                default -> System.out.println("Please enter valid input");
            }
        }
    }
    public void editShipmentTruck(String sId){
        Response res;
        String userIn;
        while (true){
            res = ss.getTruckString(sId);
            if (res.isErrorResponse()){
                System.out.println(res);
            }
            else {
                System.out.println("Current truck:");
                System.out.println(res);
            }
            System.out.println("Enter 1 to choose truck by index.");
            System.out.println("Enter 2 to view all currently listed trucks.");
            System.out.println("Enter 3 to search and assign truck by licence number.");
            System.out.println("Enter X to return to previous menu.");
            userIn  = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X" -> {
                    return;
                }
                case "1" -> {
                    System.out.println("Enter requested truck index:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentTruck(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        notifyForTruckError(sId);
                        break;
                    }
                    System.out.println("Truck changed successfully!");
                }
                case "2" -> {
                    System.out.println("Currently, a total of " + (Integer)ss.getAmountOfTrucks().ReturnValue + " are listed \n");
                    System.out.println("Current listed drivers:\n");
                    System.out.println("id \t name:\t Licence Number \t LicenceType:");
                    System.out.println(ss.getTrucksList().toString());
                }
                case "3" -> {
                    System.out.println("Enter requested licence number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setTruckByLicence(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        notifyForTruckError(sId);
                        break;
                    }
                    System.out.println("Truck changed successfully!");
                }
                default -> System.out.println("Please enter valid input");
            }
        }
    }
    private void setShipmentZone(String sId) {
        Response res;
        String userIn;
        while(true){
            System.out.println(("Choose between NORTH, SOUTH, EAST, WEST, CENTER"));
            System.out.println("Enter requested shipment zone: \t(N/S/E/W/C)");
            userIn = MainShippingUI.input.nextLine();
            res = ss.setShipmentZone(sId,userIn);
            if (res.isErrorResponse()){
                System.out.println(res);
            }
            else{
                printInGreenBackGround("shipment zone set.");
                return;
            }
        }
    }
    private void destsAndItemsUI(String sId){
        Response res;
        String userIn;
        res = ss.getShipmentSourceAndDestinationsInfo(sId);
        if (!res.isErrorResponse()){
            System.out.println("\nCurrent route of shipment:\n");
        }
        System.out.println(res.toString() + "\n");
        while (true){
            System.out.println("\n");
            System.out.println("Enter 1 change source and add items delivered from them.");
            System.out.println("Enter 2 view and delete destinations and add items delivered to and from .");
            System.out.println("Enter 3 to add new destination to this shipment.");
            System.out.println("Enter 4 to add or remove items to deliver in this shipment.");
            System.out.println("Enter X to return.");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "1":
                    editSourceUI(sId);
                case "2":
                    System.out.println("Choose which site you wish to edit:");
                    res = ss.getShipmentShortDestinationsOnlyString(sId);
                    System.out.println(res);
                    userIn= MainShippingUI.input.nextLine();
                    res = ss.isShipmentHasDestInOrder(sId,userIn);
                    if (res.isErrorResponse()){
                        System.out.println( res);
                        break;
                    }
                    else if (!(boolean)res.ReturnValue) {
                        System.out.println("No destination number " + userIn + " exists in this shipment");
                        break;
                    }
                    editDestinationUI(sId,userIn);
                    break;
                case "3":
                    int destId = getDestinationIdUI();
                    if (destId < 0){
                        break;
                    }
                    String newDestOrder = ss.addDestGetOrder(sId,""+destId).ReturnValue.toString();
                    editDestinationUI(sId,newDestOrder);
                    break;
                case "4":
                    editItemsUI(sId);
                    break;
            }
        }
    }

    private void editItemsUI(String sId) {
        Response res;
        String userIn;
        res = ss.getCurrentListedItems(sId);
        System.out.println(res.toString() + "\n");
        while(true){
            System.out.println("Enter 1 to add item to deliver in this shipment");
            System.out.println("Enter 2 to remove item from this shipment.");
            System.out.println("Enter 3 clear all items from this shipment.");
            System.out.println("Enter X to return");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "1":
                    System.out.println("Enter new item's name:");
                    String itemName = MainShippingUI.input.nextLine();
                    System.out.println("Enter weight of single unit of "+ itemName + ":");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.addNewItemToShipment(sId,itemName,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    System.out.println("item successfully added to shipment!");
                    break;
                case "2":
                    String toRemove = chooseItemUI(sId);
                    if(toRemove.isBlank()){
                        break;
                    }
                    res = ss.removeItemFromShipment(sId,toRemove);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    System.out.println("item successfully removed from shipment!");
                    break;
                case "3":
                    res = ss.clearShipmentItems(sId);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    System.out.println("cleared all items from shipment!");

                default:
                    System.out.println("Please enter valid input!");
            }
        }
    }

    private void editItemsUI(String sId, String destOrder) {
        Response res;
        String userIn;
        while(true){
            System.out.println("Current deliveries to site:");
            System.out.println(ss.getDestItemsString(sId,destOrder).toString() + "\n");
            System.out.println("Enter 1 to add item to deliver to site.");
            System.out.println("Enter 2 to add item to pick up at site.");
            System.out.println("Enter 3 to remove item from site");
            System.out.println("Enter X to return");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "1":
                    if(destOrder.equals("0")){
                        System.out.println("Error- cannot drop items at source!");
                        break;
                    }
                    String item = chooseItemUI(sId);
                    if (item.isBlank()){
                        break;
                    }
                    System.out.println("How many units to deliver to site?");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.addItemsToDeliver(sId,destOrder,item,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                case "2":
                    String itemToPick = chooseItemUI(sId);
                    if (itemToPick.isBlank()){
                        break;
                    }
                    System.out.println("How many units to pickUp at site?");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.addItemsToPickUp(sId,destOrder,itemToPick,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                case "3":
                    String toRemove = chooseItemUI(sId);
                    if (toRemove.isBlank()){
                        break;
                    }
                    res = ss.removeItemFromDest(sId,destOrder,toRemove);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                    }
                    break;
                default:
                    System.out.println("Please enter valid input!");
            }
        }

    }

    private void editDestinationUI(String sId, String destOrder) {
        Response res;
        String userIn;
        while(true){
            res = ss.getDestinationString(sId,destOrder);
            System.out.println(res + "\n");
            System.out.println("Enter 1 to change destination's site.");
            System.out.println("Enter 2 to change destination's order.");
            System.out.println("Enter 3 to add items delivered or picked up at site.");
            System.out.println("Enter D to remove this site form this shipment");
            System.out.println("Enter X to return");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "D":
                    if(removeDestinationUI(sId,destOrder)){
                        return;
                    }
                    break;
                case "1":
                    int id = getDestinationIdUI();
                    if(id < 0){
                        break;
                    }
                    res = ss.switchShipmentDestination(sId,destOrder,String.valueOf(id));
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    System.out.println("Site replaced!");
                    break;
                case "2":
                    System.out.println("replace with which site?");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.replaceSitesOrder(sId,destOrder,userIn);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    destOrder = userIn;
                case "3":
                      editItemsUI(sId,destOrder);
                      break;
                default:
                    System.out.println("Please enter valid input!");
            }
        }
    }

    private boolean removeDestinationUI(String sId, String destOrder) {
        Response res;
        String userIn;
        System.out.println("Are you sure you wish to remove this site? (Y/N) :");
        userIn = MainShippingUI.input.nextLine();
        while(true){
            switch (userIn){
                case "Y":
                    res = ss.removeDestination(sId,destOrder);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        return false;
                    }
                    System.out.println("Site removed from shipment!");
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Enter Y for yes and N for no!");
            }
        }
    }

    private String chooseItemUI(String sId){
        Response res;
        String userIn;
        System.out.println("Choose item by name: \n");
        res = ss.getShipmentItems(sId);
        System.out.println(res);
        userIn = MainShippingUI.input.nextLine();
        res = ss.isItemInShipment(sId,userIn);
        if (res.isErrorResponse()){
            System.out.println(res);
            return "";
        }
        return userIn;
    }

    private int getDestinationIdUI() {
        Response res;
        String userIn;
        System.out.println("Please choose a site to add to shipment:\n");
        while(true){
            System.out.println("Enter 1 to view all available sites.");
            System.out.println("Enter 2 to add by site name.");
            System.out.println("Enter 3 to add site by site Id.");
            System.out.println("Enter X to return to previous menu.");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X" :
                    return -1;
                case "1":
                    res = ss.getAllActiveSitesString();
                    System.out.println(res);
                    break;
                case "2":
                    System.out.println("Please enter requested site name:\n");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getSiteIdByName(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res+"\n");
                        break;
                    }
                    return Integer.parseInt((String)res.ReturnValue);
                case "3":
                    System.out.println("Please enter requested site Id:\n");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getSite(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res+"\n");
                        break;
                    }
                    return Integer.valueOf(userIn);
            }
        }

    }

    private void editSourceUI(String sId){
        Response res;
        String userIn;
        while (true){
            System.out.println("Current Source info:");
            res = ss.getSourceString(sId);
            System.out.println(res.toString() +"\n");
            boolean isSourceNull = (boolean)ss.isSourceNull(sId).ReturnValue;
            System.out.println("Enter 1 to view available sources.");
            System.out.println("Enter 2 to change source by site name.");
            System.out.println("Enter 3 to change source by site Id.");
            if (!isSourceNull){
                System.out.println("Enter 4 to manage items delivered from site.");
            }
            System.out.println("Enter X to return to previous menu.");
            userIn  = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X" -> {
                    return;
                }
                case "1" -> {
                    res = ss.getLogisticCentersString();
                    System.out.println(res);
                }
                case "2" -> {
                    System.out.println("Enter requested source name:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentSourceByName(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    System.out.println("source address Changed successfully!");
                }
                case "3" -> {
                    System.out.println("Enter requested source Id:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setShipmentSourceById(sId, userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    if (!(boolean) res.ReturnValue) {
                        System.out.println("Error editing source ");
                        break;
                    }
                    System.out.println("contact name changed successfully!");
                }
                case "4" -> {
                    if (!isSourceNull){
                        editItemsUI(sId,"0");
                        break;
                    }
                    System.out.println("Please choose source to add items to it!");
                }
                default -> System.out.println("Please enter valid input");
            }
        }
    }

    private void printInRedBackGround(String toPrint) {
        final String ANSI_RED_BACKGROUND = "\u001B[41m";
        final String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_RED_BACKGROUND + toPrint + ANSI_RESET);
    }
    private void printInGreenBackGround(String toPrint) {
        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        final String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_GREEN_BACKGROUND + toPrint + ANSI_RESET);
    }
    void notifyForTruckError(String sId){
        Response res;
        res = ss.isTruckNotOverweight(sId);
        if (!(boolean)res.ReturnValue){
            res = ss.getShipmentTotalWeightReport(sId);
            printInRedBackGround(res.toString());
        }
    }





    private void printErrorShipmentsAmount(){
         int errorCount = (int)ss.getErrorsAmount().ReturnValue;
        if (errorCount==0){
            System.out.println("No current shipment errors!");
            return;
        }
        System.out.println("There are "+errorCount+" shipments errors!");
    }



}
