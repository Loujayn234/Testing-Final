package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;

public class loginPage {

    @FindBy(name = "username")
    WebElement usernameInput;


    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    WebElement loginButton;

    // Locating the error message using the class name you provided
    WebElement errorMessage;

    public loginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        // Optional: wait for page to load before interacting
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
    }

    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    // Method to retrieve error message text using the updated class name
    public String getErrorMessage(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-login-error")));
            return errorMessage.getText();
        } catch (Exception e) {

            System.out.println("Error message not found.");
            return null;
        }
    }

}
