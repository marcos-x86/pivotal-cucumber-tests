package com.pivotaltracker.stepdefinitions;

import com.pivotaltracker.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

public class WebUIStepDefinitions {

    private ConfigReader configReader = new ConfigReader();
    private WebDriver driver;

    @Given("I navigate to the following URL {string}")
    public void iNavigateToTheFollowingURL(String urlFromFeatureFile) {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(option);

        driver.manage().window().maximize();
        driver.get(urlFromFeatureFile);
    }

    @When("I set a valid user email")
    public void iSetAValidUserEmail() {
        String email = configReader.getUserEmail();
        WebElement emailInput = driver.findElement(By.cssSelector("#credentials_username"));
        emailInput.sendKeys(email);
    }

    @And("I click on the Next button")
    public void iClickOnTheNextButton() {
        WebElement nextButton = driver.findElement(By.cssSelector("#login_type_check_form > input.app_signin_action_button"));
        nextButton.click();
    }

    @And("I set a valid user password")
    public void iSetAValidUserPassword() {
        String password = configReader.getUserPassword();
        WebElement passwordInput = driver.findElement(By.cssSelector("#credentials_password"));
        passwordInput.sendKeys(password);
    }

    @And("I click on the SignIn button")
    public void iClickOnTheSignInButton() {
        WebElement sigInButton = driver.findElement(By.cssSelector("#login_type_check_form > input.app_signin_action_button"));
        sigInButton.click();
    }

    @Then("I verify that I'm in the dashboard page")
    public void iVerifyThatIMInTheDashboardPage() {
        WebElement createProjectButton = driver.findElement(By.cssSelector("#create-project-button"));
        Assert.assertTrue(createProjectButton.isDisplayed());

        WebElement pivotalLogo = driver.findElement(By.xpath("//*[@id=\"shared_header\"]/div/div/header/ul/li[2]/div/button/div"));
        Assert.assertTrue(pivotalLogo.isDisplayed());
    }

    @When("I set the following email {string}")
    public void iSetTheFollowingEmail(String emailFromFeature) {
        WebElement emailInput = driver.findElement(By.cssSelector("#credentials_username"));
        emailInput.sendKeys(emailFromFeature);
    }

    @And("I set the following password {string}")
    public void iSetTheFollowingPassword(String passwordFromFeature) {
        WebElement passwordInput = driver.findElement(By.cssSelector("#credentials_password"));
        passwordInput.sendKeys(passwordFromFeature);
    }

    @Then("I verify that the following error message is displayed {string}")
    public void iVerifyThatTheFollowingErrorMessageIsDisplayed(String errorMessageFromFeature) {
        WebElement errorMessageElement = driver.findElement(By.cssSelector("#login_type_check_form > div.app_signin_error"));
        String actualErrorMessage = errorMessageElement.getText();
        Assert.assertEquals(actualErrorMessage, errorMessageFromFeature);
    }

    @After(value = "@closeDriver")
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
