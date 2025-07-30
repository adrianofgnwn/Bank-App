package Bank;

import Bank.exceptions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


public class PrivateBankTest {
    private PrivateBank privatebank1;
    private PrivateBank privatebank2;
    private Transaction paymentobj;

    @BeforeEach
    public void setUp() {
        privatebank1 = new PrivateBank("Konto Deutsche Bank", 0.05, 0.02);
        privatebank2 = new PrivateBank(privatebank1);
        paymentobj = new Payment("11.11.2024", 100.0, "Payment Object", 0.05, 0.03);

    }

    // Test Constructor
    @Test
    public void testConstructor() {
        //Test if object exists
        assertNotNull(privatebank1);

        //Test to see if the attributes match
        assertEquals("Konto Deutsche Bank", privatebank1.getName());
        assertEquals(0.05, privatebank1.getIncomingInterest());
        assertEquals(0.02, privatebank1.getOutgoingInterest());

        privatebank1.setIncomingInterest(0.03);
        assertEquals(0.03, privatebank1.getIncomingInterest());
        privatebank1.setOutgoingInterest(0.04);
        assertEquals(0.04, privatebank1.getOutgoingInterest());
        privatebank1.setName("Konto Commerz Bank");
        assertEquals("Konto Commerz Bank", privatebank1.getName());

    }

    @Test
    public void testCopyConstructor() {
        //Check to see if object exists
        assertNotNull(privatebank2);

        //Check if the attributes match
        assertEquals(privatebank1, privatebank2);

        // Ensure they are different objects
        assertNotSame(privatebank1, privatebank2);
    }

    @Test
    public void testEquals() {
        //The same
        assertTrue(privatebank1.equals(privatebank2));

        //Different Object
        assertFalse(privatebank1.equals(paymentobj));

        //Different Attribute
        privatebank2.setName("Konto Commerz Bank");
        assertFalse(privatebank1.equals(privatebank2));
    }

    @Test
    public void testToString() {
        assertEquals("""
                Name: Konto Deutsche Bank
                Incoming Interest: 0.05
                Outgoing Interest: 0.02
                """, privatebank1.toString());
    }

    @Test
    void testCreateAccount1() throws AccountAlreadyExistsException, IOException {
        //Create account with no transaction
        privatebank1.createAccount("Konto_Messi");
        //Check if account is successfully created
        assertTrue(privatebank1.accountsToTransactions.containsKey("Konto_Messi"));
        //Check if transaction is empty
        assertTrue(privatebank1.accountsToTransactions.get("Konto_Messi").isEmpty());
        //Check if file is successfully created
        File accountFile = new File("persistence/Konto_Messi.json");
        assertTrue(accountFile.exists());
        //Check if exception works when duplicate is created
        assertThrows(AccountAlreadyExistsException.class, () -> {
            privatebank1.createAccount("Konto_Messi");
        });
    }

    @Test
    void testCreateAccount2() throws AccountAlreadyExistsException,TransactionAlreadyExistException,TransactionAttributeException, IOException {
        //Create account with transaction
        privatebank1.createAccount("Konto_Neymar", new ArrayList<>(List.of(new Payment("15.12.2024", 200.0, "Payment Neymar"))));
        //Check if account is successfully created
        assertTrue(privatebank1.accountsToTransactions.containsKey("Konto_Neymar"));
        //Check if file is successfully created
        File accountFile = new File("persistence/Konto_Neymar.json");
        assertTrue(accountFile.exists());
        //Check if exception works when duplicate is created
        assertThrows(AccountAlreadyExistsException.class, () -> {
            privatebank1.createAccount("Konto_Neymar");
        });
    }

    @Test
    void testAddTransaction() throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        privatebank1.createAccount("Konto_Ronaldo");
        Payment paymentCR7 = new Payment("08.08.2024", 150.0, "Payment Ronaldo");
        privatebank1.addTransaction("Konto_Ronaldo", paymentCR7);
        //Check if account exists
        assertTrue(privatebank1.accountsToTransactions.containsKey("Konto_Ronaldo"));
        //Check if transaction is successfully added
        assertTrue(privatebank1.accountsToTransactions.get("Konto_Ronaldo").contains(paymentCR7));
        //Check if the amount of transaction is correct
        assertEquals(1, privatebank1.accountsToTransactions.get("Konto_Ronaldo").size());
        //Check if file is successfully created
        File accountFile = new File("persistence/Konto_Ronaldo.json");
        assertTrue(accountFile.exists());
        //Add another transaction and verify
        Payment paymentCR = new Payment("05.12.2024", 50, "Payment 2 Ronaldo");
        privatebank1.addTransaction("Konto_Ronaldo", paymentCR);
        assertEquals(2, privatebank1.accountsToTransactions.get("Konto_Ronaldo").size());
        /*
        // Attempt adding an invalid transaction and check exceptions
        assertThrows(TransactionAttributeException.class, () -> {
            Transfer transferCR7 = new Transfer("06.12.2024", -85, "Transfer Ronaldo");
            privatebank1.addTransaction("Ronaldo", transferCR7);
        });
        */
        //Check if exception works when duplicate transaction is created
        assertThrows(TransactionAlreadyExistException.class, () -> {
            privatebank1.addTransaction("Konto_Ronaldo", paymentCR7);
        });
    }

    @Test
    void testContainsTransaction() throws TransactionAlreadyExistException, AccountAlreadyExistsException, IOException, AccountDoesNotExistException, TransactionAttributeException {
        //Create transfer and the account
        privatebank1.createAccount("Konto_Monica");
        IncomingTransfer IncomingTransfer1 = new IncomingTransfer("01.12.2024", 50.0, "Überweisung", "Konto_Marlyne", "Konto_Monica");
        privatebank1.addTransaction("Konto_Monica", IncomingTransfer1);
        //Check if the account contains the transaction
        List<Transaction> transactions = privatebank1.getTransactions("Konto_Monica");
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(IncomingTransfer1));
        assertTrue(privatebank1.containsTransaction("Konto_Monica", IncomingTransfer1));
        assertFalse(privatebank1.containsTransaction("Konto_Monica", new IncomingTransfer("11.12.2024", 20.0, "Überweisung", "Konto_Imelda", "Konto_Monica")));
    }

    @Test
    void testRemoveTransaction() throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, IOException, TransactionDoesNotExistException {
        //Create account and add transactions
        privatebank1.createAccount("Konto_Marlyne");
        IncomingTransfer incomingtransfermarlyne = new IncomingTransfer("01.12.2024", 40.0, "Überweisung", "Konto_Monica", "Konto_Marlyne");
        OutgoingTransfer outgoingtransfermarlyne = new OutgoingTransfer("02.12.2024", 75.0, "Überweisung", "Konto_Marlyne", "Konto_Monica");
        privatebank1.addTransaction("Konto_Marlyne", incomingtransfermarlyne);
        privatebank1.addTransaction("Konto_Marlyne", outgoingtransfermarlyne);

        //Remove transaction and check
        privatebank1.removeTransaction("Konto_Marlyne", incomingtransfermarlyne);
        List<Transaction> transactions = privatebank1.getTransactions("Konto_Marlyne");
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(outgoingtransfermarlyne));

        //Check if exception works when transaction does not exist
        assertThrows(TransactionDoesNotExistException.class, () -> {
            privatebank1.removeTransaction("Konto_Marlyne", incomingtransfermarlyne);
        });

        //Check if exception works when account does not exist
        assertThrows(AccountDoesNotExistException.class, () -> {
            privatebank1.removeTransaction("Konto_Imelda", incomingtransfermarlyne);
        });
    }

    @Test
    void testGetAccountBalance() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        //Create account and transactions
        privatebank1.createAccount("Konto_Alfred", new ArrayList<>(List.of(
                new Payment("31.11.2024", 800.0, "Salary November"),
                new OutgoingTransfer("31.11.2024", 350.0, "Miete", "Konto_Alfred", "Vermieter")
        )));

        //Check account balance
        double balance = privatebank1.getAccountBalance("Konto_Alfred");
        assertEquals(450.0, balance); // 800 - 350 = 450
    }

    @Test
    void testGetTransationsSorted() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        //Create account with transactions
        privatebank1.createAccount("Konto_Sorted", new ArrayList<>(List.of(
                new Payment("31.11.2024", 800.0, "Salary November"),
                new OutgoingTransfer("02.12.2024", 400.0, "Miete", "Konto_Sorted", "Vermieter"),
                new Payment("03.12.2024", 100.0, "Bonus")
        )));

        //Ascending order
        List<Transaction> sortedAsc = privatebank1.getTransactionsSorted("Konto_Sorted", true);
        //Check if the amount of transition is correct
        assertEquals(3, sortedAsc.size());
        //Check if the order is ascending
        assertEquals(-400.0, sortedAsc.get(0).calculate()); // OutgoingTransfer
        assertEquals(100, sortedAsc.get(1).calculate()); // Payment
        assertEquals(800.0, sortedAsc.get(2).calculate()); // Payment

        //Descending order
        List<Transaction> sortedDesc = privatebank1.getTransactionsSorted("Konto_Sorted", false);
        //Check if the amount of transition is correct
        assertEquals(3, sortedDesc.size());
        //Check if the order is descending
        assertEquals(800.0, sortedDesc.get(0).calculate()); // Payment
        assertEquals(100.0, sortedDesc.get(1).calculate()); // Payment
        assertEquals(-400.0, sortedDesc.get(2).calculate()); // OutgoingTransfer
    }

    @Test
    void testGetTransactionsByType() throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        //Create account with transactions
        privatebank1.createAccount("Konto_TypeSorted", new ArrayList<>(List.of(
                new Payment("30.11.2024", 1000.0, "Salary November"),
                new OutgoingTransfer("02.12.2024", 400.0, "Miete", "Konto_TypeSorted", "Vermieter"),
                new Payment("03.12.2024", 150.0, "Bonus")
        )));

        //Positive transactions
        List<Transaction> positiveTransactions = privatebank1.getTransactionsByType("Konto_TypeSorted", true);
        //Check if the amount of transition is correct
        assertEquals(2, positiveTransactions.size());
        //Check if the amount is positive
        assertTrue(positiveTransactions.stream().allMatch(t -> t.calculate() >= 0));

        //Negative transactions
        List<Transaction> negativeTransactions = privatebank1.getTransactionsByType("Konto_TypeSorted", false);
        //Check if the amount of transition is correct
        assertEquals(1, negativeTransactions.size());
        //Check if the amount is negative
        assertTrue(negativeTransactions.stream().allMatch(t -> t.calculate() < 0));
    }

    @Test
    void testWriteAccount() throws IOException, AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        // Create an account and add transactions
        privatebank1.createAccount("Konto_Imelda");
        Payment paymentImelda = new Payment("12.12.2024", 700.0, "Salary");
        privatebank1.addTransaction("Konto_Imelda", paymentImelda);

        // Write account data to JSON
        privatebank1.writeAccount("Konto_Imelda");

        //Check if file is successfully created
        File accountFile = new File("./persistence/Konto_Imelda.json");
        assertTrue(accountFile.exists());

        //Read back the JSON content
        String jsonContent = new String(Files.readAllBytes(accountFile.toPath()));
        assertTrue(jsonContent.contains("Salary"));
        assertTrue(jsonContent.contains("700"));
    }

    @Test
    void testReadAccounts() throws IOException, AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        // Write test data to JSON files manually
        String jsonContent =
                """
                [
                  {
                    "CLASSNAME": "Bank.Payment",
                    "INSTANCE": {
                      "incomingInterest": 0.025,
                      "outgoingInterest": 0.05,
                      "date": "10.01.1988",
                      "amount": 1500.0,
                      "description": "Deposit; 1500"
                    }
                  },
                  {
                    "CLASSNAME": "Bank.IncomingTransfer",
                    "INSTANCE": {
                      "sender": "Account Eva",
                      "recipient": "Account Adam",
                      "date": "01.01.2021",
                      "amount": 150.0,
                      "description": "Incoming Transfer; Eva->Adam; 150"
                    }
                  },
                  {
                    "CLASSNAME": "Bank.OutgoingTransfer",
                    "INSTANCE": {
                      "sender": "Account Adam",
                      "recipient": "Account Eva",
                      "date": "03.01.2021",
                      "amount": 50.0,
                      "description": "Outgoing Transfer; Adam->Eva; 50"
                    }
                  }
                ]
                """;

        File dir = new File("./persistence");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File accountFile = new File("./persistence/ReadTest.json");
        try (FileWriter writer = new FileWriter(accountFile)) {
            writer.write(jsonContent);
        }

        privatebank1.readAccounts();

        //Check if the account exist
        assertTrue(privatebank1.accountsToTransactions.containsKey("ReadTest"));
        List<Transaction> transactions = privatebank1.accountsToTransactions.get("ReadTest");
        assertEquals(3, transactions.size());
        assertEquals(1462.5, transactions.get(0).calculate());
        assertEquals(150.0, transactions.get(1).calculate());
        assertEquals(-50.0, transactions.get(2).calculate());
        assertEquals("Deposit; 1500", transactions.get(0).getDescription());
        assertEquals("Incoming Transfer; Eva->Adam; 150", transactions.get(1).getDescription());
        assertEquals("Outgoing Transfer; Adam->Eva; 50", transactions.get(2).getDescription());
    }
}
