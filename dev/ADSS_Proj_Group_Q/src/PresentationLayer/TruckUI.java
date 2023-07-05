
package PresentationLayer;

import ServiceLayer.Response;
import ServiceLayer.TruckService;

import java.util.Arrays;
import java.util.Objects;

public class TruckUI {

    TruckService Ts;

    TruckUI(TruckService ts) {
        Ts = ts;
    }

    public void run() {
        String userIn;
        boolean abort = false;
        String[] validInput = {"0", "1", "2", "X"};
        while (!abort) {
            do {
                System.out.println("Manage truck here:");
                System.out.println("Enter 0 to view all current listed trucks.");
                System.out.println("Enter 1 to add a new truck.");
                System.out.println("Enter 2 to search, view, edit and remove trucks");
                System.out.println("Enter X to return to the PresentationLayer.main window");
                userIn = MainShippingUI.input.nextLine();
                switch (userIn) {
                    case "X":
                        abort = true;
                        break;
                    case "0":
                        System.out.println("Currently, a total of " + (Integer) Ts.getAmountOfListedTrucks().ReturnValue + " are listed \n");
                        if((Integer) Ts.getAmountOfListedTrucks().ReturnValue >0){
                            System.out.println("Current listed trucks:\n");
                            System.out.println("id \t licence number \t model \t truck weight \t max carry weight \t temperatureControl:");
                        }
                        System.out.println(Ts.getTruckTable().toString());
                        break;
                    case "1":
                        addTruckUi();
                        break;
                    case "2":
                        findTruckUi();
                        break;
                    default:
                        System.out.println("Please enter valid input");
                }
            }
            while (!Arrays.stream(validInput).anyMatch(userIn::equals));
        }
    }

    private void addTruckUi() {
        String userIn = "";
        String[] newTruckInfo = new String[5];
        for (String s :
                newTruckInfo) {
            s = "";
        }
        boolean abort = false;
        Response res;
        final String nullVal = "Unassigned";
        while (!abort) {
            System.out.println("Enter 1 to enter the requested licence number.\n");
            System.out.println("Enter 2 to enter the requested model.\n");
            System.out.println("Enter 3 to enter the requested weight (KG).\n");
            System.out.println("Enter 4 to enter the requested max carry weight (kg).\n");
            System.out.println("Enter 5 to choose temperature control.\n");
            System.out.println("Enter V after entering all data to create new truck.\n");
            System.out.println("Enter X to cancel.\n");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "X":
                    return;
                case "1":
                    System.out.println("Enter valid licence number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.isTruckNumberGood(userIn);
                    if (res.isErrorResponse()) {
                        MainShippingUI.printInRedBackGround(res.toString());
                    }
                    else {
                        newTruckInfo[0] = userIn;
                    }
                    break;
                case "2":
                    System.out.println("Enter model:");
                    userIn = MainShippingUI.input.nextLine();
                    newTruckInfo[1] = userIn;
                    break;
                case "3":
                    System.out.println("Enter valid truck weight:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.isTruckWeightValid(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                    } else {
                        newTruckInfo[2] = userIn;
                    }
                    break;
                case "4":
                    System.out.println("Enter valid truck max carry weight:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.isTruckCarryWeightValid(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                    } else {
                        newTruckInfo[3] = userIn;
                    }
                    break;
                case "5":
                    chooseTempControlUI(newTruckInfo, userIn);
                    break;
                case "V":
                    if(addTruck(newTruckInfo)){
                        return;
                    }
                default:
                    System.out.println("Please enter valid input.");
            }
            System.out.println(String.format
                    ("Current input:\n\tlicence number: %s \n\tmodel:  %s\n\t weight:  %s\n\t max carry weight:  %s \n\t Temp. control: %s\n",
                            Objects.toString(newTruckInfo[0], nullVal), Objects.toString(newTruckInfo[1], nullVal),
                            Objects.toString(newTruckInfo[2], nullVal), Objects.toString(newTruckInfo[3], nullVal), Objects.toString(newTruckInfo[4], nullVal)));

        }
    }

    private boolean addTruck(String[] info) {
        Response res;
        String userIn;
        for (String s : info) {
            if (s == null || s.trim().isEmpty()) {
                System.out.println("Please enter all required info.");
                return false;
            }
        }
        System.out.println("Create a new truck with given data?");
        System.out.println(String.format
                ("Current input:\n\tlicence number: %s \n\tmodel:  %s\n\t weight:  %s\n\t max carry weight:  %s\n\t temp.Control: %s  \n",
                        info[0], info[1], info[2], info[3], info[4]));
        System.out.println("Enter Y/N");
        do {
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "Y":
                    res = Ts.addNewTruck(info[0], info[1], info[2], info[3], info[4]);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        return false;
                    } else if (!(boolean)res.ReturnValue ) {
                        System.out.println("Failed creating truck!");
                        return false;
                    } else {
                       MainShippingUI.printInGreenBackGround("Truck added successfully!");
                        return true;
                    }

                case "N":
                    break;
                default:
                    System.out.println("Please enter valid input.");
            }
        } while (true);
    }

    private void findTruckUi() {
        String userIn;
        Response res;
        boolean abort = false;
        while (!abort) {
            System.out.println("Enter 1 to select truck by index");
            System.out.println("Enter 2 to select truck by licence number");
            System.out.println("Enter X to  return to the Manage Trucks Screen");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "1":
                    System.out.println("Enter requested truck index:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.getTruckbyId(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editTruckUi(userIn);
                    break;
                case "2":
                    System.out.println("Enter requested truck licence number:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.getTruckIdByLicence(userIn);
                    if (res.isErrorResponse()) {
                        System.out.println(res);
                        break;
                    }
                    editTruckUi(res.ReturnValue.toString());
                    break;
                case "X":
                    abort = true;
                    return;
                default:
                    System.out.println("Please enter valid input");
            }
        }
    }

    private void editTruckUi(String id) {
        String userIn;
        Response res;
        boolean abort = false;
        while (!abort) {
            System.out.println("Currently editing:\n");
            System.out.println(Ts.truckToString(id) + "\n");
            System.out.println("Enter 1 to change Licence Number");
            System.out.println("Enter 2 to change model");
            System.out.println("Enter 3 to change truck weight");
            System.out.println("Enter 4 to change max carry weight");
            System.out.println("Enter 5 to switch temperature control status");
//            System.out.println("Enter D to delete the truck");
            System.out.println("Enter X to return");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "1":
                    System.out.println("Please enter licence number to change to:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.editLicenceNumber(id, userIn);
                    if (res.isErrorResponse())
                        System.out.println(res);
                    else {
                        System.out.println("Licence number changed successfully");
                    }
                    break;
                case "2":
                    System.out.println("Please enter model to change to:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.editModel(id, userIn);
                    if (res.isErrorResponse())
                        System.out.println(res);
                    else {
                        System.out.println("Model changed successfully");
                    }
                    break;
                case "3":
                    System.out.println("Please enter weight to change to:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.editTruckWeight(id, userIn);
                    if (res.isErrorResponse())
                        System.out.println(res);
                    else {
                        System.out.println("weight changed successfully");
                    }
                    break;
                case "4":
                    System.out.println("Please enter max carry weight to change to:");
                    userIn = MainShippingUI.input.nextLine();
                    res = Ts.editTruckCarryWeight(id, userIn);
                    if (res.isErrorResponse())
                        System.out.println(res);
                    else {
                        System.out.println("Max carry weight changed successfully");
                    }
                    break;
                case "5":
                    res = Ts.switchTempControl(id);
                    if (res.isErrorResponse())
                        System.out.println(res);
                    else {
                        System.out.println("changed temperature control successfully");
                    }
                    break;
//                case "D":
//                    do {
//                        System.out.println("Are you sure you wish to delete this truck? (Y/N)");
//                        userIn = MainShippingUI.input.nextLine();
//                        switch (userIn) {
//                            case "Y":
//                                res = Ts.deleteTruck(id);
//                                if (res.isErrorResponse()) {
//                                    System.out.println(res);
//                                } else {
//                                    System.out.println("Truck removed successfully!");
//                                }
//                                return;
//                            case "N":
//                                break;
//                            default:
//                                System.out.println("Please enter valid input.");
//                        }
//                    } while (!(userIn.equals("Y") || userIn.equals("N")));
                case "X":
                    abort = true;
                    return;
                default:
                    System.out.println("Please enter valid input");
            }
        }
    }

    public void chooseTempControlUI(String[] info, String userIn) {
        while (true) {
            System.out.println("does truck have temperature control?(Y/N)");
            userIn = MainShippingUI.input.nextLine();
            switch (userIn) {
                case "Y":
                    info[4] = "true";
                    return;
                case "N":
                    info[4] = "false";
                    return;
                default:
                    System.out.println("Please enter valid input");
            }
        }
    }
}


