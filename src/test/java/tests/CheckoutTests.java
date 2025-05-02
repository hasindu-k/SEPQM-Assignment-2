package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import pages.*;

public class CheckoutTests extends TestBase {
    
	@Test
	public void completeCheckoutSuccessfully() {
	    CheckoutCompletePage completePage = new LoginPage(driver)
	        .login("standard_user", "secret_sauce")
	        .addItemToCart("sauce-labs-backpack")
	        .goToCart()
	        .proceedToCheckout()
	        .fillCheckoutInfo("John", "Doe", "12345")
	        .proceedToOverview()
	        .finishCheckout();
	    
	    assertTrue("Checkout completion message should be displayed",
	        completePage.isCheckoutComplete());
	}
	
    @Test
    public void verifyEmptyFirstNameValidation() {
        CheckoutPage checkoutPage = new LoginPage(driver)
            .login("standard_user", "secret_sauce")
            .addItemToCart("sauce-labs-backpack")
            .goToCart()
            .proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("", "Doe", "12345");
        
        // Verify we didn't proceed to next page
        assertTrue("Should not proceed with empty first name",
            driver.getCurrentUrl().contains("checkout-step-one"));
        
        // Verify error message if present
        if (checkoutPage.isErrorMessageDisplayed()) {
            String errorText = checkoutPage.getErrorMessage().toLowerCase();
            assertTrue("Error message should mention first name",
                errorText.contains("first name") || errorText.contains("required"));
        }
    }
    
    @Test
    public void verifyTotalPriceCalculation() {
        CheckoutOverviewPage overviewPage = new LoginPage(driver)
            .login("standard_user", "secret_sauce")
            .addItemToCart("sauce-labs-backpack")
            .addItemToCart("sauce-labs-bike-light")
            .goToCart()
            .proceedToCheckout()
            .fillCheckoutInfo("John", "Doe", "12345")
            .proceedToOverview();
        
        // Debug output
        System.out.println("Item Total: " + overviewPage.getItemTotal());
        System.out.println("Tax: " + overviewPage.getTax());
        System.out.println("Total: " + overviewPage.getTotal());
        
        double calculatedTotal = Math.round((overviewPage.getItemTotal() + overviewPage.getTax()) * 100.0) / 100.0;

        double displayedTotal = overviewPage.getTotal();
        
        assertEquals("Total should be sum of items plus tax",
            calculatedTotal, displayedTotal, 0.001);
    }
    
    @Test
    public void cancelCheckoutReturnsToCart() {
        CartPage cartPage = new LoginPage(driver)
            .login("standard_user", "secret_sauce")
            .addItemToCart("sauce-labs-backpack")
            .goToCart()
            .proceedToCheckout()
            .cancelCheckout();
        
        assertTrue("Should be back on cart page",
            driver.getCurrentUrl().contains("cart.html"));
    }
}