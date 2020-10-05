package wexsearchfunctionality;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class StepDefinitions {
  WebDriver driver = new ChromeDriver();
  
  @Given("I am on the WEX website")
  public void visitWex() throws InterruptedException {
    driver.get("https://www.wexinc.com/");
    driver.manage().window().maximize();
    Thread.sleep(2000);
  }

  @When("I search on the page for {string}")
  public void searchFor(String query) {
    WebDriverWait wait = new WebDriverWait(driver, 60);

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mega-menu-item-439 > a")));
    WebElement searchButton = driver.findElement(By.cssSelector("#mega-menu-item-439 > a"));
    searchButton.click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("s")));
    WebElement searchForm = driver.findElement(By.name("s"));
    searchForm.sendKeys(query);
    searchForm.submit();
  }

  @Then("the page title should be {string}")
  public void verifyTitle(String title) {
    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return d.getTitle().equals(title);
      }
    });
  }

  @Then("the navigation section element should contain {string}")
  public void verifyNavigationSection(String title) {
    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return d.findElement(By.xpath("/html/body/div[1]/div[5]/div[2]/div/div/nav/ul/li[4]")).getText().equals(title);
      }
    });
  }

  @Then("the search should not return results")
  public void verifyNoResults(){
    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return d.findElement(By.xpath("//*[@id='mainContent']/div[2]/div/div/div/p")).getText().equals("Sorry, there are no results that meet this criteria.");
      }
    });
  }

  @Then("the search must return a maximum of 10 posts per page")
  public void verifyMaxOfPosts(){
    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return d.findElements(By.className("wex-search-item wex-vr-m-bottom-small")).size() <= 10;
      }
    });
  }

  @Then("the search must return a navigation bar")
  public void verifyNavigationBar(){
    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return d.findElements(By.cssSelector("#mainContent > div.grid-container.wex-vr-m-bottom-xxxlarge > div > div > nav > ul > li.pagination-next")).size() != 0;
      }
    });
  }

  @Then("the {string} word should appear in the title or in the body text of the {string} post returned")
  public void verifyReturnedContent(String title, String position){
    WebDriverWait wait = new WebDriverWait(driver, 10);
    String index = position.split(" ")[0];

    String postUrl = driver.findElement(By.cssSelector(String.format("#mainContent > div.grid-container.wex-vr-m-bottom-xxxlarge > div > div > div:nth-child(%s) > h4 > a", index))).getAttribute("href");

    driver.get(postUrl);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.off-canvas-wrapper > div.off-canvas-content > div.wex-hero_container.wex-hero_container--short > div > div > div > div > div > h1")));

    String stringToBeTested = driver.findElement(By.cssSelector("body > div.off-canvas-wrapper > div.off-canvas-content")).getText();

    new WebDriverWait(driver, 10L).until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver d) {
        return stringToBeTested.toLowerCase().contains(title.toLowerCase());
      } 
    });
  }
  
  @After()
  public void closeBrowser() {
    driver.quit();
  }
}
