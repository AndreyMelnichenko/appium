import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;
    protected void setUp() throws Exception{
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearchArticle(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine("java");
        searchPageObject.waitForResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickOnCancelButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testTextComparing(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSerachInput();
        driver.hideKeyboard();
        searchPageObject.typeSearchLine("java");
        searchPageObject.clickByArticleSubString("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        Assert.assertEquals("We see unexpexted text",articlePageObject.articleTitle(),"Java (programming language)");
    }

    @Test
    public void testInputPlaceHolder(){
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        Assert.assertTrue(mainPageObject.isTextExist(By.xpath("//*[contains(@text, 'Search…')]"),"Search…"));
    }

    @Test//HW
    public void testResultScan(){
        String searchLine = "football";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchLine);

        List<WebElement> searchResults = mainPageObject.waitForWebElementCollectionPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                6);
        List<String> textSearchResult = new ArrayList<>();
        searchResults.forEach((a)->{
            if(!(a.getAttribute("text").toLowerCase().contains(searchLine))){
                textSearchResult.add(a.getAttribute("text").toLowerCase());
            }
        });
        if (textSearchResult.size()!=0){
            textSearchResult.forEach(System.out::println);
        }
        Assert.assertEquals("\n\n\nFound unexpected results printed above\n\n\n",0,textSearchResult.size());
    }

    @Test
    public void testSearchResultList(){
        String searchLine = "java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfResults = searchPageObject.getAmountOfObject();

        Assert.assertTrue("We Found few results", amountOfResults>0);
    }

    @Test
    public void testEmptyResultList(){
        String searchLine = "Ja045j045t450gva";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultSearch();
    }


    @Test//-------*********-----------
    public void testSwipeArticle(){
        String searchText = "Appium";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchText);
        searchPageObject.clickByArticleSubString(searchText,1);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testAddArticleToList(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine("java");
        searchPageObject.waitForResult("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.articleTitle();
        String myFolderName = "My-Folder";
        articlePageObject.addArticleToMyList(myFolderName);
        articlePageObject.closeArticle();
        NavigationUi navigationUi = new NavigationUi(driver);

        navigationUi.clickMyList();
        MyListPageObject myListPageObject = new MyListPageObject(driver);

        myListPageObject.openFolderByName(myFolderName);
        myListPageObject.swipeArticleToDelete(articleTitle);
    }
//------------------------HW-3 ------------------------------

    @Test
    public void testAddFewArticles(){
        String searchText = "Appium";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "search area not found",
                5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchText+"')]"),
                "Can't find a result",
                5);
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Context button not found",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find necessary item",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Got it']"),
                "Cannot find a button GO IT",
                5
        );
        String myList = "my list";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                myList,
                "Cannot find a input field",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find a button Ok",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot X button click",
                5
        );
        //---------------------------Part 2
        String searchText2 = "Bash";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText2,
                "search area not found",
                5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Wikimedia disambiguation page')]"),
                "Can't find a result",
                15);
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Context button not found",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find necessary item",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title']"),
                "Cannot find click fo add second article",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot X button click",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot click on MY LISTS button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title']"),
                "Cannot open MY Favorite LISTS",
                5
        );
        mainPageObject.swipeElementLeft(
                By.xpath("//*[@text='"+searchText+"']"),
                "Cannot swipe left element"
        );

        mainPageObject.assertElementsNotPresent(
                By.xpath("//*[@text='"+searchText+"']"),
                "we found some results "+searchText
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+searchText2+"']"),
                "Element didnt delete!",
                5
        );
        String articleTitle = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented",
                15
        ).getAttribute("text");
        Assert.assertEquals(articleTitle, searchText2);
    }

    @Test
    public void testArticleTitleTest(){
        String searchArticleName = "Appium";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchArticleName,
                "search area not found",
                5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '"+searchArticleName+"')]"),
                "Can't find a result",
                5);
        mainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not presented"
        );
    }
}
