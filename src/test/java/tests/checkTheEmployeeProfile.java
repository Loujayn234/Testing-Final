package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.loginPage;
import pages.PIMPage;
import java.time.Duration;

public class checkTheEmployeeProfile {
    private WebDriver driver;
    private loginPage loginPage;
    private PIMPage pimPage;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();


        // Initialize Page Objects
        loginPage = new loginPage(driver);
        pimPage = new PIMPage(driver);

        // Navigate to application

    }

    @Test
    public void testViewSpecificEmployeeRecord() {
        loginPage.login("Admin", "admin123");
        pimPage.clickEmployeeRecordByIdAndLastName("0376","22");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}