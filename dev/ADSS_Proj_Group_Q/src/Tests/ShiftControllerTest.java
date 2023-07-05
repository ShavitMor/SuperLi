package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import BusinessLayer.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShiftControllerTest {

    EmployeeController employeeController;
    ShiftController shiftController;
    Employee employee;
    LocalDate lc;
    LocalDate lc1;
    String shiftType;
    dateAndType shift;
    SiteManager siteManager;
    Site site;
    @Before
    public void setUp(){
        LoadData loadData = LoadData.getInstance();
        loadData.clearData();
        employeeController=EmployeeController.getInstance();
        shiftController=ShiftController.getInstance();
        siteManager=SiteManager.getInstance();
        siteManager.addSite(new Site("beer sheva", "bgu2", "shavit", "100",6, 7, Site.SiteType.Branch));
        siteManager.addSite(new Site("tel aviv", "bgu2", "shavit", "100",6, 7, Site.SiteType.Branch));

        shiftType="m";
        lc=LocalDate.now();
        employeeController.addNewWorker("777","Shavit Mor","discunt",212,123,50,"without friday and saturday","married","q123");
        employee=employeeController.getEmployee("777");
        shiftController.addShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m",10,6,"beer sheva");
        employeeController.addRoleToEmployee("777", "shm");
        employeeController.addRoleToEmployee("777", "sk");
        shiftController.AddConstraint("777",lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m");
        shift=new dateAndType(lc,ShiftType.Morning);
        lc1=lc.plusDays(1);
        shiftController.addShift(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(),"m",10,6,"beer sheva");
    }

    @Test
    public void testAddConstraint() {
        // Add the constraint for the employee and check if it is added successfully
        lc=lc.plusDays(2);
        shiftController.addShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),shiftType,10,6,"beer sheva");
        shiftController.AddConstraint("777", lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(), shiftType);
        HashMap<dateAndType,List<Employee>> workersCont=shiftController.getWorkersConstraints();
        assertTrue((workersCont.containsKey(shift)& workersCont.get(shift).contains(employee)));
    }

    @Test
    public void testAddWorkerToShift() {
        Assert.assertThrows(Exception.class,()-> shiftController.AddWorkerToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","777","sk","beer sheva"));
//        shiftController.AddConstraint("777", lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(), shiftType);
        shiftController.AddWorkerToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","777","shm","beer sheva");
        Shift shift1=shiftController.getShiftByBranch(shift,"beer sheva");
        Assert.assertTrue(shift1.getWorkersinShift().get(EmployeeType.ShiftManager).contains(employee));
        Assert.assertEquals("777",shift1.getShiftManagerID());
    }

    @Test
    public void testPublishShift() {
        Assert.assertThrows(Exception.class,()-> shiftController.publishShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","beer sheva"));
        shiftController.AddWorkerToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","777","shm","beer sheva");
        shiftController.publishShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","beer sheva");
        Shift shift1= shiftController.getShiftByBranch(shift,"beer sheva");
        assertTrue(shiftController.getShiftByBranch(shift,"beer sheva").getIsPublished());
        assertTrue(!shiftController.getWorkerFutureShifts("777").isEmpty());

    }

    @Test
    public void testAddRoleToShift() {
        Assert.assertThrows(Exception.class,()->shiftController.addRoleToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","shm",1,"beer sheva"));
        shiftController.addRoleToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","c",1,"beer sheva");
        Shift shift1 = shiftController.getShiftByBranch(shift,"beer sheva");
        assertTrue(shift1.getRolesNeeded().containsKey(EmployeeType.Clean));
        Assert.assertEquals(1,shift1.getRolesNeeded().get(EmployeeType.Clean).intValue());
    }

    @Test
    public void testAddShift() {
        shiftController.addShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m",10,6,"tel aviv");
        Assert.assertEquals(false,shiftController.getShiftByBranch(shift,"tel aviv").getIsPublished());
    }

    @Test
    public void testAddShiftCancellation() {
        Assert.assertThrows(Exception.class,()->shiftController.addShiftCancellation("777",lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","beer sheva",5,5));
        shiftController.AddWorkerToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","777","shm","beer sheva");
        shiftController.addShiftCancellation("777",lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","beer sheva",5,5);
        Assert.assertTrue(shiftController.getCancelationByShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"m","beer sheva").contains("product id: 5 quantity: 5"));
    }
    @Test
    public void testShowAvailableWorkersForShiftByRole() {
        lc=lc.plusDays(1);
        shiftController.addShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"e",16,3,"beer sheva");
        Assert.assertTrue(shiftController.showAvailableWorkersForShiftByRole(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),"e","shm").isEmpty());
        shiftController.AddConstraint("777", lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(), "e");
        Assert.assertFalse(shiftController.showAvailableWorkersForShiftByRole(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(), "e","shm").isEmpty());
    }
    @Test
    public void testRestrictEmployee(){

        shiftController.restrictEmployee(lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(), shiftType,"777");
        Assert.assertThrows(Exception.class,()->shiftController.AddConstraint("777", lc1.getYear(),lc1.getMonth().getValue(),lc1.getDayOfMonth(), shiftType));
    }

    @Test
    public void testAddDriverNeeded(){
        Assert.assertThrows(Exception.class,()->shiftController.getDriversNeeded().get(shift).intValue());
        shiftController.addDriverNeededToShift(lc, LocalTime.of(15,0));
        Assert.assertEquals(1,shiftController.getDriversNeeded().get(shift).intValue());
        shiftController.addDriverNeededToShift(lc, LocalTime.of(15,0));
        Assert.assertEquals(2,shiftController.getDriversNeeded().get(shift).intValue());
    }

    @Test
    public void TestAddShiftEvent(){
        Assert.assertTrue(shiftController.showShiftEvents(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),shiftType,"beer sheva").isEmpty());
        shiftController.AddWorkerToShift(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),shiftType,"777","shm","beer sheva");
        shiftController.addShiftEvent("777",lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),shiftType,"beer sheva","someHappend");
        Assert.assertTrue(shiftController.showShiftEvents(lc.getYear(),lc.getMonth().getValue(),lc.getDayOfMonth(),shiftType,"beer sheva").contains("someHappend"));
    }


    @After
    public void tearDown() {
        shiftController.clearForTest();
        employeeController.clearForTest();
        siteManager.deleteForTests();
    }

}
