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

        String lockoutMessage = "Your account has been locked";
        boolean isLockedOut = false;

        for (int i = 0; i < 5; i++) {
            loginPage.login("InvalidUsername", "InvalidPassword");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-login-error")));
                String errorMessage = loginPage.getErrorMessage(driver);

                if (errorMessage != null) {

                    if (errorMessage.contains(lockoutMessage)) {
                        isLockedOut = true;
                        System.out.println("Found Error Message:" + lockoutMessage);
                        break;

                    }
                }
        }

        if (isLockedOut) {
            System.out.println("✅ User has been locked out");
        } else {
            System.out.println("❌ User has not been locked out");
        }
    }



    @Test
    public void testPasswordFieldDoesNotAutofillAfterLogout() throws InterruptedException {
        loginPage loginPage = new loginPage(driver);

        loginPage.login("Admin", "admin123");

        wait.until(ExpectedConditions.urlContains("dashboard"));


        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("p.oxd-userdropdown-name")));
        userDropdown.click();

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.oxd-userdropdown-link[href*='logout']")));
        logoutButton.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));


        Thread.sleep(3000);

        WebElement passwordField = driver.findElement(By.name("password"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String passwordValue = (String) js.executeScript("return arguments[0].value;", passwordField);

        Assert.assertTrue(passwordValue.isEmpty());
        System.out.println("Test passed: Password field is empty after logout.");
    }



    @Test
    public void testLoginPageUsesHttps() {
        String currentUrl = driver.getCurrentUrl();

        Assert.assertNotNull(currentUrl);
        if (currentUrl.startsWith("https")) {
            System.out.println("✅ URL is secure (HTTPS): " + currentUrl);
        } else {
            System.out.println("❌ URL is NOT secure: " + currentUrl);
        }

    }
}
