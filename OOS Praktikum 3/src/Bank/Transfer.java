package Bank;

/**
 * Represents a transfer transaction with sender and recipient details.
 * Extends the Transaction class and implements CalculateBill to calculate the final amount.
 */
public class Transfer extends Transaction {
    //Attributes
    private String sender;
    private String recipient;

    /**
     * Constructor for a Transfer instance with a specified date, amount, and description.
     *
     * @param date the date of the transfer
     * @param amount the amount of the transfer
     * @param description the description of the transfer
     */
    //Constructor for Transaction attributes
    public Transfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    //Constructor with new attributes
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date,amount,description);
        this.sender = sender;
        this.recipient = recipient;
    }

    //Copy Constructor
    public Transfer(Transfer other) {
        this(other.getDate(), other.getAmount(), other.getDescription(), other.getSender(), other.getRecipient());
    }


    //Setter for amount
    @Override
    public void setAmount(double amount) {
        if (amount > 0) {
            super.setAmount(amount);
        } else {
            System.out.println("Invalid amount. Only positive values are allowed.");
        }
    }

    //Getters and Setters
    public String getSender() {return sender;}
    public void setSender(String sender) {this.sender = sender;}

    public String getRecipient() {return recipient;}
    public void setRecipient(String recipient) {this.recipient = recipient;}

    /**
     * Calculates the final transfer amount (original amount)
     *
     * @return the calculated amount (same as original amount)
     */
    //Override Calculate
    @Override
    public double calculate() {
        // No additional calculations in this case; simply return the amount
        return getAmount();
    }

    /**
     * The function returns a string of the transfer details.
     *
     * @return a string of transfer details
     */
    //Override toString
    @Override
    public String toString() {
        return super.toString() +
                "Calculated Amount: " + calculate() + "\n" +
                "Sender: " + getSender() + "\n" +
                "Recipient: " + getRecipient() + "\n";
    }

    /**
     * Compares this transfer and another object, if they are equal.
     * @param obj the object to compare
     * @return true if the object is equal to this transfer
     */
    //Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Transfer transfer) {
            // Check base attributes using super.equals
            if (!super.equals(transfer)) return false;

            // Check if sender is equal, then checks recipient
            if ((this.sender != null && this.sender.equals(transfer.sender)) || (this.sender == null && transfer.sender == null)) {
                return (this.recipient != null && this.recipient.equals(transfer.recipient)) || (this.recipient == null && transfer.recipient == null);
            }
        }
        return false;
    }
}
