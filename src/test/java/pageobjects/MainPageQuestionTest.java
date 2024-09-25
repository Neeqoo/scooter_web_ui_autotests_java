package pageobjects;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)

public class MainPageQuestionTest {
    private WebDriver driver;

    //      !---Добавляем необходимые поля (номер индекса массива, текст вопроса, текст выпадающего ответа)---!

    private final int indexNumber;
    private final String questionText;
    private final String answerText;

    //      !---Конструктор класса для теста---!

    public MainPageQuestionTest(int indexNumber, String questionText, String answerText) {
        this.indexNumber = indexNumber;
        this.questionText = questionText;
        this.answerText = answerText;
    }

    //      !---Добавляем тестовые данные для проверки соответствия заголовка и ответа---!

    @Parameterized.Parameters
    public static Object[][] headersAndText() {
        return new Object[][]{
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    //      !---Создаем драйвер для браузера Chrome и открываем страницу сайта "самокат"---!

    @Before
    public void begin() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru");
    }

    //      !---Тест для проверки заголовки и текста---!

    @Test
    public void checkQuestionAndAnswerText() {

        // Создали новый объект класса MainPage
        MainPage objMainPage = new MainPage(driver);

        //Дождались загрузки главной страницы
        objMainPage.waitForLoad();

        //Закрыли куки (не обязательно в целом)
        objMainPage.clickCookie();

        //Нажали на заголовок в "вопросы о важном" с нужным индексом из заранее предоставленных данных
        objMainPage.clickQuestionHeader(indexNumber);

        //Дождались загрузки текста с ответом
        objMainPage.waitForLoadAnswerText(indexNumber);

        //Проверяем соответствующий текст в вопросе
        MatcherAssert.assertThat("Неверный текст вопроса",
                questionText,
                equalTo(objMainPage.getQuestionHeaderText(indexNumber)));

        //Проверяем соответствующий текст из ответа
        MatcherAssert.assertThat("Неверный текст ответа",
                answerText,
                equalTo(objMainPage.getAnswerText(indexNumber)));
    }

    //      !---Закрыли браузер---!

    @After
    public void teardown() {
        driver.quit();
    }
}

