package ge.tsu.boredreader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class prodTest {
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(prodTest.class.getName());

    @BeforeEach
    void setup() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void login() {
        try {
            driver.get("https://localhost:11443/");

            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement welcomeElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("welcome")));

            assertTrue(welcomeElement.isDisplayed(), "Element class welcome should be visible after  login");



        } catch (Exception e) {
            logger.severe("TEST FAILED: Login did not work - " + e.getMessage());
            throw e;
        }
    }
}
