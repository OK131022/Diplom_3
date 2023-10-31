import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilPage;

import java.util.concurrent.TimeUnit;
@RunWith(Parameterized.class)
public class ProfilTest {
    UserClient userClient = new UserClient();
    private String email;
    private String password;
    private String accessToken;
    public static WebDriverWait wait;
    private WebDriver driver;
    private String driverType;

    public ProfilTest(String driverType){
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
    @Before
    public void createAccount(){
        CreateUser createUserRequest = new CreateUser(RandomStringUtils.randomAlphabetic(5)+"@ya.ru",RandomStringUtils.randomAlphabetic(6),RandomStringUtils.randomAlphabetic(6));
        ValidatableResponse response =userClient.createUser(createUserRequest);
        email=createUserRequest.getEmail();
        password=createUserRequest.getPassword();
        accessToken=response.extract().path("accessToken");
    }
    @Test
    @DisplayName("Переход в Личный кабинет")
    public void goToPersonalAccountOnHeadPage() throws InterruptedException {
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        objHeadPage.clickButtonPersonalAccount();
        TimeUnit.SECONDS.sleep(5);
        ProfilPage objPersonalArea= new ProfilPage(driver);
        boolean isDisplayedMessageInPersonalArea= objPersonalArea.isDisplayedMessage();
        Assert.assertTrue(isDisplayedMessageInPersonalArea);

    }
    @Test
    @DisplayName("Переход на главную страницу в раздел Конструктор через кнопку Конструктор в хедере сайта")
    public void goToConstructorInPersonalAreaPage(){
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        objHeadPage.clickButtonPersonalAccount();
        ProfilPage objPersonalArea= new ProfilPage(driver);
        objPersonalArea.clickButtonConstructor();
        boolean isDisplayedConstructor= objHeadPage.isDisplayedConstructor();
        Assert.assertTrue(isDisplayedConstructor);

    }
    @Test
    @DisplayName("Переход на главную страницу  через кнопку Логотипа в хедере сайта")
    public void goToLogoHeadInPersonalAreaPage(){

        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        objHeadPage.clickButtonPersonalAccount();
        ProfilPage objPersonalArea= new ProfilPage(driver);
        objPersonalArea.clickButtonLogoInHeader();
        boolean isDisplayedHeadPage= objHeadPage.isDisplayedHeadPage();
        Assert.assertTrue(isDisplayedHeadPage);
    }
    @Test
    @DisplayName("Выход из аккаунта")
    public void LogoutAccount() throws InterruptedException {

        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        objHeadPage.clickButtonPersonalAccount();
        ProfilPage objPersonalArea= new ProfilPage(driver);
        TimeUnit.SECONDS.sleep(5);
        objPersonalArea.clickButtonLogout();
        TimeUnit.SECONDS.sleep(5);
        boolean isDisplayedPageLogin=objLoginPage.isDisplayedButtonLogin();
        Assert.assertTrue(isDisplayedPageLogin);
    }

    @After
    public void deleteUser(){
        if (accessToken != null){
            userClient.deleteUser(accessToken);
        }
    }
    public void tearDown(){
        driver.quit();
    }
}