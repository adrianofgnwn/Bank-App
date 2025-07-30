package Bank;

import Bank.exceptions.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Files;
import java.util.*;
import java.io.*;
import java.io.IOException;



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

    private String directoryName = "./persistence";

    /**
     * A map that associates account names with their respective transaction lists.
     */
    public Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

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
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException{
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Account with the name "+account+" in the bank"+name+" already exists!");
        else {
            accountsToTransactions.put(account, new ArrayList<>());
            writeAccount(account);
            System.out.println("Account with the name " + account + " has succesfully created in the bank " + name);
        }
    }

    /**
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException
     * @throws TransactionAttributeException
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException,TransactionAlreadyExistException,TransactionAttributeException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account with the name " + account + " in the bank" + name + " already exists!");
        } else {
            for (Transaction tr : transactions) {
                if (accountsToTransactions.containsKey(account) && accountsToTransactions.get(account).contains(transactions)) {
                    throw new TransactionAlreadyExistException("Duplicate transaction can not be added to the account!");
                } else {
                    if (tr instanceof Payment payment) {
                        setIncomingInterest(payment.getIncomingInterest());
                        setOutgoingInterest(payment.getOutgoingInterest());
                    }
                }
            }
            accountsToTransactions.put(account, transactions);
            writeAccount(account);
            System.out.println("Account with the name " + account + " and its transaction has succesfully created in the bank " + name);
        }
    }

    /**
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }
        if (accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists: " + transaction);
        }
        if (transaction instanceof Payment payment) {
            payment.setIncomingInterest(incomingInterest);
            payment.setOutgoingInterest(outgoingInterest);
        }

        accountsToTransactions.get(account).add(transaction);
        System.out.println("Transaction added: " + transaction);
        writeAccount(account);
        System.out.println("Account written to file: " + account);
    }


    /**
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException
     * @throws TransactionDoesNotExistException
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException("Account with the name "+account+" in the bank"+name+" does not exist!");
        }else {
            if(!accountsToTransactions.get(account).contains(transaction)){
                throw new TransactionDoesNotExistException("This transaction does not exist in the account "+account);
            }else{
                accountsToTransactions.get(account).remove(transaction);
                writeAccount(account);
                System.out.println("Transaction successfully removed");
            }
        }
    }

    /**
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     * @return
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction){
        if(accountsToTransactions.containsKey(account)){
            if(accountsToTransactions.get(account).contains(transaction))
                return true;
            else
                return false;
        }else
            return false;
    }

    /**
     *
     * @param account the selected account
     * @return
     */
    @Override
    public double getAccountBalance(String account){
        double balance=0;
        List<Transaction> Lists = accountsToTransactions.get(account);
        for(Transaction tr:Lists){
            balance += tr.calculate();
        }
        return balance;
    }

    /**
     *
     * @param account the selected account
     * @return
     */
    @Override
    public List<Transaction> getTransactions(String account){
        return accountsToTransactions.get(account);
    }

    /**
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc){
        List<Transaction> sorted = new ArrayList<>(accountsToTransactions.get(account));
        if(asc)
            sorted.sort(Comparator.comparing(Transaction::calculate));
        else
            sorted.sort(Comparator.comparing(Transaction::calculate).reversed());
        return sorted;
    }

    /**
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive){
        List<Transaction> type = new ArrayList<>();
        List<Transaction> Lists = accountsToTransactions.get(account);
        for(Transaction tr : Lists){
            if(positive && tr.calculate()>=0){
                type.add(tr);
            }else if(!positive && tr.calculate()<0){
                type.add(tr);
            }
        }
        return type;
    }

    // Method to write a specific account and its transactions to a JSON file
    public void writeAccount(String account) throws IOException {
        //Check if account exists
        if (!accountsToTransactions.containsKey(account)) {
            throw new IOException("Account not found: " + account);
        }
        //Create Gson instance for serialization
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new Serializer()) //Register custom Serializer
                .setPrettyPrinting() //Enables human-readable formatting for the Json output
                .create(); //Build the configured Gson instance

        // Serialize transactions with custom serializer
        String jsonContent = gson.toJson(accountsToTransactions.get(account), new TypeToken<List<Transaction>>() {}.getType());
        //gson.toJson() converts the list of Transaction objects into a JSON string.
        //TypeToken<List<Transaction>>() {}.getType() specifies the type of data being serialized

        //Create file for the account
        File file = new File(directoryName, account + ".json");
        //Write the Jason data to the file
        //Ensures the FileWriter is automatically closed after use, even if an exception occurs.
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonContent);
        }
    }

    // Method to read all accounts from files and populate the map
    public void readAccounts() throws IOException {
        //Create a File object representing the directory
        File dir = new File(directoryName);
        //Checks if the directory exists
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory: " + directoryName);
        }

        //List Json files in the directory
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".json"));
        //(dir1, name) -> name.endsWith(".json") lambda ensures only .json files are included

        //Check if empty
        if (files == null) {
            return;
        }

        //Create Gson instance for serialization
        Gson gson = new GsonBuilder().registerTypeAdapter(Transaction.class, new Serializer()).create();

        //Read and process file
        //Loops through the .json files in the directory
        for (File file : files) {
            try (Reader reader = Files.newBufferedReader(file.toPath())) {
                //Files.newBufferedReader(file.toPath()): Opens the file for reading using a Reader.
                //Extract name and removes .json from the name
                String accountName = file.getName().replace(".json", "");
                //Deserialize
                List<Transaction> transactions = gson.fromJson(reader, new TypeToken<List<Transaction>>() {}.getType());
                //Update Map
                accountsToTransactions.put(accountName, transactions);
            }
        }
    }
}
