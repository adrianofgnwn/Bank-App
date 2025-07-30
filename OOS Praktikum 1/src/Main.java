import bank.Transfer;
import bank.Payment;

public class Main {
    public static void main(String[] args) {
        ////Payment Test////
        System.out.println("////Payment Test////");
        //Konstruktor Test ohne interest
        Payment Payment1 = new Payment("02.03.2024", 10000, "Payment 1");
        Payment1.printObject();

        //Konstruktor Test mit interest
        Payment Payment2 = new Payment("02.03.2024", 10000, "Payment 2", 0.5, 0.5);
        Payment2.printObject();

        //Konstruktor Test mit invalid interest
        Payment Payment3 = new Payment("02.03.2024", 10000, "Payment 3", 2, 0.5);
        Payment3.printObject();

        //Copy-Konstruktor Test
        Payment Payment4 = new Payment(Payment2);
        Payment4.printObject();

        ////Transfer Test////
        System.out.println("////Transfer Test////");
        //Konstruktor Test ohne recipient und Sender
        Transfer Transfer1 = new Transfer("11.05.2003", 10000, "Transfer 1");
        Transfer1.printObject();

        //Konstruktor Test mit falsche amount
        Transfer Transfer2 = new Transfer("11.05.2003", -500, "Transfer 2");
        Transfer2.printObject();

        //Konstruktor Test mit recipient und Sender
        Transfer Transfer3 = new Transfer("11.05.2003", 10000, "Transfer 3","Barrack" ,"Michelle");
        Transfer3.printObject();

        //Copy-Konstruktor Test
        Transfer Transfer4 = new Transfer(Transfer3);
        Transfer4.printObject();

        //Date verändern
        Transfer4.setDate("12.05.2024");
        Transfer4.printObject();
    }

}