package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PIMPage;
import pages.adminPage;
import pages.loginPage;

public class adminSearch extends BaseTest {

     loginPage loginPage;
     adminPage adminPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();

        // Initialize page objects
        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);
    }

    @Test
    public void testSearchAdminUser() {
        loginPage.login("Admin", "admin123");
        adminPage.searchForUser("Janiya River Christiansen");
        Assert.assertTrue(adminPage.isUserInResults("Janiya River Christiansen"), "Janiya River Christiansen user not found in search results");
    }

}
