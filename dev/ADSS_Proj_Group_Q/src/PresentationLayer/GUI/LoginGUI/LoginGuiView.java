package PresentationLayer.GUI.LoginGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Function;

public class LoginGuiView extends JFrame{
    Component[] comps;
    LoginGuiModel model = new LoginGuiModel();
    LoginViewController loginController;
    public LoginGuiView(){
        super("SuperLi management Login");
        this.setLayout(null);
        this.setBounds(200,200,400,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setComps();
        setAllVisible(comps);
    }

    public static void run(){
        LoginGuiView login = new LoginGuiView();
        login.setVisible(true);
    }

    public static void main(String[] args){
        LoginGuiView.run();
    }

    private void setComps(){
        JLabel header = new JLabel(LoginGuiModel.header);
        header.setSize(700,40);
        header.setFont(new Font(header.getFont().getFontName(),Font.BOLD,20));
//        header.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel userLabel = new JLabel(LoginGuiModel.userName);
        userLabel.setSize(100,50);
        userLabel.setLocation(40,70);
        JLabel pass = new JLabel(LoginGuiModel.pass);
        pass.setSize(100,50);
        pass.setLocation(40,100);
        JTextField userNameBox = new JTextField(model.usernameInput);
        userNameBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                model.updateUsernameInput(userNameBox.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                model.updateUsernameInput(userNameBox.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                model.updateUsernameInput(userNameBox.getText());
            }
        });

        userNameBox.setSize(200,20);
        userNameBox.setLocation(110,88);
        JPasswordField passBox = new JPasswordField(model.passInput);
        passBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                model.updatePasswordInput(passBox.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                model.updatePasswordInput(passBox.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                model.updatePasswordInput(passBox.getText());
            }
        });
        passBox.setSize(200,20);
        passBox.setLocation(110,118);
        JButton registerB = new JButton("Register");
        registerB.setBounds(200,150,100,50);
        JButton loginB = new JButton("Login");
        loginController = new LoginViewController(loginB,registerB,model,this);
        loginB.addActionListener(loginController);
        registerB.addActionListener(loginController);
        loginB.setBounds(80,150,100,50);
        Component[] comps = {header,userLabel,pass,userNameBox,passBox,registerB,loginB };
        this.comps = comps;
    }


    private void setAllVisible(Component[] comps){
        for (Component comp : comps){
            this.add(comp);
            comp.setVisible(true);
        }
    }




}
