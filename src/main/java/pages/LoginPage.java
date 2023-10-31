package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private By emailField = By.xpath(".//input[@type='text']");
    private By passwordField = By.xpath(".//input[@type='password']");
    private By buttonLogIn = By.xpath(".//button[contains(text(),'Войти')]");
    private By buttonRegister = By.xpath(".//a[contains(text(),'Зарегистрироваться')]");
    private By buttonRecoverPassword = By.xpath("//a[text()='Восстановить пароль']");
    private By mainClass = By.xpath(".//main[@class='App_componentContainer__2JC2W']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButtonRegister() {
                driver.findElement(buttonRegister).click();
    }
    public void sendEmail(String email) {
              driver.findElement(emailField).click();
        driver.findElement(emailField).sendKeys(email);
    }
    public void sendPassword(String password) {
          driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickButtonLogin() {
        driver.findElement(buttonLogIn).click();
    }

    public boolean headPageIsDisplayed(){
        return driver.findElement(mainClass).isDisplayed();
    }
    public void clickButtonRecoverPassword(){
        driver.findElement(buttonRecoverPassword).click();
    }
    public boolean isDisplayedButtonLogin(){
               return driver.findElement(buttonLogIn).isDisplayed();
    }
}