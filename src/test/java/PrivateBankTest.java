import bank.*;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.TransactionAttributeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PrivateBankTest {

    PrivateBank pBank;
    PrivateBank pBank2;
    Payment pay;
    IncomingTransfer inTrans;
    OutgoingTransfer outTrans;


    // Laptop:
    String directoryName = "C:\\Users\\phili\\IdeaProjects\\OOS_Praktikum_test2\\src\\main\\java\\bank\\Accounts";

    @BeforeEach
    public void init() {
        Assertions.assertDoesNotThrow(() -> pBank = new PrivateBank("Test_Bank", 0.025, 0.5, directoryName));
        Assertions.assertDoesNotThrow(() -> pBank2 = new PrivateBank("Test_Bank", 0.025, 0.5, directoryName));

        Assertions.assertDoesNotThrow(() -> pBank.createAccount("testAcc"));
        Assertions.assertDoesNotThrow(() -> pay = new Payment("10.01.1988", 1500, "Deposit; 1500", 0.5, 0.5));
        Assertions.assertDoesNotThrow(() -> pBank.addTransaction("testAcc", pay));
        Assertions.assertDoesNotThrow(() -> inTrans = new IncomingTransfer("01.01.2021", 150, "Incoming Transfer; from Adam; 150", "Account Eva", "Account Adam"));
        Assertions.assertDoesNotThrow(() -> pBank.addTransaction("testAcc", inTrans));
        Assertions.assertDoesNotThrow(() -> outTrans = new OutgoingTransfer("03.01.2021", 50, "Outgoing Transfer; to Adam; 50", "Account Adam", "Account Eva"));
        Assertions.assertDoesNotThrow(() -> pBank.addTransaction("testAcc", outTrans));
    }

    @AfterEach
    public void finish() {
        File file = new File(directoryName + "testAcc.json");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("Test_Bank", pBank.get_name());
        assertEquals(0.025, pBank.get_incomingInterest());
        assertEquals(0.5, pBank.get_outgoingInterest());
        assertEquals(directoryName, pBank.getDirectoryName());

        Assertions.assertThrows(TransactionAttributeException.class, () -> new PrivateBank("Test_Bank2", -1, 0.5, directoryName));
        Assertions.assertThrows(TransactionAttributeException.class, () -> new PrivateBank("Test_Bank3", 0.5, -2, directoryName));
    }


    @Test
    public void testCreateAcc() {
        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> pBank.createAccount("testAcc"));
    }

    Payment tempPay;

    @Test
    public void testAddTrans() {
        Assertions.assertDoesNotThrow(() -> tempPay = new Payment("10.01.1988", 1500, "Deposit; 1500", 0.5, 0.5));

        ArrayList<Transaction> list = new ArrayList<>();
        list.add(pay);
        list.add(inTrans);
        list.add(outTrans);
        list.add(tempPay);

        Assertions.assertDoesNotThrow(() -> pBank.addTransaction("testAcc", tempPay));

        assertEquals(list, pBank.getTransactions("testAcc"));
    }

    @Test
    public void testString() {
        assertEquals("name: " + pBank.get_name() + "\n"
                        + "deposit: " + pBank.get_incomingInterest() + "\n"
                        + "withdrawal: " + pBank.get_outgoingInterest() + "\n"
                        + "map: " + pBank.accountsToTransactions + "\n"
                        + "file path: " + pBank.getDirectoryName() + "\n",
                pBank.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(pBank.equals(pBank));
        assertFalse(pBank.equals(pBank2));

        assertFalse(pBank.equals(null));
        assertFalse(pBank.equals(inTrans));
    }

    @Test
    public void testContains() {
        assertTrue(pBank.containsTransaction("testAcc", pay));
        assertTrue(pBank.containsTransaction("testAcc", inTrans));
        assertTrue(pBank.containsTransaction("testAcc", outTrans));

    }

    @Test
    public void testRemove() {
        Assertions.assertDoesNotThrow(() -> pBank.removeTransaction("testAcc", outTrans));
        assertFalse(pBank.containsTransaction("testAcc", outTrans));
    }

    @Test
    public void testBalance() {
        assertEquals(1562.5, pBank.getAccountBalance("testAcc"));
    }

    @Test
    public void testTransaction() {
        assertEquals(pBank.accountsToTransactions.get("testAcc"), pBank.getTransactions("testAcc"));
    }

    @Test
    public void testSortedTransaction() {
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(pay);
        list.add(inTrans);
        list.add(outTrans);

        assertEquals(list, pBank.getTransactionsSorted("testAcc", false));
    }

    @Test
    public void testTypeTransaction() {
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(outTrans);

        assertEquals(list, pBank.getTransactionsByType("testAcc", false));
    }

    @Test
    public void testdelete(){
       // Assertions.assertDoesNotThrow(()->pBank.deleteAccount("Roy"));

    }
}
