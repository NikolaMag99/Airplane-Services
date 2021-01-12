package SKProjekat2.Servis1.forms;

public class AddCreditCardForm {
    private String cardName;
    private int cardNumber;
    private int securityCode;

    public AddCreditCardForm(String cardName, int cardNumber, int securityCode) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }
}
