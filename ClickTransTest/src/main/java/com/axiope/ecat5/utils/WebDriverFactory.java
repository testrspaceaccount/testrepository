package com.axiope.ecat5.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.HashMap;

/**
 * webdriver handling class
 */
public class WebDriverFactory {

    /**
     * Empty constructor
     */
    private WebDriverFactory() {
    }

    /**
     * Creates a web driver according to the WebTestProperties.
     *
     * Note. downloading files is currently possible with Firefox only.
     * @param downloadsFolderPath path to an already created folder
     * @return
     * @throws IOException
     */
    public static WebDriver getDriver(String downloadsFolderPath) throws IOException {
        WebDriver driver;

            System.setProperty("webdriver.chrome.driver", WebTestProperties.getProperty("chromedriver.exe"));
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadsFolderPath);
            options.setExperimentalOption("prefs", chromePrefs);
            DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
            chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(chromeCapabilities);

        driver.manage().window().setSize(new Dimension(1200, 980));

        return driver;
    }
}
