package ServiceLayer;

import BusinessLayer.ShippingManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ShippingService {

    ShippingManager sm;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public ShippingService(ShippingManager sm){
        this.sm = sm;
    }
    public ShippingService(){
        this.sm = ShippingManager.getInstance();
    }

    public Response getErrorsAmount(){
        Response res;
        try{
            res = Response.ResponseValue(sm.getErrorsAmount());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getActiveAmount(){
        Response res;
        try{
            res = Response.ResponseValue(sm.getAmountOfActiveShipments());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }



    public Response getCompletedShipmentIdByIndex(String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getCompletedShipmentId(Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getAmountOfCompletedShipments() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getAmountOfCompletedShipments());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getCompletedShipmentsToString(){
        Response res;
        try{
            res = Response.ResponseValue(sm.compeletedShipmentsToString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response getCompletedShipmentsByDate(String date) {
        Response res;
        try{
            res = Response.ResponseValue(sm.stringifyCompletedShipmentsByDate(LocalDate.parse(date, dateFormatter)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentById(String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipment(Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getFullInfoStringOfShipment(String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getFullInfoOfShipmentById(Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response deleteShipment(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.deleteShipment(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentDate(String sId,String date) {
        Response res;
        try{
            sm.setShipmentDate(Integer.parseInt(sId),LocalDate.parse(date, dateFormatter));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getShipmentDateTimeString(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.stringifyShippingDateTime(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentTime(String sId, String time) {
        Response res;
        try{
            sm.setShipmentTime(Integer.parseInt(sId), LocalTime.parse(time, timeFormatter));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getDriverString(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getDriverString(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTruckString(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getTruckString(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }






    public Response setShipmentTruck(String sId, String truckIndex) {
        Response res;
        try{
            sm.setShipmentTruck(Integer.parseInt(sId),Integer.parseInt(truckIndex));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getAmountOfTrucks() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getAmountOfTrucks());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTrucksList() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getTruckList());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setTruckByLicence(String sId, String truckLicence) {
        Response res;
        try{
            sm.setTruckByLicenceNumber(Integer.parseInt(sId),truckLicence);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getSourceString(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getSourceString(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response isTruckNotOverweight(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isTruckOverweight(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response advanceShipmentStatus(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.advanceShipmentStatus(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response isShipmentInProgress(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isShipmentInProgress(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response isShipmentDone(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isShipmentDone(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addCleanShipment() {
        Response res;
        try{
            res = Response.ResponseValue(sm.addCleanShipment());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getErrorShipmentIdByIndex(String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getErrorShipmentId(Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getActiveShipmentIdByIndex(String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getActiveShipmentId(Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getErrorShipmentsToString() {
        Response res;
        try{
            res = Response.ResponseValue(sm.errorShipmentsToString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getActiveShipmentsToString() {
        Response res;
        try{
            res = Response.ResponseValue(sm.activeShipmentsToString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getErrorShipmentsByDate(String date) {
        Response res;
        try{
            res = Response.ResponseValue(sm.stringifyErrorShipmentsByDate(LocalDate.parse(date, dateFormatter)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getActiveShipmentsByDate(String date) {
        Response res;
        try{
            res = Response.ResponseValue(sm.stringifyActiveShipmentsByDate(LocalDate.parse(date, dateFormatter)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response setShipmentZone(String sId, String userIn) {
        Response res;
        try{
            sm.setShipmentZone(Integer.parseInt(sId),userIn);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response getWaitingForDriverAmount() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentWaitingForDriverAmount());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentWaitingForDriverString() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentWaitingForDriverString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getAvailableDriversString(String shipmentId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getAvailableDriversString(Integer.parseInt(shipmentId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentDriverByEntry(String shipmentId, String entryNum) {
        Response res;
        try{
            sm.setShipmentDriverByEntry(Integer.parseInt(shipmentId),Integer.parseInt(entryNum));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentDriverById(String shipmentId, String dId) {
        Response res;
        try{
            sm.setShipmentDriverById(Integer.parseInt(shipmentId),dId);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response removeDriver(String shipmentId) {
        Response res;
        try{
            sm.removeDriver(Integer.parseInt(shipmentId));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentSourceAndDestinationsInfo(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentSourceAndDestinationsInfo(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addDestGetOrder(String sId, String destId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.addDestGetOrder(Integer.parseInt(sId), Integer.parseInt(destId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentShortDestinationsOnlyString(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentShortDestinationsOnlyString(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response isShipmentHasDestInOrder(String sId, String dOrder) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isShipmentHasDestInOrder(Integer.parseInt(sId), Integer.parseInt(dOrder)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addNewItemToShipment(String shipmentId, String itemName, String weight) {
        Response res;
        try{
            sm.addNewItemToShipment(Integer.parseInt(shipmentId),itemName,Double.parseDouble(weight));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response removeItemFromShipment(String shipmentId, String toRemove) {
        Response res;
        try{
            sm.deleteItemFromShipment(Integer.parseInt(shipmentId),toRemove);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response clearShipmentItems(String shipmentId) {
        Response res;
        try{
            sm.clearShipmentItems(Integer.parseInt(shipmentId));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getDestItemsString(String shipmentId, String destOrder) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getDestItemsString(Integer.parseInt(shipmentId), Integer.parseInt(destOrder)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addItemsToDeliver(String shipmentId, String destOrder, String item, String amount) {
        Response res;
        try{
            sm.addItemsToDeliver(Integer.parseInt(shipmentId),Integer.parseInt(destOrder),item,Integer.parseInt(amount));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addItemsToPickUp(String shipmentId, String destOrder, String itemToPick, String amount) {
        Response res;
        try{
            sm.addItemsToPickUp(Integer.parseInt(shipmentId),Integer.parseInt(destOrder),itemToPick,Integer.parseInt(amount));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response removeItemFromDest(String shipmentId, String destOrder, String toRemove) {
        Response res;
        try{
            sm.removeItemFromDest(Integer.parseInt(shipmentId),Integer.parseInt(destOrder),toRemove);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getDestinationString(String shipmentId, String destOrder) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getDestinationString(Integer.parseInt(shipmentId), Integer.parseInt(destOrder)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response switchShipmentDestination(String shipmentId, String destOrder, String newSiteId) {
        Response res;
        try{
            sm.switchShipmentDestination(Integer.parseInt(shipmentId),Integer.parseInt(destOrder),Integer.parseInt(newSiteId));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response replaceSitesOrder(String shipmentId, String destOrder, String toSwitch) {
        Response res;
        try{
            sm.replaceSitesOrder(Integer.parseInt(shipmentId),Integer.parseInt(destOrder),Integer.parseInt(toSwitch));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentItems(String shipmentId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentItems(Integer.parseInt(shipmentId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response isItemInShipment(String shipmentId, String itemName) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isItemInShipment(Integer.parseInt(shipmentId),itemName));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getAllActiveSitesString() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getAllActiveSitesString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getSiteIdByName(String siteName) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getSiteIdByName(siteName));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getSite(String siteId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getSite(Integer.valueOf(siteId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getLogisticCentersString() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getLogisticCentersString());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentSourceById(String sId, String userIn) {
        Response res;
        try{
            res = Response.ResponseValue(sm.setShipmentSourceById(Integer.parseInt(sId),Integer.parseInt(userIn)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response isSourceNull(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.isSourceNull(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response setShipmentSourceByName(String sId, String userIn) {
        Response res;
        try{
            sm.setShipmentSource(Integer.parseInt(sId),userIn);
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentTotalWeightReport(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getShipmentTotalWeightReport(Integer.parseInt(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTotalShipmentsAmount() {
        Response res;
        try{
            res = Response.ResponseValue(sm.getTotalShipmentsAmount());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getCurrentListedItems(String sId) {
        Response res;
        try{
            res = Response.ResponseValue(sm.getCurrentListedItemsString(Integer.valueOf(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response removeDestination(String sId, String destOrder) {
        Response res;
        try{
            sm.removeDestination(Integer.parseInt(sId),Integer.parseInt(destOrder));
            res = Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentsTable(String filter, String date) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentsTable(filter,date));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentModelData(String sId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentModelData(sId));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getStatusMsg(String id) {
        Response res;
        try{
            return Response.ResponseValue(sm.getStatusMsg(id));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentStatus(String id) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentStatus(id));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTruckInfo(String tId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getTruckInfo(tId));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTrucksArrays() {
        Response res;
        try{
            return Response.ResponseValue(sm.getTrucksArrays());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response removeTruck(String sId) {
        Response res;
        try{
            sm.removeShipmentTruck(Integer.parseInt(sId));
            return Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response getTruckShortInfo(String tId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getTruckShortInfo(tId));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getDriverShortInfo(String x) {
        Response res;
        try{
            return Response.ResponseValue(sm.getDriverShortInfo(x));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getAvailableDriversArrays(String id) {
        Response res;
        try{
            return Response.ResponseValue(sm.getAvailableDriverArrays(Integer.valueOf(id)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getItemInfo(String sId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentItemInfo(Integer.valueOf(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getRouteInfo(String sId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentRouteInfo(Integer.valueOf(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getSourcesInfo() {
        Response res;
        try{
            return Response.ResponseValue(sm.getSourcesInfo());
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getDestsInfo(String sId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getDestsInfo(Integer.valueOf(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response getDestInfo(String sId, String order) {
        Response res;
        try{
            return Response.ResponseValue(sm.getDestInfo(Integer.valueOf(sId),Integer.parseInt(order)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getSiteItemInfo(String sId,String order) {
        Response res;
        try{
            return Response.ResponseValue(sm.getSiteItemsInfo(Integer.valueOf(sId),Integer.parseInt(order)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getShipmentSizeArr(String sId) {
        Response res;
        try{
            return Response.ResponseValue(sm.getShipmentSizeArr(Integer.valueOf(sId)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getItemArr(String sId,String order) {
        Response res;
        try{
            return Response.ResponseValue(sm.getItemsArr(Integer.parseInt(sId),Integer.parseInt(order)));
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response addItemPickOrDrop(String sId, String order, String x, String amount) {
        Response res;
        try{
            res = Integer.parseInt(amount) < 0 ? addItemsToDeliver(sId,order,x,String.valueOf(-Integer.parseInt(amount))):addItemsToPickUp(sId,order,x,amount);
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
}

