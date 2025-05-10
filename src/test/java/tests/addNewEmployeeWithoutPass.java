package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.adminPage;
import pages.loginPage;

public class addNewEmployeeWithoutPass extends BaseTest {
     loginPage loginPage;
     adminPage adminPage;

    @BeforeMethod
    public void setUp() {
        super.setUp(); // Call BaseTest setup

        // Initialize page objects
        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);
    }

    @Test
    public void testAddAdminUser() {
        loginPage loginPage = new loginPage(driver);
        loginPage.login("Admin", "admin123");
        adminPage adminPage = new adminPage(driver);
        adminPage.addNewUserWithoutPass("lojy7898", "mazen  12345");
    }

}

