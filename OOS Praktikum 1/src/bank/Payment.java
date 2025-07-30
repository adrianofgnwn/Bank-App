package bank;

//For payment
public class Payment {

    //Attributes
    private String date;
    private double amount;
    private String description;
    private double incomingInterest;
    private double outgoingInterest;
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

    public double getincomingInterest() {
        return incomingInterest;
    }

    public double getoutcomingInterest() {
        return outgoingInterest;
    }

    //Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setincomingInterest(double incomingInterest) {
        if(incomingInterest>= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;

        } else {
            System.out.println("Invalid  incoming interest, set to 0");
            check = false;
        }
    }

    public void setoutcomingInterest(double outcomingInterest) {
        if(outcomingInterest >= 0 && outcomingInterest <= 1) {
            this.outgoingInterest = outcomingInterest;

        } else {
            System.out.println("Invalid outcoutgoingoming interest, set to 0");
            check = false;
        }
    }

    //Konstruktor
    public Payment(String date, double amount, String description){
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    public Payment(String date, double amount, String description, double incomingInterest, double outcomingInterest) {
        this(date, amount, description);
        setincomingInterest(incomingInterest);
        setoutcomingInterest(outcomingInterest);

    }

    //Copy-Konstruktor
    public Payment(Payment other) {
        this(other.date, other.amount, other.description, other.incomingInterest, other.outgoingInterest);
    }

    //Print method
    public void printObject() {
        if(check) {
            System.out.println("Date: " + getDate());
            System.out.println("Amount: " + getAmount());
            System.out.println("Description: " + getDescription());
            System.out.println("Incoming interest: " + getincomingInterest());
            System.out.println("Outgoing interest: " + getoutcomingInterest());
            System.out.println();
        } else {
            System.out.println("Error!");
            System.out.println();
        }
    }
}
