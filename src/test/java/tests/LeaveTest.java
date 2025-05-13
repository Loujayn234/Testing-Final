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

        String employeeName = "Shrouk Mohamed Mohamed";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        LeavePage.addLeaveEntitlement(employeeName, leaveType, entitlement);

        Assert.assertTrue(LeavePage.isEntitlementSuccessMessageDisplayed());

    }
    // TEST CASE 2: Verify valid entitlement
    @Test
    public void testAddInvalidLeaveEntitlement() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();

        String employeeName = "Shrouk Mohamed Mohamed";
        String leaveType = "US - Personal";
        String invalidEntitlement = "9.99";

        LeavePage.addLeaveEntitlement(employeeName, leaveType, invalidEntitlement);
        Assert.assertTrue(LeavePage.isEntitlementSuccessMessageDisplayed());

    }
    // TEST CASE 3:Cancel leave entitlement process
    @Test
    public void testCancelLeaveEntitlement() {
        loginPage.login("Admin", "admin123");
        LeavePage.navigateToLeavePage();

        // Fill form but cancel instead of submitting
        String employeeName = "Shrouk Mohamed Mohamed";
        String leaveType = "US - Vacation";
        String entitlement = "20";

        // Add method in LeavePage to handle cancellation
        LeavePage.fillLeaveEntitlementForm(employeeName, leaveType, entitlement);
        LeavePage.clickCancelButton();

        // Verify we're back to the entitlements list
        Assert.assertTrue(driver.getCurrentUrl().contains("viewLeaveEntitlements"),
                "Should return to entitlements list after cancellation");
    }
    }

