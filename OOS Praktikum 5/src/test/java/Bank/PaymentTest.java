package Bank;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment1;
    private Payment payment2;
    private Payment payment3;
    private Transfer transferobj;

    @BeforeEach
    public void init() {
        payment1 = new Payment("10.10.2024", 50.0, "Test Constructor");
        payment2 = new Payment("11.11.2024", 100.0, "Test Complete Constructor", 0.05, 0.03);
        payment3 = new Payment(payment2); // Using copy constructor
    }

    @Test
    public void testConstructor() {
        //Test if object exists
        assertNotNull(payment1);
        assertNotNull(payment2);

        //Test to see if the attributes match
        assertEquals("10.10.2024", payment1.getDate());
        assertEquals(50.0, payment1.getAmount());
        assertEquals("Test Constructor", payment1.getDescription());

        payment2.setIncomingInterest(0.06);
        assertEquals(0.06, payment2.getIncomingInterest());
        payment2.setOutgoingInterest(0.07);
        assertEquals(0.07, payment2.getOutgoingInterest());

    }

    @Test
    public void testCopyConstructor() {
        //Check to see if object exists
        assertNotNull(payment3);

        //Check if the attributes match
        assertEquals(payment2, payment3);

        // Ensure they are different objects
        assertNotSame(payment2, payment3);
    }

    @Test
    public void testCalculate() {
        // Test for incoming interest
        payment2.setAmount(1000.0);
        assertEquals(950.0, payment2.calculate(), 0.001);

        // Test for outgoing interest
        payment2.setAmount(-1000.0);
        assertEquals(-1030.0, payment2.calculate(), 0.001);
    }

    @Test
    public void testEquals() {
        //The same
        assertTrue(payment2.equals(payment3));

        //Different Object
        transferobj = new Transfer("12.12.2024", 500.0, "Transfer to John", "Alice", "John");
        assertFalse(payment2.equals(transferobj));

        //Different Attribute
        payment3.setAmount(120.0);
        assertFalse(payment2.equals(payment3));
    }

    @Test
    public void testToString() {
        assertEquals("""
                        Date: 11.11.2024
                        Amount: 100.0
                        Description: Test Complete Constructor
                        Calculated Amount: 95.0
                        Incoming Interest: 0.05
                        Outgoing Interest: 0.03
                        """, payment2.toString());
    }


}
