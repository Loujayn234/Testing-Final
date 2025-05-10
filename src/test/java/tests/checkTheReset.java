package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PIMPage;
import pages.WebDriverFactory;
import pages.adminPage;
import pages.loginPage;

public class checkTheReset extends BaseTest {

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
    public void testUserSearchAndReset() {
        loginPage.login("Admin", "admin123");
        adminPage.navigateToAdminSection();
        adminPage.searchUser("FMLName", "ESS", "Enabled");
        adminPage.clickReset();
        Assert.assertTrue(adminPage.areFieldsCleared(), "Fields were not cleared after reset");
    }


}