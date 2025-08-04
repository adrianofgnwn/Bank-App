package bank;

abstract class Transaction {
    protected String date;
    protected double amount;
    protected String description;

    //Setters und Getters
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //Methoden
    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Transaction(Transaction transaction) {
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
    }

    @Override
    public String toString() {
        return "Date: " + this.date + ", Amount: " + this.amount + ", Description: " + this.description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}

        Transaction that = (Transaction) obj;

        return this.amount == that.getAmount() &&
                this.date.equals(that.getDate()) &&
                this.description.equals(that.getDescription());
    }

}
