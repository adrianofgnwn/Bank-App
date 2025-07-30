package Bank;

import Bank.exceptions.*;

import java.util.*;

/**
 * Represents a private bank implementation that manages accounts and transactions.
 * The bank supports various operations such as creating accounts, adding transactions, and calculating account balances.
 * It applies incoming and outgoing interest rates to `Payment` transactions.
 * Implements the `Bank` interface.
 */
public class PrivateBank implements Bank {

    // Attributes
    private String name;
    private double incomingInterest;
    private double outgoingInterest;

    /**
     * A map that associates account names with their respective transaction lists.
     */
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    /**
     * Constructor to initialize a PrivateBank instance with specified parameters.
     *
     * @param name             the name of the private bank
     * @param incomingInterest the incoming interest rate (must be between 0 and 1)
     * @param outgoingInterest the outgoing interest rate (must be between 0 and 1)
     */
    //Constructor
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        this.name = name;
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy constructor to create a deep copy of a PrivateBank instance.
     *
     * @param privateBank the `PrivateBank` instance to copy
     */
    //Copy Constructor
    public PrivateBank(PrivateBank privateBank) {
        this(privateBank.getName(), privateBank.getIncomingInterest(), privateBank.getOutgoingInterest());
    }

    // Getters and Setters
    public String getName() {return name; }
    public void setName(String name) {this.name = name; }

    public double getIncomingInterest() {return incomingInterest; }
    /**
     * Sets the incoming interest rate for the bank and updates all `Payment` transactions.
     * The value must be between 0 and 1.
     *
     * @param incomingInterest the new incoming interest rate
     */
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Incoming interest rate must be between 0 and 1.");
            return;
        }

        // Update incoming interest for all existing `Payment` transactions
        if (!accountsToTransactions.isEmpty()) {
            for (String account : accountsToTransactions.keySet()) {
                for (Transaction transaction : accountsToTransactions.get(account)) {
                    if (transaction instanceof Payment payment) {
                        payment.setIncomingInterest(incomingInterest);
                    }
                }
            }
        }

        this.incomingInterest = incomingInterest;
    }

    public double getOutgoingInterest() {return outgoingInterest; }
    /**
     * Sets the outgoing interest rate for the bank and updates all `Payment` transactions.
     * The value must be between 0 and 1.
     *
     * @param outgoingInterest the new outgoing interest rate
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Outgoing interest rate must be between 0 and 1.");
            return;
        }

        // Update outgoing interest for all existing `Payment` transactions
        if (!accountsToTransactions.isEmpty()) {
            for (String account : accountsToTransactions.keySet()) {
                for (Transaction transaction : accountsToTransactions.get(account)) {
                    if (transaction instanceof Payment payment) {
                        payment.setOutgoingInterest(outgoingInterest);
                    }
                }
            }
        }

        this.outgoingInterest = outgoingInterest;
    }

    // Override Methods
    /**
     * Provides a string representation of the `PrivateBank` instance.
     *
     * @return a string describing the bank's attributes
     */
    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Incoming Interest: " + getIncomingInterest() + "\n" +
                "Outgoing Interest: " + getOutgoingInterest() + "\n";
    }

    /**
     * Compares this `PrivateBank` instance with another object for equality.
     *
     * @param obj the object to compare
     * @return true if the object is equal to this bank instance
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof PrivateBank privateBank) {
            return Double.compare(this.incomingInterest, privateBank.incomingInterest) == 0 &&
                    Double.compare(this.outgoingInterest, privateBank.outgoingInterest) == 0 &&
                    Objects.equals(this.name, privateBank.name) &&
                    Objects.equals(this.accountsToTransactions, privateBank.accountsToTransactions);
        }
        return false;
    }

    // Bank Interface Implementation
    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " in the bank " + getName() + " already exists.");
        }
        accountsToTransactions.put(account, new ArrayList<>());
        System.out.println("Account " + account + " in the bank " + getName() + " is successfully created.");
    }

    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " in the bank " + getName() + " already exists.");
        }

        // Add transactions to the account if valid
        accountsToTransactions.put(account, new ArrayList<>());
        for (Transaction transaction : transactions) {
            if (accountsToTransactions.get(account).contains(transaction)) {
                accountsToTransactions.remove(account);  // Remove account if transactions are invalid
                throw new TransactionAlreadyExistException("Transaction " + transaction + " already exists in account " + account);
            }
        }

        accountsToTransactions.get(account).addAll(transactions);
        System.out.println("Account " + account + " in the bank " + getName() + " is successfully created.");
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account " + account + " in the bank " + getName() + " does not exist.");
        }

        if (transaction instanceof Payment payment) {
            payment.setIncomingInterest(this.incomingInterest);
            payment.setOutgoingInterest(this.outgoingInterest);
        }

        if (accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists.");
        }

        accountsToTransactions.get(account).add(transaction);
        System.out.println("Transaction is successfully added to account " + account + " in the bank " + getName() + ".");
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        if (accountsToTransactions.containsKey(account)) {
            for (Transaction transaction : accountsToTransactions.get(account)) {
                balance += transaction.calculate();
            }
        }
        return balance;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransactions.getOrDefault(account, List.of());
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account. Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> sorted = new ArrayList<>(accountsToTransactions.get(account));
        if (asc) {
            sorted.sort(Comparator.comparing(Transaction::calculate));
        } else {
            sorted.sort(Comparator.comparing(Transaction::calculate).reversed());
        }
        return sorted;
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> transactions = accountsToTransactions.get(account);
        List<Transaction> filteredTransactions = new ArrayList<>();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                if (positive && transaction.calculate() > 0) {
                    filteredTransactions.add(transaction);
                } else if (!positive && transaction.calculate() < 0) {
                    filteredTransactions.add(transaction);
                }
            }
        }
        return filteredTransactions;
    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.getOrDefault(account, List.of()).contains(transaction);
    }

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account " + account + " in the bank " + getName() + " does not exist.");
        }
        if (!accountsToTransactions.get(account).remove(transaction)) {
            throw new TransactionDoesNotExistException("Transaction does not exist in the account " + account);
        }
    }
}
