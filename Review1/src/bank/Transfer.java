package bank;

//Überweisungen
public class Transfer   {
    private String date;
    private double amount;
    private String description;

    private String sender;
    private String recipient;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    //Bei Transfer muss Amount eine positive Werte sein
    public void setAmount(double amount) {
        if(amount < 0) {
            System.out.println("Amount can't be negative");
        } else {
            this.amount = amount;
        }
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        this.date = date;
        setAmount(amount);
        this.description = description;
    }

    //Kontruktor mit allen Attributen
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this.date = date;
        setAmount(amount);
        this.description = description;
        this.sender = sender;
        this.recipient = recipient;
    }

    //Copy Konstruktor
    public Transfer(Transfer transfer) {
        this.date = transfer.getDate();
        this.amount = transfer.getAmount();
        this.description = transfer.getDescription();
        this.sender = transfer.getSender();
        this.recipient = transfer.getRecipient();
    }

    public void printObject() {
        System.out.println("Date: " + this.date +
                ", Amount: " + this.amount +
                ", Description: " + this.description +
                ", Sender: " + this.sender +
                ", Recipient: " + this.recipient
        );
    }



}
