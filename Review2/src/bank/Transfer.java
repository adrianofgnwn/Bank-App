package bank;

//Überweisungen
public class Transfer extends Transaction implements CalculateBill {
    private String sender;
    private String recipient;

    //Only Positive Amount
    @Override
    public void setAmount(double amount) {
        if(amount < 0) {
            System.out.println("Error: Amount can't be negative");
        } else {
            this.amount = amount;
        }
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    //Konstruktor
    public Transfer(String date, double amount, String description) {
        super(date, 0, description);
        setAmount(amount);
    }

    //Konstruktor mit allen Attributen
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        super(date, 0, description);
        setAmount(amount);
        setSender(sender);
        setRecipient(recipient);
    }

    //Copy Konstruktor
    public Transfer(Transfer transfer) {
        super(transfer.getDate(), transfer.getAmount(), transfer.getDescription());
        setSender(transfer.getSender());
        setRecipient(transfer.getRecipient());
    }

    @Override
    public String toString() {
        return super.toString() + ", Sender: " + this.sender + ", Recipient: " + this.recipient + ", Calculate: " + this.calculate();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        if (!super.equals(obj)) {return false;}

        Transfer that = (Transfer) obj;

        return this.sender.equals(that.getSender()) && this.recipient.equals(that.getRecipient());
    }

    @Override
    public double calculate() {
        return getAmount();
    }



}
