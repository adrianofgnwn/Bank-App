
package Bank;

import Bank.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represents a private bank alt with  name, incoming and outgoing interest rates.
 * Implements Bank.
 */
public class PrivateBankAlt implements Bank {
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    private String directoryName = "./persistence";

    // Consturctor
    public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest) {
        this.name = name;
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    // Copy-Constructor
    public PrivateBankAlt(PrivateBank privateBank) {
        this(privateBank.getName(), privateBank.getIncomingInterest(), privateBank.getOutgoingInterest());
    }

    // Getter and Setter for name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    // Getter and Setter for incomingInterest
    public double getIncomingInterest() {return incomingInterest;}
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Incoming interest rate must be between 0 and 1.");
        }

        if (!accountsToTransactions.isEmpty()) {                                            // if the hash map is not empty
            for (String account : accountsToTransactions.keySet()) {                        // checks each account in the map
                for (Transaction transaction : accountsToTransactions.get(account)) {       // checks each transaction in the list
                    if (transaction instanceof Payment payment) {
                        payment.setIncomingInterest(incomingInterest);                      // set each payment in the transaction list
                    }
                }
            }
        }

        this.incomingInterest = incomingInterest;
    }

    // Getter and Setter for outgoingInterest
    public double getOutgoingInterest() {return outgoingInterest;}
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Outgoing interest rate must be between 0 and 1.");
        }

        if (!accountsToTransactions.isEmpty()) {                                            // if the hash map is not empty
            for (String account : accountsToTransactions.keySet()) {                        // checks each account in the map
                for (Transaction transaction : accountsToTransactions.get(account)) {       // checks each transaction in the list
                    if (transaction instanceof Payment payment) {
                        payment.setOutgoingInterest(outgoingInterest);                      // set each payment's outgoing interest
                    }
                }
            }
        }

        this.outgoingInterest = outgoingInterest;
    }

    /**
     * The function returns a string of the payment details.
     *
     * @return a string of PrivateBank details
     */
    @Override
    public String toString() {
        return  "Name: " + getName() + "\n" +
                "Incoming Interest: " + getIncomingInterest() + "\n" +
                "Outgoing Interest: " + getOutgoingInterest() + "\n";
    }

    /**
     * Compares this payment and another object, if they are equal.
     * @param obj the object to compare
     * @return true if the object is equal to this privateBank
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;                   //checks if the current object and the object being compared is in the same memory location aka bank2 == bank2
        if (obj instanceof PrivateBankAlt privateBankAlt) { // Type check with pattern matching
            return Double.compare(this.incomingInterest, privateBankAlt.incomingInterest) == 0 &&
                    Double.compare(this.outgoingInterest, privateBankAlt.outgoingInterest) == 0 &&
                    Objects.equals(this.name, privateBankAlt.name) &&
                    Objects.equals(this.accountsToTransactions, privateBankAlt.accountsToTransactions);
        }
        return false;
    }

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " in the bank " + getName() + " already exists.");
        }
        accountsToTransactions.put(account, new ArrayList<>());
        System.out.println("Account " + account + " in the bank " + getName() + " is successfully created.");
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " in the bank " + getName() + " already exists.");
        }

        accountsToTransactions.put(account, new ArrayList<>());
        for (Transaction transaction : transactions) {
            // check the account name first then if it's not we can create an account with that name
            // then we can check if the transaction is already inside or not.
            if (accountsToTransactions.get(account).contains(transaction)) {
                // then remove the account creation if any transaction is invalid
                accountsToTransactions.remove(account);
                throw new TransactionAlreadyExistException("Transaction " + transaction + " already exists in account " + account);
            }
        }

        accountsToTransactions.get(account).addAll(transactions);
        System.out.println("Account " + account + " in the bank " + getName() + " is successfully created.");
    }

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

    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        if (accountsToTransactions.containsKey(account)) {
            for (Transaction transaction : accountsToTransactions.get(account)) {
                if (transaction instanceof Transfer transfer) {
                    if (transfer.getSender().equals(account)) {
                        balance -= transfer.getAmount();
                    } else if (transfer.getRecipient().equals(account)) {
                        balance += transfer.getAmount();
                    }
                }
            }
        }
        return balance;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        if (accountsToTransactions.containsKey(account)) {
            return accountsToTransactions.get(account);
        }
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> sorted = accountsToTransactions.get(account);
        if (accountsToTransactions.containsKey(account) && asc) {
            sorted.sort(Comparator.comparing(Transaction::calculate));
            return sorted;
        } else if (accountsToTransactions.containsKey(account) && !asc) {
            sorted.sort(Comparator.comparing(Transaction::calculate).reversed());
            return sorted;
        }
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> allTransactions = accountsToTransactions.get(account);
        List<Transaction> resultTransactions = new ArrayList<>();

        if (accountsToTransactions.containsKey(account) && positive) {
            for (Transaction transaction : allTransactions) {
                if(transaction.calculate() > 0)
                    resultTransactions.add(transaction);        // adds only the positive
            }

            return resultTransactions;
        } else if (accountsToTransactions.containsKey(account) && !positive) {
            for (Transaction transaction : allTransactions) {
                if(transaction.calculate() < 0)
                    resultTransactions.add(transaction);        // adds only the positive
            }

            return resultTransactions;
        }
        return List.of();
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (accountsToTransactions.containsKey(account)) {
            for (Transaction t : accountsToTransactions.get(account)) {
                if (t.equals(transaction)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account " + account + " in the bank " + getName() + " does not exist.");
        }
        if (!accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionDoesNotExistException("Transaction " + transaction + " does not exist in account " + account + " in the bank " + getName() + ".");
        }
        accountsToTransactions.get(account).remove(transaction);
        System.out.println("Transaction " + transaction + " is successfully removed from account " + account + " in the bank " + getName() + ".");
    }

    @Override
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        // Check if the directory exists and is valid
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Directory does not exist or is not valid: " + directoryName);
        }

        // Check if the account exists
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }

        // Remove the account from the map
        accountsToTransactions.remove(account);

        // Delete the associated JSON file
        File accountFile = new File(directory, account + ".json");
        if (accountFile.exists() && !accountFile.delete()) {
            throw new IOException("Failed to delete account file: " + accountFile.getPath());
        }

        System.out.println("Account " + account + " successfully deleted.");
    }

    @Override
    public List<String> getAllAccounts() {
        return new ArrayList<>(accountsToTransactions.keySet());
    }

}
