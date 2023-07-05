package PresentationLayer.GUI.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class TimePicker {

    private JFrame frame;
    private JTextField timeField;

    public TimePicker() {}

    private void runTimePicker(VoidStringUpdater vsu) {
        JPanel timePickerPanel = new JPanel();
        timePickerPanel.setLayout(new FlowLayout());

        // Create hour and minute spinner models
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0, 23, 1);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);

        // Create hour and minute spinners
        JSpinner hourSpinner = new JSpinner(hourModel);
        JSpinner minuteSpinner = new JSpinner(minuteModel);

        timePickerPanel.add(hourSpinner);
        timePickerPanel.add(new JLabel(":"));
        timePickerPanel.add(minuteSpinner);

        int result = JOptionPane.showOptionDialog(
                frame,
                timePickerPanel,
                "Select Time",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        if (result == JOptionPane.OK_OPTION) {
            int selectedHour = (int) hourSpinner.getValue();
            int selectedMinute = (int) minuteSpinner.getValue();
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
            String formattedTime = selectedTime.toString();
            timeField.setText(formattedTime);
            vsu.updateString(formattedTime);
        }
    }

    public void connectButtonAndTextBox(JButton b, JTextField tf, JFrame parentFrame,VoidStringUpdater vsu){
        frame= parentFrame;
        timeField = tf;
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTimePicker(vsu);
            }
        });
    }
}
