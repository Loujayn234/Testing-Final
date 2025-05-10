package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.adminPage;
import pages.loginPage;
import pages.PIMPage;
import java.time.Duration;

public class checkTheEmployeeProfile extends BaseTest {
     loginPage loginPage;
     PIMPage pimPage;


    @BeforeMethod
    public void setUp() {
        super.setUp();

        // Initialize page objects
        loginPage = new loginPage(driver);
        pimPage = new PIMPage(driver);
    }

    @Test
    public void testViewSpecificEmployeeRecord() {
        loginPage.login("Admin", "admin123");
        pimPage.clickEmployeeRecordByIdAndLastName("0312","010Z");
    }


}