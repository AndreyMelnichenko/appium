package Mdp;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected AndroidDriver<AndroidElement> driver;
    @BeforeClass
    public void setUp () throws MalformedURLException, InterruptedException {
        File f = new File("src/main/resources");
        File fs = new File(f, "mdp.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus10");
        System.out.println(fs);
        cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        Thread.sleep(10);
        driver.closeApp();
    }

    @AfterClass
    public void tearDown(){
        driver.closeApp();
    }
}