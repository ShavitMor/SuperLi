package PresentationLayer.GUI.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LabelAndTextboxJPane extends JPanel {

    public JLabel label;
    public JTextField inputField;

    public static final Rectangle defaultPanePos = new Rectangle(30,30,160,40);


    public LabelAndTextboxJPane(String labelText){
        super();
        setLayout(null);
        this.label = new JLabel(labelText);
        inputField = new JTextField();
        add(label);
        add(inputField);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVisible(true);
        inputField.setVisible(true);
        setBounds(defaultPanePos);
        label.setBounds(10,10,100,40);
        inputField.setBounds(130,10,150,30);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public void setTextBoxAction(Action a){
        inputField.setAction(a);
    }
    public void setTextBoxDocumentListener(DocumentListener dl){
        inputField.getDocument().addDocumentListener(dl);
    }

    public void setTextBoxColor(boolean isValidInput){
        inputField.setForeground(isValidInput ? Color.GREEN : Color.RED);
    }

    public String getInputText() {
       return inputField.getText();
    }


    public void addDocumentListenersToTextBoxes(LabelAndTextboxJPane lTP, StringInputValidator ivi, VoidStringUpdater vsu){
        lTP.setTextBoxDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                vsu.updateString(lTP.inputField.getText());
                lTP.setTextBoxColor(ivi.isValidString(lTP.inputField.getText()));
            }
        });
    }
}
