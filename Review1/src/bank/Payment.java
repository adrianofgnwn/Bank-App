package bank;

//Ein- und Auszahlungen repräsentieren
public class Payment {
    private String date;
    private double amount;
    private String description;

    private double incomingInterest;
    private double outgoingInterest;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    //Bei Payment kann Amount sowohl eine positive Werte als auch negative Werte sein
    public void setAmount(double amount) {
            this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }
    public void setIncomingInterest(double incomingInterest) {
        if(incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Incoming interest must be between 0 and 1");
        }
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }
    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Outgoing interest must be between 0 and 1");
        }
    }

    //Konstruktor
    public Payment(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    //Kontruktor mit allen Attributen
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    //Copy Konstruktor
    public Payment(Payment payment) {
        this.date = payment.getDate();
        this.amount = payment.getAmount();
        this.description = payment.getDescription();
        this.incomingInterest = payment.getIncomingInterest();
        this.outgoingInterest = payment.getOutgoingInterest();
    }

    public void printObject() {
        System.out.println("Date: " + this.date +
                        ", Amount: " + this.amount +
                        ", Description: " + this.description +
                        ", Incoming interest: " + this.incomingInterest +
                        ", Outgoing interest: " + this.outgoingInterest
        );
    }


}
