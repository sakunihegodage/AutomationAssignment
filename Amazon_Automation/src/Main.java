import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {
    private WebDriver driver;

    @Before
    public void setUp(){
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver","C:\\Users\\sdila\\Desktop\\Driver\\chromedriver.exe");
        //Initialize the ChromeDriver
        driver = new ChromeDriver();
        // Maximize the browser window
        driver.manage().window().maximize();
    }

    @Test
    public void testExample() throws InterruptedException {
        // Open a web page
        driver.get(ConfigFile.BASE_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Find an element by its ID and interact with it
        WebElement element = driver.findElement(By.cssSelector("input[placeholder=\"Search Amazon\"]"));
        Thread.sleep(5000);
        // Get the placeholder value
        String placeholderValue = element.getAttribute("placeholder");

        // Assert the placeholder value
        assert placeholderValue.equals("Search Amazon");

        //Click dropdown
        driver.findElement(By.xpath("//select[@id=\"searchDropdownBox\"]")).click();
        Thread.sleep(5000);

        //Find Books term in the list
        WebElement bookElement = driver.findElement(By.xpath("//option[@value=\"search-alias=stripbooks-intl-ship\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value=\"search-alias=stripbooks-intl-ship\"]")));
        bookElement.click();

        //Type Automation in Search bar
        WebElement searchElement = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));
        searchElement.sendKeys("Automation");

        //Click Search icon in navigation
        WebElement navSearchElement = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
        navSearchElement.click();

        //Select 4 star up
        WebElement reviewSectionElement = driver.findElement(By.xpath("//section[@aria-label='4 Stars & Up']"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@aria-label='4 Stars & Up']")));
        reviewSectionElement.click();

        //Select Language as English
        WebElement languageElement = driver.findElement(By.xpath("//div[@id='p_n_feature_nine_browse-bin-title']/following-sibling::ul[1]/span[1]"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='p_n_feature_nine_browse-bin-title']/following-sibling::ul[1]/span[1]")));
        languageElement.click();

        //Get the name of the second product in the product list
        WebElement bookNameElement = driver.findElement(By.xpath("//div[@id=\"search\"]//div[3]//div/div/div/div[1]/h2/a/span"));
        String bookNameText = bookNameElement.getText();
        bookNameElement.click();
        Thread.sleep(5000);

        //Get the price
        WebElement priceElement = driver.findElement(By.xpath("//span[@id='price']"));
        String priceValue = priceElement.getText();
        String priceWithoutDollarSign = priceValue.replaceAll("\\$", "");
        Thread.sleep(1000);

        WebElement detailPageBookNameElement = driver.findElement(By.xpath("//span[@id='productTitle']"));
        String detailPageBookName = detailPageBookNameElement.getText();
        Thread.sleep(1000);

        Assert.assertEquals(bookNameText, detailPageBookName);

        WebElement dropDownElement = driver.findElement(By.xpath("//span[@id='a-autoid-0-announce']"));
        dropDownElement.click();

        //Select Quantity as 2
        WebElement quantityElement = driver.findElement(By.xpath("//a[@id='quantity_1']"));
        quantityElement.click();
        Thread.sleep(1000);

        //Click add to cart button
        WebElement addToCartElement = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
        addToCartElement.click();
        Thread.sleep(1000);

        //Click go to cart
        WebElement goToCartElement = driver.findElement(By.xpath("//a[@data-csa-c-slot-id='sw-gtc']"));
        goToCartElement.click();
        Thread.sleep(1000);

        //Verify book quantity
        WebElement selectQuantity = driver.findElement(By.xpath("//span[@id='sc-subtotal-label-buybox']"));
        String getQuantityValue = selectQuantity.getText();

        // Use regular expression to extract the numeric part
        String numericPart = getQuantityValue.replaceAll("[^0-9]", "");
        String orgVal = "2";

        Assert.assertTrue(orgVal.contains(numericPart));

        //Verify Price
        WebElement totPrice = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-buybox']"));
        String getTotPrice = totPrice.getText();

        //Remove the $ sign
        String priceWithoutDollarSignCart = getTotPrice.replaceAll("\\$", "");

        //Converted the above value into double to multiply from the quantity
        double converted = Double.parseDouble(priceWithoutDollarSign);
        double tot = converted*2.0;

        //Convert Double value to String value to compare
        String totStringVal = String.valueOf(tot);

        //Assert Final page total value with the rginal value
        Assert.assertTrue(priceWithoutDollarSignCart.contains(totStringVal));

        //Click delete button
        WebElement delete = driver.findElement(By.xpath("//input[@value='Delete']"));
        delete.click();

        //verify total amount = 0
        WebElement amountFinal = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']"));
        String amountFinalPage = amountFinal.getText();

        //Remove $0.00 $sign of this value
        String priceWithoutDollarSignFinalPage = amountFinalPage.replaceAll("\\$", "");
        double converted2 = Double.parseDouble(priceWithoutDollarSignFinalPage);
        String totStringVal2 = String.valueOf(converted2);
        String orgVal2 = "0.00";

        // Comparing the final value is "0.00"
        Assert.assertTrue(orgVal2.contains(totStringVal2));
    }

    @After
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}