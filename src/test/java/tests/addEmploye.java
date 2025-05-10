package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.*;
import static org.testng.Assert.*;

public class addEmploye {
    private WebDriver driver;
    private loginPage loginPage;
    private PIMPage pimPage;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver
        driver = WebDriverFactory.createDriver("chrome");
        driver.manage().window().maximize();

        // Navigate to application
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Initialize page objects
        loginPage = new loginPage(driver);
        pimPage = new PIMPage(driver);
    }

    @Test
    public void testAddEmployeeWithEmptyEmployeeId() {
        loginPage.login("Admin", "admin123");
        pimPage.enterEmployeeDetails("lhoayyy", "nabil", "fathy");

    }

    @AfterMethod
    public void tearDown() {
        // Clean up
        if (driver != null) {
            driver.quit();
        }
    }
}