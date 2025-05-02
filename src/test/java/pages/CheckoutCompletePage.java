package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutCompletePage {
    private final WebDriver driver;
    
    @FindBy(className = "title") private WebElement pageTitle;
    @FindBy(className = "complete-header") private WebElement completeHeader;
    @FindBy(id = "back-to-products") private WebElement backHomeButton;
    
    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitForPageToLoad();
    }
    
    private void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOf(completeHeader));
    }
    
    public boolean isCheckoutComplete() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(completeHeader));
            return completeHeader.getText().toLowerCase().contains("thank you");
        } catch (Exception e) {
            return false;
        }
    }
   
    public String getCompleteHeaderText() {
        return completeHeader.getText();
    }
    
    public ProductsPage backToProducts() {
        backHomeButton.click();
        return new ProductsPage(driver);
    }
}