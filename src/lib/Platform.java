package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * created by Andrey Melnichenko at 19:03 20-09-2018
 */

public class Platform {
    private static final String PLATFORM_IOS="ios";
    private static final String PLATFORM_ANDROID="android";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    public AppiumDriver getDriver() throws Exception{
        URL url = new URL(APPIUM_URL);
        if(this.isAndroid()){
            return new AndroidDriver(url, this.getAndroidDesiredCapabilities());
        }else if(this.isIOS()){
            return new IOSDriver(url, this.getIOSDesiredCapabilities());
        }else {
            throw  new Exception("Cannot detect type of platform"+this.getPlatformVar());
        }
    }

    public boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS(){
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","androidTestDevice");
        capabilities.setCapability("platformVersion","5.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app", "/Users/andrey/git/appium/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone 8 Plus");
        capabilities.setCapability("platformVersion","11.3");
        capabilities.setCapability("app","/Users/andrey/git/appium/apks/Wikipedia.app");
        return capabilities;
    }

    private String getPlatformVar(){
        return System.getenv("PLATFORM");
    }

    private boolean isPlatform(String myPlatform){
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }
}
