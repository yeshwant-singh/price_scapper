package com;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.utils.InitiateDriver;
import com.utils.XMLReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestBase
{
    protected RemoteWebDriver driver;
    protected Log logger;

    public TestBase() {
    }

    @BeforeMethod(alwaysRun = true)
    public void setup()
    {
        logger = LogFactory.getLog(this.getClass().getName());
        InitiateDriver initiateDriver = new InitiateDriver();
        driver = initiateDriver.getDriver();

        try
        {
            driver.get("http://www.amazon.in");
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown()
    {
        driver.quit();
    }
}