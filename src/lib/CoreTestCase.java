package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String url = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","androidTestDevice");
        capabilities.setCapability("platformVersion","5.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");

        if(System.getProperty("os.name").equals("Windows 10")) {
            capabilities.setCapability("app", "C:\\JavaProjects\\appium\\apks\\org.wikipedia.apk");
        }else {
            capabilities.setCapability("app", "/Users/andrey/git/appium/apks/org.wikipedia.apk");
        }

        driver = new AndroidDriver(new URL(url), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }
}
