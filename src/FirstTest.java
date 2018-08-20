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
        capabilities.setCapability("platformVersion","5.1");
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
        WebElement goSearch = waitFor("//*[contains(@text, 'Search Wikipedia')]", "first input not found",5);
        goSearch.click();
        driver.hideKeyboard();
        WebElement searchField = waitFor("//*[contains(@text, 'Search…')]", "search area not found",5);
        searchField.sendKeys("java");
        WebElement tapToResult = waitFor("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']", "Can't find a result", 15);
        tapToResult.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WebElement waitFor(String xpath, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
