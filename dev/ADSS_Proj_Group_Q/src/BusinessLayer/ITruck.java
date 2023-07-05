package BusinessLayer;

import java.util.Observable;

public interface ITruck {

    boolean setLicenceNum(String newLicenceNum);
    String getLicenceNum();
    void setModel(String newModel);
    String getModel();
    int getId();
    boolean setWeight(double newWeight);
    double getWeight();
    boolean setMaxCarryWeight(double newCarryWeight);
    double getMaxCarryWeight();
    boolean isTempratureControlled();



}