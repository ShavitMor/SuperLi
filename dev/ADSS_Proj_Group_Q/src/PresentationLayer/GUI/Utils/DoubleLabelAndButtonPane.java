package PresentationLayer.GUI.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoubleLabelAndButtonPane extends JPanel {

    public JLabel firstLabel;
    public JLabel secondLabel;
    public JButton button;

    public static final Rectangle defaultPanePos = new Rectangle(30,30,160,40);

    public DoubleLabelAndButtonPane(String first,String second, String buttonText){
        super();
        setLayout(null);
        this.firstLabel = new JLabel(first);
        add(firstLabel);
        firstLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstLabel.setFont(new Font(firstLabel.getFont().getFontName(),Font.PLAIN,14));
        firstLabel.setVisible(true);
        this.secondLabel = new JLabel(second);
        add(secondLabel);
        secondLabel.setHorizontalAlignment(SwingConstants.CENTER);
        secondLabel.setFont(new Font(secondLabel.getFont().getFontName(),Font.PLAIN,14));
        secondLabel.setVisible(true);
        setBounds(defaultPanePos);
        button = new JButton(buttonText);
        add(button);
        button.setVisible(true);
        firstLabel.setBounds(10,10,140,30);
        secondLabel.setBounds(110,10,140,30);
        button.setBounds(230,10,130,30);

    }

    public JButton getButton(){
        return button;
    }

    public void setActionController(ActionListener a){

    }
}
