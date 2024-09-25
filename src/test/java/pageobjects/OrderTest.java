package pageobjects;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.hamcrest.Matchers.containsString;


@RunWith(Parameterized.class)

public class OrderTest {
    private WebDriver driver;

    //      !---Добавляем необходимые для заказа поля---!

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rent;
    private final String color;
    private final String comment;

    //      !---Конструктор класса для теста---!

    public OrderTest(String name, String surname, String address,
                     String metro, String phone, String date,
                     String rent, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rent = rent;
        this.color = color;
        this.comment = comment;
    }

    //      !---Добавляем тестовые данные для всех полей заказа---!

    @Parameterized.Parameters
    public static Object[][] dataForOrder() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, ул. Русаковская, дом 29", "Сокольники", "89991234567", "19.11.2024", "двое суток", "серая безысходность", "настойчиво звонить, могу спать"},
                {"Пётр", "Петров", "Москва, ул. Русаковская, дом 29", "Сокольники", "89991234567", "19.11.2024", "трое суток", "чёрный жемчуг", "не звонить в домофон"},
        };
    }

    //      !---Создаем драйвер для браузера Firefox (на хроме тест падает) и открываем страницу сайта "самокат"---!

    @Before
    public void begin() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru");
    }

    //      !---Тест успешного заказа при клике на кнопку "Заказать" в шапке страницы---!

    @Test
    public void orderWithHeaderButton() {

        //Создаем два объекта нужных нам классов
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        //Ждем загрузки, закрываем куки и нажимаем на кнопку "заказать" в шапке страницы
        objMainPage.waitForLoad();
        objMainPage.clickCookie();
        objMainPage.clickOnHeaderOrderButton();

        //Ждем загрузки, заполняем данные на первой странице оформления, и нажимаем на кнопку "далее"
        objOrderPage.waitForLoad();
        objOrderPage.setInputName(name);
        objOrderPage.setInputSurname(surname);
        objOrderPage.setInputAddress(address);
        objOrderPage.setInputMetro(metro);
        objOrderPage.setInputPhone(phone);
        objOrderPage.clickOnNextButton();

        //Ждем загрузку, заполняем оставшиеся данные заказа
        objOrderPage.waitForLoad();
        objOrderPage.setInputDate(date);
        objOrderPage.setRentPeriod(rent);
        objOrderPage.setScooterColor(color);
        objOrderPage.setInputComment(comment);

        //Подтверждаем заказ и "получаем" текст на странице "заказ оформлен"
        objOrderPage.clickOnOrderButton();
        objOrderPage.clickOnYesButton();
        objOrderPage.getOrderPlacedText();

        //Проверяем что заказ успешно оформился путем сравнения текста "Заказ оформлен"
        MatcherAssert.assertThat("Не удалось создать новый заказ",
                objOrderPage.getOrderPlacedText(),
                containsString("Заказ оформлен"));
    }

    //      !---Тест успешного заказа при клике на кнопку "Заказать" в середине страницы---!
    @Test
    public void orderWithMiddleButton() {

        //Создаем два объекта нужных нам классов
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        //Ждем загрузки, закрываем куки и нажимаем на кнопку "заказать" в шапке страницы
        objMainPage.waitForLoad();
        objMainPage.clickCookie();
        objMainPage.clickOnMiddleOrderButton();

        //Ждем загрузки, заполняем данные на первой странице оформления, и нажимаем на кнопку "далее"
        objOrderPage.waitForLoad();
        objOrderPage.setInputName(name);
        objOrderPage.setInputSurname(surname);
        objOrderPage.setInputAddress(address);
        objOrderPage.setInputMetro(metro);
        objOrderPage.setInputPhone(phone);
        objOrderPage.clickOnNextButton();

        //Ждем загрузку, заполняем оставшиеся данные заказа
        objOrderPage.waitForLoad();
        objOrderPage.setInputDate(date);
        objOrderPage.setRentPeriod(rent);
        objOrderPage.setScooterColor(color);
        objOrderPage.setInputComment(comment);

        //Подтверждаем заказ и "получаем" текст на странице "заказ оформлен"
        objOrderPage.clickOnOrderButton();
        objOrderPage.clickOnYesButton();
        objOrderPage.getOrderPlacedText();

        //Проверяем что заказ успешно оформился путем сравнения текста "Заказ оформлен"
        MatcherAssert.assertThat("Не удалось создать новый заказ",
                objOrderPage.getOrderPlacedText(),
                containsString("Заказ оформлен"));
    }


    //      !---Закрыли браузер---!

    @After
    public void teardown() {
        driver.quit();
    }

}