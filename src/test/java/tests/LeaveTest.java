package tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LeavePage;
import pages.loginPage;

public class LeaveTest extends BaseTest {
    private loginPage loginPage;
    private LeavePage LeavePage;

    @BeforeMethod
    public void setUp() {
        super.setUp();

        // Initialize page objects
        loginPage = new loginPage(driver);
        LeavePage = new LeavePage(driver);
    }
    @Test
    public void testAddLeaveEntitlement() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();

        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        LeavePage.addLeaveEntitlement(employeeName, leaveType, entitlement);

        Assert.assertTrue(LeavePage.isEntitlementSuccessMessageDisplayed());

    }
    // TEST CASE 2: Verify invalid entitlement
    @Test
    public void testAddInvalidLeaveEntitlement() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();

        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String invalidEntitlement = "-5"; // Negative value (invalid)

        LeavePage.addLeaveEntitlement(employeeName, leaveType, invalidEntitlement);
        Assert.assertFalse(LeavePage.isEntitlementSuccessMessageDisplayed(),
                "Should be a number with upto 2 decimal places");

    }
    // TEST CASE 3:Cancel leave entitlement process
    @Test
    public void testCancelLeaveEntitlement() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();

        // Fill form but cancel instead of submitting
        String employeeName = "shrouk";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        // Add method in LeavePage to handle cancellation
        LeavePage.fillLeaveEntitlementForm(employeeName, leaveType, entitlement);
        LeavePage.clickCancelButton();

        // Verify we're back to the entitlements list
        Assert.assertTrue(driver.getCurrentUrl().contains("viewLeaveEntitlements"),
                "Should return to entitlements list after cancellation");
    }
    // TEST CASE 4:Validate required fields
    @Test
    public void testRequiredFieldValidation() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();
        LeavePage.clickSaveButton();

        Assert.assertTrue(LeavePage.isEmployeeNameErrorDisplayed(),
                "Employee name required error should appear");
        Assert.assertTrue(LeavePage.isLeaveTypeErrorDisplayed(),
                "Leave type required error should appear");
        Assert.assertTrue(LeavePage.isEntitlementErrorDisplayed(),
                "Entitlement required error should appear");
    }


    }

