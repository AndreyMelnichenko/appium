package tests.iOS;

import lib.iOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

/**
 * created by Andrey Melnichenko at 18:18 18-09-2018
 */

public class GetStartedTest extends iOSTestCase {

    @Test
    public void testPassThroughWlcome(){
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
        welcomePageObject.firstScreen();
        welcomePageObject.clickNextButton();
        welcomePageObject.secondScreen();
        welcomePageObject.clickNextButton();
        welcomePageObject.thirdScreen();
        welcomePageObject.clickNextButton();
        welcomePageObject.fourScreen();
        welcomePageObject.clickGetStartedButton();
    }
}
