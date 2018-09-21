package lib.ui;

import io.appium.java_client.AppiumDriver;

/**
 * created by Andrey Melnichenko at 18:20 18-09-2018
 */

public class WelcomePageObject extends MainPageObject {
    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }

    private static final String
        FIRST_SCREEN_LINK="id:Learn more about Wikipedia",
        FIRST_SCREEN_NEXT="id:Next",
        SECOND_SCREEN_LINK="id:New ways to explore",
        THIRD_SCREEN_LINK="id:Add or edit preferred languages",
        FOUR_SCREEN_LINK="id:Learn more about data collected",
        GET_STARTED_BUTTON="id:Get started";

    public void firstScreen(){
        this.waitForElementPresent(FIRST_SCREEN_LINK, "Cannot find Wiki learn more link", 10);
    }

    public void clickNextButton(){
        this.waitForElementAndClick(FIRST_SCREEN_NEXT, "Cannot find NEXT button", 10);
    }

    public void secondScreen(){
        this.waitForElementPresent(SECOND_SCREEN_LINK, "Cannot find Second Screen TEXT", 10);
    }

    public void thirdScreen(){
        this.waitForElementPresent(THIRD_SCREEN_LINK, "Cannot find Third Screen TEXT", 10);
    }

    public void fourScreen(){
        this.waitForElementPresent(FOUR_SCREEN_LINK, "Cannot find Four Screen TEXT", 10);
    }

    public void clickGetStartedButton(){
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find Get started button", 15);
    }
}
