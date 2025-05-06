import Pages.Leavepage;
import Pages.Loginpage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeaveTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void testValidLogin() {
        // Initialize the LoginPage object and login
        Loginpage loginPage = new Loginpage(driver);
        loginPage.login("Admin", "admin123");

        // Assert to verify if login was successful
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);

        // Initialize the LeavePage object and navigate to leave module
        Leavepage leavePage = new Leavepage(driver);
        leavePage.openLeaveModule();
        // Set the leave type, leave period, and then search
        leavePage.selectLeaveType("Paid Leave"); // Ensure "Paid Leave" is an available option
        leavePage.setLeavePeriod("2024-01-01", "2024-12-31"); // Set both dates before searching
        leavePage.clickSearch(); // Click search only after setting dates

        // Cleanup
        driver.quit();
    }
}
