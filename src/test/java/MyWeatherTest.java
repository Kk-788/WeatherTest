import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import org.openqa.selenium.support.ui.ExpectedConditions; // condition that is waited for
import org.openqa.selenium.support.ui.WebDriverWait; // 
import org.testng.annotations.Test;

import java.time.Duration; // needed to pass duration of seconds as argument


public class MyWeatherTest {

    @Test
    public void testH2TagText_WhenSearchingCityOrlando() throws InterruptedException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*", "--headless", "--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(chromeOptions);

        String cityName = "Orlando";
        String expectedResult = "Orlando, US";

        driver.get("https://openweathermap.org/ ");

        //Thread.sleep(5000);    

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // driver will wait for a maximum of 20 seconds

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(
                                "div[aria-label= 'Loading']"
                        )
                )
        ); // waits for the white semi-transparent "Loading" div to disappear so that the searchbar can be clicked
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//div[@id = 'weather-widget']//input[@placeholder = 'Search city']"
                        )
                )
        ); // if needed, waits for the searchbar to become clickable if it is not already

        WebElement searchCityField = driver.findElement(
                By.xpath(
                        "//div[@id = 'weather-widget']//input[@placeholder = 'Search city']"
                )
        ); // finds searchbar

        searchCityField.click(); // clicks searchbar
        searchCityField.sendKeys(cityName); // sends "Orlando" to searchbar

        WebElement searchButton = driver.findElement(By.xpath("//button[@type = 'submit']"));
        searchButton.click(); // submits keys

        Thread.sleep(1000);

        WebElement charlotteDropdownMenu = driver.findElement(
                By.xpath("//ul[@class = 'search-dropdown-menu']/li/span[text() = 'Orlando, US ']"));
        charlotteDropdownMenu.click();

        WebElement h2CityNameHeader = driver.findElement(
                By.xpath("//div[@class = 'section-content']/div/div/div/h2"));

        Thread.sleep(1000);

        String actualResult = h2CityNameHeader.getText();
        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();

    }
}
