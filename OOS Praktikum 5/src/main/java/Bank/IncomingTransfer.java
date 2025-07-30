package Bank;

public class IncomingTransfer extends Transfer{
    //Constructor with transaction attributes only
    public IncomingTransfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    //Constructor with sender and recipient
    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    //Override Calculate
    @Override
    public double calculate() {
        double amount = super.getAmount();
        return amount; //+ because incoming
    }
}
