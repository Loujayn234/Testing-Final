package pages;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class PIMPage {
    private WebDriver driver;
    private WebDriverWait wait;


    // PIM menu in the sidebar
    private By pimMenu = By.cssSelector("a.oxd-main-menu-item[href*='pim']");

    // "Employee List" link under PIM
    private By employeeListLink = By.xpath("//a[text()='Employee List']");

    // "Add" button on Employee List page
    private By addButton = By.xpath("//button[text()=' Add ']");


    // Input field for First Name
    private By firstNameInput = By.name("firstName");

    // Input field for Middle Name
    private By middleNameInput = By.name("middleName");

    // Input field for Last Name
    private By lastNameInput = By.name("lastName");

    // "Save" button on the form
    private By saveButton = By.xpath("//button[normalize-space()='Save']");

    // Toast message container (for success/error notifications)
    private By toastMessage = By.cssSelector(".oxd-toast-container");

    // Loading spinner (appears during page load or actions)
    private By loadingSpinner = By.cssSelector(".oxd-loading-spinner");

    // List of employee records displayed in table cards
    private By employeeRecords= By.xpath("//div[contains(@class, 'oxd-table-row')]");





    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }



    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
    }

    public void navigateToEmployeeList() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeListLink)).click();
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void enterEmployeeDetails(String firstName, String middleName, String lastName) {

        navigateToPIM();
        navigateToEmployeeList();
        clickAddEmployee();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(middleNameInput).sendKeys(middleName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        clickSaveAndWait();
    }

    public void clickSaveAndWait() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(saveButton));
            wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
        } catch (Exception e) {
            System.out.println("Error after clicking save: " + e.getMessage());
        }
    }


    public void clickEmployeeRecordByIdAndLastName(String employeeId, String lastName) {
        navigateToPIM();
        navigateToEmployeeList();
        List<WebElement> records = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(employeeRecords));


        for (WebElement record : records) {
            String recordText = record.getText();
            System.out.println("Checking record: " + recordText);

            if (recordText.contains(employeeId) && recordText.contains(lastName)) {
                WebElement idCell = record.findElement(By.xpath(".//div[contains(@class, 'oxd-table-cell')][2]"));
                wait.until(ExpectedConditions.elementToBeClickable(idCell)).click();
                return;
            }
        }

        throw new NoSuchElementException("Employee with ID " + employeeId + " and last name " + lastName + " not found");
    }


}