package pages;

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

    // Locators
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

    // ✅ Fixed XPath locators for errors
    private final By employeeNameError = By.xpath("//label[contains(text(),'Employee Name')]/following::span[contains(@class,'oxd-input-field-error-message')][1]");
    private final By leaveTypeError = By.xpath("//label[contains(text(),'Leave Type')]/following::span[contains(@class,'oxd-input-field-error-message')][1]");
    private final By entitlementError = By.xpath("//label[contains(text(),'Entitlement')]/following::span[contains(@class,'oxd-input-field-error-message')][1]");

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
        actions.pause(Duration.ofSeconds(2)) // Shorter pause is better for real tests
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
        clickElement(saveButton);
    }

    public void fillLeaveEntitlementForm(String employeeName, String leaveType, String entitlement) {
        enterEmployeeName(employeeName);
        selectLeaveType();
        setEntitlementAmount(entitlement);
    }

    public void clickCancelButton() {
        clickElement(cancelButton);
    }

    // ✅ Safe error checkers
    public boolean isEmployeeNameErrorDisplayed() {
        return !driver.findElements(employeeNameError).isEmpty() &&
                driver.findElement(employeeNameError).isDisplayed();
    }

    public boolean isLeaveTypeErrorDisplayed() {
        return !driver.findElements(leaveTypeError).isEmpty() &&
                driver.findElement(leaveTypeError).isDisplayed();
    }

    public boolean isEntitlementErrorDisplayed() {
        return !driver.findElements(entitlementError).isEmpty() &&
                driver.findElement(entitlementError).isDisplayed();
    }
}
