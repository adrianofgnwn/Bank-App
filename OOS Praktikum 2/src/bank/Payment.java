package bank;

/**
 * Represents a payment transaction with additional information for incoming and outgoing interest rates.
 */
public class Payment extends Transaction {
    private double incomingInterest;
    private double outgoingInterest;

    /**
     * Gets the incoming interest rate.
     * @return the incoming interest rate.
     */
    public double getIncomingInterest() { return incomingInterest; }

    /**
     * Gets the outgoing interest rate.
     * @return the outgoing interest rate.
     */
    public double getOutgoingInterest() { return outgoingInterest; }

    /**
     * Sets the incoming interest rate within a valid range.
     * @param incomingInterest the incoming interest rate.
     */
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Invalid incoming interest, set to 0");
            check = false;
        }
    }

    /**
     * Sets the outgoing interest rate within a valid range.
     * @param outgoingInterest the outgoing interest rate.
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Invalid outgoing interest, set to 0");
            check = false;
        }
    }

    /**
     * Constructs a Payment with the given date, amount, and description.
     * @param date the date of the payment.
     * @param amount the payment amount.
     * @param description the payment description.
     */
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Constructs a Payment with the given date, amount, description, incoming interest, and outgoing interest.
     * @param date the date of the payment.
     * @param amount the payment amount.
     * @param description the payment description.
     * @param incomingInterest the incoming interest rate.
     * @param outgoingInterest the outgoing interest rate.
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy constructor to create a copy of a Payment object.
     * @param other the Payment object to copy.
     */
    public Payment(Payment other) {
        this(other.date, other.amount, other.description, other.incomingInterest, other.outgoingInterest);
    }

    /**
     * Calculates the payment amount considering interest rates.
     * @return the calculated payment amount.
     */
    @Override
    public double calculate() {
        if (amount > 0) {
            return amount - (amount * incomingInterest);
        } else {
            return amount + (amount * outgoingInterest);
        }
    }

    /**
     * Returns a string representation of the payment.
     * @return a formatted string with payment details.
     */
    @Override
    public String toString() {
        return super.toString() + ", incomingInterest=" + incomingInterest + ", outgoingInterest=" + outgoingInterest;
    }

    /**
     * Checks if this payment is equal to another object.
     * @param obj the object to compare with.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Payment other = (Payment) obj;
        return Double.compare(other.incomingInterest, incomingInterest) == 0 &&
                Double.compare(other.outgoingInterest, outgoingInterest) == 0;
    }
}
