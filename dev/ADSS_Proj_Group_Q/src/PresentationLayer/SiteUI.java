
package PresentationLayer;


import BusinessLayer.SiteManager;
import ServiceLayer.Response;
import ServiceLayer.SiteService;

import java.util.Objects;

public class SiteUI {

    private SiteService ss;

    public SiteUI(){
        this.ss =new SiteService(SiteManager.getInstance());
    }

    public void run(){
        String userIn;
        Response res;
        while(true){
            System.out.println("Manage Sites here:");
            System.out.println("Enter 0 to view all current listed sites.");
            System.out.println("Enter 1 to add a new site.");
            System.out.println("Enter 2 to search, view, edit and remove site");
            System.out.println("Enter X to return to the PresentationLayer.main window");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X":
                    return;
                case "0":
                    System.out.println("Currently, a total of " + (Integer) ss.getSiteAmount().ReturnValue + " are listed \n");
                    System.out.println(ss.getSiteTableString().toString());
                    break;
                case "1":
                    addSiteUi();
                    break;
                case "2":
                    findSiteUi();
                    break;
                default:
                    System.out.println("Please enter valid input");
            }
        }
    }

    private void findSiteUi() {
        String userIn;
        Response res;
        while(true){
            System.out.println("Enter 1 to search site by ID.");
            System.out.println("Enter 2 to search site by name");
            System.out.println("Enter 3 to view sites by type.");
            System.out.println("Enter X to return.");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "X":
                    return;
                case "1":
                    System.out.println("Please enter requested site ID");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getSiteByID(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    editSiteUI(userIn);
                    break;
                case "2":
                    System.out.println("Please enter requested site Name");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.getSiteByName(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    }
                    editSiteUI(String.valueOf((int)res.ReturnValue));
                    break;
                case "3":
                    String type = getStringOfType();
                    res = ss.getSiteStringForType(type);
                    System.out.println(res);
                    break;
            }
        }

    }

    private void editSiteUI(String siteID) {
        Response res;
        String userIn;
        while (true){
            System.out.println("Current Site Info:");
            res = ss.getSiteString(siteID);
            System.out.println(res.ReturnValue.toString() + "\n");
            System.out.println("Enter 1 to edit site address.");
            System.out.println("Enter 2 to edit site contact name.");
            System.out.println("Enter 3 to edit site contact phone.");
            System.out.println("Enter 4 edit site location.");
            System.out.println("Enter D to deactivate site.");
            System.out.println("Enter X to return to the PresentationLayer.main window");
            userIn = MainShippingUI.input.nextLine();
            switch(userIn){
                case "X":
                    return;
                case "D":
                    if(deactivateSiteUI(siteID)){
                        return;
                    }
                    break;
                case "1":
                    System.out.println("Please enter requested site address:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setSiteAddress(siteID,userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    }
                    else {
                        System.out.println("Address changed successfully!\n");
                    }
                    break;
                case "2":
                    System.out.println("Please enter requested site  contact name:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setSiteContactName(siteID,userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    }
                    else {
                        System.out.println("Contact name changed successfully!\n");
                    }
                    break;
                case "3":
                    System.out.println("Please enter requested site Contact phone:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.setSiteContactPhone(siteID,userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    }
                    else {
                        System.out.println("Contact phone changed successfully!\n");
                    }
                    break;
                case "4":
                    System.out.println("Please enter requested site X coordinate:");
                    userIn = MainShippingUI.input.nextLine();
                    System.out.println("Please enter requested site Y coordinate:");
                    userIn +=","+ MainShippingUI.input.nextLine();
                    res = ss.setSiteCoordinate(siteID,userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    }
                    else {
                        System.out.println("Site coordinate changed successfully!\n");
                    }
                    break;
                case "5":
                    userIn = getStringOfType();
                    res = ss.setSiteType(siteID,userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    }
                    else {
                        System.out.println("Site coordinate changed successfully!\n");
                    }
                    break;
            }
        }

    }

    private boolean deactivateSiteUI(String siteID) {
        Response res;
        String userIn;
        while(true){
            MainShippingUI.printInRedBackGround("***WARNING***");
            System.out.println("Deactivating this site will prevent shipments to be made to/from site,and from employees to work in this site in the future.");
            System.out.println("Are you sure you wish to continue? (Y/N");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "Y":
                    res = ss.deactivateSite(siteID);
                    if(res.isErrorResponse()){
                        System.out.println(res);
                        return false;
                    }
                    else {
                        System.out.println("Site deactivated successfully!\n");
                    }
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Please enter valid input");
            }

        }
    }

    private void addSiteUi() {
        String userIn;
        Response res;
        String[] data = new String[6];
        while (true){
            System.out.println("Current data of new site data:\n");
            System.out.println("Name:\t" + Objects.toString(data[0],""));
            System.out.println("Address:\t" + Objects.toString(data[1],""));
            System.out.println("Contact name:\t" + Objects.toString(data[2],""));
            System.out.println("ContactPhone:\t" + Objects.toString(data[3],""));
            System.out.println("Location:\t" + Objects.toString(data[4],""));
            System.out.println("Site Type:\t" + Objects.toString(data[5],"")+"\n");
            System.out.println("Enter 1 to edit site name.");
            System.out.println("Enter 2 to edit site address.");
            System.out.println("Enter 3 to edit site contact name.");
            System.out.println("Enter 4 to edit site contact phone.");
            System.out.println("Enter 5 to edit site location.");
            System.out.println("Enter 6 to edit site type.");
            System.out.println("Enter V to add new site with give data");
            System.out.println("Enter X to return to the PresentationLayer.main window");
            userIn = MainShippingUI.input.nextLine();
            switch(userIn){
                case "X":
                    return;
                case "V":
                    if (!isInputValid(data)){
                        System.out.println("Please fill all fields to create new shipment!");
                        break;
                    }
                    else {
                        res = ss.addNewSite(data[0],data[1],data[2],data[3],data[4],data[5]);
                        if(res.isErrorResponse()){
                            System.out.println(res);
                            break;
                        }
                        else{
                            System.out.println("Site created successfully!");
                            return;
                        }
                    }
                case "1":
                    System.out.println("Please enter requested site name:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.isValidName(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                        break;
                    } else if (!(boolean) res.ReturnValue ) {
                        System.out.println("Please enter valid input!");
                        break;
                    }
                    else {
                        data[0] = userIn;
                        break;
                    }
                case "2":
                    System.out.println("Please enter requested site address:");
                    userIn = MainShippingUI.input.nextLine();
                    data[1] = userIn;
                    break;
                case "3":
                    System.out.println("Please enter requested site contact name:");
                    userIn = MainShippingUI.input.nextLine();
                    data[2] = userIn;
                    break;
                case "4":
                    System.out.println("Please enter requested site Contact phone:");
                    userIn = MainShippingUI.input.nextLine();
                    res = ss.isValidPhone(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    } else if (!(boolean) res.ReturnValue ) {
                        System.out.println("Please enter valid input!");
                    }
                    else {
                        data[3] = userIn;
                    }
                    break;
                case "5":
                    System.out.println("Please enter requested site X coordinate:");
                    userIn = MainShippingUI.input.nextLine();
                    System.out.println("Please enter requested site Y coordinate:");
                    userIn +=","+ MainShippingUI.input.nextLine();
                    res = ss.isCoordinate(userIn);
                    if (res.isErrorResponse()){
                        System.out.println(res);
                    } else if (!(boolean) res.ReturnValue ) {
                        System.out.println("Please enter valid input!");
                    }
                    else {
                        data[4] = userIn;
                    }
                    break;
                case "6":
                    userIn = getStringOfType();
                    data[5] = userIn;
                    break;
            }
        }

    }

    private String getStringOfType(){
        String userIn;
        while (true){
            System.out.println("Please choose site type:\n");
            System.out.println("Enter 1 for BRANCH");
            System.out.println("Enter 2 for SUPPLIER");
            System.out.println("Enter 3 for LOGISTIC CENTER");
            System.out.println("Enter 4 for ELSE.");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn){
                case "1":
                    return "BRANCH";
                case "2":
                    return "SUPPLIER";
                case "3":
                    return "LOGISTICCTR";
                case "4":
                    return "ELSE";
                default:
                    System.out.println("Please enter valid input!");
            }
        }
    }

    private boolean isInputValid(String[] arr){
        for (String s: arr){
            if (s == null || s.trim().isBlank()  ){
                return false;
            }
        }
        return true;
    }
}

