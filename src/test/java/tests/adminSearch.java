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

public class adminSearch {
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

    }

    @Test
    public void testSearchAdminUser() {
        loginPage.login("Admin", "admin123");
        adminPage.searchForUser("selim");
        Assert.assertTrue(adminPage.isUserInResults("selim"), "alice_duval user not found in search results");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}