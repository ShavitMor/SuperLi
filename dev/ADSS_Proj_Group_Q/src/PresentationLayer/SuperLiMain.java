package PresentationLayer;

import BusinessLayer.EmployeeController;
import GUI.HrUI;
import GUI.StoreManagerWindow;
import PresentationLayer.GUI.SuperLiMainGUI;

public class SuperLiMain {
    public static void main(String[] args) {

        if (args.length < 2) {
            if (args[0].equals("CLI")) {
                LoginUI.main(args);
            } else if (args[0].equals("GUI")) {
                GUI.LoginUI.main(args);
            }
        } else if (args.length == 2) {
            if (args[0].equals("GUI")) {

                if (args[1].equals("StoreManager")) {
                    StoreManagerWindow.main(args);
                }
                if (args[1].equals("HRManager")) {
                    HrUI.main(args);
                }


            } else if (args[0].equals("CLI")) {
                if (args[1].equals("HRManager")) {
                    HRUI hrui = new HRUI();
                    hrui.run(EmployeeController.getInstance().getHumanResurceId());
                }
            }

        }
    }
}