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
        FOOTER="//*[@text='View page in browser']",
        OPTIONS="//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST="//*[@text='Add to reading list']",
        ADD_TO_MY_LIST="//*[@text='Got it']",
        CLEAR_INPUT_FIELD="//*[@resource-id='org.wikipedia:id/text_input']",
        OK_BUTTON="//*[@text='OK']",
        EXISTS_MY_LIST="//*[@resource-id='org.wikipedia:id/item_title']",
        CLOSE_ARTICLE_BUTTON="//android.widget.ImageButton[@content-desc='Navigate up']";



    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE), "search area not found", 15);
    }

    public boolean assertArticleTitlePresent(){
        return this.waitForElementPresent(
                By.id(TITLE),
                "Article title not presented"
                ).isDisplayed();
    }

    public String articleTitle(){
        WebElement element = waitForTitleElement();
        return element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToElement(By.xpath(FOOTER),"End point of page didn't find", 20);
    }

    public void addArticleToMyList(String folderName){
        this.waitForElementAndClick(
                By.xpath(OPTIONS),
                "Context button not found",
                5
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST),
                "Cannot find necessary item",
                5
        );
        this.waitForElementAndClick(
                By.xpath(ADD_TO_MY_LIST),
                "Cannot find a button GO IT",
                5
        );

        this.waitForElementAndClear(
                By.xpath(CLEAR_INPUT_FIELD),
                "Cannot clear an input area",
                5
        );
        this.waitForElementAndSendKeys(
                By.xpath(CLEAR_INPUT_FIELD),
                folderName,
                "Cannot find a input field",
                5
        );
        this.waitForElementAndClick(
                By.xpath(OK_BUTTON),
                "Cannot find a button Ok",
                5
        );
    }

    public void addArticleToMyList(){
        this.waitForElementAndClick(
                By.xpath(OPTIONS),
                "Context button not found",
                5
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST),
                "Cannot find necessary item",
                5
        );
        this.waitForElementAndClick(
                By.xpath(EXISTS_MY_LIST),
                "Cannot find click fo add second article",
                5
        );

    }

    public void closeArticle(){
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot X button click",
                5
        );
    }
}
