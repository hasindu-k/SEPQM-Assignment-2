package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import pages.LoginPage;

public class LoginTests extends TestBase {
    
    @Test
    public void successfulLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        assertTrue("Login failed!", 
            driver.getCurrentUrl().contains("inventory.html"));
    }
    
    @Test
    public void failedLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        
        assertEquals("Error message mismatch!", 
            "Epic sadface: Sorry, this user has been locked out.", 
            loginPage.getErrorMessage());
    }
    
    @Test
    public void emptyUsernameLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "secret_sauce");
        
        assertEquals("Error message mismatch!", 
            "Epic sadface: Username is required", 
            loginPage.getErrorMessage());
    }
    
    @Test
    public void emptyPasswordLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "");
        
        assertEquals("Error message mismatch!", 
            "Epic sadface: Password is required", 
            loginPage.getErrorMessage());
    }
    
    @Test
    public void performanceGlitchUserTest() {
        LoginPage loginPage = new LoginPage(driver);
        long startTime = System.currentTimeMillis();
        loginPage.login("performance_glitch_user", "secret_sauce");
        long endTime = System.currentTimeMillis();
        
        // Increase timeout to 7 seconds to account for the glitch
        assertTrue("Login took too long!", (endTime - startTime) < 7000);
        assertTrue("Login failed!", 
            driver.getCurrentUrl().contains("inventory.html"));
    }
}