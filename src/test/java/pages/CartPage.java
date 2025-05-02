package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    WebDriver driver;
    
    @FindBy(className = "title") WebElement cartTitle;
    @FindBy(id = "checkout") WebElement checkoutButton;
    @FindBy(id = "continue-shopping") WebElement continueShoppingButton;
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean isCartPageDisplayed() {
        return cartTitle.isDisplayed() && cartTitle.getText().equals("Your Cart");
    }
    
    public CheckoutPage proceedToCheckout() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(checkoutButton))
            .click();
        return new CheckoutPage(driver);
    }
    
    public ProductsPage continueShopping() {
        continueShoppingButton.click();
        return new ProductsPage(driver);
    }
    
    public void removeItem(String itemId) {
        driver.findElement(By.id("remove-" + itemId)).click();
    }
}