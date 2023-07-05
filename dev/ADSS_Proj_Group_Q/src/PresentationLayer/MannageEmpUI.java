package PresentationLayer;

import ServiceLayer.EmployeeService;
import ServiceLayer.Response;

import java.util.ArrayList;
import java.util.Scanner;

public class MannageEmpUI {
    static Scanner reader = new Scanner(System.in);
    static EmployeeService employeeService;
    editEmpUI  editEmp;
    public MannageEmpUI(EmployeeService employeeService){
        this.employeeService=employeeService;
        editEmp=new editEmpUI();
    }
    public void run(String id){
        boolean termiManager = false;
        while (!termiManager) {
            System.out.println("\n");
            System.out.println("1: show all workers");
            System.out.println("2: get employee's month salary");
            System.out.println("3: add new worker");
            System.out.println("4: remove worker (stay in system but cant login and add constraints)");
            System.out.println("5: add driver");
            System.out.println("6: edit employee");
            System.out.println("0: back");

            int press = reader.nextInt();
            switch (press){
                case (1):
                    showWorkers();
                    break;
                case (2):
                    getMonthSalary();
                    break;
                case (3):
                    addNewWorker();break;
                case (4):
                    removeWorker();break;
                case (5):
                    addDriver();
                    break;
                case (6):
                    editEmp.run(id);
                    break;
                case (0):
                    termiManager=true;
            }

        }

    }
    public static void showWorkers(){


        Response res=employeeService.showWorkers();
        try{
            if(!res.isErrorResponse()){
                ArrayList<String> workers= (ArrayList<String>) res.ReturnValue;
                for(String emp :workers){
                    System.out.println(emp);
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
    public static void getMonthSalary(){
        System.out.print("Please enter the employee's ID: ");
        String ID=reader.next();
        System.out.println("please enter the month: ");
        int month=reader.nextInt();

        Response res= employeeService.getMonthSalary(ID, month);
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
    public static void addNewWorker(){

        System.out.print("Please enter the employee's ID: ");
        String id = reader.next();
        System.out.print("Enter name please: ");
        reader.nextLine();
        String name = reader.nextLine();
        System.out.print("Enter bank company please: ");
        String bankCompany = reader.nextLine();
        System.out.print("Enter bank branch please: ");
        int mbankBranch = reader.nextInt();
        System.out.print("Enter bank id please: ");
        int mbankId = reader.nextInt();
        System.out.print("Enter salary please: ");
        double msalary = reader.nextDouble();
        System.out.print("Enter terms please: ");
        reader.nextLine();
        String mterms = reader.nextLine();
        System.out.print("Enter state please: ");
        String state = reader.nextLine();
        System.out.print("Enter password please: ");
        String password = reader.next();


        Response res = employeeService.addNewWorker(id, name, bankCompany, mbankBranch, mbankId, msalary, mterms, state, password);

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

    public static void removeWorker(){
        System.out.print("Please enter the employee's ID: ");
        String ID=reader.next();
        Response res=employeeService.removeWorker(ID);
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

    public static void addDriver(){

        System.out.println("Please enter the member's ID:");
        String mid = reader.next();
        reader.nextLine(); // consume the new line character
        System.out.println("Please enter the member's name:");
        String mname = reader.next();
        reader.nextLine();
        System.out.println("Please enter the member's bank company:");
        String mbankCompany = reader.next();
        reader.nextLine();
        System.out.println("Please enter the member's bank branch:");
        int mbankBranch = reader.nextInt();
        System.out.println("Please enter the member's bank ID:");
        int mbankId = reader.nextInt();
        System.out.println("Please enter the member's salary:");
        double msalary = reader.nextDouble();
        System.out.println("Please enter the member's terms and conditions:");
        reader.nextLine();
        String mterms = reader.nextLine();
        System.out.println("Please enter the member's state:");
        String state = reader.nextLine();
        System.out.println("Please enter the member's password:");
        String password = reader.next();
        System.out.println("Please enter the member's license type: (B/C/C1)");
        String licenceType = reader.next();
        System.out.println("Please enter the member's license number:");
        String licenceNumber = reader.next();
        System.out.println("Please enter the member's temperature controlled license (t/f):");
        String tempControlledLicence = reader.next();

        Response res=employeeService.addNewDriver(mid,mname,mbankCompany,mbankBranch,mbankId,msalary,mterms,state,password,licenceType,licenceNumber,tempControlledLicence);
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
