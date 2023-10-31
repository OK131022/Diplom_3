import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private String driverType;

    public ConstructorTest(String driverType){
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
    @DisplayName("Переход к разделу Булки")
    public void checkGoToBun() {
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonFillings();
        objHeadPage.clickButtonBun();
        String actualResult = objHeadPage.getTabBuns();
        Assert.assertEquals("Булки", actualResult);
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    public void checkGoToSause() {

        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonSause();
        String actualResult=objHeadPage.getTabSause();
         Assert.assertEquals("Соусы", actualResult);
    }
    @Test
    @DisplayName("Переход к разделу Начинки")
    public void checkGoToFillings() {
        MainPage objHeadPage = new MainPage(driver);
        objHeadPage.clickButtonFillings();
        String actualResult=objHeadPage.getTabFillings();
        Assert.assertEquals("Начинки", actualResult);
    }
    @After
    public void tearDown(){
        driver.quit();
    }


}
