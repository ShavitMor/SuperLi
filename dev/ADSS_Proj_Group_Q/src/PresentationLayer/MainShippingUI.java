
package PresentationLayer;

import BusinessLayer.ShiftController;
import ServiceLayer.EmployeeService;
import ServiceLayer.ServiceFactory;

import java.util.Scanner;

public class MainShippingUI {
    private static TruckUI TUI;
    private static ShippingUI SUI;
    private static SiteUI siteUI;
    static Scanner input = new Scanner(System.in);


    public MainShippingUI(){init();}
    public void runShippingUI(){
        run();
    }
    private static void init(){
        ServiceFactory SF = ServiceFactory.getInstance();
        TUI = new TruckUI(SF.createTruckService());
        SUI = new ShippingUI(SF.createShippingService());
        siteUI = new SiteUI();
    }

    private static void run(){
        System.out.println("Welcome to Super-Li Shipping control module!");
        while(true){
            boolean loopBreaker = selectManage();
            if (loopBreaker){
                break;
            }
        }
    }

    public static boolean selectManage(){
        boolean abort = false;
        while(!abort){
            String userInput;
            System.out.println("Please choose data to manage:\n ");
            System.out.println("\t Enter 1 to manage Trucks");
            System.out.println("\t Enter 2 to manage Shipments");
            System.out.println("\t Enter 3 to manage sites");
            System.out.println("\t Enter X to exit");
            do{
                userInput = input.nextLine();
                switch (userInput){
                    case "1":
                        TUI.run();
                        return false;
                    case "2":
                        SUI.run();
                        return false;
                    case "3":
                        siteUI.run();
                        return false;
                    case "X":
                        abort = true;
                        return true;
                    default:
                        System.out.println("Please enter valid input.");
                }
            }while(true);
        }

        return true;
    }

    public void callSiteUI(){
        siteUI.run();
    }
    public static void printInRedBackGround(String toPrint) {
        final String ANSI_RED_BACKGROUND = "\u001B[41m";
        final String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_RED_BACKGROUND + toPrint + ANSI_RESET);
    }
    public static void printInGreenBackGround(String toPrint) {
        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        final String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_GREEN_BACKGROUND + toPrint + ANSI_RESET);
    }
}

