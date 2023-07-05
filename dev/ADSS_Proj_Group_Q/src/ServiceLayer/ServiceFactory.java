package ServiceLayer;

import BusinessLayer.LoadData;
import BusinessLayer.ShippingManager;
import BusinessLayer.TruckManager;

public class ServiceFactory {

    private static ServiceFactory instance = null;

    private ServiceFactory(){};

    public static ServiceFactory getInstance(){
        if(instance == null){
            instance = new ServiceFactory();
        }
        return instance;
    }


    public Response loadData (){
        Response res;
        try {
            LoadData.getInstance().initData();
            return Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
    public ShippingService createShippingService(){
        return new ShippingService(ShippingManager.getInstance());
    }
    public TruckService createTruckService(){
        return new TruckService(TruckManager.getInstance());
    }


    public Response clearData() {
        Response res;
        try {
            LoadData.getInstance().clearData();
            return Response.Empty();
        }catch (Exception ex){
            res = Response.ErrorResponse(ex);
        }
        return res;
    }
}
