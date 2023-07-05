package PresentationLayer.GUI.SiteGUI;

import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class SiteManagerModel extends DefaultTableModel {
    static final Object [] headers = {"ID","Name","Type","Address"};
    Object [][] sites;
    String [] siteTypesFilter = {"None","Branch","Supplier","Logistic Center"};


    public SiteManagerModel(Object[][] sites){
        super(sites, headers);
        this.sites = sites;
    }

    public void updateSitesInfo(Object[][] filteredSites){
        sites = filteredSites;
    }

    public Object getIDValueAt(int row, int col) {
        if (sites == null || sites.length == 0) {
            return "No entries";
        } else {
            return sites[row][0];
        }
    }
    @Override
    public boolean isCellEditable(int row,int column){
        return false;
    }
}
