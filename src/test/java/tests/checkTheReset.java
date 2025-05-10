package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.WebDriverFactory;
import pages.adminPage;
import pages.loginPage;

public class checkTheReset {
    private WebDriver driver;
    private loginPage loginPage;
    private adminPage adminPage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();

        loginPage = new loginPage(driver);
        adminPage = new adminPage(driver);


        // Navigate to application and login


    }

    @Test
    public void testUserSearchAndReset() {
        loginPage.login("Admin", "admin123");
        adminPage.navigateToAdminSection();
        adminPage.searchUser("FMLName", "ESS", "Enabled");
        adminPage.clickReset();
        Assert.assertTrue(adminPage.areFieldsCleared(), "Fields were not cleared after reset");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}