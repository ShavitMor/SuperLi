package PresentationLayer.GUI.MainShippingGUI;

import PresentationLayer.GUI.LoginGUI.LoginGuiModel;
import PresentationLayer.GUI.LoginGUI.LoginViewController;
import PresentationLayer.MainShippingUI;

import javax.swing.*;
import java.awt.*;

public class MainShippingView extends JFrame {

    Component[] comps;
    MainShippingViewController controller;
    public MainShippingView(){
        super("Main Shipping View");
        this.setLayout(new GridLayout(4,1));
        this.setBounds(200,200,500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setComps();
        setButtonsActionListener();
        setAllVisible(comps);

    }

    public static void run(){
        new MainShippingView().setVisible(true);
    }

    private void setComps() {
        JLabel header = new JLabel("Choose what to manage:");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font(header.getFont().getFontName(),Font.PLAIN,18));
        JButton siteB = new JButton("Site Manager");
//        siteB.setBounds(200,150,100,50);
        JButton truckB = new JButton("Truck Manager");
//        truckB.setBounds(200,150,100,50);
        JButton shipB = new JButton("Shipping Manager");
//        shipB.setBounds(200,150,100,50);
        Component[] arr = {header,siteB, truckB, shipB};
        comps = arr;
        controller = new MainShippingViewController(siteB,truckB,shipB,this);

    }

    private void setAllVisible(Component[] comps){
        this.add(comps[0], BorderLayout.PAGE_START);
        this.add(comps[1], BorderLayout.CENTER);
        this.add(comps[2], BorderLayout.CENTER);
        this.add(comps[3], BorderLayout.PAGE_END);
        for (Component comp : comps){
            comp.setVisible(true);
        }
    }

    private void setButtonsActionListener(){
        for (Component comp: comps){
            if(comp instanceof JButton){
                ((JButton) comp).addActionListener(controller);
            }
        }
    }
}
