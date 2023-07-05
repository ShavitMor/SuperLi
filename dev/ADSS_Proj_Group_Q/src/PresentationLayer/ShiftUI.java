package PresentationLayer;

import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import java.util.List;
import java.util.Scanner;

public class ShiftUI {
    static Scanner reader = new Scanner(System.in);
    static EmployeeService employeeService;
    static ShiftService shiftService;


    public ShiftUI(EmployeeService employeeService1,ShiftService shiftService1){
        employeeService=employeeService1;
        shiftService=shiftService1;
    }

    public void run(){
        boolean termiManager = false;
        while (!termiManager) {
            System.out.println("\n");
            System.out.println("1: show available workers for shift");
            System.out.println(("2: add worker to shift"));
            System.out.println("3: show history of shifts");
            System.out.println("4: add role to shift");
            System.out.println("5: show shift events");
            System.out.println("6: show available workers for shift by specific role");
            System.out.println("7: add shift");
            System.out.println("8: publish shift");
            System.out.println("9: the shift's cancelations");
            System.out.println("10: restrict employee from shift");
            System.out.println("0: back");
            int press = reader.nextInt();
            switch (press) {
                case (1):
                    showAvailableWorkersForShift();
                    break;
                case (2):
                    addWorkerToShift();
                    break;
                case (3):
                    showHistoryOfShifts();
                    break;
                case (4):
                    addRoleToShift();
                    break;
                case (5):
                    showShiftEvents();
                    break;
                case (6):
                    showAvailableWorkersForShiftByRole();
                    break;
                case (7):
                    addShift();
                    break;
                case (8):
                    publishShift();
                    break;
                case (9):
                    getCancelationsByShift();
                    break;
                case (10):
                    restrictEmployee();
                    break;
                case (0):
                    termiManager=true;break;
            }
        }
    }
    public static void addWorkerToShift(){
        System.out.print("Enter the year of the shift: ");
        int year = reader.nextInt();
        System.out.print("Enter the month of the shift: ");
        int month = reader.nextInt();
        System.out.print("Enter the day of the shift: ");
        int day = reader.nextInt();
        System.out.print("Enter day part (m for Morning, e for Evning): ");
        String st = reader.next();
        System.out.print("Please enter the employee's ID: ");
        String id = reader.next();
        System.out.print("Enter the type of the employee: ");
        System.out.println("for shift manager: write shm\nfor store keeper: write sk\nfor Sales Man: write sm\nfor security: write se\nfor clean: write c\nfor usher: write u\nfor general: write g");
        String type = reader.next();
        reader.nextLine();
        System.out.println("Enter the branch of the shift: ");
        String branch = reader.nextLine();
        Response res= shiftService.addWorkerToShift(year, month, day, st, id, type, branch);
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
    }

    public static void showHistoryOfShifts(){
        Response res =shiftService.showHistoryOfShifts();
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
    }

    public static void addRoleToShift(){
        System.out.print("Enter year: ");
        int year = reader.nextInt();

        System.out.print("Enter month: ");
        int month = reader.nextInt();

        System.out.print("Enter day: ");
        int day = reader.nextInt();

        System.out.print("Enter day (m for Morning, e for Evning): ");
        String st = reader.next();

        System.out.print("Enter role: ");
        System.out.println("\nfor shift manager: write shm\nfor store keeper: write sk\nfor Sales Man: write sm\nfor security: write se\nfor clean: write c\nfor usher: write u\nfor general: write g");
        String role = reader.next();

        System.out.print("Enter quantity: ");
        int quantity = reader.nextInt();
        reader.nextLine();
        System.out.print("Enter branch: ");
        String branch = reader.nextLine();

        Response res=shiftService.addRoleToShift(year, month, day, st, role, quantity, branch);
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
    }

    public static void showShiftEvents(){
        System.out.print("Enter year: ");
        int year = reader.nextInt();
        System.out.print("Enter month: ");
        int month = reader.nextInt();
        System.out.print("Enter day: ");
        int day = reader.nextInt();
        System.out.print("Enter day (m for Morning, e for Evning): ");
        String st = reader.next();
        reader.nextLine();
        System.out.print("Enter branch: ");
        String branch = reader.nextLine();

        Response res= shiftService.shiftEvents(year, month, day, st, branch);
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
    }

    public static void showAvailableWorkersForShiftByRole(){
        System.out.println("Enter year:");
        int year = reader.nextInt();
        System.out.println("Enter month:");
        int month = reader.nextInt();
        System.out.println("Enter day:");
        int day = reader.nextInt();
        System.out.print("Enter day (m for Morning, e for Evning): ");
        String st = reader.next();
        System.out.println("Enter role:");
        System.out.println("\nfor shift manager: write shm\nfor store keeper: write sk\nfor Sales Man: write sm\nfor security: write se\nfor clean: write c\nfor usher: write u\nfor general: write g");
        String role = reader.next();

        Response res =shiftService.showAvailableWorkersForShiftByRole(year, month, day, st, role);
        try{
            if(!res.isErrorResponse()){
                List<String> lst = (List<String>) res.ReturnValue;
                for (String st1:lst)
                    System.out.println(st1);
            }
            else{
                System.out.println(res.ErrorMsg);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void publishShift(){
        System.out.println("Enter year:");
        int year = reader.nextInt();

        System.out.println("Enter month:");
        int month = reader.nextInt();

        System.out.println("Enter day:");
        int day = reader.nextInt();

        System.out.print("Enter day (m for Morning, e for Evning): ");
        String st = reader.next();
        reader.nextLine();
        System.out.println("Enter branch:");
        String branch = reader.nextLine();

        Response res=shiftService.PublishShift(year, month, day, st, branch);
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
    }

    public static void addShift(){
        System.out.print("Please enter the year: ");
        int year = reader.nextInt();

        System.out.print("Please enter the month: ");
        int month = reader.nextInt();

        System.out.print("Please enter the day: ");
        int day = reader.nextInt();

        System.out.print("Please enter the start time  ");
        int startTime = reader.nextInt();


        System.out.println("enter shift period");
        int time=reader.nextInt();

        System.out.print("Enter day time (m for Morning, e for Evning): ");
        String shiftType = reader.next();
        reader.nextLine();
        System.out.print("Please enter the branch: ");
        String branch = reader.nextLine();
        Response res=shiftService.addShift(year, month, day, startTime,time, shiftType,branch);
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
    }

    public static void getCancelationsByShift(){
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

        Response res =shiftService.getCancelationsByShift(year, month, day, shiftType, branch);
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
    }
//-------------------EmploySerivce-------------





    public static void restrictEmployee(){

        System.out.print("Please enter the year: ");
        int year = reader.nextInt();

        System.out.print("Please enter the month: ");
        int month = reader.nextInt();

        System.out.print("Please enter the day: ");
        int day = reader.nextInt();

        System.out.print("Enter day (m for Morning, e for Evning): ");
        String shiftType = reader.next();

        System.out.print("Please enter the employee's ID: ");
        String empID = reader.next();

        Response res= employeeService.restrictEmployee(year, month, day, shiftType, empID);

    }
    public static void showAvailableWorkersForShift(){
        System.out.print("Please enter the year: ");
        int year = reader.nextInt();

        System.out.print("Please enter the month: ");
        int month = reader.nextInt();

        System.out.print("Please enter the day: ");
        int day = reader.nextInt();

        System.out.print("Enter day (m for Morning, e for Evning): ");
        String st = reader.next();

        Response res=shiftService.showAvailableWorkersForShift(year, month, day, st);
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
    }

}
