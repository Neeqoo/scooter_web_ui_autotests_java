package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//      !---Класс для главное страницы---!

public class MainPage {
    private final WebDriver driver;

    //      !---Основные локаторы страницы---!

    //Локатор для логотипа Яндекса
    private final By headerLogoYandex = By.className("Header_LogoYandex__3TSOI");
    //Локатор для логотипа Самоката
    private final By headerLogoScooter = By.className("Header_LogoScooter__3lsAR");
    //Локатор для кнопки "Принять Куки"
    private final By acceptCookieButton = By.className("App_CookieButton__3cvqF");
    // Локатор картинки самоката (для ожидания загрузки)
    private final By scooterImage = By.xpath(".//img[@src='/assets/blueprint.png']");

    //      !---Локаторы для работы с "заказами"---!

    //Локатор для кнопки "Заказать" в шапке страницы
    private final By headerOrderButton = By.className("Button_Button__ra12g");
    //Локатор для кнопки "Заказать" в середине страницы
    private final By middleOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    //Локатор кнопки "Статус заказа" в шапке страницы
    private final By headerOrderStatusButton = By.className("Header_Link__1TAG7");
    // Локатор поля "Введите номер заказа"
    private final By inputTrack = By.xpath(".//input[contains(@placeholder,'Введите')]");
    // Локатор кнопки "Go!"
    private final By goButton = By.xpath(".//button[contains(@class,'Header_Button__28dPO')]");
    // Локатор для картинки "Номер заказа не найден"
    public final By trackNotFound = By.xpath(".//img[@src='/assets/not-found.png']");

    //      !---Локаторы выпадающего списка "Вопросы о важном" ---!

    // Локатор заголовка раскрывающегося списка
    private final By questionText = By.xpath(".//div[starts-with(@id, 'accordion__heading-')]");
    // Локатор текста внутри раскрывающегося списка
    private final By answerText = By.xpath(".//div[starts-with(@id, 'accordion__panel-')]");

    //      !---Основной конструктор класса ManePage---!
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //      !---Методы---!
    //      !---Общие для главной страницы---!


    // Метод для клика по кнопке "Принять куки"
    public void clickCookie() {
        driver.findElement(acceptCookieButton).click();
    }

    // Метод для ожидания загрузки (ставлю 10 сек, т.к. интернет не стабильный сейчас)
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(scooterImage));
    }


    //      !---Методы для работы с заказами---!

    // Метод для клика по кнопке "Заказать" в шапке страницы
    public void clickOnHeaderOrderButton() {
        driver.findElement(headerOrderButton).click();
    }

    // Метод для клика по кнопке "Заказать" в середине страницы
    public void clickOnMiddleOrderButton() {
        driver.findElement(middleOrderButton).click();
    }

    // Метод для клика по кнопке "Статус заказа"
    public void clickOnHeaderOrderStatusButton() {
        driver.findElement(headerOrderStatusButton).click();
    }

    //Метод для ввода номера заказа
    public void setInputTrack(String track) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(inputTrack)).click();
        driver.findElement(inputTrack).sendKeys(track);
    }

    // Метод для клика по кнопке "Go!" c ожиданием
    public void clickOnGoButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(goButton)).click();
    }

    // Метод для ожидания загрузки "заказ не найден"
    public void waitForLoadNotFound() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(trackNotFound));
    }


    //      !---Методы для работы с выпадающими списками "Вопросы о Важном"---!

    // Метод для ожидания загрузки ответов
    public void waitForLoadAnswerText(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElements(answerText).get(index)));
    }

    // Метод для получения текста из заголовка в списке
    public String getQuestionHeaderText(int index) {
        return driver.findElements(questionText).get(index).getText();
    }

    // Метод для получения текста из раскрывающегося списка
    public String getAnswerText(int index) {
        return driver.findElements(answerText).get(index).getText();
    }

    // Метод для клика на вопрос
    public void clickQuestionHeader(int index) {
        driver.findElements(questionText).get(index).click();
    }


}
