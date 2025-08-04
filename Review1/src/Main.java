import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Review1 test");
        System.out.println();

        //== == == Payment Test == == ==//
        //Konstruktor 1
        Payment payment1 = new Payment("31.07.2025", 100, "Payment 1 - Konstruktor 1 Test - Positive Amount");
        payment1.printObject();
        Payment payment2 = new Payment("31.07.2025", -200, "Payment 2 - Konstruktor 1 Test - Negative Amount");
        payment2.printObject();
        //Konstruktor 2
        Payment payment3 = new Payment("31.07.2025", 300, "Payment 3 - Konstruktor 2 Test - Interest in range", 0.5, 0.5);
        payment3.printObject();
        Payment payment4 = new Payment("31.07.2025", 400, "Payment 4- Konstruktor 2 Test - Interest out of range", 2, -1);
        payment4.printObject();
        //Konstruktor 3
        Payment payment5 = new Payment(payment3);
        payment5.setDescription("Copy of payment4");
        payment5.printObject();

        System.out.println();
        System.out.println();

        //== == == Transfer Test == == ==//
        //Konstruktor 1
        Transfer transfer1 = new Transfer("31.07.2025", 100, "Payment 1 - Konstruktor 1 Test - Valid Amount");
        transfer1.printObject();
        Transfer transfer2 = new Transfer("31.07.2025", -200, "Payment 2 - Konstruktor 1 Test - Out of range Amount");
        transfer2.printObject();
        //Konstruktor 2
        Transfer transfer3 = new Transfer("31.07.2025", 300, "Payment 3 - Konstruktor 2 Test", "Messi", "Ronaldo");
        transfer3.printObject();
        //Konstruktor 3
        Transfer transfer4 = new Transfer(transfer3);
        transfer4.setDescription("Copy of transfer 3");
        transfer4.printObject();
    }
}