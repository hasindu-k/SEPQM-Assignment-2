package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class TestBase {
    protected WebDriver driver;

    // Method to capture screenshots with timestamp
    protected void takeScreenshot(String fileName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String fullFileName = fileName + "_" + timestamp + ".png";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("screenshots/" + fullFileName));
            System.out.println("Screenshot saved: " + fullFileName);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            takeScreenshot(description.getMethodName() + "_FAILED");
        }

        @Override
        protected void succeeded(Description description) {
            takeScreenshot(description.getMethodName() + "_PASSED");
        }
    };

    @Before
    public void setUp() {
        new File("screenshots").mkdirs();
        
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        
        // Add these to suppress password change prompts
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-autofill-keyboard-accessory-view[1]");
        options.addArguments("--disable-infobars");
        
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            try {
                takeScreenshot("final_state");
            } finally {
                driver.quit();
                System.out.println("Browser session ended");
            }
        }
    }
}