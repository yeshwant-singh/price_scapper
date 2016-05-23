package com.utils;

import java.net.URL;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Platform;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.utils.PropertyReader.getProperties;

/**
 * Created by rohanbajaj on 05/05/16.
 */
public class InitiateDriver
{
    private RemoteWebDriver driver;


    /**
     * initiate driver depend upon BROWSER and URL value in config.properties file
     */
    public InitiateDriver()
    {
        try
        {
            String url = System.getProperty("url") == null ? getProperties().get("URL") : System.getProperty("url");
            String username = System.getenv("BROWSERSTACK_USER");
            String accessKey = System.getenv("BROWSERSTACK_ACCESSKEY");
            String HUB_URL = "http://%s:%s@%s";
            String browser = null;
            browser = System.getProperty("browser") == null ? getProperties().get("BROWSER") : System.getProperty("browser");
            DesiredCapabilities capabilities = null;
            capabilities = DesiredCapabilities.firefox();
            capabilities.setBrowserName("firefox");
            //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true
            capabilities.setCapability("browserstack.local", System.getenv("BROWSERSTACK_LOCAL"));
            capabilities.setCapability("browserstack.localIdentifier", System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER"));
            capabilities.setPlatform(Platform.ANY);
            capabilities.setCapability("browserstack.local","true");
            //capabilities.setCapability("browserstack.localIdentifier", System.getenv("BUILD_ID"));
            System.out.println(String.format(HUB_URL, username, accessKey,url));
            driver = new RemoteWebDriver(new URL(String.format(HUB_URL, username, accessKey,url)), capabilities);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public RemoteWebDriver getDriver()
    {
        if (driver == null)
            throw new RuntimeException("We have not instantiated the driver.");
        return driver;
    }



}
