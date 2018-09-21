import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUi;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest extends CoreTestCase {

    protected void setUp() throws Exception{
        super.setUp();
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
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        driver.hideKeyboard();
        Assert.assertTrue(searchPageObject.compareTextPlaceHolderSearchLine("Search…"));
    }

    @Test//HW
    public void testResultScan(){
        String searchLine = "football";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchLine);
        int result = searchPageObject.matchResultList(searchLine);
        Assert.assertEquals("\n\n\nFound unexpected results printed above\n\n\n",0,result);
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

    @Test
    public void testAddFewArticles(){
        String searchText = "Appium";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchText);
        searchPageObject.clickByArticleSubString(searchText,1);
        articlePageObject.assertArticleTitlePresent();
        String articleTitle = articlePageObject.articleTitle();

        String myFolderName = "My-Folder";
        articlePageObject.addArticleToMyList(myFolderName);
        articlePageObject.closeArticle();

        String searchText2 = "Bash";
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchText2);
        searchPageObject.openSecondArticle();
        articlePageObject.assertArticleTitlePresent();
        String articleTitle2 = articlePageObject.articleTitle();
        articlePageObject.addArticleToMyList();
        articlePageObject.closeArticle();

        NavigationUi navigationUi = new NavigationUi(driver);
        navigationUi.clickMyList();

        MyListPageObject myListPageObject = new MyListPageObject(driver);
        myListPageObject.openFolderByName(myFolderName);
        myListPageObject.swipeArticleToDelete(articleTitle);
        myListPageObject.waitForArticleDisappeared(articleTitle);
        myListPageObject.waitForArticleAppeared(articleTitle2);
    }

    @Test
    public void testArticleTitleTest(){
        String searchText = "Appium";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        searchPageObject.initSerachInput();
        searchPageObject.typeSearchLine(searchText);
        searchPageObject.clickByArticleSubString(searchText,1);
        articlePageObject.assertArticleTitlePresent();
    }
}
