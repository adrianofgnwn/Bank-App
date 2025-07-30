package Bank;

public class OutgoingTransfer extends Transfer{
    //Constructor with transaction attributes only
    public OutgoingTransfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    //Constructor with sender and recipient
    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    //Override Calculate
    @Override
    public double calculate() {
        double amount = super.getAmount();
        return -amount; //- because outgoing
    }
}
