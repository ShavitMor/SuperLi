package PresentationLayer.GUI.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePicker {

    private JFrame frame;
    private JTextField dateField;

    public DatePicker() {}

    private void runDatePicker(VoidStringUpdater vsu) {
        JPanel datePickerPanel = new JPanel();
        datePickerPanel.setLayout(new FlowLayout());

        // Create a JDatePicker or any other custom date picker component here
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy");
        spinner.setEditor(dateEditor);
        spinner.setValue(new Date());

        datePickerPanel.add(spinner);

        int result = JOptionPane.showOptionDialog(
                frame,
                datePickerPanel,
                "Select Date",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        if (result == JOptionPane.OK_OPTION) {
            Date selectedDate = (Date) spinner.getValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = dateFormat.format(selectedDate);
            dateField.setText(formattedDate);
            vsu.updateString(formattedDate);
        }
    }

    public void connectButtonAndTextBox(JButton b, JTextField tf, JFrame parentFrame,VoidStringUpdater vsu){
        frame= parentFrame;
        dateField = tf;
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runDatePicker(vsu);
            }
        });
    }
}
