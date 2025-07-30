package Bank;

/**
 * Represents a  transaction with a date, amount, and description.
 * This is a base class for transaction types such as Payment and Transfer.
 */
public abstract class Transaction implements CalculateBill {
    //Attributes
    private String date;
    private double amount;
    private String description;

    /**
     * Constructor for a Transaction.
     *
     * @param date the date of the transaction
     * @param amount the amount of the transaction
     * @param description the description of the transaction
     */
    //Constructor
    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.setAmount(amount);
        this.description = description;
    }

    //Getters and Setters
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    /**
     * The function return a string representation of the transaction.
     *
     * @return a string of transaction details
     */
    //Override toString
    @Override
    public String toString() {
        return "Date: " + date + "\n" +
                "Amount: " + amount + "\n" +
                "Description: " + description + "\n";
    }

    /**
     * Compares this transaction and another object, if they are equal.
     * @param obj the object to compare
     * @return true if the object is equal to this transaction
     */
    //Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;           //checks if the current object and the object being compared is in the same memory location aka trans2 == trans2
        if (obj instanceof Transaction transaction) {
            // Compare date, if similar, checks description then checks the amount
            if ((this.date != null && this.date.equals(transaction.date)) || (this.date == null && transaction.date == null)) {
                if ((this.description != null && this.description.equals(transaction.description)) || (this.description == null && transaction.description == null)) {
                    return Double.compare(this.amount, transaction.amount) == 0;
                }
            }
        }
        return false;
    }
}
