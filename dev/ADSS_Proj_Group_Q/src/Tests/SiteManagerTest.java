package Tests;

import BusinessLayer.Site;
import BusinessLayer.SiteManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.*;

public class SiteManagerTest {

    SiteManager siteManager;
    Site site;
    @Before
    public void setUp(){
        siteManager=SiteManager.getInstance();
        siteManager.deleteForTests();
        siteManager=SiteManager.getInstance();
        site = new Site("beer sheva", "bgu2", "shavit", "100",6, 7, Site.SiteType.Branch);
        siteManager.addSite(site);
    }

    @AfterEach
    public void delete(){
        siteManager.deleteForTests();
    }
    @Test
    public void testAddNewSite() {
        // Add site that already exits and check if it throws exception for it.
        siteManager.addNewSiteReturnId("petach tikva","tel aviv","beinleomi","1050","7,6","m");
        Assert.assertThrows(Exception.class,()->siteManager.addNewSiteReturnId("petach tikva","tel aviv","beinleomi","1050","7,6","m"));
    }

    @Test
    public void testIsValidName() {
        assertFalse(siteManager.isValidName("!A9"));
    }

    @Test
    public void testIsValidPhone() {
        assertFalse(siteManager.isValidPhone(":1"));
    }
    @Test
    public void testIsValidCoordinate() {
        Assert.assertThrows(Exception.class,()->siteManager.isValidCoordinate("70 50"));
        assertTrue(siteManager.isValidCoordinate("7,6"));
    }
    @Test
    public void testGetSite() {
        assertEquals(siteManager.getSite(1),site);
    }
    @Test
    public void testGetSiteIDFromName() {
        assertEquals(siteManager.getSiteIDFromName("beer sheva"),1);
    }
    @Test
    public void testSetSiteContactName() {
        assertThrows(Exception.class,()->siteManager.setSiteContactName(1,"A:3$"));
    }
    @Test
    public void testSetSiteContactPhone() {
        assertThrows(Exception.class,()->siteManager.setSiteContactPhone(1,"052:134"));
    }
    @Test
    public void testSetSiteCoordinate() {
        assertThrows(Exception.class,()->siteManager.setSiteCoordinate(1,"sa23d,234s"));
    }
    @Test
    public void testDeactivateSite() {
        assertEquals(siteManager.getActiveSites().get(1),site);
        siteManager.deactivateSite(1);
        assertNotEquals(siteManager.getActiveSites().get(1),site);
    }
}
