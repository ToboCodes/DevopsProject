package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.GoogleSearchPage;

public class GoogleSearchSteps {
    private WebDriver driver;
    private GoogleSearchPage googleSearchPage;

    @Given("I am on the Google search page")
    public void i_am_on_the_google_search_page() {
        driver = new FirefoxDriver();
        googleSearchPage = new GoogleSearchPage(driver);
        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        googleSearchPage.searchFor(searchTerm);
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        Assertions.assertTrue(driver.getTitle().contains(expectedTitle));
        driver.quit();
    }
}
