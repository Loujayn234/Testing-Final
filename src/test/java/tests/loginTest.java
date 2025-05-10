package tests;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.loginPage;
import pages.WebDriverFactory;

import org.openqa.selenium.By;



public class loginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
    }

    @Test
    public void testValidLogin() {
        loginPage loginPage = new loginPage(driver);
        loginPage.login("Admin", "admin123");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();

        }
    }
}

