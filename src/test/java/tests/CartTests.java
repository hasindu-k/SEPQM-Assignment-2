package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;

public class CartTests extends TestBase {
    
    @Test
    public void addItemToCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addItemToCart("sauce-labs-backpack");
        productsPage.goToCart();
        
        assertTrue("Item not in cart!", 
            driver.findElement(By.id("item_4_title_link")).isDisplayed());
    }
    
    @Test
    public void removeItemFromCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addItemToCart("sauce-labs-backpack");
        productsPage.goToCart();
        
        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem("sauce-labs-backpack");
        
        assertTrue("Cart should be empty but items still present",
            driver.findElements(By.className("cart_item")).isEmpty());
    }
    
    @Test
    public void continueShoppingTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addItemToCart("sauce-labs-backpack");
        productsPage.goToCart();
        
        CartPage cartPage = new CartPage(driver);
        cartPage.continueShopping();
        
        assertTrue("Did not return to products page",
            driver.getCurrentUrl().contains("inventory.html"));
    }
    
    @Test
    public void cartBadgeUpdatesOnRemoveTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addItemToCart("sauce-labs-backpack");
        productsPage.addItemToCart("sauce-labs-bike-light");
        productsPage.goToCart();
        
        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem("sauce-labs-backpack");
        
        assertEquals("Cart badge should show 1 item", "1", 
            productsPage.getCartBadgeCount());
    }
}