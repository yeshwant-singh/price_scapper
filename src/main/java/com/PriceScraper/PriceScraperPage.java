package com.PriceScraper;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.PageBase;
/**
 * Created by rohanbajaj on 05/05/16.
 */
import java.util.ArrayList;
import java.util.List;

import static com.utils.PropertyReader.getProperties;
public class PriceScraperPage extends PageBase
{

    public PriceScraperPage(RemoteWebDriver driver)
    {
        super(domFile, driver);
    }

    private static final String domFile = getProperties().get("PRICE_DOM_FILE");
    public void searchiPhoneInAmazon()
    {
        Mapper.waitForElementToBeVisible(domFile, "aSearchBar");
        Mapper.find(domFile, "aSearchBar").sendKeys("iPhone 6");
        Mapper.find(domFile, "aSearchKey").click();
        Mapper.finds(domFile, "aSmartphones").get(0).click();
        Mapper.waitForElementToBeVisible(domFile, "aPrices");
    }

    public void searchiPhoneInFlipkart()
    {
        driver.get("www.flipkart.com");
        Mapper.waitForElementToBeVisible(domFile, "fSearchBar");
        Mapper.find(domFile, "fSearchBar").sendKeys("iPhone 6");
        Mapper.find(domFile, "fSearchKey").click();
        Mapper.waitForElementToBeVisible(domFile, "fSmartphones");
        Mapper.find(domFile, "fSmartphones").click();
    }

    public List<Double> getaPrices()
    {
        List <Double> aPrices = new ArrayList<Double>();
        String str;
        Mapper.waitForElementToBeVisible(domFile, "aPrices");
        List<WebElement> elementList = Mapper.finds(domFile, "aPrices");
        for(WebElement webElement : elementList)
        {
            str = webElement.getText().replaceAll("\u20B9", "").replaceAll(",", "").trim();
//            str = str.split(".")[0];
            aPrices.add(Double.parseDouble(str));
//            System.out.println(str);
        }
        return aPrices;
    }

    public List<Integer> getfPrices()
    {
        List <Integer> fPrices = new ArrayList<Integer>();
        Mapper.waitForElementToBeVisible(domFile, "fPrices");
        List<WebElement> elementList = Mapper.finds(domFile, "fPrices");
        for(WebElement webElement : elementList)
        {
            fPrices.add(Integer.parseInt(webElement.getText().replaceAll("Rs.", "").replaceAll(",", "").trim()));
//            System.out.println(webElement.getText().replaceAll("Rs.", "").replaceAll(",", "").trim().split(".")[0]);
        }
        return fPrices;
    }
}
