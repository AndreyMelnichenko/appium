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

    private AndroidDriver driver;
    private static final String APP_NAME = "mdp.apk";
    private static final String APP_PATH = "src/main/resources";
    private static final String DEVICE_NAME = "Nexus10";
    private static final String REMOTE_HOST = "http://127.0.0.1:4723/wd/hub";

    @BeforeClass
    public void setUp () throws MalformedURLException {
        File f = new File(APP_PATH);
        File fs = new File(f, APP_NAME);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
        //cap.setCapability(MobileCapabilityType.PA, "modern_paper");
        driver = new AndroidDriver(new URL(REMOTE_HOST), cap);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown(){
        driver.closeApp();
    }
}