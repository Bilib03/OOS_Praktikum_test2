import bank.*;
import bank.exceptions.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {


        // PrivateBank-Tests
        System.out.println(" \nPrivateBank-Tests");

        PrivateBank DKB = new PrivateBank("Deutsche Kredit Bank", 0.0, 0.5, "F:\\Studium\\OneDrive - Fachhochschule Aachen\\3. Semester\\OoS\\Praktikum\\_OOS-P_Bank\\src\\main\\java\\bank\\Accounts\\");

        /*
        DKB.createAccount("Roy");
        Payment bar = new Payment("21.22.2022", 100, "wurst", 0.5, 0.5);
        DKB.addTransaction("Roy", bar);
        IncomingTransfer Philipp = new IncomingTransfer("01.01.2023", 80, "Döner");
        DKB.addTransaction("Roy", Philipp);


        try {
            DKB.writeAccounts("Roy");
            System.out.println(" \nwrite war erfolgreich");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            DKB.readAccounts();
            System.out.println("\nread war erfolgreich");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
*/
    }
}
