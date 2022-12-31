package ru.netology.bdd.page.object.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.page.object.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement transferCanselButton = $("[data-test-id=action-cancel]");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountField.setValue(amountToTransfer);
        fromCardField.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public DashboardPage canselTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountField.setValue(amountToTransfer);
        fromCardField.setValue(cardInfo.getCardNumber());
        transferCanselButton.click();
        return new DashboardPage();
    }

    public TransferPage emptyForm() {
        transferButton.click();
        return new TransferPage();
    }

    public TransferPage makeInvalidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountField.setValue(amountToTransfer);
        fromCardField.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new TransferPage();
    }

    public TransferPage makeTransferWithKopecks(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountField.setValue(amountToTransfer);
        fromCardField.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new TransferPage();
    }
}
