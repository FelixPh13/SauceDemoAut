package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutCompletePage {


    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean isConfirmationMessageDisplayed() {
        WebElement confirmationContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_complete_container")));
        return confirmationContainer.isDisplayed();
    }
}