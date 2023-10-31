import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
@RunWith(Parameterized.class)
public class RegistrationTest {

    private WebDriver driver;
    private String driverType;

    public RegistrationTest(String driverType){
        this.driverType = driverType;
        System.setProperty(
                "webdriver.chrome.driver",
                "src\\main\\resources\\drivers\\" + this.driverType + ".exe"
        );
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://stellarburgers.nomoreparties.site");
    }

    @Parameterized.Parameters(name="driver: {0}")
    public static Object[][] getDriver(){
        return new Object[][]{
                {"chromedriver"},
                {"yandexdriver"}

        };
    }

    @Test
    @DisplayName("Успешная регистрация")
       public void SucsessRegistration(){
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonPersonalAccount();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickButtonRegister();
        RegistrationPage objRegistrationPage=new RegistrationPage(driver);
        objRegistrationPage.sendName(RandomStringUtils.randomAlphanumeric(5));
        objRegistrationPage.sendEmail(RandomStringUtils.randomAlphabetic(5)+"@ya.ru");
        objRegistrationPage.sendPassword(RandomStringUtils.randomAlphabetic(6));
        objRegistrationPage.clickButtonRegistration();
        boolean isVisibleLoginPage=objRegistrationPage.visibleLoginPage();
        Assert.assertTrue(isVisibleLoginPage);
    }
    @Test
    @DisplayName("Ошибка регистриции для некорректного пароля")
       public void ErrorRegistrationWithWrongPassword(){
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonPersonalAccount();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickButtonRegister();
        RegistrationPage objRegistrationPage=new RegistrationPage(driver);
        objRegistrationPage.sendName(RandomStringUtils.randomAlphanumeric(5));
        objRegistrationPage.sendEmail(RandomStringUtils.randomAlphabetic(5)+"@ya.ru");
        objRegistrationPage.sendPassword(RandomStringUtils.randomAlphabetic(5));
        objRegistrationPage.clickButtonRegistration();
        boolean isVisibleMessage =objRegistrationPage.visibleMessageNotCorrectPassword();
        Assert.assertTrue(isVisibleMessage);
    }
    @After
    public void tearDown(){
        driver.quit();
    }

}