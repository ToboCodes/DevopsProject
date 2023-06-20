package seleniumtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleSearchTest {

    @Test
    public void googleSearch() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

        // Go to the Google home page
        driver.get("https://www.google.com");

        // Find the search box using its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter a search query
        element.sendKeys("Kibernum");

        // Submit the query. WebDriver will find the form for us from the element
        element.submit();

        // Wait for the page title to be updated
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // wait up to 10 seconds
        wait.until(ExpectedConditions.titleContains("Kibernum"));

        // Check that the title contains the search query
        Assertions.assertTrue(driver.getTitle().contains("Kibernum"));

        driver.quit();
    }
}