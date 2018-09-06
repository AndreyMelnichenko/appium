import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class FirstTest extends CoreTestCase {

    @Test
    public void testSearchArticle(){
        System.out.println("First test run");
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        //List<WebElement> searchResults = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, 'Object-oriented programming language')]"),
                "Can't find a result",
                15);
    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "first input not found",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "search area not found",
                6);
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find a result",
                5);
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "element presented",
                5);
    }

    @Test
    public void testTextComparing(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, 'Object-oriented programming language')]"),
                "Can't find a result",
                15);
        WebElement element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "search area not found"
        );
        Assert.assertEquals("We see unexpexted text",element.getAttribute("text"),"Java (programming language)");
    }

    @Test
    public void testInputPlaceHolder(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        Assert.assertTrue(isTextExist(By.xpath("//*[contains(@text, 'Search…')]"),"Search…"));
    }

    @Test
    public void testSearchResultList(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "java",
                "search area not found",
                5);
        List<WebElement> searchResults = waitForWebElementCollectionPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                5);
        Assert.assertNotEquals(0, searchResults.size());
        waitForElementAndClick(
                By.className("android.widget.ImageButton"),
                "Can't find a result",
                5);
        Assert.assertTrue(waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "element presented",
                5));

    }

    @Test
    public void testResultScan(){
        String searchWord = "football";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchWord,
                "search area not found",
                5);
        List<WebElement> searchResults = waitForWebElementCollectionPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                6);
        List<String> textSearchResult = new ArrayList<>();
        searchResults.forEach((a)->{
            if(!(a.getAttribute("text").toLowerCase().contains(searchWord))){
                textSearchResult.add(a.getAttribute("text").toLowerCase());
            }
        });
        if (textSearchResult.size()!=0){
            textSearchResult.forEach(System.out::println);
        }
        Assert.assertEquals("\n\n\nFound unexpected results printed above\n\n\n",0,textSearchResult.size());
    }

    @Test
    public void testSwipeArticleTest(){
        String searchText = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "search area not found",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchText+"')]"),
                "Can't find a result",
                5);
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        swipeUpToElement(
                By.xpath("//*[@text='View page in browser']"),
                "End point of page didn't find",
                20
        );
    }

    @Test
    public void testAddArticleToList(){
        String searchText = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "search area not found",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchText+"')]"),
                "Can't find a result",
                5);
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Context button not found",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find necessary item",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Got it']"),
                "Cannot find a button GO IT",
                5
        );

        waitForElementAndClear(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                "Cannot clear an input area",
                5
        );
        String myList = "my list";
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                myList,
                "Cannot find a input field",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find a button Ok",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot X button click",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot click on MY LISTS button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='"+myList+"']"),
                "Cannot name of my LIST",
                5
        );
        swipeElementLeft(
                By.xpath("//*[@text='"+searchText+"']"),
                "Cannot swipe left element"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='"+searchText+"']"),
                "Element didnt delete!",
                5
        );
    }

    @Test
    public void testEmptyResultTest(){
        String searchText = "Ja045j045t450gva";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "search area not found",
                5);
        String searchResult = "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";
        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot empty result label found",
                15
        );
        assertElementsNotPresent(
                By.xpath(searchResult),
                "we found some results "+searchText
        );
    }
//------------------------HW-3 ------------------------------

    @Test
    public void testAddFewArticles(){
        String searchText = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "search area not found",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchText+"')]"),
                "Can't find a result",
                5);
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Context button not found",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find necessary item",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Got it']"),
                "Cannot find a button GO IT",
                5
        );
        String myList = "my list";
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                myList,
                "Cannot find a input field",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find a button Ok",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot X button click",
                5
        );
        //---------------------------Part 2
        String searchText2 = "Bash";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText2,
                "search area not found",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Wikimedia disambiguation page')]"),
                "Can't find a result",
                15);
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Context button not found",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find necessary item",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title']"),
                "Cannot find click fo add second article",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot X button click",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot click on MY LISTS button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title']"),
                "Cannot open MY Favorite LISTS",
                5
        );
        swipeElementLeft(
                By.xpath("//*[@text='"+searchText+"']"),
                "Cannot swipe left element"
        );

        assertElementsNotPresent(
                By.xpath("//*[@text='"+searchText+"']"),
                "we found some results "+searchText
        );
        waitForElementAndClick(
                By.xpath("//*[@text='"+searchText2+"']"),
                "Element didnt delete!",
                5
        );
        String articleTitle = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        ).getAttribute("text");
        Assert.assertEquals(articleTitle, searchText2);
    }

    @Test
    public void testArticleTitleTest(){
        String searchArticleName = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchArticleName,
                "search area not found",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchArticleName+"')]"),
                "Can't find a result",
                5);
        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented"
        );
    }

    //-------------------- util methods --------------------------------------------------------------------------------

}
