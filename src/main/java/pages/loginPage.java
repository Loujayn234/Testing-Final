package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;

public class loginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.className("orangehrm-login-error");

    public loginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

        // Optional: wait for page to load before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // Method to retrieve error message text
    public String getErrorMessage(WebDriver driver) {
        WebDriverWait shortWait = new WebDriverWait(this.driver, Duration.ofSeconds(4));
        try {
            WebElement errorMsgElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return errorMsgElement.getText();
        } catch (Exception e) {
            System.out.println("Error message not found.");
            return null;
        }
    }
}
