package bank;

/**
 * Represents a transfer transaction with sender and recipient information.
 */
public class Transfer extends Transaction {
    private String sender;
    private String recipient;

    /**
     * Gets the sender of the transfer.
     * @return the sender.
     */
    public String getSender() { return sender; }

    /**
     * Gets the recipient of the transfer.
     * @return the recipient.
     */
    public String getRecipient() { return recipient; }

    /**
     * Sets the sender of the transfer.
     * @param sender the sender.
     */
    public void setSender(String sender) { this.sender = sender; }

    /**
     * Sets the recipient of the transfer.
     * @param recipient the recipient.
     */
    public void setRecipient(String recipient) { this.recipient = recipient; }

    /**
     * Constructs a Transfer with the given date, amount, and description.
     * @param date the date of the transfer.
     * @param amount the transfer amount.
     * @param description the transfer description.
     */
    public Transfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Constructs a Transfer with the given date, amount, description, sender, and recipient.
     * @param date the date of the transfer.
     * @param amount the transfer amount.
     * @param description the transfer description.
     * @param sender the sender of the transfer.
     * @param recipient the recipient of the transfer.
     */
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     * Copy constructor to create a copy of a Transfer object.
     * @param other the Transfer object to copy.
     */
    public Transfer(Transfer other) {
        this(other.date, other.amount, other.description, other.sender, other.recipient);
    }

    /**
     * Calculates the transfer amount, which remains unchanged.
     * @return the transfer amount.
     */
    @Override
    public double calculate() {
        return amount;
    }

    /**
     * Returns a string representation of the transfer.
     * @return a formatted string with transfer details.
     */
    @Override
    public String toString() {
        return super.toString() + ", sender=" + sender + ", recipient=" + recipient;
    }

    /**
     * Checks if this transfer is equal to another object.
     * @param obj the object to compare with.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Transfer other = (Transfer) obj;
        return sender.equals(other.sender) && recipient.equals(other.recipient);
    }
}
