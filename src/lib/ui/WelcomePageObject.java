package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * created by Andrey Melnichenko at 18:20 18-09-2018
 */

public class WelcomePageObject extends MainPageObject {
    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }

    public void firstScreen(){
        this.waitForElementPresent(By.id("Learn more about Wikipedia"), "Cannot find Wiki learn more link", 10);
    }

    public void clickNextButton(){
        this.waitForElementAndClick(By.id("Next"), "Cannot find NEXT button", 10);
    }

    public void secondScreen(){
        this.waitForElementPresent(By.id("New ways to explore"), "Cannot find Second Screen TEXT", 10);
    }

    public void thirdScreen(){
        this.waitForElementPresent(By.id("Add or edit preferred languages"), "Cannot find Third Screen TEXT", 10);
    }

    public void fourScreen(){
        this.waitForElementPresent(By.id("Learn more about data collected"), "Cannot find Four Screen TEXT", 10);
    }

    public void clickGetStartedButton(){
        this.waitForElementAndClick(By.id("Get started"), "Cannot find Get started button", 15);
    }
}
