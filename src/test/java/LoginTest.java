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
import pages.LoginPage;
import pages.MainPage;
import pages.RecoverPassword;
import pages.RegistrationPage;
@RunWith(Parameterized.class)
public class LoginTest {
    UserClient userClient = new UserClient();
    private String email;
    private String password;
    private String accessToken;
    private WebDriver driver;
    private String driverType;

    public LoginTest(String driverType){
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
    public void createAccount() {
       CreateUser createUserRequest = new CreateUser(RandomStringUtils.randomAlphabetic(5)+"@ya.ru",RandomStringUtils.randomAlphabetic(6),RandomStringUtils.randomAlphabetic(6));
        ValidatableResponse response =userClient.createUser(createUserRequest);
        email=createUserRequest.getEmail();
        password=createUserRequest.getPassword();
        accessToken=response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Вход через кнопку Войти в аккаунт на главной")
    public void loginButtonForMainPage() {
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        boolean isDisplayedHeadPage= objLoginPage.headPageIsDisplayed();
        Assert.assertTrue(isDisplayedHeadPage);
    }

    @Test
    @DisplayName("Вход через кнопку Личный кабинет")
       public void loginButtonPersonalAccountForMainPage(){
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonPersonalAccount();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        boolean isDisplayedHeadPage= objLoginPage.headPageIsDisplayed();
        Assert.assertTrue(isDisplayedHeadPage);
    }
    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
      public void loginButtonOnRegistrationPage(){
       MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickButtonRegister();
        RegistrationPage objRegistrationPage=new RegistrationPage(driver);
        objRegistrationPage.clickButtonLogin();
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        boolean isDisplayedHeadPage= objLoginPage.headPageIsDisplayed();
        Assert.assertTrue(isDisplayedHeadPage);
    }
    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginFromForgotPasswordPage(){
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickButtonRecoverPassword();
        RecoverPassword objForgotPage=new RecoverPassword(driver);
        objForgotPage.clickButtonLogin();
        objLoginPage.sendEmail(email);
        objLoginPage.sendPassword(password);
        objLoginPage.clickButtonLogin();
        boolean isDisplayedHeadPage= objLoginPage.headPageIsDisplayed();
        Assert.assertTrue(isDisplayedHeadPage);
    }

    @After
    public void deleteUser(){
        if (accessToken != null){
            userClient.deleteUser(accessToken);
        }}
    public void tearDown(){
        driver.quit();
    }
}