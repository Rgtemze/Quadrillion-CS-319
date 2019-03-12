package data;

public class PurchaseInfo {
    private String name;
    private String surname;
    private String cardNo;
    private String cvc;
    private String expiryDate;
    private String cost;

    public PurchaseInfo(String name, String surname,
                        String cardNo, String cvc,
                        String expiryDate, String cost) {
        this.name = name;
        this.surname = surname;
        this.cardNo = cardNo;
        this.cvc = cvc;
        this.expiryDate = expiryDate;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public String getCardNo() {
        return cardNo;
    }
    public String getCvc() {
        return cvc;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
    public String getCost() {
        return cost;
    }
}
