package seleniumtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GoogleSearchTest {

    @Test
    public void googleSearch() {
        // Define a path to the geckodriver executable that is required by Firefox
    	System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
    	System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"./geckodriver.log");

    	FirefoxOptions options = new FirefoxOptions();
    	options.setLogLevel(FirefoxDriverLogLevel.TRACE);
    	
    	// Aquí ajusta la ruta a la ubicación de tu instalación de Firefox
    	options.setBinary("C:\\Program Files\\Firefox Developer Edition\\\\firefox.exe");

    	WebDriver driver = new FirefoxDriver(options);

        // Go to the Google home page
        driver.get("https://www.google.com");

        // Find the search box using its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter a search query
        element.sendKeys("OpenAI");

        // Submit the query. WebDriver will find the form for us from the element
        element.submit();
        
        // Wait for the page title to be updated
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // wait up to 10 seconds
        wait.until(ExpectedConditions.titleContains("OpenAI"));
        
        // Check that the title contains the search query
        Assertions.assertTrue(driver.getTitle().contains("OpenAI"));

        driver.quit();
    }
}