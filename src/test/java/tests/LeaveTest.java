package Test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.LeavePage;
import Pages.LoginPage;
import Pages.Webclass;


public class LeaveTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = Webclass.createDriver("chrome");
        driver.get("https://opensource-demo.orangehrmlive.com");
        driver.manage().window().maximize();
        new LoginPage(driver).login("Admin", "admin123");
    }
// TEST CASE 1:Check enter valid data
    @Test
    public void testAddLeaveEntitlement() {
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToLeavePage();

        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        leavePage.addLeaveEntitlement(employeeName, leaveType, entitlement);

        Assert.assertTrue(leavePage.isEntitlementSuccessMessageDisplayed());

    }
    // TEST CASE 2: Verify invalid entitlement
    @Test
    public void testAddInvalidLeaveEntitlement() {
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToLeavePage();

        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String invalidEntitlement = "-5"; // Negative value (invalid)

        leavePage.addLeaveEntitlement(employeeName, leaveType, invalidEntitlement);
        Assert.assertFalse(leavePage.isEntitlementSuccessMessageDisplayed(),
                "Should be a number with upto 2 decimal places");

    }
    // TEST CASE 3:Cancel leave entitlement process
    @Test
    public void testCancelLeaveEntitlement() {
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToLeavePage();

        // Fill form but cancel instead of submitting
        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        // Add method in LeavePage to handle cancellation
        leavePage.fillLeaveEntitlementForm(employeeName, leaveType, entitlement);
        leavePage.clickCancelButton();

        // Verify we're back to the entitlements list
        Assert.assertTrue(driver.getCurrentUrl().contains("viewLeaveEntitlements"),
                "Should return to entitlements list after cancellation");
    }
    // TEST CASE 4:Validate required fields
    @Test
    public void testRequiredFieldValidation() {
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToLeavePage();

        leavePage.clickSaveButton();

        Assert.assertTrue(leavePage.isEmployeeNameErrorDisplayed(),
                "Employee name required error should appear");
        Assert.assertTrue(leavePage.isLeaveTypeErrorDisplayed(),
                "Leave type required error should appear");
        Assert.assertTrue(leavePage.isEntitlementErrorDisplayed(),
                "Entitlement required error should appear");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {

        }
    }
}