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
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","androidTestDevice");
        capabilities.setCapability("platformVersion","5.1");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");

        if(System.getProperty("os.name").equals("Windows 10")) {
            capabilities.setCapability("app", "C:\\JavaProjects\\appium\\apks\\org.wikipedia.apk");
        }else {
            capabilities.setCapability("app", "/Users/andrey/git/appium/apks/org.wikipedia.apk");
        }

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
        Assert.assertTrue(isTextExist(By.xpath("//*[contains(@text, 'Search…')]"),"Search…"));
    }

    @Test
    public void searchResultList(){
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> searchResults = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));
        Assert.assertNotEquals(0, searchResults.size());
        waitForElementAndClick(
                By.className("android.widget.ImageButton"),
                "Can't find a result",
                5);
        Assert.assertTrue(waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "element presented",
                5));

    }

    @Test
    public void resultScan(){
        String searchWord = "football";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchWord,
                "search area not found",
                5);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> searchResults = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        for(WebElement element:searchResults){
            Assert.assertTrue((element.getAttribute("text").toLowerCase()).contains(searchWord));
        }
    }

    private boolean isTextExist(By by, String text){
        return waitForElementPresent(by, "searched element not presented").getAttribute("text").equals(text);
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
