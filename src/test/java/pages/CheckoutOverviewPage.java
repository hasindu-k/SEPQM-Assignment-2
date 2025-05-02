package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutOverviewPage {
    private final WebDriver driver;
    
    @FindBy(className = "title") private WebElement pageTitle;
    @FindBy(id = "finish") private WebElement finishButton;
    @FindBy(className = "summary_subtotal_label") private WebElement itemTotalLabel;
    @FindBy(className = "summary_tax_label") private WebElement taxLabel;
    @FindBy(className = "summary_total_label") private WebElement totalLabel;
    
    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitForPageToLoad();
    }
    
    private void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.visibilityOf(pageTitle));
    }
    
    public CheckoutCompletePage finishCheckout() {
        new WebDriverWait(driver, Duration.ofSeconds(35))
            .until(ExpectedConditions.elementToBeClickable(finishButton))
            .click();
        
        // Wait for completion page to load
        new WebDriverWait(driver, Duration.ofSeconds(25))
        .until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("complete-header")),
            ExpectedConditions.urlContains("checkout-complete")
        ));
    
    return new CheckoutCompletePage(driver);    }
    
    public double getItemTotal() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOf(itemTotalLabel));
        String text = itemTotalLabel.getText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    public double getTax() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOf(taxLabel));
        String text = taxLabel.getText().replace("Tax: $", "");
        return Double.parseDouble(text);
    }

    public double getTotal() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.visibilityOf(totalLabel));
        String text = totalLabel.getText().replace("Total: $", "");
        return Double.parseDouble(text);
    }
    
    private double parseCurrency(String value) {
        return Double.parseDouble(value.replace("$", ""));
    }
}