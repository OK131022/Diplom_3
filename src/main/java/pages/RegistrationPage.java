package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver driver;
    private By nameField = By.xpath(".//label[contains(text(),'Имя')]/../input");
    private By emailField =By.xpath("//label[contains(text(),'Email')]/../input");
    private By passwordField = By.xpath(".//label[contains(text(),'Пароль')]/../input");
    private By buttonRegistration= By.xpath(".//button[contains(text(),'Зарегистрироваться')]");
    private By buttonLogin=By.xpath(".//a[@href='/login']");
    private By messageErrorPassword = By.xpath(".//p[contains(text(),'Некорректный пароль')]");
    private By buttonHeaderLogo = By.className("AppHeader_header__logo__2D0X2");
    private By mainClass = By.xpath(".//main[@class='App_componentContainer__2JC2W']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void sendName(String name){
        driver.findElement(nameField).click();
        driver.findElement(nameField).sendKeys(name);
    }

    public String sendEmail(String email){
        driver.findElement(emailField).click();
        driver.findElement(emailField).sendKeys(email);
        return email;
    }

    public String sendPassword(String password){
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
        return password;
    }

    public void clickButtonRegistration(){
        driver.findElement(buttonRegistration).click();
    }

    public boolean visibleLoginPage(){
        return (driver.findElement(mainClass).isDisplayed());
    }

    public boolean visibleMessageNotCorrectPassword(){
        return (driver.findElement(messageErrorPassword).isDisplayed());
    }

    public void clickLogoSite(){
        driver.findElement(buttonHeaderLogo).click();
    }

    public void clickButtonLogin(){
        driver.findElement(buttonLogin).click();
    }
}