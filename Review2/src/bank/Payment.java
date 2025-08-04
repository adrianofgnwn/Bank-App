package bank;

//Ein- und Auszahlungen repräsentieren
public class Payment extends Transaction implements CalculateBill {
    private double incomingInterest;
    private double outgoingInterest;

    public double getIncomingInterest() {
        return incomingInterest;
    }
    public void setIncomingInterest(double incomingInterest) {
        if(incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Error: Incoming interest must be between 0 and 1");
        }
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }
    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Error: Outgoing interest must be between 0 and 1");
        }
    }

    //Konstruktor
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    //Kontruktor mit allen Attributen
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    //Copy Konstruktor
    public Payment(Payment payment) {
        super(payment.getDate(), payment.getAmount(), payment.getDescription());
        this.incomingInterest = payment.getIncomingInterest();
        this.outgoingInterest = payment.getOutgoingInterest();
    }

    @Override
    public String toString() {
        return super.toString() + ", Incoming interest: " + this.incomingInterest + ", Outgoing interest: " + this.outgoingInterest + ", Calculate: " + this.calculate();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {return true;}
        if(obj == null || getClass() != obj.getClass()) {return false;}
        if(!super.equals(obj)) {return false;}

        Payment payment = (Payment) obj;

        return (this.incomingInterest == payment.getIncomingInterest() && this.outgoingInterest == payment.getOutgoingInterest());

    }

    @Override
    public double calculate() {
        if(amount >= 0) {
            return amount - amount*incomingInterest;
        } else {
            return amount + amount*outgoingInterest;
        }
    }
}
