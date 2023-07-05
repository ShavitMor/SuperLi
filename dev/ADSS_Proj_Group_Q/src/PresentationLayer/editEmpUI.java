package PresentationLayer;

import BusinessLayer.EmployeeController;
import BusinessLayer.ShiftController;
import ServiceLayer.EmployeeService;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import java.util.Scanner;

public class editEmpUI {
    static Scanner reader = new Scanner(System.in);
    static ShiftService shiftService=new ShiftService();
    static EmployeeService employeeService=new EmployeeService();
    static HRUI hrUi =new HRUI();

    public void run(String id1){
            boolean termiEdit=false;

            while(!termiEdit) {



                    System.out.println("enter the employee's id you want to edit ");
                    System.out.println("enter X to go back");
                    String id = reader.next();
                    if(id.equals("X")){
                        return;
                    }


                    if(employeeService.getWorkerName(id)!=null){

                    if (employeeService.getDriverLicense(id) != null) {

                        System.out.println("1: update driver's state");
                        System.out.println("2: set temp controll");
                        System.out.println("3: set bonus for driver");
                        System.out.println("4: set salary");
                        System.out.println("5: set terms");
                        System.out.println("6: set bank");
                        System.out.println("7: set bonus for driver");
                        System.out.println("8: set license Number");
                        System.out.println("9: set license type");
                        System.out.println("0: cancel   ");



                        int press = reader.nextInt();

                        switch (press) {
                            case (1):
                                updateState(id);
                                break;
                            case (2):
                                setTempControll(id);
                                break;
                            case (3):
                                setBonus(id);
                                break;
                            case (4):
                                setSalary(id);
                                break;
                            case (5):
                                setTerms(id);
                                break;
                            case (6):
                                setBank(id);
                                break;
                            case (7):
                                setBonus(id);
                                break;
                            case (8):
                                setLicenseNumber(id);
                                break;
                            case (9):
                                setLicenseType(id);
                                break;
                            case (0):
                                termiEdit = true;
                                break;
                        }

                    } else {

                        System.out.println("1: update employee's state");
                        System.out.println("2: add role to employee");
                        System.out.println("3: set bonus for employee");
                        System.out.println("4: set salary");
                        System.out.println("5: set terms");
                        System.out.println("6: set bank");
                        System.out.println("0: BACK");

                        int press = reader.nextInt();

                        switch (press) {
                            case (1):
                                updateState(id);
                                break;
                            case (2):
                                addRole(id);
                                break;
                            case (3):
                                setBonus(id);
                                break;
                            case (4):
                                setSalary(id);
                                break;
                            case (5):
                                setTerms(id);
                                break;
                            case (6):
                                setBank(id);
                                break;
                            case (0):
                                termiEdit = true;
                                break;
                        }
                    }
                    }
                    else
                        System.out.println("please try again");

                }
            }
        public static void setBonus(String ID){

            System.out.println("please enter the bonus: ");
            int bonus=reader.nextInt();

            employeeService.setBonus(ID, bonus);

        }
        public static void setBank(String empId){

            System.out.print("Enter bank company name: ");
            reader.nextLine();
            String bankCompany = reader.nextLine();

            System.out.print("Enter bank branch number: ");
            int bankBranch = reader.nextInt();

            System.out.print("Enter bank ID: ");
            int bankId = reader.nextInt();

            Response res= employeeService.setBank(empId, bankCompany, bankBranch, bankId);
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

        public static void setTerms(String empId){


            System.out.print("Enter new terms: ");
            String terms = null;
            while (terms==null ||  terms.isBlank()){
                terms = reader.nextLine();
            }
            Response res=employeeService.setTerms(empId, terms);
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

        public static void setSalary(String empId) {



            System.out.print("Enter new salary: ");
            int salary = reader.nextInt();

            Response res= employeeService.setSalary(empId, salary);
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

        public static void setLicenseNumber(String driverId){

            System.out.print("Enter new license number: ");
            String licenceNumber = null;
            while (licenceNumber==null ||  licenceNumber.isBlank()){
                licenceNumber = reader.nextLine();
            }

            Response res= employeeService.setLicenceNumberString(driverId, licenceNumber);
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

        public static void setLicenseType(String driverId) {


            System.out.print("Enter new license type: ");
            String licenceType = reader.nextLine();

            Response res= employeeService.setLisenceType(driverId, licenceType);
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
        public static void setTempControll(String driverId) {


            System.out.print("Enter new temperature control setting: ");
            String tempControlled = null;
            while (tempControlled==null ||  tempControlled.isBlank()){
                tempControlled = reader.nextLine();
            }

            Response res= employeeService.setTempControlled(driverId, tempControlled);
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
        public static void updateState(String ID){
            System.out.println("please enter state: ");
            String state = null;
            while (state==null ||  state.isBlank()){
                state = reader.nextLine();
            }

            Response res= employeeService.updateState(ID, state);
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



        public static void addRole(String id){
            System.out.println("Please enter the role:");
            System.out.println("\nfor shift manager: write shm\nfor store keeper: write sk\nfor Sales Man: write sm\nfor security: write se\nfor clean: write c\nfor usher: write u\nfor general: write g");
            String role = reader.next();

            Response res= employeeService.addRoleToEmployee(id, role);
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


