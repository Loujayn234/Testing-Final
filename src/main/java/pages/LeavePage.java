package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeavePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Locators (unchanged)
    private final By leaveMenu = By.xpath("//a[.//span[text()='Leave']]");
    private final By entitlementsMenu = By.xpath("//span[contains(text(),'Entitle')]");
    private final By addEntitlementsOption = By.xpath("//a[text()='Add Entitlements']");
    private final By employeeNameInput = By.cssSelector("input[placeholder*='Type']");
    private final By leaveTypeDropdown = By.cssSelector(".oxd-select-wrapper:first-of-type .oxd-select-text");
    private final By leaveTypeOption = By.xpath("//span[contains(text(),'US - Vacation')]");
    private final By entitlementInput = By.xpath("//label[text()='Entitlement']/following::input[1]");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By confirmButton = By.xpath("//button[normalize-space()='Confirm']");
    private final By successToast = By.cssSelector(".oxd-toast-container");
    private final By cancelButton = By.cssSelector("button.oxd-button.oxd-button--ghost");
    private final By employeeNameError = By.cssSelector("div.oxd-input-group:has(label:contains('Employee Name')) + span.oxd-input-field-error-message");
    private final By leaveTypeError = By.cssSelector(".error-message.leaveType");
    private final By entitlementError = By.cssSelector(".error-message.entitlement");
    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToLeavePage() {
        clickElement(leaveMenu);
        clickElement(entitlementsMenu);
        clickElement(addEntitlementsOption);
        waitForElement(employeeNameInput);
    }

    public void addLeaveEntitlement(String employeeName, String leaveType, String entitlementAmount) {
        enterEmployeeName(employeeName);
        selectLeaveType();
        setEntitlementAmount(entitlementAmount);
        submitForm();
    }

    public boolean isEntitlementSuccessMessageDisplayed() {
        try {
            return waitForElement(successToast).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void enterEmployeeName(String employeeName) {
        WebElement nameInput = waitForElement(employeeNameInput);
        nameInput.sendKeys(employeeName);
        actions.pause(Duration.ofSeconds(10))
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .perform();
    }

    private void selectLeaveType() {
        clickElement(leaveTypeDropdown);
        clickElement(leaveTypeOption);
    }

    private void setEntitlementAmount(String entitlementAmount) {
        WebElement entitlementField = waitForElement(entitlementInput);
        entitlementField.clear();
        entitlementField.sendKeys(entitlementAmount);
    }

    private void submitForm() {
        clickElement(saveButton);
        clickElement(confirmButton);
    }
    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void fillLeaveEntitlementForm(String employeeName, String leaveType, String entitlement) {
        enterEmployeeName(employeeName);
        selectLeaveType();
        setEntitlementAmount(entitlement);
    }
    public void clickCancelButton() {
        clickElement(cancelButton);
    }

    public boolean isEmployeeNameErrorDisplayed() {
        return !driver.findElements(employeeNameError).isEmpty();
    }

    public boolean isLeaveTypeErrorDisplayed() {
        return !driver.findElements(leaveTypeError).isEmpty();
    }

    public boolean isEntitlementErrorDisplayed() {
        return !driver.findElements(entitlementError).isEmpty();
    }

}
