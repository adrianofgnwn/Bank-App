package bank;

//For Transfer
public class Transfer {
    //Attributes
    private String date;
    private double amount;
    private String description;
    private String sender;
    private String recipient;
    boolean check = true;

    //Getters
    public String getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public String getsender() {
        return sender;
    }
    public String getrecipient() {
        return recipient;
    }

    //Setters
    public void setDate(String date) {
        this.date = date;
    }
    public void setAmount(double amount) {
        if(amount >= 0) {
            this.amount = amount;

        } else {
            System.out.println("Invalid Amount, set to 0");
            check = false;
        }
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setsender(String sender) {
        this.sender = sender;
    }
    public void setrecipient(String recipient) {
        this.recipient = recipient;
    }

    //Konstruktor
    public Transfer(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date, amount, description);
        setsender(sender);
        setrecipient(recipient);
    }

    //Copy-Konstruktor
    public Transfer(Transfer other) {
        this(other.date, other.amount, other.description, other.sender, other.recipient);
    }

    //Print method
    public void printObject() {
        if(check) {
            System.out.println("Date: " + getDate());
            System.out.println("Amount: " + getAmount());
            System.out.println("Description: " + getDescription());
            System.out.println("Sender: " + getsender());
            System.out.println("Recipient: " + getrecipient());
            System.out.println();
        } else {
            System.out.println("Error!");
            System.out.println();
        }
    }
}
