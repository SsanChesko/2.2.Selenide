import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCardWithDelivery {

    @BeforeEach
    private void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(4);

    @Test
    void shouldTestHappyPath() {
        open("http://localhost:9999/");
        $x("//input[@class='input__control']").val("Пермь");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(planningDate);
        $("[data-test-id='name'] input").val("Александр Холи-дэй");
        $("[data-test-id='phone'] input").val("+79028383123");
        $x("//span[@class='checkbox__box']").click();
        $(withText("Забронировать")).click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));


    }

    @Test
    void shouldTestChoosePetrozavodsk() {
        String planningDate = generateDate(7);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").val("Пе").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $$("input[type='text']").get(1).setValue("Александр Холидэй");
        $$("input[type='tel']").last().setValue("+79028383123");
        $x("//span[@class='checkbox__box']").click();
        $(withText("Забронировать")).click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));

    }

    String dataThisMonth = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("d"));
    int days = 4;
    LocalDate localDate = LocalDate.now().plusDays(3);

        @Test
        void shouldTestDataPlusWeek () {
            open("http://localhost:9999/");
            $("[data-test-id='city'] input").sendKeys("Санкт-Петербург");
            $("[data-test-id='date'] input").doubleClick();
            if (localDate.plusDays(days).getMonth() != localDate.getMonth()) {
                $("[data-step='1']").click();
            }
            $$("[role='gridcell'].calendar__day").findBy(text(dataThisMonth)).click();
            $("[data-test-id='name'] input").val("Александр Холи-дэй");
            $("[data-test-id='phone'] input").val("+79028383123");
            $("[data-test-id='agreement']").click();
            $(withText("Забронировать")).click();
            $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(7)), Duration.ofSeconds(15));
        }
    }
