package PresentationLayer.GUI.TruckGUI;

import javax.swing.table.DefaultTableModel;

public class TruckManagerModel extends DefaultTableModel {
    static final Object [] headers = {"ID","Licence Number","model","Cooling"};
    Object [][] trucks;
    String [] minimumLicenceTypes = {"None","B","C","C1"};


    public TruckManagerModel(Object[][] trucks){
        super(trucks, headers);
        this.trucks = trucks;
    }

    public void updateSitesInfo(Object[][] filteredSites){
        trucks = filteredSites;
    }

    public Object getIDValueAt(int row, int col) {
        if (trucks == null || trucks.length == 0) {
            return "No entries";
        } else {
            return trucks[row][0];
        }
    }
    @Override
    public boolean isCellEditable(int row,int column){
        return false;
    }
}
