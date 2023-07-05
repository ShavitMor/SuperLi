package PresentationLayer.GUI.SiteGUI.EditSiteGUI;

import PresentationLayer.GUI.SiteGUI.AddSiteGUI.SiteModel;

public class EditSiteModel extends SiteModel {
    String id;
    String isActive;

    public EditSiteModel(String[] arr){
        super();
        id = arr[0];
        updateName(arr[1]);
        updateAddress(arr[2]);
        updateContactName(arr[3]);
        updateContactPhone(arr[4]);
        updateX(arr[5]);
        updateY(arr[6]);
        updateType(arr[7]);
        isActive = arr[8];
    }

    public void updateActive(){
        isActive = String.valueOf(false);
    }
}
