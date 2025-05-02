package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {
    WebDriver driver;
    
    @FindBy(className = "title") WebElement pageTitle;
    @FindBy(className = "product_sort_container") WebElement sortDropdown;
    @FindBy(className = "shopping_cart_link") WebElement cartIcon;
    @FindBy(className = "shopping_cart_badge") WebElement cartBadge;
    
    // Add this missing element declaration
    @FindBy(id = "checkout") WebElement checkoutButton;
    
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean isPageDisplayed() {
        return pageTitle.isDisplayed() && pageTitle.getText().equals("Products");
    }
    
 // Changed to return ProductsPage
    public ProductsPage addItemToCart(String itemId) {
        driver.findElement(By.id("add-to-cart-" + itemId)).click();
        return this;
    }
    
    // Changed to return ProductsPage
    public ProductsPage removeItemFromCart(String itemId) {
        driver.findElement(By.id("remove-" + itemId)).click();
        return this;
    }
    
    public void sortProducts(String value) {
        new Select(sortDropdown).selectByValue(value);
    }
    
    public String getCartBadgeCount() {
        return cartBadge.getText();
    }
    
    public CartPage goToCart() {
        cartIcon.click();
        return new CartPage(driver);
    }

    public CheckoutPage proceedToCheckout() {
        // Add explicit wait to ensure button is clickable
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(checkoutButton))
            .click();
        return new CheckoutPage(driver);
    }
}