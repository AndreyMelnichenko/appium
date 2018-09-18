package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS="ios";
    private static final String PLATFORM_ANDROID="android";

    protected AppiumDriver driver;
    private static String url = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = this.getCapabilitiesByPlatform();
        driver = this.getDriver(new URL(url), capabilities);//new AndroidDriver(new URL(url), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int timeOut){
        driver.runAppInBackground(timeOut);
    }

    private DesiredCapabilities getCapabilitiesByPlatform() throws Exception{
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (platform.equals(PLATFORM_ANDROID)){
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","androidTestDevice");
            capabilities.setCapability("platformVersion","5.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app", "/Users/andrey/git/appium/apks/org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)){
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 8 Plus");
            capabilities.setCapability("platformVersion","11.3");
            capabilities.setCapability("app","/Users/andrey/git/appium/apks/Wikipedia.app");
        } else {
            throw new Exception("Cannot find necessary caps like "+platform);
        }
        return capabilities;
    }

    private AppiumDriver getDriver(URL url, DesiredCapabilities capabilities) throws Exception{
        String platform = System.getenv("PLATFORM");
        if(platform.equals(PLATFORM_ANDROID)) {
            return driver = new AndroidDriver(url, capabilities);
        }else {
            return driver= new IOSDriver(url, capabilities);
        }
    }
}
