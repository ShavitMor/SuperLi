package PresentationLayer.GUI.LoginGUI;

import PresentationLayer.GUI.MainShippingGUI.MainShippingView;
import ServiceLayer.Response;
import ServiceLayer.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewController implements ActionListener {
    JButton loginB;
    JButton registerB;
    String username ="";
    String password = "";
    LoginGuiModel model;
    LoginGuiView LoginView;

    public LoginViewController(JButton loginB, JButton registerB, LoginGuiModel model, LoginGuiView view){
        this.loginB = loginB;
        this.registerB = registerB;
        this.model = model;
        this.LoginView = view;
        loadData();
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object pressed = e.getSource();
        if(pressed == registerB){
            register();
        }
        else {
            login();
        }
    }

    private void login(){
        username = model.usernameInput;
        password = model.passInput;
        System.out.println("username: " + username);
        System.out.println("pass: " + password);
        MainShippingView.run();
        LoginView.dispose();
    }
    private void register(){
        System.out.println("REGISTERRRRR");
    }

    private static void loadData(){
        Response res;
        res = ServiceFactory.getInstance().loadData();
        if (res.isErrorResponse()){
            System.out.println(res);
            res = ServiceFactory.getInstance().loadData();
            if (res.isErrorResponse()){
                System.out.println(res);
            }
        }
    }
}


