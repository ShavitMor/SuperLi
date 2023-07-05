package Tests;

import BusinessLayer.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class IntegratedTests {


     LocalDate lc,lc1,lc2,lc3,lc4,lc5;
     EmployeeController employeeController;
     ShiftController shiftController;
     SiteManager siteManager;
     dateAndType zman;
     Site site;
     ShippingManager shippingManager;
     TruckManager truckManager;
     LoadData loadData;


    @Before
    public void setUp(){
        loadData=LoadData.getInstance();
        loadData.clearData();
        employeeController=EmployeeController.getInstance();
        shiftController=ShiftController.getInstance();
        siteManager=SiteManager.getInstance();
        truckManager=TruckManager.getInstance();
        shippingManager=new ShippingManager();
        //test 1
        siteManager.addSite(new Site("beer sheva", "bgu3", "shavit", "100",6, 7, Site.SiteType.Branch));
        lc=LocalDate.now();
        employeeController.addNewWorker("777","Shavit Mor","discunt",212,123,50,"without friday and saturday","married","q123");
        employeeController.addNewWorker("1","rami","discunt",212,123,50,"without friday and saturday","married","q123");
        employeeController.addNewWorker("2","idan","discunt",212,123,50,"without friday and saturday","married","q123");
        employeeController.addNewWorker("3","osher","discunt",212,123,50,"without friday and saturday","married","q123");
        employeeController.addNewDriver("4","eden","discunt",212,123,50,"without friday and saturday","married","q123","B","12121","t");
        employeeController.addNewWorker("5", "niv","discunt",212,123,50,"without friday and saturday","married","q123");
        employeeController.addRoleToEmployee("777","shm");
        employeeController.addRoleToEmployee("1","sk");
        employeeController.addRoleToEmployee("2","c");
        employeeController.addRoleToEmployee("3","shm");
        employeeController.addRoleToEmployee("3","sk");
        employeeController.addRoleToEmployee("3","c");
        employeeController.addRoleToEmployee("5","sk");
        zman= new dateAndType(lc,ShiftType.Morning);
        site=siteManager.getFirstMatchingBranchByName("beer sheva");
        //test 2
        lc1=lc.plusDays(1);
        siteManager.addNewSite("tel aviv", "bgu3", "shavit", "100",6, 7, Site.SiteType.LogisticCenter);
        shiftController.addShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m",10,6,"beer sheva");
        shiftController.addRoleToShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m","sk",1,"beer sheva");
        shiftController.AddConstraint("777",lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m");
        shiftController.AddConstraint("1",lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m");
        shiftController.AddWorkerToShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m","777","shm","beer sheva");
        shiftController.AddWorkerToShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m","1","sk","beer sheva");
        shiftController.publishShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m","beer sheva");

        shiftController.addShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"e",10,6,"beer sheva");
        shiftController.addRoleToShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"e","sk",1,"beer sheva");
        shiftController.AddConstraint("3",lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"e");
        shiftController.AddConstraint("5",lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"e");
        shiftController.AddWorkerToShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"e","3","shm","beer sheva");
        shiftController.AddWorkerToShift(lc1.getYear(),lc.getMonth().getValue(),lc1.getDayOfMonth(),"e","5","sk","beer sheva");
        shiftController.publishShift(lc1.getYear(),lc.getMonth().getValue(),lc1.getDayOfMonth(),"e","beer sheva");

        shippingManager.addNewShipment();
        shippingManager.setShipmentZone(1,"C");
        shippingManager.setShipmentDate(1,lc1);
        shippingManager.setShipmentTime(1, LocalTime.of(13,0));
        shiftController.AddConstraint("4",lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m");
        shippingManager.setShipmentSource(1,"tel aviv");
        truckManager.addTruck("456","mazda",500,2500,true);
        shippingManager.setShipmentTruck(1,1);
        shippingManager.addShipmentDestination(1,"beer sheva");
        shippingManager.setShipmentDriver(1,"4");
        shippingManager.addNewItemToShipment(1,"Passing Grade", 0.100);
        shippingManager.addItemsToPickUp(1,0,"Passing Grade",4);
        shippingManager.addItemsToDeliver(1,1,"Passing Grade",4);

        //test 3
        lc2=lc1.plusDays(1);
        shiftController.addShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"m",10,6,"beer sheva");
        shiftController.addShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"e",10,6,"beer sheva");
        shiftController.addRoleToShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"m","sk",1,"beer sheva");
        shiftController.addRoleToShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"e","sk",1,"beer sheva");
        shiftController.AddConstraint("1",lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"m");
        shiftController.AddConstraint("3",lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"e");
        shippingManager.addNewShipment();
        shippingManager.setShipmentZone(2,"C");
        shippingManager.setShipmentDate(2,lc2);
        shippingManager.setShipmentTime(2, LocalTime.of(13,0));
        shiftController.AddConstraint("4",lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"m");
        shippingManager.setShipmentSource(2,"tel aviv");
        shippingManager.setShipmentTruck(2,1);
        shippingManager.setShipmentDriver(2,"4");
        shippingManager.addShipmentDestination(2,"beer sheva");
        shippingManager.addNewItemToShipment(2,"Passing Grade", 0.100);
        shippingManager.addItemsToPickUp(2,0,"Passing Grade",4);
        shippingManager.addItemsToDeliver(2,1,"Passing Grade",4);



        //test 4
        lc3=lc2.plusDays(1);
        shiftController.addShift(lc3.getYear(),lc3.getMonth().getValue(),lc3.getDayOfMonth(),"m",10,6,"beer sheva");
        shippingManager.addNewShipment();
        shippingManager.setShipmentZone(3,"C");
        shippingManager.setShipmentDate(3,lc3);
        shippingManager.setShipmentTime(3, LocalTime.of(13,0));
        shippingManager.setShipmentSource(3,"tel aviv");
        shippingManager.setShipmentTruck(3,1);

        //test 5
        lc4=lc3.plusDays(1);
        shiftController.addShift(lc4.getYear(),lc4.getMonth().getValue(),lc4.getDayOfMonth(),"m",10,6,"beer sheva");
        shippingManager.addNewShipment();
        shippingManager.setShipmentZone(4,"C");
        shippingManager.setShipmentDate(4,lc4);
    }

    @Test
    public void testShiftSite() {
        // Add the constraint for the employee and check if it is added successfully
        shiftController.addShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m",10,6,"beer sheva");
        Assert.assertEquals(site,shiftController.getShiftByBranch(zman,"beer sheva").getBranch());
    }

    @Test
    public void testFutureShipments() {
        // Add the constraint for the employee and check if it is added successfully
        Assert.assertThrows(Exception.class,()->shiftController.getFutureShipmentsForEmp("2"));
        shippingManager.advanceShipmentStatus(1);
        Assert.assertTrue(!shiftController.getFutureShipmentsForEmp("777").contains("you dont have any shipments soon"));
        Assert.assertTrue(!shiftController.getFutureShipmentsForEmp("1").isEmpty());//watch again
    }

    @Test
    public void testAddDestWithSK() {
        //throws
        Assert.assertEquals(IShipment.ShipmentStatus.SiteError,shippingManager.getShipment(2).getStatus());
        shiftController.AddWorkerToShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"m","1","sk","beer sheva");
        Assert.assertEquals(IShipment.ShipmentStatus.SiteError,shippingManager.getShipment(2).getStatus());
       //throws
        shiftController.AddWorkerToShift(lc2.getYear(),lc2.getMonth().getValue(),lc2.getDayOfMonth(),"e","3","sk","beer sheva");
        //doesn't throws
        Assert.assertEquals(IShipment.ShipmentStatus.StandBy,shippingManager.getShipment(2).getStatus());
    }

    @Test
    public void testAddDriverWithConstraints() {
        //add new shipment-could be in setup
        //add driver to the shipment
        //throws
        Assert.assertThrows(Exception.class,()->shippingManager.setShipmentDriver(3,"4"));
        shiftController.AddConstraint("4",lc3.getYear(),lc3.getMonth().getValue(),lc3.getDayOfMonth(),"m");
        shippingManager.setShipmentDriver(3,"4");
        //add driver to shipment

        //assert-true drivers shifts contains "eden" & shipment driver is "eden"
        Assert.assertEquals(employeeController.getEmployee("4"),shippingManager.getShipment(3).getDriver());
    }

    @Test
    public void modifyShipmentDateOverShifts() {
      //create new shipment
        dateAndType andType=new dateAndType(lc4,ShiftType.Morning);
        shippingManager.setShipmentTime(4, LocalTime.of(13,0));
        Assert.assertEquals(1,shiftController.getDriversNeeded().get(andType).intValue());
        shiftController.AddConstraint("4",lc4.getYear(),lc4.getMonth().getValue(),lc4.getDayOfMonth(),"m");
        shippingManager.setShipmentDriver(4,"4");
        //check driver needed plus 1
        //add driver to shipment
        //check if driver contains in shipment & driverShifts
        Assert.assertTrue(shiftController.getDriverShifts().get(andType).contains(employeeController.getDriver("4")));
        Assert.assertEquals(0,shiftController.getDriversNeeded().get(andType).intValue());
        //change date of shipment
        //check drivers need minus 1
        shippingManager.setShipmentDate(4,lc.plusMonths(1));
        Assert.assertEquals(0,shiftController.getDriversNeeded().get(andType).intValue());
        Assert.assertTrue(!shiftController.getDriverShifts().get(andType).contains(employeeController.getDriver("4")));
        //check if driver isnt in shipment and not in driversshifts

    }

    @After
    public void tearDown() {
        shiftController.clearForTest();
        employeeController.clearForTest();
        siteManager.deleteForTests();
        shippingManager.clearForTest();
        truckManager.clearForTest();
    }

}
