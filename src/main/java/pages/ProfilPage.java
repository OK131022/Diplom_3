package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilPage {
    private WebDriver driver;
    private By messageInPagePersonalArea=By.xpath(".//a[contains(text(),'Профиль')]");
    private By buttonConstructor =By.xpath(".//p[contains(text(),'Конструктор')]");
    private By buttonHeaderLogo= By.className("AppHeader_header__logo__2D0X2");
    private By buttonLogout= By.xpath("//button[text()='Выход']");


    public ProfilPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean isDisplayedMessage(){
          return driver.findElement(messageInPagePersonalArea).isDisplayed();
    }
    public void clickButtonConstructor(){
          driver.findElement(buttonConstructor).click();
    }
    public void clickButtonLogoInHeader(){
         driver.findElement(buttonHeaderLogo).click();
    }
    public void clickButtonLogout() throws InterruptedException {
       driver.findElement(buttonLogout).click();
    }
}