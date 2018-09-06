package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * created by Andrey Melnichenko at 19:29 06-09-2018
 */

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE="org.wikipedia:id/view_page_title_text",
        FOOTER="//*[@text='View page in browser']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE), "search area not found", 15);
    }

    public String articleTitle(){
        WebElement element = waitForTitleElement();
        return element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToElement(By.xpath(FOOTER),"End point of page didn't find", 20);
    }
}
