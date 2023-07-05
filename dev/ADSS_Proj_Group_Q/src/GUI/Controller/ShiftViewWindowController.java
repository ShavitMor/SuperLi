package GUI.Controller;

import GUI.AddRoleToShiftUI;
import GUI.AddWorkerToShiftWindow;
import GUI.RegisterUI;
import GUI.ShiftViewWindow;
import Model.ShiftModel;
import ServiceLayer.Response;
import ServiceLayer.ShiftService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ShiftViewWindowController {
    ShiftService shiftService;
    JFrame cancellationsView;
    JFrame workerView;
    JFrame roleView=null;
    JFrame shiftEventsView=null;
    private ShiftModel shiftModel;
    public ShiftViewWindowController(ShiftViewWindow shiftViewWindow, ShiftModel shiftModel){
        shiftService=new ShiftService();
        this.shiftModel=shiftModel;
        shiftViewWindow.getAddWorkerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the view with combo boxes
                AddWorkerToShiftWindow addWorkerToShiftWindow=new AddWorkerToShiftWindow(shiftModel);
                addWorkerToShiftWindow.setVisible(true);
            }
        });

        shiftViewWindow.getAddRoleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddRoleToShiftUI addRoleToShiftUI=new AddRoleToShiftUI(shiftModel);
                addRoleToShiftUI.setVisible(true);
            }
        });


        shiftViewWindow.getShowShiftEventsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the view with a JList
                shiftEventsView = new JFrame("Shift Events");
                shiftEventsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shiftEventsView.setSize(300, 200);

                // Create the list model and add your shift events to it
                DefaultListModel<String> listModel = new DefaultListModel<>();
                Response response = shiftService.shiftEvents(shiftModel.getDate().getYear(),shiftModel.getDate().getMonth().getValue(),shiftModel.getDate().getDayOfMonth(),shiftModel.getShiftType().substring(0,1),shiftModel.getBranch());
                List<String> lst= (List<String>) response.ReturnValue;
                for(String s:lst)
                    listModel.addElement(s);
                // Create the JList with the list model
                JList<String> shiftEventsList = new JList<>(listModel);

                // Create a scroll pane and add the JList to it
                JScrollPane scrollPane = new JScrollPane(shiftEventsList);

                // Add the scroll pane to the shiftEventsView frame
                shiftEventsView.getContentPane().add(scrollPane);

                // Display the shiftEventsView frame
                shiftEventsView.setVisible(true);
            }
        });


        shiftViewWindow.getPublishShiftButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response res = shiftService.PublishShift(shiftModel.getDate().getYear(),shiftModel.getDate().getMonth().getValue(),shiftModel.getDate().getDayOfMonth(), shiftModel.getShiftType().substring(0,1), shiftModel.getBranch());
                if(res.isErrorResponse()){
                    JOptionPane.showMessageDialog(shiftViewWindow, res.ErrorMsg);
                }
                else {
                shiftModel.setPublished(true);
                }
            }
        });
        shiftViewWindow.getCancellationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the view with a JList
                cancellationsView = new JFrame("Cancellations");
                cancellationsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cancellationsView.setSize(300, 200);


                // Create the list model and add your cancellations to it
                DefaultListModel<String> dif=new DefaultListModel();
                ArrayList<String> branchList = (ArrayList<String>) shiftModel.getCancelations();
               for(String s:branchList)
                   dif.addElement(s);


                // Create the JList with the list model
                JList<String> cancellationsList = new JList<>(dif);

                // Create a scroll pane and add the JList to it
                JScrollPane scrollPane = new JScrollPane(cancellationsList);

                // Add the scroll pane to the cancellationsView frame
                cancellationsView.getContentPane().add(scrollPane);

                // Display the cancellationsView frame
                cancellationsView.setVisible(true);
            }
        });
        shiftViewWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Close all additional windows here
                if (cancellationsView != null) {
                    cancellationsView.dispose();
                }
                if (workerView != null) {
                    workerView.dispose();
                }
                if (shiftEventsView != null) {
                    shiftViewWindow.dispose();
                }
                if (roleView != null) {
                    roleView.dispose();
                }
            }
        });


    }
}
