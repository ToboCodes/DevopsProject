package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleSearchPage {
    private WebDriver driver;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchFor(String searchTerm) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(searchTerm);
        searchBox.submit();
    }
}
