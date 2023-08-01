package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    Locale locale = new Locale("ru");

    public String generateDate(long addDays, String pattern, Locale locale) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern).withLocale(locale));
    }

    @BeforeEach
    public void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void successSubmitForm() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(3, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void successSubmitFormDash() {

        $("[data-test-id=city] input").val("Улан-Удэ");
        String planningDate = generateDate(3, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Краснова Анна-Мария");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void cityNotList() {

        $("[data-test-id=city] input").val("Серпухов");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void cityEmpty() {

        $("[data-test-id=city] input").val("");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void dataTodayPlusTwoDays() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(2, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void dataTodayPlusFourDays() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void dataTodayPlusManyDays() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(25, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void dataEmpty() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void nameEnglish() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Petrov Ivan");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameNumber() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван2");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameSymbol() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров% Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameEmpty() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void phoneStartEight() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("89001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSmallNumber() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7900111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneBigNumber() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+790011122334");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWhitespace() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7 9001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSymbol() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7(001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneLetter() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7A001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneEmpty() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void notAgreement() {

        $("[data-test-id=city] input").val("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $(By.className("button")).click();

        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(Condition.visible);

    }

    @Test
    void successSubmitFormCitySelectList() {

        $("[data-test-id=city] input").val("Ка");

        $(By.className("menu")).shouldBe(Condition.visible);
        $$(By.className("menu-item__control")).find(Condition.text("Сыктывкар")).click();

        String planningDate = generateDate(4, "dd.MM.yyyy", locale);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void cityNoList() {

        $("[data-test-id=city] input").val("Лю");
        $(By.className("menu")).shouldNotBe(Condition.visible);
    }

    @Test
    void successSubmitFormCalendarDateThreeDays() {

        $("[data-test-id=city] input").val("Москва");

        int addDays = 3;
        String planningDate = generateDate(addDays, "dd.MM.yyyy", locale);
        String day = generateDate(addDays, "d", locale);
        String month = generateDate(addDays, "LLLL", locale);
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        String year = generateDate(addDays, "yyyy", locale);

        $(By.className("calendar-input")).click();

        int max = addDays / 30 + 2;
        for (int i = 0; i <= max; i++) {
            if ($("div .calendar__name").getText().equals(month + " " + year)) {
                $$("td").find(Condition.text(day)).click();
                break;
            } else {
                $("[data-step='1']").click();
            }
        }

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void successSubmitFormCalendarDateFourDays() {

        $("[data-test-id=city] input").val("Москва");

        int addDays = 4;
        String planningDate = generateDate(addDays, "dd.MM.yyyy", locale);
        String day = generateDate(addDays, "d", locale);
        String month = generateDate(addDays, "LLLL", locale);
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        String year = generateDate(addDays, "yyyy", locale);

        $(By.className("calendar-input")).click();

        int max = addDays / 30 + 2;
        for (int i = 0; i <= max; i++) {
            if ($("div .calendar__name").getText().equals(month + " " + year)) {
                $$("td").find(Condition.text(day)).click();
                break;
            } else {
                $("[data-step='1']").click();
            }
        }

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void successSubmitFormCalendarDateAnotherMonth() {

        $("[data-test-id=city] input").val("Москва");

        int addDays = 34;
        String planningDate = generateDate(addDays, "dd.MM.yyyy", locale);
        String day = generateDate(addDays, "d", locale);
        String month = generateDate(addDays, "LLLL", locale);
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        String year = generateDate(addDays, "yyyy", locale);

        $(By.className("calendar-input")).click();

        int max = addDays / 30 + 2;
        for (int i = 0; i <= max; i++) {
            if ($("div .calendar__name").getText().equals(month + " " + year)) {
                $$("td").find(Condition.text(day)).click();
                break;
            } else {
                $("[data-step='1']").click();
            }
        }

        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }


}
