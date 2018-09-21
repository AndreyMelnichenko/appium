package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUi extends MainPageObject {

    private static final String
            MY_LIST_LINK ="xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUi(AppiumDriver driver){
        super(driver);
    }

    public void clickMyList(){
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Cannot click on MY LISTS button",
                5
        );
    }
}
