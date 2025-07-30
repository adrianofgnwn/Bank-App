package Bank;

/**
 * Represents a payment transaction with incoming and outgoing interest rates.
 * Extends the Transaction class and implements CalculateBill to calculate the final amount.
 */
public class Payment extends Transaction {
    //Attributes
    private double incomingInterest;
    private double outgoingInterest;

    /**
     * Constructor for a Payment with a specified date, amount, and description.
     *
     * @param date the date of the payment
     * @param amount the amount of the payment
     * @param description the description of the payment
     */
    //Constructor for Transaction attributes
    public Payment(String date, double amount, String description) {
            super(date, amount, description);
    }

    //Constructor with new attributes
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    //Copy Constructor
    public Payment(Payment other) {
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getIncomingInterest(), other.getOutgoingInterest());
    }


    //Getters and Setters
    public double getIncomingInterest() {return incomingInterest;}
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Invalid incoming interest rate. Must be between 0 and 1.");
        }
    }

    public double getOutgoingInterest() {return outgoingInterest;}
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Invalid outgoing interest rate. Must be between 0 and 1.");
        }
    }

    /**
     * Calculates the final amount with the interest rates.
     *
     * @return the calculated amount with interest
     */
    //Override Calculate
    @Override
    public double calculate() {
        // Calculate amount based on incoming and outgoing interest
        double total = getAmount();
        if (getAmount() > 0) {  // Incoming transaction
            total -= getAmount() * incomingInterest;
        } else {  // Outgoing transaction
            total += getAmount() * outgoingInterest;
        }
        return total;
    }

    /**
     * The function returns a string of the payment details.
     *
     * @return a string of payment details
     */
    //Override toString
    @Override
    public String toString() {
        return super.toString() +
                "Calculated Amount: " + calculate() + "\n" +
                "Incoming Interest: " + getIncomingInterest() + "\n" +
                "Outgoing Interest: " + getOutgoingInterest() + "\n";
    }

    /**
     * Compares this payment and another object, if they are equal.
     * @param obj the object to compare
     * @return true if the object is equal to this payment
     */
    //Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                   //checks if the current object and the object being compared is in the same memory location aka payment2 == payment2
        if (obj instanceof Payment payment) {
            // Check base attributes using super.equals
            if (!super.equals(payment)) return false;

            // Compare incomingInterest and outgoingInterest using Double.compare
            return Double.compare(this.incomingInterest, payment.incomingInterest) == 0 &&
                    Double.compare(this.outgoingInterest, payment.outgoingInterest) == 0;
        }
        return false;
    }
}
