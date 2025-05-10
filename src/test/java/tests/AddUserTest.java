package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.PIMPage;
import pages.WebDriverFactory;
import pages.loginPage;
import pages.adminPage;

public class AddUserTest extends BaseTest {  // Extend BaseTest here

    loginPage loginPage;
    adminPage adminPage;

    @BeforeMethod
    public void setup() {
        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);
    }

    @Test
    public void testAddAdminUser() {
        loginPage.login("Admin", "admin123");
        adminPage.addNewUser("lojy1234", "Janiya River Christiansen", "loay1234", "loay1234");
    }

    // No need for tearDown, since it's inherited from BaseTest
}
