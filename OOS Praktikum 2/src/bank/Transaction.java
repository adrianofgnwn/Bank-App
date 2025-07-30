package bank;

/**
 * Represents a bank transaction with basic information such as date, amount, and description.
 * This is an abstract class that implements the CalculateBill interface.
 */
public abstract class Transaction implements CalculateBill {
    protected String date;
    protected double amount;
    protected String description;
    protected boolean check = true;

    /**
     * Gets the date of the transaction.
     * @return the transaction date.
     */
    public String getDate() { return date; }

    /**
     * Gets the amount of the transaction.
     * @return the transaction amount.
     */
    public double getAmount() { return amount; }

    /**
     * Gets the description of the transaction.
     * @return the transaction description.
     */
    public String getDescription() { return description; }

    /**
     * Sets the date of the transaction.
     * @param date the date to set.
     */
    public void setDate(String date) { this.date = date; }

    /**
     * Sets the amount of the transaction.
     * @param amount the amount to set.
     */
    public void setAmount(double amount) { this.amount = amount; }

    /**
     * Sets the description of the transaction.
     * @param description the description to set.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Constructs a Transaction object with the given date, amount, and description.
     * @param date the date of the transaction.
     * @param amount the amount of the transaction.
     * @param description the description of the transaction.
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    /**
     * Copy constructor to create a copy of a Transfer object.
     * @param other the Transfer object to copy.
     */
    public Transaction(Transfer other) {
        this(other.date, other.amount, other.description);
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Amount: " + calculate() + ", Description: " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return Double.compare(that.amount, amount) == 0 &&
                date.equals(that.date) &&
                description.equals(that.description);
    }
}
