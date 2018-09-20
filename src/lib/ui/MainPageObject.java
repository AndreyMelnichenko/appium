package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver=driver;
    }

    public void assertElementPresent(String locator, String errorMessage){
        By by = this.getLocatorByString(locator);
        String message = "\n\n\nAn elements ["+by.toString()+"] not present\n\n\n";
        try{
            if(!driver.findElement(by).isDisplayed()){
                throw new AssertionError(message+" "+errorMessage);
            }
        }catch (NoSuchElementException e){
            throw new AssertionError(message+" "+errorMessage);
        }
    }
    public void assertElementsNotPresent(String locator, String errorMessage){
        By by = this.getLocatorByString(locator);
        int amountElements = amountOfElements(locator);
        if (amountElements>0){
            String message = "An elements ["+by.toString()+"] not present";
            throw new AssertionError(message+" "+errorMessage);
        }
    }

    public int amountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void swipeElementLeft(String locator, String errorMessage){
        WebElement element = waitForElementPresent(
                locator,
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

    public void swipeUpToElement(String locator, String errorMessage, int maxSwipes){
        By by = this.getLocatorByString(locator);
        int alredySwiped=0;
        while (driver.findElements(by).size() ==0){
            if(alredySwiped>maxSwipes){
                waitForElementPresent(locator, "Cannot find element by swiping up \n"+errorMessage,0);
                return;
            }
            swipeUpQuick();
            ++alredySwiped;
        }
    }

    public boolean isTextExist(String locator, String text){
        return waitForElementPresent(locator, "searched element not presented").getAttribute("text").equals(text);
    }

    public WebElement waitForElementPresent(String locator, String errMessage){
        return waitForElementPresent(locator, errMessage,5);
    }

    public WebElement waitForElementPresent(String locator, String errMessage, int timeOut){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> waitForWebElementCollectionPresent(String locator, int timeOut){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.withMessage("\n\n\n Element Collection not found \n\n\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForElementAndClick(String locator, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(locator,errMessage,timeOut);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(locator,errMessage,timeOut);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errMessage, int timeOut){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(locator,errMessage,timeOut);
        element.clear();
        return element;
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")){
            return By.xpath(locator);
        }else if(by_type.equals("id")){
            return By.id(locator);
        }else {
            throw new IllegalArgumentException("Cannot get type of locator "+locator_with_type);
        }
    }
}
