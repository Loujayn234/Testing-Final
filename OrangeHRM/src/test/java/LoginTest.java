import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.Loginpage;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    // ✅ Actions:
    //-----------------
    // ✅ Valid Login
    // ✅ Valid Login Test
    @Test
    public void testValidLogin() {
        Loginpage loginPage = new Loginpage(driver);
        loginPage.login("admin", "admin123");

        // Wait until URL contains "dashboard"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));

        // Get current URL
        String currentUrl = driver.getCurrentUrl();

        // Assert URL contains "dashboard"
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.contains("dashboard"), "Login failed or dashboard not reached.");
    }


    // ✅ Invalid Login
    @Test
    public void testInvalidLogin() {
        Loginpage loginPage = new Loginpage(driver);
        loginPage.login("InvalidUser", "InvalidPassword");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-alert-content-text")));

        String errorMessage = loginPage.getErrorMessage(driver);
        Assert.assertTrue(errorMessage.contains("Invalid"), "Error message not displayed for invalid login.");
    }

    // ✅ Test lockout after 10 invalid entries
    @Test
    public void checkAccountLockout() {
        Loginpage loginPage = new Loginpage(driver);

        String lockoutMessage = "Your account has been locked"; // Change this based on actual message
        boolean isLockedOut = false;

        for (int i = 0; i < 10; i++) {
            loginPage.login("InvalidUsername", "InvalidPassword");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-alert-content-text")));

            String errorMessage = loginPage.getErrorMessage(driver);
            System.out.println("Attempt " + (i + 1) + ": " + errorMessage);

            if (errorMessage.contains(lockoutMessage)) {
                isLockedOut = true;
                break;
            }
        }

        if (isLockedOut) {
            System.out.println("User has been locked out");
        } else {
            System.out.println("User has not been locked out");
        }
    }

    // ✅ Checks if password autofill
    @Test
    public void testPasswordFieldDoesNotAutofillAfterLogout() {
        Loginpage loginPage = new Loginpage(driver);

        // Step 1: Log in using valid credentials
        loginPage.login("Admin", "admin123");

        // Step 2: Wait to verify the login was successful (check for dashboard or homepage)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));

        // Step 3: Log out from the app
        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='oxd-userdropdown-name']")));
        userDropdown.click();
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
        logoutButton.click();

        // Step 4: Wait for login page to be visible again
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        // Step 5: Check if password field does not autofill
        WebElement passwordField = driver.findElement(By.name("password"));

        // Using JavaScript to get the value of the password field
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String passwordValue = (String) js.executeScript("return arguments[0].value;", passwordField);

        // Assert that the password field value is empty after logout
        Assert.assertNotNull(passwordValue);
        Assert.assertTrue(passwordValue.isEmpty(), "Password field has autofill value after logout.");
    }
    // ✅ Check if login page opens over HTTPS (secure connection).
    @Test
    public void testLoginPageUsesHttps() {
        // Navigate to the login page
        driver.get("https://opensource-demo.orangehrmlive.com");

        // Get the current URL
        String currentUrl = driver.getCurrentUrl();

        // Assert that the URL starts with 'https://'
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.startsWith("https://"), "The login page is not using HTTPS.");
    }
//    // ✅ Checks Status Code: 200, 401...302??
//      @Test
//        public void testValidLoginStatusCode() {
//            Response response = RestAssured.given()
//                    .header("Content-Type", "application/json")
//                    .body("{\"username\": \"Admin\", \"password\": \"admin123\"}")
//                    .post("https://opensource-demo.orangehrmlive.com"); // <-- Change this to real API endpoint
//
//            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for valid login");
//        }
//
//        @Test
//        public void testInvalidLoginStatusCode() {
//            Response response = RestAssured.given()
//                    .header("Content-Type", "application/json")
//                    .body("{\"username\": \"InvalidUser\", \"password\": \"InvalidPass\"}")
//                    .post("https://opensource-demo.orangehrmlive.com"); // <-- Change this to real API endpoint
//
//            Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid login");
//        }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();

        }
    }
}
// ENHANCEMENT: data-driven



