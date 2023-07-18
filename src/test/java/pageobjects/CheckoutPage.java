package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    public void enterRandomCustomerInformation() {
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name")));
        WebElement zipCodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code")));

        firstNameInput.sendKeys(generateRandomString(6, true));
        lastNameInput.sendKeys(generateRandomString(8, true));
        zipCodeInput.sendKeys(generateRandomString(5, false));
    }

    public void clickContinue() {
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        continueButton.click();
    }

    public void clickFinish() {
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        finishButton.click();
    }

    private String generateRandomString(int length, boolean useLetters) {
        String characters = useLetters ? "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" : "0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
