package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.loginPage;

import java.time.Duration;

public class loginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        loginPage loginPage = new loginPage(driver);
        loginPage.login("admin", "admin123");

        wait.until(ExpectedConditions.urlContains("dashboard"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.contains("dashboard"), "Login failed or dashboard not reached.");
    }

    @Test
    public void testInvalidLogin() {
        loginPage loginPage = new loginPage(driver);
        loginPage.login("InvalidUser", "InvalidPassword");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-alert-content-text")));

        String errorMessage = loginPage.getErrorMessage(driver);
        Assert.assertTrue(errorMessage.contains("Invalid"), "Error message not displayed for invalid login.");
    }

    @Test
    public void checkAccountLockout() {
        loginPage loginPage = new loginPage(driver);

        String lockoutMessage = "Your account has been locked"; // Change this based on actual message
        boolean isLockedOut = false;

        for (int i = 0; i < 10; i++) {
            loginPage.login("InvalidUsername", "InvalidPassword");

            try {
                Thread.sleep(2000); // Pause before checking
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Good practice to re-interrupt
                e.printStackTrace();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-alert-content-text")));
                String errorMessage = loginPage.getErrorMessage(driver);

                System.out.println("Attempt " + (i + 1) + ": " + (errorMessage != null ? errorMessage : "No error message"));

                if (errorMessage != null && errorMessage.contains(lockoutMessage)) {
                    isLockedOut = true;
                    break;
                }

            } catch (Exception e) {
                System.out.println("Attempt " + (i + 1) + ": Error message not found.");
            }
        }

        if (isLockedOut) {
            System.out.println("✅ User has been locked out");
        } else {
            System.out.println("❌ User has not been locked out");
        }
    }


    @Test
    public void testPasswordFieldDoesNotAutofillAfterLogout() {
        loginPage loginPage = new loginPage(driver);

        loginPage.login("Admin", "admin123");

        wait.until(ExpectedConditions.urlContains("dashboard"));

        // Clicking the user dropdown using CSS Selector
        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("p.oxd-userdropdown-name")));
        userDropdown.click();

        // Clicking the logout button using CSS Selector
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.oxd-userdropdown-link[href*='logout']")));
        logoutButton.click();

        // Waiting until the login page appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        WebElement passwordField = driver.findElement(By.name("password"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String passwordValue = (String) js.executeScript("return arguments[0].value;", passwordField);

        // Assert that the password field is empty
        Assert.assertNotNull(passwordValue);
        Assert.assertTrue(passwordValue.isEmpty(), "Password field has autofill value after logout.");
    }


    @Test
    public void testLoginPageUsesHttps() {
        String currentUrl = driver.getCurrentUrl();

        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.startsWith("https://"), "The login page is not using HTTPS.");
    }
}
