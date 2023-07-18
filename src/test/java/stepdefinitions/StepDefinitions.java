package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobjects.CheckoutPage;
import pageobjects.LoginPage;
import pageobjects.InventoryPage;
import pageobjects.CartPage;
import pageobjects.CheckoutCompletePage;

import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {

    private List<Double> cartProductPrices;
    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CheckoutCompletePage checkoutCompletePage;


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
        cartProductPrices = new ArrayList<>();
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeAnErrorMessage(String error_message) {
        String expectedErrorMessage = error_message;
        String actualErrorMessage = loginPage.isErrorMessageDisplayed();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Then("all product images should be replaced with the incorrect image")
    public void allProductImagesShouldBeReplaced() {
        List<WebElement> productImages = driver.findElements(By.className("inventory_item_img"));
        for (WebElement productImage : productImages) {
            String outerHtml = productImage.getAttribute("outerHTML");
            String imageSource = outerHtml.substring(outerHtml.indexOf("src=\"") + 5, outerHtml.indexOf("\"", outerHtml.indexOf("src=\"") + 5));
            Assert.assertEquals("/static/media/sl-404.168b1cce.jpg", imageSource);
        }
    }

    @Then("the page should not load within {int} milliseconds")
    public void pageShouldNotLoadWithinMilliseconds(int threshold) {
        long loadTime = (Long) ((JavascriptExecutor) driver).executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
        Assert.assertTrue("Page load time exceeds threshold: " + loadTime + "ms", loadTime <= threshold);
    }

    @Given("I am logged in")
    public void i_am_logged_in() {
        loginPage.setUsername("standard_user");
        loginPage.setPassword("secret_sauce");
        loginPage.clickLogin();

        checkoutPage = new CheckoutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @When("I select the {string} filter")
    public void i_select_the_filter(String filterOption) {
        inventoryPage = new InventoryPage(driver);
        inventoryPage.selectFilter(filterOption);
    }

    @Then("The products should be sorted by {string}")
    public void the_products_should_be_sorted_by(String sortType) {
        boolean isSorted = inventoryPage.areProductsSortedBy(sortType);
        Assert.assertTrue("The products are not sorted by " + sortType, isSorted);
    }

    @When("I add {string} to cart")
    public void i_add_to_cart(String productName) {
        inventoryPage = new InventoryPage(driver);
        inventoryPage.updateCart(productName, "add");
        Double price = inventoryPage.getProductPrice(productName);
        cartProductPrices.add(price);
    }

    @When("I remove {string} from cart")
    public void i_remove_from_cart(String productName) {
        inventoryPage = new InventoryPage(driver);
        inventoryPage.updateCart(productName, "remove");
    }

    @Then("I open the cart")
    public void i_open_the_cart() {
        cartPage.openCart();
    }

    @Then("I should see {string} in the cart")
    public void i_should_see_in_the_cart(String productName) {

        boolean isItemPresent = cartPage.isItemPresentInCart(productName);
        Assert.assertTrue("Product is not present in the cart", isItemPresent);
    }

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assert.assertTrue("Cart is not empty", cartPage.isCartEmpty());
    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        cartPage.clickCheckout();
    }

    @When("I enter random customer information")
    public void i_enter_random_customer_information() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterRandomCustomerInformation();
    }

    @When("I click continue on checkout page")
    public void i_click_continue_on_checkout_page() {
        checkoutPage.clickContinue();
    }

    @When("I click finish on checkout page")
    public void i_click_finish_on_checkout_page() {
        checkoutPage.clickFinish();
    }

    @Then("I should see the success message")
    public void i_should_see_the_message() {
        boolean isConfirmationDisplayed = checkoutCompletePage.isConfirmationMessageDisplayed();
        Assert.assertTrue("Confirmation message is not displayed", isConfirmationDisplayed);
    }
    @Then("The product images for all products should be displayed")
    public void the_product_images_for_all_products_should_be_displayed() {
        List<String> productNames = inventoryPage.getProductNames();
        for (String productName : productNames) {
            boolean isImageDisplayed = inventoryPage.isProductImageDisplayed(productName);
            Assert.assertTrue("The product image for " + productName + " is not displayed", isImageDisplayed);
        }
    }

    @Then("The product descriptions for all products should be displayed")
    public void the_product_descriptions_for_all_products_should_be_displayed() {
        List<String> productNames = inventoryPage.getProductNames();
        for (String productName : productNames) {
            boolean isDescriptionDisplayed = inventoryPage.isProductDescriptionDisplayed(productName);
            Assert.assertTrue("The product description for " + productName + " is not displayed", isDescriptionDisplayed);
        }
    }
    @Then("The total amount in the cart should match the sum of the prices of the added products")
    public void the_total_amount_in_the_cart_should_match_the_sum_of_the_prices_of_the_added_products() {
        Double expectedTotal = cartProductPrices.stream().mapToDouble(Double::doubleValue).sum();
        Double actualTotal = cartPage.getCartTotal();
        Assert.assertEquals("The total amount in the cart is incorrect", expectedTotal, actualTotal, 0.01);
    }

    @After
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

}
