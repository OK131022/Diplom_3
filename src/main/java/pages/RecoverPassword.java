package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoverPassword {
    private WebDriver driver;
    private By buttonLogin = By.xpath(".//a[@href='/login']");

    public RecoverPassword(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButtonLogin () {
        driver.findElement(buttonLogin).click();
    }
}