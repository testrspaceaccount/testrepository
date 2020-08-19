package com.axiope.ecat5.pageobjects.clicktranspages;

import com.axiope.ecat5.utils.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Base class for clicktrans pages
 */
public abstract class ClickTransPage {

    //log
    Logger log = LoggerFactory.getLogger(ClickTransPage.class);

    //used driver
    public WebDriver driver;

    //url
    private String testingServerURL;

    /**
     * Two-argument constructor. Creates registration page object with assigned mainPageURL
     * @param driver
     * @param mainPageURL
     */
    public ClickTransPage(WebDriver driver, String mainPageURL) {
    	this.driver = driver;
    	this.testingServerURL = mainPageURL;
    }

    /**
     * Opens page with this object's testingServerURL
     */
    public void open() {
        load();
        waitUntilPageLoaded();
    }

    /**
     * Checks whether page with this object's testingServerURL is loaded
     * @return
     */
    public boolean isPageLoaded() {
        String url = driver.getCurrentUrl();
        return url.contains(this.testingServerURL);
    }

    /**
     * Waits until page is loaded and in ready state.
     */
    public void waitUntilPageLoaded() {
        waitUntilPageLoaded(this.testingServerURL);
    }

    /**
     * Waits until page with given urlToWaitFor is loaded and in ready state.
     * @param urlToWaitFor url of the page to wait
     */
    private void waitUntilPageLoaded(String urlToWaitFor) {

        // wait for url to load for Constants.longer_timeout
        try {
            new WebDriverWait(driver, Constants.longer_timeout).until((WebDriver webDriver) ->
                    webDriver.getCurrentUrl().contains(urlToWaitFor));

            // wait for page being ready
            waitUntilPageInReadyState();
            waitForJQueryProcessing();

        } catch (TimeoutException te) {
            log.error("expected page didn't load", te);
            String currentUrl = driver.getCurrentUrl();
            assertTrue("unexpected url: " + currentUrl + ", was waiting for: " + urlToWaitFor,
                    currentUrl.contains(urlToWaitFor));
        }
    }

    /**
     * Loads page with this object's testingServerURL
     */
    protected void load() {
        driver.get(this.testingServerURL);
    }

    /**
     * Waits until jquery processing is finished
     * @return
     */
    public boolean waitForJQueryProcessing() {
        boolean jQcondition = false;
        try {

            // we expect the page to be loaded at this point, so jquery should be available in short_timeout
            new WebDriverWait(driver, Constants.short_timeout).until((WebDriver webDriver) ->
                    Boolean.TRUE.equals(((JavascriptExecutor) webDriver).executeScript(
                            "return window.jQuery != undefined")));
            new WebDriverWait(driver, Constants.timeout).until((WebDriver webDriver) ->
                    Boolean.TRUE.equals(((JavascriptExecutor) webDriver).executeScript(
                            "return window.jQuery != undefined && jQuery.active === 0 && $(\":animated\").length == 0")));
            jQcondition = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }

    /**
     * Waits until the page is ready
     * @return
     */
    public boolean waitUntilPageInReadyState() {
        return waitForJavaScriptCondition("return document.readyState === 'interactive' || document.readyState === 'complete'");
    }

    /**
     * Waits until given javascript condition occurs
     * @param javaScript javascript containing the condition
     * @return
     */
    public boolean waitForJavaScriptCondition(final String javaScript) {
        boolean jscondition;
        try {
            new WebDriverWait(driver, Constants.timeout) {
            }.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
                }
            });
            jscondition = (Boolean) executeJavaScript(javaScript);
            return jscondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Waits for element to be clicked on to become clickable, clicks
     * and waits for subsequent JS/jquery to complete.
     *
     * @param locator element locator
     */
    public WebElement click(By locator) {
        WebElement element = (new WebDriverWait(driver, Constants.timeout)).until(
                ExpectedConditions.elementToBeClickable(locator));
        element.click();
        waitForJQueryProcessing();
        return element;
    }



    /**
     * Types text into the element identified by the locator, waiting
     * for the element to appear.
     *
     * @param locator element locator
     * @param value text to type
     */
    public void type(By locator, String value) {
        WebElement element = waitForElement(locator);
        assertNotNull(element);
        type(element, value);
    }

    /**
     * TYpes text into the element, assuming it is already ready to be
     * typed into.
     *
     * @param element element
     * @param value text to type
     */
    public void type(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Waits for element to be visible, waiting for Constants.timeout
     *
     * @param locator element locator
     * @return the element, or <code>null</code> if not found
     */
    public WebElement waitForElement(final By locator) {
        return  waitForElement(locator, Constants.timeout);
    }

    /**
     * Waits for element to be visible, waiting for given timeout
     * @param locator element locator
     * @param timeout given timeout
     * @return the element, or <code>null</code> if not found
     */
    public WebElement waitForElement(final By locator, long timeout) {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes the javascript given on the page
     * @param javaScript given javascript
     */
    public Object executeJavaScript(final String javaScript) {
        return ((JavascriptExecutor) driver).executeScript(javaScript);
    }

    /**
     * Finds element by it's locator
     * @param locator element locator
     * @return
     */
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Makes checkbox checked
     * @param checkbox checkbox's locator
     */
    public void markCheckbox(By checkbox) {
        WebElement checkboxElement = findElement(checkbox);
        if(!checkboxElement.isSelected()) {
            checkboxElement.click();
        }
    }
}
