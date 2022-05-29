import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class TestCardWithDelivery {

    @BeforeEach
    private void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldTestHappyPath() {
        open("http://localhost:9999/");
        $x("//input[@class='input__control']").val("Пермь");
//        $x("//input[@type='tel']").doubleClick().sendKeys("10.06.2022");
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@type='tel']").val("10.06.2022");
        $$("input[type='text']").get(1).setValue("Александр Холидэй");
        $$("input[type='tel']").last().setValue("+79028383123");
        $x("//span[@class='checkbox__box']").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Встреча успешно забронирована на')]").should(visible, Duration.ofSeconds(15));

    }

    @Test
    @Disabled
    void shouldTestNegotiveCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys("Березники");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, "30.06.2022");
        $("[data-test-id='name'] input").val("Александр Холидэй");
        $("[data-test-id='phone'] input").val("+79028383123");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='city] .input_invalid .input_sub");
        $(withText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestChoosePetrozavodsk() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").val("Пе").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE, "17.07.2022");
        $$("input[type='text']").get(1).setValue("Александр Холидэй");
        $$("input[type='tel']").last().setValue("+79028383123");
        $x("//span[@class='checkbox__box']").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Встреча успешно забронирована на')]").should(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldTestDataPlusWeek() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        $("[data-test-id='name'] input").val("Александр Холидэй");
        $("[data-test-id='phone'] input").val("+79028383123");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Встреча успешно забронирована на')]").should(visible, Duration.ofSeconds(15));
    }
}
