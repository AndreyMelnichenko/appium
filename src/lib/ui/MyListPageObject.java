package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListPageObject extends MainPageObject {

    public static final String
        MY_FOLDER="xpath://*[@text='{SUBSTRING}']",
        ARTICLE_BY_TITLE="xpath://*[@text='{SUBSTRING}']";

    public MyListPageObject(AppiumDriver driver){
        super(driver);
    }

    private static String replacerFolderBuName(String substring){
        return MY_FOLDER.replace("{SUBSTRING}",substring);
    }

    private static String replacerArticleByTitle(String substring){
        return ARTICLE_BY_TITLE.replace("{SUBSTRING}",substring);
    }

    public void openFolderByName(String folderName){
        String finalXpath = replacerFolderBuName(folderName);
        this.waitForElementAndClick(
                finalXpath,
                "Cannot find a folder by name "+folderName,
                5
        );
    }

    public void swipeArticleToDelete(String articleTitle){
        String finalXpath = replacerArticleByTitle(articleTitle);
        this.waitForArticleAppeared(articleTitle);
        this.swipeElementLeft(
                finalXpath,
                "Cannot swipe left element"
        );
        this.waitForArticleDisappeared(articleTitle);
    }

    public void waitForArticleDisappeared(String articleTitle){
        String finalXpath = replacerArticleByTitle(articleTitle);
        this.waitForElementNotPresent(
                finalXpath,
                "Element didnt delete!",
                15
        );
    }

    public void waitForArticleAppeared(String articleTitle){
        String finalXpath = replacerArticleByTitle(articleTitle);
        this.waitForElementPresent(
                finalXpath,
                "Element didnt delete!",
                15
        );
    }
}
