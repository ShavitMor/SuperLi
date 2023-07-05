package Tests;

import BusinessLayer.Truck;
import BusinessLayer.TruckManager;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class TruckManagerTest {
    private TruckManager tm;

    @BeforeAll
    static void clearBeforeRun(){
        TruckManager deleter= TruckManager.getInstance();
        deleter.clearForTest();
    }
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tm = TruckManager.getInstance();
        Truck t = new Truck(1,"1234567","T",2500.5,2000.5,true);
        Truck j = new Truck(2,"1111111","J",1000,150,false);
        Truck k = new Truck(3,"AB55555","X35",200,500,false);
        tm.addTruck(t);
        tm.addTruck(j);
        tm.addTruck(k);
    }

    @org.junit.jupiter.api.AfterEach
    void clearData(){
        tm.clearForTest();
    }


    @org.junit.jupiter.api.Test
    void getTruckByLicenceNumber() {
        Truck t = new Truck(1,"1234567","T",2500.5,2000.5,true);
        assertEquals(tm.getTruckByLicenceNumber("1234567"),t);
    }


    @org.junit.jupiter.api.Test
    void changeLicenceNumber() {
        tm.changeLicenceNumber(1,"8888888");
        Truck t = tm.getTruckByID(1);
        assertEquals(t.getLicenceNum(),"8888888");
    }

    @org.junit.jupiter.api.Test
    void isLicenceNumberValidTrue() {
        String validNum = "1V4L1D";
        assertTrue(tm.isLicenceNumberValidForUse(validNum));
    }
    @org.junit.jupiter.api.Test
    void isLicenceNumberValidFalse() {
        String invalidNum = "i am Invalid number";
        assertThrows(Exception.class,()->tm.isLicenceNumberValidForUse(invalidNum) );
    }
    @org.junit.jupiter.api.Test
    void isLicenceNumberAlreadyRegisteredTrue() {
        assertTrue(tm.isLicenceNumberAlreadyRegistered("1234567"));
    }
    @org.junit.jupiter.api.Test
    void isLicenceNumberAlreadyRegisteredFalse() {
        assertFalse(tm.isLicenceNumberAlreadyRegistered("GGN00B"));
    }

    @org.junit.jupiter.api.Test
    void isTruckAlreadyRegisteredTrue() {
        Truck t = new Truck(1,"1234567","T",2500.5,2000.5,true);
        assertTrue(tm.isTruckAlreadyRegistered(t));
    }
    @org.junit.jupiter.api.Test
    void isTruckAlreadyRegisteredFalse() {
        Truck l = new Truck(4,"84939A","JK",2500.5,2000.5,true);
        assertFalse(tm.isTruckAlreadyRegistered(l));
    }
}