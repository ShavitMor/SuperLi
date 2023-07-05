package ServiceLayer;

import BusinessLayer.SiteManager;

import java.util.List;

public class SiteService {
    private SiteManager sManager;

    public SiteService(SiteManager sm){
        sManager = sm;
    }

    public Response getSiteTableString(){
        try{
            return Response.ResponseValue(sManager.toString());
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteAmount(){
        try{
            return Response.ResponseValue(sManager.getSiteAmount());
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response addNewSite2(String name, String address, String cName, String cPhone, String sCoordinate, String type) {
        try{
            return Response.ResponseValue(sManager.addNewSiteReturnId(name,address,cName,cPhone,sCoordinate,type));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response addNewSite(String name, String address, String cName, String cPhone, String sCoordinate, String type) {
        try{
            return Response.ResponseValue(sManager.addNewSiteReturnIdWithExceptions(name,address,cName,cPhone,sCoordinate,type));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response isValidName(String name) {
        try{
            return Response.ResponseValue(sManager.isValidName(name));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response isValidNameAndNotTaken(String name) {
        try{
            return Response.ResponseValue(sManager.isNameValidForUse(name));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }
    public Response isValidPhone(String phone) {
        try{
            return Response.ResponseValue(sManager.isValidPhone(phone));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response isCoordinate(String coordinate) {
        try{
            return Response.ResponseValue(sManager.isValidCoordinate(coordinate));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteByID(String userIn) {
        try{
            return Response.ResponseValue(sManager.getSite(Integer.valueOf(userIn)));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteByName(String userIn) {
        try{
            return Response.ResponseValue(sManager.getSiteIDFromName(userIn));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteStringForType(String type) {
        try{
            return Response.ResponseValue(sManager.toStringByType(type));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }
    public Response getSiteString(String userIn) {
        try{
            return Response.ResponseValue(sManager.getSiteString(Integer.parseInt(userIn)));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteName(String siteID, String userIn) {
        try{
            sManager.setSiteName(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteAddress(String siteID, String userIn) {
        try{
            sManager.setSiteAddress(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteContactName(String siteID, String userIn) {
        try{
            sManager.setSiteContactName(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteContactPhone(String siteID, String userIn) {
        try{
            sManager.setSiteContactPhone(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteCoordinate(String siteID, String userIn) {
        try{
            sManager.setSiteCoordinate(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response setSiteType(String siteID, String userIn) {
        try{
            sManager.setSiteType(Integer.parseInt(siteID),userIn);
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response deactivateSite(String siteID) {
        try{
            sManager.deactivateSite(Integer.parseInt(siteID));
            return Response.Empty();
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteTable(String filter,String viewInactive) {
        try{
            return Response.ResponseValue(sManager.getSiteTable(filter,viewInactive));
        }catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
    }

    public Response getSiteModelData(String siteId) {
        try{
            return Response.ResponseValue(sManager.getSiteModelData(siteId));
        }catch (Exception ex){

            return Response.ErrorResponse(ex);
        }
    }

    public Response getAllBranchesNames(){
        try {
            List<String> branches=sManager.getAllBranchesNames();
            return Response.ResponseValue(branches);
        }catch (Exception ex)
        {
            return Response.ErrorResponse(ex);
        }
    }
}
