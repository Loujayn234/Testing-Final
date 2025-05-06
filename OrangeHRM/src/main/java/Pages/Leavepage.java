package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Leavepage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By leaveModule = By.xpath("//span[text()='Leave']");
    By entitlements = By.xpath("//a[contains(@href,'Entitlements')]");
    By myEntitlements = By.xpath("//a[contains(@href,'viewMyLeaveEntitlements')]");
    By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/following::div[1]");
    By fromDate = By.xpath("//label[text()='From']/following::input[1]");
    By toDate = By.xpath("//label[text()='To']/following::input[1]");
    By searchBtn = By.xpath("//button[@type='submit']");

    // Constructor
    public Leavepage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));  // Increase wait time to 20s
    }

    // Actions
    public void openLeaveModule() {
        // Wait for Leave module to be clickable, then click
        wait.until(ExpectedConditions.elementToBeClickable(leaveModule)).click();

        // Debugging log
        System.out.println("Clicked on Leave Module");

        // Wait for Entitlements link to be clickable and then click
        try {
            WebElement entitlementsElement = wait.until(ExpectedConditions.elementToBeClickable(entitlements));
            System.out.println("Entitlements element is clickable");

            // Scroll to the element if necessary
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entitlementsElement);
            entitlementsElement.click();
        } catch (Exception e) {
            System.out.println("Failed to click on Entitlements: " + e.getMessage());
        }

        // Wait for "My Entitlements" link to be clickable and then click
        wait.until(ExpectedConditions.elementToBeClickable(myEntitlements)).click();
    }

    public void selectLeaveType(String leaveType) {
        // Wait until leave type dropdown is clickable, then click
        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDropdown)).click();

        // Wait until the specific leave type option is clickable, then click it
        By option = By.xpath("//div[@role='listbox']//span[text()='" + leaveType + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void setLeavePeriod(String from, String to) {
        // Wait for the "From" and "To" fields to become visible and enter dates
        WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(fromDate));
        WebElement toInput = wait.until(ExpectedConditions.visibilityOfElementLocated(toDate));
        fromInput.clear();
        fromInput.sendKeys(from);
        toInput.clear();
        toInput.sendKeys(to);
    }

    public void clickSearch() {
        // Wait for the search button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }
}
