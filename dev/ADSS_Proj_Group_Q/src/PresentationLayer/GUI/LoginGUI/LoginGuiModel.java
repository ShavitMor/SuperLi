package PresentationLayer.GUI.LoginGUI;

public class LoginGuiModel {
    static String header = "SuperLi management module:";
    static String userName = "Username:";
    static String pass = "Password:";
    String passInput = "password";
    String usernameInput = "username";

    public void updateUsernameInput(String text) {
        usernameInput = text;
    }
    public void updatePasswordInput(String text) {
        passInput = text;
    }
}
