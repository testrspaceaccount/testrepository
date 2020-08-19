package com.axiope.ecat5.clicktranstests;

import com.axiope.ecat5.pageobjects.clicktranspages.RegistrationPage;
import com.safaribooks.junitattachments.CaptureFile;
import com.safaribooks.junitattachments.RecordAttachmentRule;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Main test class for Selenium tests on clicktrans page.
 *
 */

public class ClickTransTest {

    //log
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    //time managing fields
    private static long start, globalStart, end, globalEnd, diff;
    private static String time;

    //used driver
    public WebDriver driver;

    //test watcher property used to log test actions
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            logger.info("Starting Test [" + description.getMethodName() + "]");
        }

        @Override
        protected void failed(Throwable e, Description description) {
            logger.error("Test [" + description.getMethodName() + "] failed with exception ["
                    + e.getMessage() + "]");
        }

        @Override
        protected void succeeded(Description description) {
            logger.info("Test [" + description.getMethodName() + "] succeeded.");
        }

		@Override
		protected void finished(Description description) {
			logger.info("Test [" + description.getMethodName() + "] completed.");
		}
    };

    //registration page object
    protected RegistrationPage registrationPage;

    //downloads folder used to locate the chromedriver
    protected File downloadsFolder;

    /**
     * Performs necessary actions before running tests
     */
    @BeforeClass
    public static void beforeStart() {
        globalStart = System.currentTimeMillis();
    }

    /**
     * Performs necessary actions after running tests
     */
    @AfterClass
    public static void afterClass() {
        globalEnd = System.currentTimeMillis();
        diff = globalEnd - globalStart;
        time = DurationFormatUtils.formatDuration(diff, "HH:mm:ss,SSS");
        LoggerFactory.getLogger(ClickTransTest.class).info("Total time taken running Selenium tests is " + time);
    }

    /**
     * Sets webdriver path and initializes the webdriver
     * @throws IOException
     */
    public void initializeBrowser() throws IOException {
        downloadsFolder = new File("/chromedriver_win32/chromedriver.exe");
        downloadsFolder.mkdir();
        System.setProperty("webdriver.chrome.driver",downloadsFolder.getAbsolutePath());
      	driver = new ChromeDriver();
		driver.get(downloadsFolder.getAbsolutePath());

    }

    /**
     * Gets the URL of a testing server.
     * @return the URL of a testing server
     */
	protected String getMainPageURL() {
			return "https://dev-1.clicktrans.pl/register-test/courier";
	}

    /**
     * Gets the url and initializes registration page class with it
     */
    public void initializePages() {
        String mainPageURL = getMainPageURL();
        registrationPage = new RegistrationPage(driver, mainPageURL);
    }

    /**
     * Performs webdriver and page initialization before running tests
     * @throws IOException
     */
    @Before
    public void initialize() throws IOException {
        initializeBrowser();
        initializePages();
    }

    /**
     * Closes the browser after running tests
     */
    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * sets up time of starting the test
     */
    @Before
    public void setUp() {
        start = System.currentTimeMillis();
    }




	/**
	 * Forced sleep to slow down browser. Should not be used generally, a wait
	 * condition should be preferred.<p/>
	 *Use a wait condition instead.
	 * @param ms
	 */
	protected void forceDelay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
