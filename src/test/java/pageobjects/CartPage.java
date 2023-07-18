package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openCart() {
        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        cartIcon.click();
        wait.until(ExpectedConditions.urlContains("/cart.html"));
    }

    public boolean isItemPresentInCart(String itemName) {
        List<WebElement> items = driver.findElements(By.className("cart_item"));
        return items.stream()
                .map(item -> item.findElement(By.className("inventory_item_name")))
                .anyMatch(itemTitle -> itemTitle.getText().equals(itemName));
    }

    public boolean isCartEmpty() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        return cartItems.isEmpty();
    }
    public double getCartTotal() {
        WebElement summaryContainer = driver.findElement(By.id("checkout_summary_container"));
        String summaryText = summaryContainer.getText();
        String itemTotalText = summaryText.split("Item total: ")[1].split("\n")[0];
        itemTotalText = itemTotalText.replace("$", "");
        return Double.parseDouble(itemTotalText);
    }

    public void clickCheckout() {
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();
    }
}
