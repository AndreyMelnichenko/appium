package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    protected Platform platform;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.platform=new Platform();
        driver = this.platform.getDriver();
        this.rotateScreenPortrait();
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

/*

    private AppiumDriver getDriver(URL url, DesiredCapabilities capabilities) throws Exception{
        String platform = System.getenv("PLATFORM");
        if(platform.equals(PLATFORM_ANDROID)) {
            return driver = new AndroidDriver(url, capabilities);
        }else {
            return driver= new IOSDriver(url, capabilities);
        }
    }*/
}
