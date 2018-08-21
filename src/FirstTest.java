import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","androidTestDevice");
        capabilities.setCapability("platformVersion","5.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/andrey/git/appium/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest(){
        System.out.println("First test run");
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        //List<WebElement> searchResults = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, 'Object-oriented programming language')]"),
                "Can't find a result",
                15);
    }

    @Test
    public void cancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "first input not found",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "search area not found",
                5);
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find a result",
                5);
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "element presented",
                5);
    }

    @Test
    public void textComparing(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, 'Object-oriented programming language')]"),
                "Can't find a result",
                15);
        WebElement element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "search area not found"
        );
        Assert.assertEquals("We see unexpexted text",element.getAttribute("text"),"Java (programming language)");
    }

    @Test
    public void inputPlaceHolder(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        WebElement element = waitForElementPresent(By.xpath("//*[contains(@text, 'Search…')]"),
                "search area not found");
        Assert.assertEquals("We see unexpexted text",element.getAttribute("text"),"Search…");
    }

    private WebElement waitForElementPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errMessage){
        return waitForElementPresent(by, errMessage,5);
    }

    private WebElement waitForElementAndClick(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.clear();
        return element;
    }
}
