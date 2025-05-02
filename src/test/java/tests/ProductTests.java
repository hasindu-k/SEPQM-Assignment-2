package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.ProductsPage;

public class ProductTests extends TestBase {
    
    @Test
    public void productSortingAZTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProducts("az");
        
        String firstItem = driver.findElement(By.className("inventory_item_name")).getText();
        assertEquals("First item after A-Z sort incorrect", "Sauce Labs Backpack", firstItem);
    }
    
    @Test
    public void productSortingZATest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProducts("za");
        
        String firstItem = driver.findElement(By.className("inventory_item_name")).getText();
        assertEquals("First item after Z-A sort incorrect", "Test.allTheThings() T-Shirt (Red)", firstItem);
    }
    
    @Test
    public void productSortingLowHighTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProducts("lohi");
        
        String firstItemPrice = driver.findElement(By.className("inventory_item_price")).getText();
        assertEquals("First item after price low-high sort incorrect", "$7.99", firstItemPrice);
    }
    
    @Test
    public void productSortingHighLowTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortProducts("hilo");
        
        String firstItemPrice = driver.findElement(By.className("inventory_item_price")).getText();
        assertEquals("First item after price high-low sort incorrect", "$49.99", firstItemPrice);
    }
    
    @Test
    public void multipleItemsAddToCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addItemToCart("sauce-labs-backpack");
        productsPage.addItemToCart("sauce-labs-bike-light");
        
        assertEquals("Cart badge count incorrect", "2", 
            productsPage.getCartBadgeCount());
    }
}