package pageobjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.Assert.*;

public class MainPageCheckOrderTest {
    private WebDriver driver;

    //      !---Добавляем необходимые поля (импровизированный номер заказа)---!
    private final String inputTrack = "123456";


    //      !---Создаем драйвер для браузера Chrome и открываем страницу сайта "самокат"---!

    @Before
    public void begin() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru");
    }

    // Тест "дополнительного сценария" на открытие страницы с статусом заказа и с надписью "такого заказа нет"

    @Test
    public void wrongTrackNotFound () {

        // Создали новый объект класса MainPage
        MainPage objMainPage = new MainPage(driver);

        //Дождались загрузки главной страницы
        objMainPage.waitForLoad();

        //Нажали на кнопку "статус заказа"
        objMainPage.clickOnHeaderOrderStatusButton();

        //Ввели импровизированный номер несуществующего заказа
        objMainPage.setInputTrack(inputTrack);

        //Нажали на кнопку "Go"
        objMainPage.clickOnGoButton();

        //Дождались загрузки страницы с надписью "такого заказа нет"
        objMainPage.waitForLoadNotFound();

        //Проверяем что картинка с надписью "такого заказа нет" появилась на экране
        assertTrue(driver.findElement(objMainPage.trackNotFound).isDisplayed());

    }

    //      !---Закрыли браузер---!

    @After
    public void teardown() {
        driver.quit();
    }

}