package PresentationLayer;

import BusinessLayer.Driver;
import BusinessLayer.EmployeeController;
import BusinessLayer.ShiftController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ServiceFactory;
import ServiceLayer.ShiftService;

import java.util.Scanner;

public class HRUI {

    static Scanner reader = new Scanner(System.in);
    static ShiftService shiftService=new ShiftService();
    static EmployeeService employeeService=new EmployeeService();
    static ShiftUI shiftUI=new ShiftUI(employeeService,shiftService);
    static MannageEmpUI mannageEmpUI=new MannageEmpUI(employeeService);
    public HRUI(){ loadData();   }
    private static void loadData(){
        Response res;
        res = ServiceFactory.getInstance().loadData();
        if (res.isErrorResponse()) {
            System.out.println(res);
            res = ServiceFactory.getInstance().loadData();
            if (res.isErrorResponse()) {
                System.out.println(res);
            }
        }
    }
    public void run(String id){
        System.out.println("Hi " + employeeService.getWorkerName(id) + ", What do you want to do Boss? Pick a Number:\n");

        shiftService.notifyArrangement();


        boolean termiManager = false;

        while (!termiManager) {
            System.out.println("\nHi " + employeeService.getWorkerName(id) + ", What do you want to do Boss? Pick a Number:\n");

            System.out.println("1: manage workers");
            System.out.println(("2: manage shifts"));
            System.out.println("0: logout");


            int press = reader.nextInt();


            //check for notify if theres a workArrange for the next 24

            switch (press) {
                case (1):
                    mannageEmpUI.run(id);break;
                case (2):
                    shiftUI.run();break;
                case (0):
                    logout(id);
                    termiManager=true;
                    break;

            }
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
