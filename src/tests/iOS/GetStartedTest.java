package tests.iOS;

import lib.CoreTestCase;
import lib.iOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

/**
 * created by Andrey Melnichenko at 18:18 18-09-2018
 */

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testPassThroughWelcome(){
        if(this.platform.isAndroid()){
            return;
        }
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
