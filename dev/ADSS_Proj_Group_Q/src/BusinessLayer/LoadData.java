package BusinessLayer;

public class LoadData {

    private EmployeeController employeeController;
    private ShiftController shiftController;
    private SiteManager siteManager;
    private TruckManager truckManager;
    private ShippingManager shippingManager;
    private static LoadData instance;


    public static LoadData getInstance(){
        if(instance==null) {
            instance = new LoadData();
        }
        return instance;
    }

    private LoadData(){
        employeeController=EmployeeController.getInstance();
        shiftController=ShiftController.getInstance();
        siteManager=SiteManager.getInstance();
        truckManager  = TruckManager.getInstance();
        shippingManager = ShippingManager.getInstance();
    }

    public void initData(){
        employeeController.LoadEmployees();
        siteManager.loadSites();
        shiftController.loadShift();
        truckManager.loadTrucks();
        shippingManager.loadShipments();
    }

    public void clearData() {
        employeeController.clearForTest();
        siteManager.deleteForTests();
        shiftController.clearForTest();
        truckManager.clearForTest();
        shippingManager.clearForTest();
    }
}
