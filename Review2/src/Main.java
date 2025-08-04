import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Review2 Test ===");
        System.out.println();

        System.out.println("=== Payment Tests ===");
        //Konstruktor 1
        Payment payment1 = new Payment("31.07.2025", 100, "Payment 1 - Konstruktor 1 Test - Positive Amount");
        System.out.println(payment1);
        Payment payment2 = new Payment("31.07.2025", -200, "Payment 2 - Konstruktor 1 Test - Negative Amount");
        System.out.println(payment2);
        //Konstruktor 2
        Payment payment3 = new Payment("31.07.2025", 300, "Payment 3 - Konstruktor 2 Test - Interest in range", 0.5, 0.5);
        System.out.println(payment3);
        Payment payment4 = new Payment("31.07.2025", 400, "Payment 4 - Konstruktor 2 Test - Interest out of range", 2, -1);
        System.out.println(payment4);
        //Konstruktor 3
        Payment payment5 = new Payment(payment3);
        System.out.println(payment5);
        //Equals test
        System.out.println("Equals Test - Correct: " + (payment3.equals(payment5)));
        payment5.setDescription("Changed Description for Equals Test");
        System.out.println("Equals Test - False: " + (payment3.equals(payment5)));

        System.out.println();

        System.out.println("=== Transfer Tests ===");
        //Konstruktor 1
        Transfer transfer1 = new Transfer("31.07.2025", 100, "Payment 1 - Konstruktor 1 Test - Valid Amount");
        System.out.println(transfer1);
        Transfer transfer2 = new Transfer("31.07.2025", -200, "Payment 2 - Konstruktor 1 Test - Out of range Amount");
        System.out.println(transfer2);
        //Konstruktor 2
        Transfer transfer3 = new Transfer("31.07.2025", 300, "Payment 3 - Konstruktor 2 Test", "Messi", "Ronaldo");
        System.out.println(transfer3);
        //Konstruktor 3
        Transfer transfer4 = new Transfer(transfer3);
        System.out.println(transfer4);
        //Equals test
        System.out.println("Equals Test - Correct: " + (transfer3.equals(transfer4)));
        transfer4.setDescription("Changed Description for Equals Test");
        System.out.println("Equals Test - false: " + (transfer3.equals(transfer4)));
    }
}