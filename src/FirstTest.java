import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","androidTestDevice");
        capabilities.setCapability("platformVersion","5.1");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");

        if(System.getProperty("os.name").equals("Windows 10")) {
            capabilities.setCapability("app", "C:\\JavaProjects\\appium\\apks\\org.wikipedia.apk");
        }else {
            capabilities.setCapability("app", "/Users/andrey/git/appium/apks/org.wikipedia.apk");
        }

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest(){
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
    public void cancelSearch(){
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
    public void textComparing(){
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
    public void inputPlaceHolder(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "first input not found",
                5);
        driver.hideKeyboard();
        Assert.assertTrue(isTextExist(By.xpath("//*[contains(@text, 'Search…')]"),"Search…"));
    }

    @Test
    public void searchResultList(){
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
    public void resultScan(){
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
    public void swipeArticleTest(){
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
    public void addArticleToList(){
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
    public void emptyResultTest(){
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
    public void addFewArticles(){
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
    public void articleTitleTest(){
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
    protected void assertElementPresent(By by, String errorMessage){
        if(!driver.findElement(by).isDisplayed()){
            String message = "An elements ["+by.toString()+"] not present";
            throw new AssertionError(message+" "+errorMessage);
        }
    }
    private void assertElementsNotPresent(By by, String errorMessage){
        int amountElements = amountOfElements(by);
        if (amountElements>0){
            String message = "An elements ["+by.toString()+"] not present";
            throw new AssertionError(message+" "+errorMessage);
        }
    }

    public int amountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    protected void swipeElementLeft(By by, String errorMessage){
        WebElement element = waitForElementPresent(
                by,
                "Cannot find element to swipe left \n",
                10);
        int left_x = element.getLocation().getX();
        int right_x=left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y+lower_y)/2;
        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x,middle_y)
                .release()
                .perform();
    }

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height*0.8);
        int end_y = (int)(size.height*0.2);
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToElement(By by, String errorMessage, int maxSwipes){
        int alredySwiped=0;
        while (driver.findElements(by).size() ==0){
            if(alredySwiped>maxSwipes){
                waitForElementPresent(by, "Cannot find element by swiping up \n"+errorMessage,0);
                return;
            }
            swipeUpQuick();
            ++alredySwiped;
        }
    }


    private boolean isTextExist(By by, String text){
        return waitForElementPresent(by, "searched element not presented").getAttribute("text").equals(text);
    }

    private WebElement waitForElementPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private List<WebElement> waitForWebElementCollectionPresent(By by, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.withMessage("\n\n\n Element Collection not found \n\n\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private WebElement waitForElementPresent(By by, String errMessage){
        return waitForElementPresent(by, errMessage,5);
    }

    private WebElement waitForElementAndClick(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.clear();
        return element;
    }
}
