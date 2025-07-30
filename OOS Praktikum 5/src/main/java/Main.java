import Bank.*;

import Bank.exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, AccountDoesNotExistException {
        /*
        PrivateBank DeutscheBank = new PrivateBank("DB", 0.5, 0.1);;
        PrivateBankAlt CommerzBank = new PrivateBankAlt("Commerz", 0.2, 0.4);

        DeutscheBank.createAccount("Max", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Max","Tim"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3),
                new Transfer("02.08.2024",450,"GESCHENK","Joe","Max")
        )));

        CommerzBank.createAccount("Max", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Max","Tim"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3),
                new Transfer("02.08.2024",450,"GESCHENK","Joe","Max")
        )));

        DeutscheBank.createAccount("Vi", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Vi","Powder"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3)
        )));

        CommerzBank.createAccount("Vi", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Vi","Powder"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3)
        )));

        System.out.println(DeutscheBank + "\n \n \n" + CommerzBank + "\n \n \n \n \n");


        // Exception from createAccount
        try{
            DeutscheBank.createAccount("Max");
        }catch (AccountAlreadyExistsException err){
            System.out.println(err);
        }


        // Exception from createAccount
        try{
            CommerzBank.createAccount("Vi",List.of(new Transfer("02.08.2024",520,"ESSEN","Vi","Powder")));
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException err){
            System.out.println(err);
        }

        // Exception from addTransfer
        try{
            CommerzBank.addTransaction("Jinx", new Transfer("02.08.2024",450,"GESCHENK","Caitlyn","Vi"));
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException err){
            System.out.println(err);
        }
        // addTransfer works
        try{
            CommerzBank.addTransaction("Vi", new Transfer("02.08.2024",450,"GESCHENK","Caitlyn","Vi"));
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException err){
            System.out.println(err);
        }

        System.out.println("\n\n UPDATED----------------------" + CommerzBank + "\n \n \n \n \n");

        // Exception from removeTransaction
        try{
            DeutscheBank.removeTransaction("Jinx", new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException err) {
            System.out.println(err);
        }
        // removeTransfer works
        try{
            DeutscheBank.removeTransaction("Vi", new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException err) {
            System.out.println(err);
        }

        System.out.println("\n\n UPDATED----------------------" + DeutscheBank + "\n \n \n \n \n");


        System.out.println("DB SORTED ASC : " + DeutscheBank.getTransactionsSorted("Max",true) + "\n\n");
        System.out.println("DB SORTED DSC : " + DeutscheBank.getTransactionsSorted("Max",false) + "\n\n");
        System.out.println("DB TYPE TRUE : " + DeutscheBank.getTransactionsByType("Max",true) + "\n\n");
        System.out.println("DB SORTED FALSE : " + DeutscheBank.getTransactionsByType("Max",false) + "\n\n");

        System.out.println("Commerz contain: " + CommerzBank.containsTransaction("Vi",new Payment("01.02.2024",-1100,"WG",0.12,0.3)));
        System.out.println("Commerz contain: " + CommerzBank.containsTransaction("Vi",new Payment("01.02.2024",-1100,"HALO",0.12,0.3)));
        System.out.println("Commerz contain: " + CommerzBank.containsTransaction("Jinx",new Payment("01.02.2024",-1100,"WG",0.12,0.3)));


        PrivateBankAlt CommerzBank_Kopie = new PrivateBankAlt("Commerz", 0.2, 0.4);
        CommerzBank_Kopie.createAccount("Max", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Max","Tim"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3),
                new Transfer("02.08.2024",450,"GESCHENK","Joe","Max")
                // Different amount for equals ^^
        )));
        CommerzBank_Kopie.createAccount("Vi", new ArrayList<>(List.of(
                new Transfer("02.08.2024",520,"ESSEN","Vi","Powder"),
                new Payment("01.02.2024",1200,"EINKAUF",0.15,0.1),
                new Payment("01.02.2024",-1100,"WG",0.12,0.3),
                new Transfer("02.08.2024",450,"GESCHENK","Caitlyn","Vi")
                // added after the addTransaction^^
        )));



        PrivateBank Sparkasse=new PrivateBank("S", 0.1,0.6);
        Sparkasse.createAccount("Jayce", List.of(
                new Transfer("02.08.2022",520,"WG","Jayce","Victor"),
                new Payment("01.02.2021",1200,"EINKAUF",0.15,0.1)

        ));
        Sparkasse.createAccount("Vander", List.of(
                new Transfer("02.08.2022",560,"GESCHENK","Vander","Silco"),
                new Transfer("02.08.2022",520,"ESSEN","Silco","Vander")
        ));

        System.out.println("CommerzBank.equals(CommerzBank_Kopie) : " + CommerzBank.equals(CommerzBank_Kopie));
        System.out.println("DeutscheBank.equals(Sparkasse) : " + DeutscheBank.equals(Sparkasse) + "\n\n");
        */

//        //Testing Payment class
//        Payment payment1 = new Payment("15.10.2024", 1500.50, "Salary Deposit");
//        Payment payment2 = new Payment("15.10.2024", 1500.50, "Salary Deposit", 2, 0.3);  // 2% outgoing interest
//        Payment payment3 = new Payment(payment2);
//
//        System.out.println("Testing Payment Object:");
//        System.out.println(payment1);
//        System.out.println(payment2);
//        System.out.println(payment3);
//
//        System.out.println();
//        System.out.println();
//        Paymenn();
//
//        if(payment1.equals(payment2)){
//            System.out.println("Objects are equal");
//        } else {
//            System.out.println("Objects are not equal");
//        }
//        System.out.println();
//        if(payment2.equals(payment3)){
//            System.out.println("Objects are equal");
//        } else {
//            System.out.println("Objects are not equal");
//        }
//
//        System.out.println();
//        System.out.println("----------------------------------------------------------------------------");
//        System.out.println();
//
//        // Testing Transfer class
//        Transfer transfer1 = new Transfer("16.10.2024", -500.00, "Rent Payment");
//        Transfer transfer2 = new Transfer("16.10.2024", 500.00, "Rent Payment", "Sheifer", "Timothy");
//        Transfer transfer3 = new Transfer(transfer2);
//
//        System.out.println("Testing Transfer Object:");
//        System.out.println(transfer1);
//        System.out.println(transfer2);
//        transfer3.setDate("12.11.2022");
//        System.out.println(transfer3);
//
//        System.out.println();
//        System.out.println();
//
//        if(transfer1.equals(transfer2)){
//            System.out.println("Objects are equal");
//        } else {
//            System.out.println("Objects are not equal");
//        }
//        System.out.println();
//        if(transfer2.equals(transfer3)){
//            System.out.println("Objects are equal");
//        } else {
//            System.out.println("Objects are not equal");
//        }
//
//        if(payment1.equals(null)){
//            System.out.println("Objects are equal");
//        } else {
//            System.out.println("Objects are not equal");
//        }

    }

/* Test Payment Copy Constructor
    public static void main(String[] args) {
        Payment original = new Payment("2024-12-02", 100.0, "Salary", 0.05, 0.02);
        Payment copy = new Payment(original);

        System.out.println("Original: " + original);
        System.out.println("Copy: " + copy);

        // Verify equality
        System.out.println("Equal: " + original.equals(copy));
    }
 */
}