package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    public void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void successSubmitForm() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void successSubmitFormDash() {

        $("[data-test-id=city] input").val("Улан-Удэ");
        $("[data-test-id=name] input").val("Краснова Анна-Мария");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void cityNotList() {

        $("[data-test-id=city] input").val("Серпухов");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void cityEmpty() {

        $("[data-test-id=city] input").val("");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void nameEnglish() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Petrov Ivan");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameNumber() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван2");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameSymbol() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров% Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameEmpty() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("");
        $("[data-test-id=phone] input").val("+79001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void phoneStartEight() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("89001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSmallNumber() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7900111223");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneBigNumber() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+790011122334");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWhitespace() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7 9001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneSymbol() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7(001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneLetter() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+7A001112233");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneEmpty() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void notAgreement() {

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=name] input").val("Петров Иван");
        $("[data-test-id=phone] input").val("+79001112233");
        $(By.className("button")).click();

        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(Condition.visible);

    }

}
