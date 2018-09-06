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

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver=driver;
    }

    public void assertElementPresent(By by, String errorMessage){
        String message = "\n\n\nAn elements ["+by.toString()+"] not present\n\n\n";
        try{
            if(!driver.findElement(by).isDisplayed()){
                throw new AssertionError(message+" "+errorMessage);
            }
        }catch (NoSuchElementException e){
            throw new AssertionError(message+" "+errorMessage);
        }
    }
    public void assertElementsNotPresent(By by, String errorMessage){
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

    public void swipeElementLeft(By by, String errorMessage){
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

    public void swipeUpToElement(By by, String errorMessage, int maxSwipes){
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


    public boolean isTextExist(By by, String text){
        return waitForElementPresent(by, "searched element not presented").getAttribute("text").equals(text);
    }

    public WebElement waitForElementPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> waitForWebElementCollectionPresent(By by, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.withMessage("\n\n\n Element Collection not found \n\n\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForElementPresent(By by, String errMessage){
        return waitForElementPresent(by, errMessage,5);
    }

    public WebElement waitForElementAndClick(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errMessage, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.withMessage("\n\n\n"+errMessage+"\n\n\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String errMessage, int timeOut){
        WebElement element = waitForElementPresent(by,errMessage,timeOut);
        element.clear();
        return element;
    }
}
