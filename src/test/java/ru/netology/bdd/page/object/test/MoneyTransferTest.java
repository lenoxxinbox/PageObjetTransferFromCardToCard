package ru.netology.bdd.page.object.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.page.object.data.DataHelper;
import ru.netology.bdd.page.object.page.DashboardPage;
import ru.netology.bdd.page.object.page.LoginPage;
import ru.netology.bdd.page.object.page.TransferPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class MoneyTransferTest {

    @BeforeEach
    void before() {
        Configuration.holdBrowserOpen = true;
        Configuration.startMaximized = true;
        open("http://localhost:9999");

        var authInfo = DataHelper.getAuthInfo();
        new LoginPage()
                .validLogin(authInfo)
                .validVerify(DataHelper.getVerificationCodeFor(authInfo));
    }

    @Test
    void shouldTransferMoneyFromFirstCard() {
        int primaryAmountFirstCard = new DashboardPage().getFirstCardBalance();
        int primaryAmountSecondCard = new DashboardPage().getSecondCardBalance();

        new DashboardPage()
                .transferToFirstCard();
        new TransferPage()
                .transfer(DataHelper.getTransferSecondCard());

        Assertions.assertEquals((primaryAmountFirstCard + Integer.parseInt(DataHelper.getTransferSecondCard().getAmount())), new DashboardPage().getFirstCardBalance());
        Assertions.assertEquals((primaryAmountSecondCard - Integer.parseInt(DataHelper.getTransferSecondCard().getAmount())), new DashboardPage().getSecondCardBalance());


    }

    @Test
    void shouldTransferMoneyFromSecondCard() {
        int primaryAmountFirstCard = new DashboardPage().getFirstCardBalance();
        int primaryAmountSecondCard = new DashboardPage().getSecondCardBalance();

        new DashboardPage()
                .transferToSecondCard();
        new TransferPage()
                .transfer(DataHelper.getTransferFirstCard());

        Assertions.assertEquals((primaryAmountFirstCard - Integer.parseInt(DataHelper.getTransferFirstCard().getAmount())), new DashboardPage().getFirstCardBalance());
        Assertions.assertEquals((primaryAmountSecondCard + Integer.parseInt(DataHelper.getTransferFirstCard().getAmount())), new DashboardPage().getSecondCardBalance());
    }

    @Test
    void shouldSendingEmptyForm() {
        new DashboardPage()
                .transferToSecondCard();
        new TransferPage().
                emptyForm();

        $("[data-test-id=error-notification]").shouldHave(text("Произошла ошибка")).shouldBe(visible);

    }

    @Test
    void shouldTransferMoneyCansel() {
        int primaryAmountFirstCard = new DashboardPage().getFirstCardBalance();
        int primaryAmountSecondCard = new DashboardPage().getSecondCardBalance();

        new DashboardPage()
                .transferToFirstCard();
        new TransferPage()
                .canselTransfer(DataHelper.getTransferSecondCard());

        Assertions.assertEquals(primaryAmountFirstCard, new DashboardPage().getFirstCardBalance());
        Assertions.assertEquals(primaryAmountSecondCard, new DashboardPage().getSecondCardBalance());
    }

    @Test
    void shouldTransferAboveBalance() {
        int primaryAmountSecondCard = new DashboardPage().getSecondCardBalance();

        new DashboardPage()
                .transferToFirstCard();
        new TransferPage()
                .aboveBalance(DataHelper.getTransferSecondCard(), primaryAmountSecondCard);

        $("[data-test-id=error-notification-insufficient]").shouldHave(text("Недостаточно средств на карте")).shouldBe(visible);
    }

    @Test
    void shouldTransferMoneyWithKopecks() {
        int primaryAmountFirstCard = new DashboardPage().getFirstCardBalance();
        int primaryAmountSecondCard = new DashboardPage().getSecondCardBalance();

        new DashboardPage()
                .transferToSecondCard();
        new TransferPage()
                .transfer(DataHelper.getTransferFirstCardWithKopecks());

        Assertions.assertEquals((primaryAmountFirstCard - Double.parseDouble(DataHelper.getTransferFirstCardWithKopecks().getAmount())), new DashboardPage().getFirstCardBalance());
        Assertions.assertEquals((primaryAmountSecondCard + Double.parseDouble(DataHelper.getTransferFirstCardWithKopecks().getAmount())), new DashboardPage().getSecondCardBalance());
    }
}

