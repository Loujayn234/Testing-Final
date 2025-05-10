package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.*;
import static org.testng.Assert.*;

public class addEmploye extends BaseTest {  // Extend BaseTest

    private loginPage loginPage;
    private PIMPage pimPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();

        // Initialize page objects
        loginPage = new loginPage(driver);
        pimPage = new PIMPage(driver);
    }

    @Test
    public void testAddEmployeeWithEmptyEmployeeId() {
        loginPage.login("Admin", "admin123");
        pimPage.enterEmployeeDetails("lhoayyy", "nabil", "fathy");
        // Add your assertions or validations here if needed
    }

    // No need for tearDown, it's inherited from BaseTest
}
