package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.loginPage;
import pages.adminPage;
import pages.WebDriverFactory;

public class deleteUserTest {
    WebDriver driver;
    loginPage loginPage;
    adminPage adminPage;

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();

        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);
    }

    @Test
    public void testDeleteUser() {

        loginPage.login("Admin", "admin123");

        String usernameToDelete = "alice_duval";
        adminPage.deleteUser(usernameToDelete);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}