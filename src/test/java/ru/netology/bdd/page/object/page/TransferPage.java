package ru.netology.bdd.page.object.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.page.object.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement transferCanselButton = $("[data-test-id=action-cancel]");

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");

    public TransferPage transfer(DataHelper.TransferCard transferCard) {
        amountField.setValue(transferCard.getAmount());
        fromCardField.setValue(transferCard.getFromCardNumber());
        transferButton.click();
        return new TransferPage();
    }

    public TransferPage emptyForm() {
        transferButton.click();
        return new TransferPage();
    }

    public TransferPage canselTransfer(DataHelper.TransferCard transferCard) {
        amountField.setValue(transferCard.getAmount());
        fromCardField.setValue(transferCard.getFromCardNumber());
        transferCanselButton.click();
        return new TransferPage();
    }

    public TransferPage aboveBalance(DataHelper.TransferCard transferCard, int primaryAmount) {
        amountField.setValue(String.valueOf(primaryAmount * 2));
        fromCardField.setValue(transferCard.getFromCardNumber());
        transferButton.click();
        return new TransferPage();
    }
}
