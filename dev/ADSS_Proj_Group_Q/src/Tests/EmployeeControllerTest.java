package Tests;

import BusinessLayer.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class EmployeeControllerTest {

    private EmployeeController mockEmployeeController;
    private Employee mockEmp;
    private dateAndType p1;
    private dateAndType p2;
    private LocalDate lc;





    @Before
    public void setUp()  {
        // Create a mock worker and give him shift
        mockEmployeeController=EmployeeController.getInstance();
        mockEmployeeController.addNewWorker("123", "idan asis", "ABC Bank",123,456,50.0,"Full-time","Student","a123");
        mockEmp=mockEmployeeController.getEmployee("123");
        lc=LocalDate.now();
        p1=new dateAndType(lc,ShiftType.Morning);
        p2=new dateAndType(lc,ShiftType.Evening);
    }
    @Test
    public void getSalary() {
        Assert.assertEquals(0,mockEmployeeController.getSalary("123",lc.getMonth().getValue()),0.0);
        mockEmployeeController.setBonus("123",300);
        Assert.assertEquals(300,mockEmployeeController.getSalary("123",lc.getMonth().getValue()),0.0);
        mockEmployeeController.setBonus("123",600);
        Assert.assertEquals(600,mockEmployeeController.getSalary("123",lc.getMonth().getValue()),0.0);
        mockEmployeeController.setBonus("123",700);
        Assert.assertEquals(700,mockEmployeeController.getSalary("123",lc.getMonth().getValue()),0.0);
    }

    @Test
    public void addNewWorker() {
        Assert.assertThrows(Exception.class,()->mockEmployeeController.getEmployee("1234"));
        mockEmployeeController.addNewWorker("1234", "idan asis", "ABC Bank",123,456,50.0,"Full-time","Student","a123");
        Assert.assertNotNull(mockEmployeeController.getEmployee("1234"));
    }

    @Test
    public void addRoleToEmployee() {
        Assert.assertFalse(mockEmp.getEmployeeTypes().contains(EmployeeType.ShiftManager));
        mockEmployeeController.addRoleToEmployee("123","shm");
        Assert.assertTrue(mockEmp.getEmployeeTypes().contains(EmployeeType.ShiftManager));
        mockEmployeeController.addRoleToEmployee("123","c");
        Assert.assertTrue(mockEmp.getEmployeeTypes().contains(EmployeeType.Clean));
    }

    @Test
    public void updateState() {
        mockEmployeeController.updateState("123","new state");
        Assert.assertEquals("new state",mockEmp.getPersonalState());
    }

    @Test
    public void RemoveWorkerTest(){
        Assert.assertTrue(mockEmp.isActive());
        mockEmployeeController.removeWorker("123");
        mockEmp=mockEmployeeController.getEmployee("123");
        Assert.assertTrue(!mockEmp.isActive());

    }
    @Test
    public void AddDriverTest(){
        Assert.assertThrows(Exception.class,()->mockEmployeeController.getEmployee("5681").getName());
        mockEmployeeController.addNewDriver("5681","osher","leumi",123,11,60,"start from 11","in relationship","aa12","B","223","T");
        Assert.assertEquals("osher",mockEmployeeController.getEmployee("5681").getName());
    }






    @After
    public void tearDown(){
        mockEmployeeController.clearForTest();

        mockEmp=null;
    }

}