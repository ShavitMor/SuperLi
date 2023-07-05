package PresentationLayer.GUI.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseFromComboPane {
    JComboBox chooser;
    String[] DataToValueMap;
    JFrame parentFrame;

    public ChooseFromComboPane(){}



    public void setData(String[] comboBoxData, String[] DataTOValueMap,String current,JFrame parent){
        parentFrame = parent;
        setComboAndDataValues(comboBoxData,DataTOValueMap);
        for (int i = 0; i < DataToValueMap.length; i++) {
            if(DataToValueMap[i]!= null && DataToValueMap[i].equals(current)){
                chooser.setSelectedIndex(i);
            }
        }

    }

    public void setData(String[] comboBoxData, String[] DataTOValueMap,JFrame parent){
        parentFrame = parent;
        chooser = new JComboBox<>(comboBoxData);
        DataToValueMap = DataTOValueMap;

    }

    public void run(VoidStringUpdater vsu,String title){
        JPanel chooserPanel = new JPanel();
        chooserPanel.setLayout(new FlowLayout());
        chooserPanel.add(chooser);
        if(handleEmpty()){
            return;
        }

        int result = JOptionPane.showOptionDialog(
                parentFrame,
                chooserPanel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        if (result == JOptionPane.OK_OPTION) {
            int selectedOption = chooser.getSelectedIndex();
            if(selectedOption > -1){
                vsu.updateString(DataToValueMap[selectedOption]);
            }
        }
    }
    private void setComboAndDataValues(String[] comboBoxData, String[] DataTOValueMap){
        String[] updatedCData = new String[comboBoxData.length+1];
        String[] updatedData = new String[DataTOValueMap.length+1];
        updatedData[0] = null;
        updatedCData[0] = "Unassigned";
        for (int i = 0; i < comboBoxData.length; i++) {
            updatedData[i+1] = DataTOValueMap[i];
            updatedCData[i+1] = comboBoxData[i];
        }
        chooser = new JComboBox<>(updatedCData);
        this.DataToValueMap = updatedData;
    }

    public boolean handleEmpty(){
        if (chooser.getItemCount() == 0){
            JOptionPane.showMessageDialog(null, "No items to chooseFrom!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
}
