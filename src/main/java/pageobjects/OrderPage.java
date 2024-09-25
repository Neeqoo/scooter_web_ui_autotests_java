package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

//      !---Класс для страницы заказа---!

public class OrderPage {
    private final WebDriver driver;

    // Локатор для формы заказа
    private final By orderForm = By.xpath(".//div[@class='Order_Form__17u6u']");


    //      !---Локаторы для полей формы заказа "Для кого самокат"---!

    // Локатор для поля "Имя"
    private final By inputName = By.xpath(".//input[contains(@placeholder,'Имя')]");
    // Локатор для поля "Фамилия"
    private final By inputSurname = By.xpath(".//input[contains(@placeholder,'Фамилия')]");
    // Локатор для поля "Адрес"
    private final By inputAddress = By.xpath(".//input[contains(@placeholder,'Адрес')]");
    // Локатор для поля "Станция метро"
    private final By inputMetro = By.xpath(".//input[contains(@placeholder,'Станция')]");
    // Локатор для доступных станций метро
    private final By metroListValues = By.xpath(".//div[@class='select-search__select']//div[@class='Order_Text__2broi']");
    // Локатор для поля "Телефон"
    private final By inputPhone = By.xpath(".//input[contains(@placeholder,'Телефон')]");
    // Локатор для кнопки "Далее"
    private final By nextButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");


    //      !---Локаторы для полей формы заказа "Про аренду"---!

    // Локатор для поля "Когда привезти самокат"
    private final By inputDate = By.xpath(".//input[contains(@placeholder,'Когда')]");
    // Локатор выбранной даты
    private final By selectDate = By.className("react-datepicker__day--selected");
    // Локатор календаря
    private final By calendarContainer = By.xpath(".//div[@class='react-datepicker__month-container']");
    // Локатор для выпадающего списка "Срок аренды"
    private final By rentPeriod = By.xpath(".//div[@class='Dropdown-placeholder']");
    // Локатор для выбора срока аренды
    private final By chooseRentPeriod = By.className("Dropdown-option");
    // Локатор для выбора цвета самоката
    private final By scooterColor = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");
    // Локатор для поля "Комментарий"
    private final By inputComment = By.xpath(".//input[contains(@placeholder,'Комментарий')]");
    // Локатор для кнопки "Заказать"
    private final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //Локатор для кнопки "Да"
    private final By yesButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    //Локатор всплывающего окна с подтверждением заказа
    private final By orderPlaced = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");

    //      !---Основной конструктор класса OrderPage---!
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //      !---Методы для тестов---!

    // Метод для ожидания загрузки страницы
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(orderForm));
    }

    //Метод для выбора элемента из выпадающего списка
    private void clickOnElementFromDropdown (By dropdownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = driver.findElements(dropdownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    // Метод для заполнения поля "Имя"
    public void setInputName(String name) {
        driver.findElement(inputName).sendKeys(name);
    }

    // Метод для заполнения поля "Фамилия"
    public void setInputSurname(String surname) {
        driver.findElement(inputSurname).sendKeys(surname);
    }

    // Метод для заполнения поля "Адрес"
    public void setInputAddress(String address) {
        driver.findElement(inputAddress).sendKeys(address);
    }

    // Метод для выбора станции метро
    public void setInputMetro(String metro) {
        driver.findElement(inputMetro).sendKeys(metro);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(metroListValues));
        clickOnElementFromDropdown(metroListValues, metro);
    }

    // Метод для заполнения поля "Телефон"
    public void setInputPhone(String phone) {
        driver.findElement(inputPhone).sendKeys(phone);
    }

    // Метод для клика по кнопке "Далее"
    public void clickOnNextButton() {
        driver.findElement(nextButton).click();
    }

    // Метод для установки даты в поле "Когда привезти самокат"
    public void setInputDate(String date) {
        driver.findElement(inputDate).sendKeys(date);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(calendarContainer));
        driver.findElement(selectDate).click();
    }

    // Метод для клика на поле "Срок аренды"
    public void setRentPeriod(String rent) {
        driver.findElement(rentPeriod).click();
        clickOnElementFromDropdown(chooseRentPeriod, rent);
    }

    // Метод для выбора цвета
    public void setScooterColor(String color) {
        clickOnElementFromDropdown(scooterColor, color);
    }

    // Метод для заполнения поля "Комментарий"
    public void setInputComment(String comment) {
        driver.findElement(inputComment).sendKeys(comment);
    }

    // Метод для клика на кнопку "Заказать"
    public void clickOnOrderButton() {
        driver.findElement(orderButton).click();
    }

    // Метод для клика на кнопку "Да" во всплывающем окне
    public void clickOnYesButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(yesButton));
        driver.findElement(yesButton).click();
    }

    // Метод для получения текста об успешном заказе
    public String getOrderPlacedText() {
        return driver.findElement(orderPlaced).getText();
    }

}
