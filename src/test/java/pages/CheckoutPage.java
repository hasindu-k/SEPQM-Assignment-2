package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private final WebDriver driver;
    
    @FindBy(id = "first-name") private WebElement firstNameInput;
    @FindBy(id = "last-name") private WebElement lastNameInput;
    @FindBy(id = "postal-code") private WebElement postalCodeInput;
    @FindBy(id = "continue") private WebElement continueButton;
    @FindBy(id = "cancel") private WebElement cancelButton;
    @FindBy(css = "h3[data-test='error']") private WebElement errorMessage;
    
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOf(firstNameInput));
    }
    
    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postalCodeInput.sendKeys(postalCode);
        return this;
    }
    
    public CheckoutOverviewPage proceedToOverview() {
        continueButton.click();
        return new CheckoutOverviewPage(driver);
    }
    
    public CartPage cancelCheckout() {
        cancelButton.click();
        return new CartPage(driver);
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}