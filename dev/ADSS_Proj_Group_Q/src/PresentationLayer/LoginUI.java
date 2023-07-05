package PresentationLayer;

import java.util.Scanner;
import BusinessLayer.*;
import ServiceLayer.*;


public class LoginUI {
    static Scanner reader = new Scanner(System.in);
    static ShiftService shiftService=new ShiftService();
    static EmployeeService employeeService=new EmployeeService();
    static MainShippingUI mainShippingUI = new MainShippingUI();
    static HRUI hrui = new HRUI();
    static EmployeeUI empUi = new EmployeeUI();

    public static void main(String[] args){
//        if(isClearData()){
//            clearData();
//            employeeService=new EmployeeService(ShiftController.getInstance(),EmployeeController.getInstance());
//            shiftService=new ShiftService(ShiftController.getInstance(), EmployeeController.getInstance());
//        }
        loadData();
        run();
    }

    private static void clearData(){
        Response res;
        res = ServiceFactory.getInstance().clearData();
        if (res.isErrorResponse()){
            System.out.println(res);
            res = ServiceFactory.getInstance().clearData();
            if (res.isErrorResponse()){
                System.out.println(res);
            }
        }
    }
    private static void loadData(){
        Response res;
        res = ServiceFactory.getInstance().loadData();
        if (res.isErrorResponse()){
            System.out.println(res);
            res = ServiceFactory.getInstance().loadData();
            if (res.isErrorResponse()){
                System.out.println(res);
            }
        }
    }

    private static boolean isClearData(){
        System.out.println("Clear database? (Y/N)");
        while (true){
            String in = reader.nextLine();
            switch (in){
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Enter valid input!");
            }
        }
    }

    public static void run(String title){
        loadData();
        switch (title) {
            case "LogManager":
                mainShippingUI.runShippingUI();
                break;
            case "HRManager":
                //TODO
                break;
            case "StoreManager":
                //TODO
                break;
            default:
                run();
                break;
        }
    }

    public static void run(){
        boolean terminate=false;
        while(!terminate){

            System.out.println("Hello There! \npress the number to:\n");
            System.out.println("1: Register as HR Manager");
            System.out.println("2: Register as Shipment Manager");
            System.out.println("3: login\n");
//            System.out.println("4: DEBUG - skip to MainShippingUI");
            System.out.println("0: exit");
            String choose = reader.next();
            if(choose.equals("1")){
                registerHR();
            }
            else if(choose.equals("2")){
                registerShipmentManager();
            }
            else if(choose.equals("3")){
                login();
            }
//            else if (choose.equals("4")) {
//                mainShippingUI.runShippingUI();
//                logout("3");
//            }
//            else if(choose.equals("5"))
//                hrui.run("1");
             else if (choose.equals("0")){
                terminate=true;
            }else {
                System.out.println("Please enter valid input!");
            }
        }
    }

    public static void registerHR(){
        System.out.println("Enter your ID:");
        String id = reader.next();
        reader.nextLine();
        System.out.println("Enter your password:");
        String password = reader.next();

        System.out.println("Enter your manager name:");
        String mname = reader.next();
        reader.nextLine();
        System.out.println("Enter your bank company:");
        String mbankCompany = reader.next();
        reader.nextLine();
        System.out.println("Enter your bank branch:");
        int mbankBranch = reader.nextInt();

        System.out.println("Enter your bank ID:");
        int mbankId = reader.nextInt();

        System.out.println("Enter your salary:");
        double msalary = reader.nextDouble();

        System.out.println("Enter your terms:");
        String mterms = reader.next();
        reader.nextLine();
        System.out.println("Enter your state:");
        String state = reader.next();
        reader.nextLine();
        Response res= employeeService.humanResourceManagerRegister(id, mname, mbankCompany, mbankBranch, mbankId, msalary, mterms, state, password);
        try{
            if(!res.isErrorResponse()){
                System.out.println(res.ReturnValue);
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        login();

    }

    public static void registerShipmentManager() {

        System.out.println("Enter your ID:");
        String id = reader.next();

        System.out.println("Enter your password:");
        String password = reader.next();

        System.out.println("Enter your manager name:");
        String mname = reader.next();
        reader.nextLine();
        System.out.println("Enter your bank company:");
        String mbankCompany = reader.nextLine();

        System.out.println("Enter your bank branch:");
        int mbankBranch = reader.nextInt();

        System.out.println("Enter your bank ID:");
        int mbankId = reader.nextInt();

        System.out.println("Enter your salary:");
        double msalary = reader.nextDouble();

        System.out.println("Enter your terms:");
        String mterms = reader.next();
        reader.nextLine();
        System.out.println("Enter your state:");
        String state = reader.next();
        reader.nextLine();
        Response res= employeeService.shipmentManagerRegister(id, mname, mbankCompany, mbankBranch, mbankId, msalary, mterms, state, password);
        try{
            if(!res.isErrorResponse()){
                System.out.println(res.ReturnValue);
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        login();
    }

        public static void login(){
        System.out.println("please login");
        System.out.println("enter your ID");
        String id=reader.next();
        reader.nextLine();
        System.out.println("Enter your password");
        String password=reader.next();


        Response res=employeeService.login(id,password);
            try {
                if (!res.isErrorResponse()) {
                    if(res.ReturnValue.equals("Logged-In Succesfully"))
                    {
                        if(id.equals(EmployeeController.getInstance().getHumanResurceId())) {
                            hrui.run(id);
                        }
                        else if (id.equals((EmployeeController.getInstance().getShipmentManagerId()))){
                            mainShippingUI.runShippingUI();
                            logout(id);
                        }

                        else{
                            empUi.run(id);
                        }
                    }
                } else {
                    System.out.println(res.ErrorMsg);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }



    }
    public static void logout(String id){

        Response res =employeeService.logout(id);

        try{
            if(!res.isErrorResponse()){
                System.out.println("Logged-out succscefully");
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}