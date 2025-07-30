package Bank;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransferTest {
    private Transfer transfer1;
    private Transfer transfer2;
    private Transfer transfer3;
    private Payment paymentobj;

    @BeforeEach
    public void init() {
        transfer1 = new Transfer("10.10.2024", 50.0, "Test Constructor");
        transfer2 = new Transfer("12.12.2024", 500.0, "Transfer to John", "Alice", "John");
        transfer3 = new Transfer(transfer2);
    }

    @Test
    public void testConstructor() {
        //Test if object exists
        assertNotNull(transfer1);
        assertNotNull(transfer2);

        //Test to see if the attributes match
        assertEquals("12.12.2024", transfer2.getDate());
        assertEquals(500.0, transfer2.getAmount());
        assertEquals("Transfer to John", transfer2.getDescription());

        transfer2.setSender("Alfred");
        assertEquals("Alfred", transfer2.getSender());
        transfer2.setRecipient("Jason");
        assertEquals("Jason", transfer2.getRecipient());
    }

    @Test
    public void testCopyConstructor() {
        //Check to see if object exists
        assertNotNull(transfer3);

        //Check if the attributes match
        assertEquals(transfer2, transfer3);

        // Ensure they are different objects
        assertNotSame(transfer2, transfer3);
    }

    @Test
    public void testCalculate() {
        IncomingTransfer incoming = new IncomingTransfer("01.01.2025", 1000.0, "Incoming Payment", "John", "Alice");
        OutgoingTransfer outgoing = new OutgoingTransfer("01.01.2025", 1000.0, "Outgoing Payment", "Alice", "John");

        assertEquals(50.0, transfer1.calculate(), 0.001);
        assertEquals(1000.0, incoming.calculate(), 0.001); // No interest, amount should be the same
        assertEquals(-1000.0, outgoing.calculate(), 0.001); // Negative amount for outgoing
    }

    @Test
    public void testEquals() {
        //The same
        assertTrue(transfer2.equals(transfer3));

        //Different Object
        paymentobj = new Payment("11.11.2024", 100.0, "Test Complete Constructor", 0.05, 0.03);
        assertFalse(transfer2.equals(paymentobj));

        //Different Attribute
        transfer3.setDescription("Test Desciption");
        transfer3.setDate("02.02.2024");
        assertFalse(transfer2.equals(transfer3));
    }

    @Test
    public void testToString() {
        assertEquals("""
                        Date: 12.12.2024
                        Amount: 500.0
                        Description: Transfer to John
                        Calculated Amount: 500.0
                        Sender: Alice
                        Recipient: John
                        """, transfer2.toString());
    }
}

