
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class adminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public adminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Locators ---
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By addButton = By.xpath("//button[normalize-space()='Add']");
    private By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[1]");
    private By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    private By employeeNameField = By.cssSelector("input[placeholder='Type for hints...']");
    private By passwordField = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By searchUsernameField = By.xpath("//div[contains(@class,'oxd-input-group')]//input");

    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By userRecords = By.cssSelector(".oxd-table-card");
    private By toastMessage = By.cssSelector(".oxd-toast-container");
    private By confirmDeleteButton = By.cssSelector(".oxd-button--label-danger");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");

    // --- Reusable Methods ---
    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    private void selectFromDropdown(By dropdown, String optionText) {
        click(dropdown);
        click(By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", optionText)));
    }

    private void selectEmployee(String name) {
        type(employeeNameField, name);
        By suggestion = By.xpath("//div[@role='option'][contains(.,'" + name.split(" ")[0] + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(suggestion)).click();
    }

    // --- Page Actions ---
    public void navigateToAdminSection() {
        click(adminTab);
    }

    public void addNewUser(String username, String employeeName, String password, String confirmPass) {
        try {
            navigateToAdminSection();
            click(addButton);
            selectFromDropdown(userRoleDropdown, "Admin");
            selectFromDropdown(statusDropdown, "Enabled");
            type(usernameField, username);
            selectEmployee(employeeName);
            type(passwordField, password);
            type(confirmPasswordField, confirmPass);
            click(saveButton);

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(toastMessage),
                    ExpectedConditions.invisibilityOfElementLocated(saveButton)
            ));

            String message = driver.findElement(toastMessage).getText();
            if (!message.contains("Success")) {
                throw new RuntimeException("Unexpected toast message: " + message);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to add new user: " + e.getMessage(), e);
        }
    }

    public void addNewUserWithoutPass(String username, String employeeName) {
        navigateToAdminSection();
        click(addButton);
        selectFromDropdown(userRoleDropdown, "Admin");
        selectFromDropdown(statusDropdown, "Enabled");
        type(usernameField, username);
        selectEmployee(employeeName);
        click(saveButton);
    }

    public void deleteUser(String username) {
        try {
            navigateToAdminSection();
            type(usernameField, username);
            click(searchButton);

            List<WebElement> users = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(userRecords));
            if (users.isEmpty()) {
                throw new NoSuchElementException("User not found: " + username);
            }

            WebElement deleteIcon = users.get(0).findElement(By.cssSelector("i.bi-trash"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteIcon);
            deleteIcon.click();
            click(confirmDeleteButton);

            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
            Assert.assertTrue(toast.getText().contains("Successfully Deleted"), "Delete failed!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + username, e);
        }
    }

    public void searchForUser(String username) {
        navigateToAdminSection();
        type(searchUsernameField, username);
        click(searchButton);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(userRecords));

    }

    public void searchUser(String username, String userRole, String status) {
        navigateToAdminSection();

        type(searchUsernameField, username);
        if (userRole != null && !userRole.isEmpty()) {
            selectFromDropdown(userRoleDropdown, userRole);
        }
        if (status != null && !status.isEmpty()) {
            selectFromDropdown(statusDropdown, status);
        }
        click(searchButton);
    }

    public void clickReset() {
        click(resetButton);
    }

    public boolean isUserInResults(String username) {
        List<WebElement> users = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(userRecords));
        return users.stream().anyMatch(record -> record.getText().contains(username));
    }

    public boolean areFieldsCleared() {
        try {
            Thread.sleep(1000);
            String userInput = driver.findElement(searchUsernameField).getAttribute("value");
            String roleText = driver.findElement(userRoleDropdown).getText();
            String statusText = driver.findElement(statusDropdown).getText();

            return userInput.isEmpty() && roleText.equals("-- Select --") && statusText.equals("-- Select --");
        } catch (Exception e) {
            return false;
        }
    }
}
