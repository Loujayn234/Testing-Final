package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.adminPage;
import pages.loginPage;

public class addNewEmployeeWithoutPass {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void testAddAdminUser() {
        loginPage loginPage = new loginPage(driver);
        loginPage.login("Admin", "admin123");
        adminPage adminPage = new adminPage(driver);
        adminPage.addNewUserWithoutPass("loay ccnabillllfl", "Alice  Duval .");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
