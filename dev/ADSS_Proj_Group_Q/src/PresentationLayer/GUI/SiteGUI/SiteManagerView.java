package PresentationLayer.GUI.SiteGUI;

import BusinessLayer.SiteManager;
import PresentationLayer.GUI.MainShippingGUI.MainShippingView;
import ServiceLayer.SiteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SiteManagerView extends JFrame {

    Component[] comps;
    SiteManagerModel model ;
    SiteManagerViewController controller;
    SiteService ss = new SiteService(SiteManager.getInstance());
    JTable siteTable;
    boolean viewInactive = false;
    private final int offset = 30;
    JFrame toReturn;

    public SiteManagerView(JFrame home){
        super("Site Manager");
        toReturn = home;
        this.setLayout(null);
        this.setBounds(200,200,450,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new SiteManagerModel(getSiteInfo(true));
        setComps();
        setButtonsActionListener();
        setCompsBounds();
        setAllVisible(comps);
    }
    public static void run(JFrame toReturn){
        new SiteManagerView(toReturn).setVisible(true);
    }
    private void setComps() {
        JComboBox filterBar = new JComboBox(model.siteTypesFilter);
        filterBar.addActionListener(controller);
        JButton addSiteB = new JButton("+");
        JButton searchNameB = new JButton("Search by name");
        JButton searchIdB = new JButton("Search by ID");
        JButton returnB  = new JButton("Main Menu");
        siteTable = new JTable(model);
        JScrollPane tablePane = new JScrollPane(siteTable);
        siteTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        JLabel filterLabel = new JLabel("Filter by type:");
        siteTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    int row = siteTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Object value = siteTable.getValueAt(row, 0);
                        handleTableValueClick(value); // Call your function here
                    }
                }
            }
        });
        controller = new SiteManagerViewController(addSiteB,returnB,searchNameB,searchIdB,this,toReturn);
        siteTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        JCheckBox showInactive = new JCheckBox();
        showInactive.addActionListener(controller);
        this.viewInactive = showInactive.isSelected();
        JLabel inactiveL = new JLabel("Show inactive?") ;

        Component[] arr = {filterBar,siteTable,addSiteB,returnB,tablePane,searchNameB,searchIdB, filterLabel,showInactive,inactiveL};
        comps = arr;
    }

    private void setCompsBounds(){
        comps[0].setBounds(50-offset,520,120,30);
        comps[2].setBounds(390-offset,505,60,45);
        comps[3].setBounds(50-offset,5,100,20);
        comps[4].setBounds(50-offset,30,400,470);
        comps[5].setBounds(260-offset,505,125,20);
        comps[6].setBounds(260-offset,530,125,20);
        comps[7].setBounds(50-offset,500, 100, 20);
        comps[8].setBounds(195-offset,522,40,40);
        comps[9].setBounds(172-offset,500,100,20);

    }

    private void setAllVisible(Component[] comps){
        for (Component comp : comps){
            if(!(comp instanceof JTable)){
                this.add(comp);
            }
            comp.setVisible(true);
        }
    }

    private void setButtonsActionListener(){
        for (Component comp: comps){
            if(comp instanceof JButton){
                ((JButton) comp).addActionListener(controller);
            }
        }
        setComboBox();
    }

    public String[][] getSiteInfo(boolean startUp){
       return(String[][]) ss.getSiteTable(
               startUp ? "None" : ((JComboBox)comps[0]).getSelectedItem().toString(),viewInactive+"").ReturnValue;
    }

    private void handleTableValueClick(Object value) {
        // Perform your desired action here
        controller.runEditSite(value);
    }

    private void setComboBox(){
        ((JComboBox) comps[0]).addActionListener(controller);
    }

    public void updateTableInfo() {
        model.setDataVector(getSiteInfo(false),SiteManagerModel.headers);
        model.fireTableDataChanged();
        siteTable.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public void updateTableInfoAndComboBox() {
        ((JComboBox) comps[0]).setSelectedIndex(0);
        updateTableInfo();
    }
}
