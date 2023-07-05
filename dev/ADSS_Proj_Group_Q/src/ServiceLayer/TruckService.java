package ServiceLayer;

import BusinessLayer.TruckManager;
public class TruckService {

    TruckManager TM;

    public TruckService(TruckManager Tm) {
        TM = Tm;
    }
    public TruckService() {
        TM = TruckManager.getInstance();
    }

    public Response getTruckTable() {
        return Response.ResponseValue(TM.toString());
    }

    public Response getAmountOfListedTrucks() {
        return Response.ResponseValue(TM.getAmountOfTrucks());
    }

    public Response isTruckNumberGood(String licenceNumber) {
        Response res;
        try {
            res = Response.ResponseValue(TM.isLicenceNumberValidForUse(licenceNumber));
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response isTruckWeightValid(String Weight){
        Response res;
        try {
            TM.ensureValidTruckWeight(Double.parseDouble(Weight));
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response isTruckCarryWeightValid(String Weight){
        Response res;
        try {
            TM.ensureValidMaxCarryWeight(Double.parseDouble(Weight));
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response addNewTruck(String licenceNum,String model,String weight,String carryWeight,String tCon){
        Response res;
        try {
            res =Response.ResponseValue(TM.addTruck(licenceNum,model,Double.parseDouble(weight),
                    Double.parseDouble(carryWeight),Boolean.parseBoolean(tCon)));
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTruckIdByLicence(String LicenceNumber){
        Response res;
        try {
            res=Response.ResponseValue(TM.getTruckIdByLicence(LicenceNumber));
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response getTruckbyId(String id){
        Response res;
        try {
            res =Response.ResponseValue(TM.getTruckByID(Integer.parseInt(id)));
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response editLicenceNumber(String id, String licenceNumber){
        Response res;
        try {
            TM.changeLicenceNumber(Integer.parseInt(id),licenceNumber);
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response editModel(String id, String model){
        Response res;
        try {
            TM.changeModel(Integer.parseInt(id),model);
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response editTruckWeight(String id, String weight){
        Response res;
        try {
            TM.changeWeight(Integer.parseInt(id),Double.parseDouble(weight));
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response editTruckCarryWeight(String id, String weight){
        Response res;
        try {
            TM.changeCarryWeightWeight(Integer.parseInt(id),Double.parseDouble(weight));
            res = Response.Empty();
        } catch (Exception ex) {
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response truckToString(String id){
        Response res;
        try{
            res = Response.ResponseValue(TM.truckToString(Integer.parseInt(id)));
        }
        catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
        return res;
    }
    public Response deleteTruck(String id){
        Response res;
        try{
            res = Response.ResponseValue(TM.removeTruckById(Integer.parseInt(id)));
        }
        catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response switchTempControl(String id) {
        Response res;
        try{
            res = Response.ResponseValue(TM.switchTempControl(Integer.parseInt(id)));
        }
        catch (Exception ex){
            return Response.ErrorResponse(ex);
        }
        return res;
    }


    public Response getTruckTableInfo(String comboBoxInput, String checkBox) {
        Response res;
        try{
            res = Response.ResponseValue(TM.getTruckTableInfo(comboBoxInput,Boolean.valueOf(checkBox)));
        }catch (Exception ex){
            res= Response.ErrorResponse(ex);
        }
        return res;
    }

    public Response getTruckModelData(String truckId) {
        Response res;
        try{
            res = Response.ResponseValue(TM.getTruckModelData(truckId));
        }catch (Exception ex){
            res= Response.ErrorResponse(ex);
        }
        return res;
    }
}
