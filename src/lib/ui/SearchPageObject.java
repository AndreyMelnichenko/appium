package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Andrey Melnichenko at 18:44 06-09-2018
 */

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT="//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT="//*[contains(@text, 'Search…')]",
        SEARCH_RESULT_TPL ="//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, '{SUBSTRING}')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_SHORT="//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '{SUBSTRING}')]",
        SEARCH_RESULT_BASH="//*[contains(@text, 'Wikimedia disambiguation page')]",
        SEARCH_RESULT_ELEMETS="//*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULTS="//*[@text='No results found']",
        SEARCH_RESULTS_TITLES="//*[@resource-id='org.wikipedia:id/page_list_item_title']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* Template methods */

    private static String substitutor(String substring){
        return SEARCH_RESULT_TPL.replace("{SUBSTRING}",substring);
    }

    private static String substitutor(String substring, int i){
        return SEARCH_RESULT_SHORT.replace("{SUBSTRING}",substring);
    }

    public void initSerachInput(){
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find and click on search element",10);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find input after click search element",10);
    }

    public void typeSearchLine(String searchText){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),searchText,"Cannot find input element",10);
    }

    public boolean compareTextPlaceHolderSearchLine(String placeHolderLine){
        return this.waitForElementPresent(By.xpath(SEARCH_INPUT),
                "Cannot find input element",
                10).getAttribute("text").equals(placeHolderLine);
    }

    public void waitForResult(String substring){
        String finalXpath = substitutor(substring);
        this.waitForElementAndClick(By.xpath(finalXpath), "Cannot find results like "+substring,5);
    }

    public void clickByArticleSubString(String substring){
        String finalXpath = substitutor(substring);
        this.waitForElementAndClick(By.xpath(finalXpath), "Cannot find and click search result  "+substring,5);
    }

    public void clickByArticleSubString(String substring, int i){
        String finalXpath = substitutor(substring,i);
        this.waitForElementAndClick(By.xpath(finalXpath), "Cannot find and click search result  "+substring,5);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Can't search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button still present", 5);
    }

    public void clickOnCancelButton(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public int getAmountOfObject(){
        List<WebElement> searchResults = this.waitForWebElementCollectionPresent(
                By.xpath(SEARCH_RESULT_ELEMETS),
                5);
        return searchResults.size();
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULTS),"Cannot find empty label", 15);
    }

    public void assertThereIsNoResultSearch(){
        this.assertElementsNotPresent(By.xpath(SEARCH_RESULT_ELEMETS),"Result elements is present!");
    }

    public int matchResultList(String searchLine){
        List<WebElement> searchResults = this.waitForWebElementCollectionPresent(
                By.xpath(SEARCH_RESULTS_TITLES),
                6);
        List<String> textSearchResult = new ArrayList<>();
        searchResults.forEach((a)->{
            if(!(a.getAttribute("text").toLowerCase().contains(searchLine))){
                textSearchResult.add(a.getAttribute("text").toLowerCase());
            }
        });
        if (textSearchResult.size()!=0){
            textSearchResult.forEach(System.out::println);
            return 0;
        }
        return textSearchResult.size();
    }

    public void openSecondArticle(){
        this.waitForElementAndClick(By.xpath(SEARCH_RESULT_BASH),"Can't find a result",5);
    }

}
