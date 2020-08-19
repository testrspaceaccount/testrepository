package com.axiope.ecat5.pageobjects.clicktranspages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * class for registration page
 */
public class RegistrationPage extends ClickTransPage {

    //field declarations
    private By registrationSuccess = By.xpath("//div[contains(@class,'ui success message')]");
    private By companyName = By.id("user_register_company_name");
    private By companyEmail = By.id("user_register_email");
    private By userName = By.id("user_register_name");
    private By phoneCode = By.id("user_register_phoneCode");
    private By phone = By.id("user_register_phone");
    private By password = By.id("user_register_plainPassword");
    private By agreementRegulations = By.id("user_register_settings_agreementRegulations");
    private By personalDataAgreement = By.id("user_register_settings_agreementPersonalData");
    private By marketingAgreement = By.id("user_register_settings_agreementMarketing");
    private By registrationButton = By.id("user_register_submit");

    /**
     * Two-argument constructor. Creates registration page object with assigned mainPageURL
     * @param driver
     * @param mainPageURL
     */
    public RegistrationPage(WebDriver driver, String mainPageURL) {
        super(driver, mainPageURL);
    }

    /**
     * Checks whether the company is registered (the "OK - some registration logic is mocked" message appears)
     * @return
     */
    public boolean isRegistered() {
        return waitForElement(registrationSuccess) != null;
    }

    /**
     * Sets company name
     * @param value company name value
     */
    public void setCompanyName(String value) {
        type(companyName,value);
    }

    /**
     * Sets company email
     * @param value company email value
     */
    public void setCompanyEmail(String value) {
        type(companyEmail,value);
    }

    /**
     * Sets full name of the user
     * @param value full name of the user value
     */
    public void setUserName(String value) {
        type(userName,value);
    }

    /**
     * Sets national phone code
     * @param value phone code value
     */
    public void setPhoneCode(String value) {
        Select code = new Select(waitForElement(phoneCode));
        code.selectByVisibleText(value);
    }

    /**
     * Sets phone number
     * @param value phone number value
     */
    public void setPhone(int value) {
        type(phone,"" + value);
    }

    /**
     * Sets password
     * @param value password value
     */
    public void setPassword(String value) {
        type(password,value);
    }

    /**
     * Marks regulation agreement
     */
    public void markRegulationAgreement() {
        markCheckbox(agreementRegulations);
    }

    /**
     * Marks personal data agreement
     */
    public void markPersonalDataAgreement() {
        markCheckbox(personalDataAgreement);
    }

    /**
     * Marks marketing agreement
     */
    public void markMarketingAgreement() {
        markCheckbox(marketingAgreement);
    }

    /**
     * Submits registration
     */
    public void submitRegistration() {
        click(registrationButton);
    }

    /**
     * Scrolls down the web page
     */
    public void scrollDown() {
        executeJavaScript("window.scrollBy(0,100)");
    }

    /**
     * Scrolls up the page
     */
    public void scrollUp() {
        executeJavaScript("window.scrollBy(0,-100)");
    }

}
