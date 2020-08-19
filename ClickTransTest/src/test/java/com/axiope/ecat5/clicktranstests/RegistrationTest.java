package com.axiope.ecat5.clicktranstests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Class running the registration test
 */
public class RegistrationTest extends ClickTransTest {

    /**
     * Registration test
     */
    @Test
    public void RegisterCompany() {

        //open registration page
        registrationPage.open();

        //assert whether the page is loaded and company is not registered
        assertTrue(registrationPage.isPageLoaded());
        assertFalse(registrationPage.isRegistered());

        //set company data
        registrationPage.setCompanyName("TESTTOUR");
        registrationPage.setCompanyEmail("test@test.wp.pl");
        registrationPage.setUserName("Tomasz Mueller");
        registrationPage.setPhoneCode("(+48) Polska");
        registrationPage.setPhone(686294929);
        registrationPage.setPassword("pass12345!");

        //mark agreements
        registrationPage.markRegulationAgreement();
        registrationPage.markPersonalDataAgreement();
        registrationPage.scrollDown();//scroll down to marketing agreement checkbox
        registrationPage.markMarketingAgreement();

        //submit registration
        registrationPage.submitRegistration();
        registrationPage.scrollUp();//scroll up to mocked registration message

        //assert whether the company is registered
        assertTrue(registrationPage.isRegistered());
    }
}
