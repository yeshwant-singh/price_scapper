package com.PriceScraper;

import com.TestBase;
import com.utils.InitiateDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rohanbajaj on 05/05/16.
 */
public class PriceScraperTest
{
    protected RemoteWebDriver driver;
    protected Log logger;

    public PriceScraperTest() {
    }

    @BeforeMethod(alwaysRun = true)
    public void setup()
    {
        logger = LogFactory.getLog(this.getClass().getName());
        InitiateDriver initiateDriver = new InitiateDriver();
        driver = initiateDriver.getDriver();
        System.out.println("my Sessiom"+driver.getSessionId());
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

    @Test
    public void priceScrapeTest()
    {
        PriceScraperPage priceScraperPage = new PriceScraperPage(driver);
        priceScraperPage.searchiPhoneInAmazon();
        List<Double> aPrices= new ArrayList<Double>();
        aPrices = priceScraperPage.getaPrices();
        Collections.sort(aPrices);
        for(Double aPrice: aPrices){
            System.out.println("Amazon prices in sorted order"+aPrice);
        }
        priceScraperPage.searchiPhoneInFlipkart();
        List<Integer> fPrices= new ArrayList<Integer>();
        fPrices = priceScraperPage.getfPrices();
        Collections.sort(fPrices);
        for(int fPrice: fPrices){
            System.out.println("Flipkart prices in sorted order"+fPrice);
        }
    }
}
