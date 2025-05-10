package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.loginPage;
import pages.adminPage;
import pages.WebDriverFactory;

public class deleteUserTest extends BaseTest {
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
    public void testDeleteUser() {

        loginPage.login("Admin", "admin123");

        String usernameToDelete = "andy_jassy";
        adminPage.deleteUser(usernameToDelete);

    }


}