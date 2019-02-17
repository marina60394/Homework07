package com.aqacoueses.tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by Marina on 17.02.2019.
 */
public class CheckLoginAlertsTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String URL = "https://s1.demo.opensourcecms.com/s/44";
    private final String INVALID_LOGIN_MESSAGE = "//span[@id='spanMessage']";
    public final String FIELD_LOGIN = "//input[@id='txtUsername']";
    public final String FIELD_PASSWORD = "//input[@id='txtPassword']";
    public final String BUTTON_LOGIN = "//input[@id='btnLogin']";
    public final String REMOVE_FRAME = "//div[@class='preview__action--close']/a/span";
    public final String IFRAME_PREVIEW = "//iframe[@name='preview-frame']";

    /**
     * Set up method to initialize driver and WebDriverWait
     */
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        // Explicitly wait
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Check Login Alerts
     */
    @Test
    public void checkLoginAlerts() {

        // go to the page
        driver.get(URL);

        // switch to iframe
        driver.switchTo().frame(driver.findElement(By.xpath(IFRAME_PREVIEW)));

        // find and sendKeys to field Login
        driver.findElement(By.xpath(FIELD_LOGIN)).sendKeys("testLogin");

        // find and sendKeys to field Password
        driver.findElement(By.xpath(FIELD_PASSWORD)).sendKeys("testPassword");

        // click to Button Login
        driver.findElement(By.xpath((BUTTON_LOGIN))).click();

        // Wait invalid message "Invalid credentials"
        wait.until(ExpectedConditions.textToBe(By.xpath(INVALID_LOGIN_MESSAGE), "Invalid credentials"));

        // click to Login button
        driver.findElement(By.xpath(BUTTON_LOGIN)).click();

        // Wait invalid message "Username cannot be empty"
        wait.until(ExpectedConditions.textToBe(By.xpath(INVALID_LOGIN_MESSAGE), "Username cannot be empty"));

        //  sendKeys to element Login
        driver.findElement(By.xpath(FIELD_LOGIN)).sendKeys("Login");

        // click to Login button
        driver.findElement(By.xpath(BUTTON_LOGIN)).click();

        // Wait invalid message "Password cannot be empty"
        wait.until(ExpectedConditions.textToBe(By.xpath(INVALID_LOGIN_MESSAGE), "Password cannot be empty"));

        // switch to default Content
        driver.switchTo().defaultContent();

        //  remove iframe
        driver.findElement(By.xpath(REMOVE_FRAME)).click();

        // Wait for iframe is not present on the DOM.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(IFRAME_PREVIEW)));

    }

    /**
     * Quit WebDriver
     */
    @After
    public void tearDown() {
        driver.quit();
    }


}


