package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.PIMPage;
import pages.WebDriverFactory;
import pages.loginPage;
import pages.adminPage;

public class AddUserTest {
    WebDriver driver;
    loginPage loginPage;
    adminPage adminPage;

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();

        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);
    }

    @Test
    public void testAddAdminUser() {
        loginPage.login("Admin", "admin123");
        adminPage.addNewUser("loay nabilhllllnnllm", "Alice  Duval .", "loay1234","loay1234");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
