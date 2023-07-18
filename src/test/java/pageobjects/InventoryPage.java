package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }
    public double getProductPrice(String productName) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement itemTitle = item.findElement(By.className("inventory_item_name"));
            if (itemTitle.getText().equals(productName)) {
                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
                String priceText = priceElement.getText().replace("$", "");
                return Double.parseDouble(priceText);
            }
        }
        throw new NoSuchElementException("Could not find product with name: " + productName);
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));

        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replace("$", "");
            double price = Double.parseDouble(priceText);
            prices.add(price);
        }

        return prices;
    }
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        List<WebElement> productElements = driver.findElements(By.className("inventory_item_name"));
        for (WebElement productElement : productElements) {
            productNames.add(productElement.getText());
        }
        return productNames;
    }

    private boolean isSortedByNameAscending(List<String> names) {
        for (int i = 0; i < names.size() - 1; i++) {
            String currentName = names.get(i);
            String nextName = names.get(i + 1);
            if (currentName.compareToIgnoreCase(nextName) > 0) {
                return false;
            }
        }
        return true;
    }
    private boolean isSortedByNameDescending(List<String> names) {
        for (int i = 0; i < names.size() - 1; i++) {
            String currentName = names.get(i);
            String nextName = names.get(i + 1);
            if (currentName.compareToIgnoreCase(nextName) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByPriceAscending(List<Double> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            double currentPrice = prices.get(i);
            double nextPrice = prices.get(i + 1);
            if (currentPrice > nextPrice) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByPriceDescending(List<Double> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            double currentPrice = prices.get(i);
            double nextPrice = prices.get(i + 1);
            if (currentPrice < nextPrice) {
                return false;
            }
        }
        return true;
    }
    public void selectFilter(String filterOption) {
        WebElement filterDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_sort_container")));
        Select dropdownSelect = new Select(filterDropdown);
        dropdownSelect.selectByVisibleText(filterOption);
    }

    public boolean areProductsSortedBy(String sortType) {
        switch (sortType) {
            case "Name (A to Z)":
                return isSortedByNameAscending(getProductNames());
            case "Name (Z to A)":
                return isSortedByNameDescending(getProductNames());
            case "Price (low to high)":
                return isSortedByPriceAscending(getProductPrices());
            case "Price (high to low)":
                return isSortedByPriceDescending(getProductPrices());
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType +
                        ". Valid types are: 'Name (A to Z)', 'Name (Z to A)', 'Price (low to high)', 'Price (high to low)'.");
        }
    }

    public void updateCart(String itemName, String action) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement itemTitle = item.findElement(By.className("inventory_item_name"));
            if (itemTitle.getText().equals(itemName)) {
                WebElement cartButton = item.findElement(getButtonClass(action));
                cartButton.click();
                break;
            }
        }
    }


    public boolean isProductImageDisplayed(String productName) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement itemTitle = item.findElement(By.className("inventory_item_name"));
            if (itemTitle.getText().equals(productName)) {
                WebElement img = item.findElement(By.cssSelector(".inventory_item_img img"));
                return img.isDisplayed();
            }
        }
        throw new NoSuchElementException("Could not find product with name: " + productName);
    }

    public boolean isProductDescriptionDisplayed(String productName) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {
            WebElement itemTitle = item.findElement(By.className("inventory_item_name"));
            if (itemTitle.getText().equals(productName)) {
                WebElement desc = item.findElement(By.className("inventory_item_desc"));
                return desc.isDisplayed();
            }
        }
        throw new NoSuchElementException("Could not find product with name: " + productName);
    }


    private By getButtonClass(String action) {
        switch (action) {
            case "add":
                return By.className("btn_inventory");
            case "remove":
                return By.className("btn_secondary");
            default:
                throw new IllegalArgumentException("Invalid action provided: " + action +
                        ". Valid actions are 'add' and 'remove'.");
        }
    }
}
