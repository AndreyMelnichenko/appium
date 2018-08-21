import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
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
                "//*[contains(@text, 'Search Wikipedia')]",
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                "//*[contains(@text, 'Search…')]",
                "java",
                "search area not found",
                5);
        //List<WebElement> searchResults = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));
        waitForElementAndClick(
                "//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, 'Object-oriented programming language')]",
                "Can't find a result",
                15);
    }

    @Test
    public void cancelSearch(){
        waitForElementByIdAndClick("org.wikipedia:id/search_container",
                "first input not found",
                5);
        waitForElementByIdAndClick("org.wikipedia:id/search_close_btn",
                "Can't find a result",
                5);
        waitForElementNotPresent("org.wikipedia:id/search_close_btn",
                "element presented",
                5);
    }

    private WebElement waitForElementByXpath(String xpath, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementByXpath(String xpath, String errMessage){
        return waitForElementByXpath(xpath, errMessage,5);
    }

    private WebElement waitForElementAndClick(String xpath, String errMessage, int timeOut){
        WebElement element = waitForElementByXpath(xpath,errMessage,timeOut);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(String xpath, String value, String errMessage, int timeOut){
        WebElement element = waitForElementByXpath(xpath,errMessage,timeOut);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementPresentById(String id, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        By by = By.id(id);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementByIdAndClick(String id, String errMessage, int timeOut){
        WebElement element = waitForElementPresentById(id,errMessage,timeOut);
        element.click();
        return element;
    }

    private boolean waitForElementNotPresent(String id, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        By by = By.id(id);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
