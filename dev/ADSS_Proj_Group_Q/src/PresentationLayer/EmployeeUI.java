package PresentationLayer;

import BusinessLayer.EmployeeController;
import BusinessLayer.ShiftController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import java.util.List;
import java.util.Scanner;

public class EmployeeUI {
    static Scanner reader = new Scanner(System.in);
    static ShiftService shiftService=new ShiftService();
    static EmployeeService employeeService=new EmployeeService();
    public EmployeeUI(){
    }

    public void run(String id){
        //user is not manager

        boolean termiUser=false;

        while(!termiUser){

            System.out.println("\nHi "+employeeService.getWorkerName(id)+", What do you want to do ? Pick a Number:\n");

            System.out.println("1: add constraint");
            System.out.println("2: show future published shifts");
            System.out.println("3: add cancelation");
            System.out.println("4: add special event\n");
            System.out.println("5: show future shipments (only for storekeeper or shift manager)");
            System.out.println("0: logout");

            int press=reader.nextInt();

            if(press==0){
                termiUser=true;}

            switch (press) {
                case (1):
                    AddConstraint(id);break;
                case (2):
                    showShifts(id);break;
                case (3):
                    addShiftCancellation(id);break;
                case (4):
                    addShiftEvent(id);break;
                case (5):
                    showFutureShipments(id);break;
                case(0):
                    logout(id);break;
            }

        }


    }

    public static void AddConstraint(String id){


        System.out.print("Enter year: ");
        int year = reader.nextInt();

        System.out.print("Enter month: ");
        int month = reader.nextInt();

        System.out.print("Enter day : ");
        int day = reader.nextInt();

        System.out.print("Enter shift type (m for morning, e for evening): ");
        String st = reader.next();


        Response res= shiftService.AddConstraint(id, year, month, day, st);
        try{
            if(!res.isErrorResponse()){
                System.out.println("Constraint Added" );
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    public static void showShifts(String id){

        Response res= employeeService.showShifts(id);
        try{
            if(!res.isErrorResponse()){
                List<String> shifts=(List<String>)res.ReturnValue;
                for(String shift : shifts){
                    System.out.println(shift);
                }
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }


    }
    public static void logout(String id){
        Response res=  employeeService.logout(id);

        try{
            if(!res.isErrorResponse()){
                System.out.println("Logged out Succsefully");
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void addShiftCancellation(String id){


        System.out.print("Please enter the year: ");
        int year = reader.nextInt();

        System.out.print("Please enter the month: ");
        int month = reader.nextInt();

        System.out.print("Please enter the day: ");
        int day = reader.nextInt();

        System.out.print("Enter day (m for Morning, e for Evning): ");
        String shiftType = reader.next();
        reader.nextLine();
        System.out.print("Please enter the branch: ");
        String branch = reader.nextLine();

        System.out.print("Please enter the product ID: ");
        int prodId = reader.nextInt();

        System.out.print("Please enter the quantity: ");
        int quantity = reader.nextInt();

        Response res= shiftService.addShiftCancellation(id, year, month, day, shiftType, branch, prodId, quantity);
        try{
            if(!res.isErrorResponse()){
                System.out.println("Added shift cancellation");
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void addShiftEvent(String id){


        System.out.println("Please enter the year:");
        int year = reader.nextInt();
        System.out.println("Please enter the month:");
        int month = reader.nextInt();
        System.out.println("Please enter the day:");
        int day = reader.nextInt();
        System.out.println("Please enter the shift type:");
        String shiftType = reader.next();
        reader.nextLine();
        System.out.println("Please enter the branch:");
        String branch = reader.nextLine();
        System.out.println("Please enter the event:");
        String event = reader.nextLine();

        Response res=shiftService.addShiftEvent(id, year, month, day, shiftType, branch, event);

        try{
            if(!res.isErrorResponse()){
                System.out.println("Added shift event succscefully");
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void showFutureShipments(String id) {

        Response res=shiftService.getFutureShipmentsForEmp(id);

        try{
            if(!res.isErrorResponse()){
                List<String> shifts=(List<String>)res.ReturnValue;
                for(String shift : shifts){
                    System.out.println(shift);
                }
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
