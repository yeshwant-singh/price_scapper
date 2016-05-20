package com;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import com.utils.Mapper;
/**
 * Created by rohanbajaj on 05/05/16.
 */
public class PageBase
{
    private String fileName;
    protected Log logger;
    protected RemoteWebDriver driver;
    public Mapper Mapper;

    public PageBase(String fileName, RemoteWebDriver driver)
    {
        // set homepath for logger to use
        if(System.getProperty("os.name").equalsIgnoreCase("Linux"))
        {
            System.setProperty("homePath","/home/quikr");
        }
        else
        {
            System.setProperty("homePath","/Users/apple");
        }

        logger = LogFactory.getLog(this.getClass().getName());
        this.fileName =  fileName;
        this.driver = driver;
        Mapper = new Mapper(this.driver);
    }

    /*
        verify if element is present or not
     */
    protected boolean isElementPresent(String element)
    {
        try
        {
            if(Mapper.find(fileName,element) == null)
            {
                return false;
            };

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * get current location of the browser
     * @return
     */
    public String getCurrentLocation()
    {
        driver.executeScript("return document.readyState;").equals("complete");
        return driver.getCurrentUrl();
    }

    /**
     * execute java script
     * @param script
     * @return
     */
    public Object executeScript(String script)
    {
        return driver.executeScript(script);
    }

    public Object executeScript(String script,WebElement element)
    {
        return  ((JavascriptExecutor) driver).executeScript(script, element);

    }

    /**
     * get page source
     * @return
     */
    public String getPageSource()
    {
        return driver.getPageSource();
    }

    /**
     * return current window handles
     */
    protected Set<String> getWindowHandles()
    {
        return driver.getWindowHandles();
    }


    /**
     * return current window handle
     */
    protected String getWindowHandle()
    {
        return driver.getWindowHandle();
    }

    /**
     * return targert locator
     * @return
     */
    public WebDriver.TargetLocator switchTo()
    {
        return driver.switchTo();
    }

    /**
     * navigate to new page
     * @return
     */
    protected WebDriver.Navigation navigateTo()
    {
        return driver.navigate();
    }

    /**
     * get title of page
     * @return
     */
    public String getTitle()
    {
        return driver.getTitle();
    }

    /**
     * opens passed Url
     * @param url
     */
    public void openUrl(String url){
        driver.get(url);
    }

    /**
     * delete all cookies
     */
    public void deleteAllCookies()
    {

        driver.manage().deleteAllCookies();
    }


    /**
     * Generic method to Switch to Pop Up, enter element as parameter by which clicking on it pop up opens
     *
     * @param popup
     */
    public String switchtoPopup(WebElement popup) {
        String mainWindowHandle = driver.getWindowHandle();
        popup.click();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mainWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }

        return mainWindowHandle;
    }

    /**
     * Switch to Parent window with parameter value of Parent window
     *
     * @param Parentwindow
     */
    public void switchtoParentfromPopUp(String Parentwindow) {
        driver.switchTo().window(Parentwindow);
    }

    public void closeChildWindowofPopUp(String childwindow) {
        driver.switchTo().window(childwindow).close();
    }
    /**
     * This function is used to return currentwindow
     *
     * @return
     */
    public String returnCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    public boolean binarySearch(String searchString, String[] arrayToSearchFrom)
    {
        boolean retVal = false;
        Arrays.sort(arrayToSearchFrom);
        int index = Arrays.binarySearch(arrayToSearchFrom, searchString);
        if (index!=-1)
        {
            retVal=true;
        }
        else
        {
            logger.info("Search String not present in the given array. Please check!");
            return false;
        }
        return retVal;
    }


    /**
     * wait for page to load having given URL
     *
     * @param pageURL
     */
    public boolean waitForPageToLoad(final String pageURL) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 45);
            return wait.until(new ExpectedCondition<Boolean>() {
                                  public Boolean apply(WebDriver d) {
                                      return (driver.getCurrentUrl().contains(pageURL)) && driver.executeScript("return document.readyState;").equals("complete");
                                  }
                              }
            );
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
