import bank.*;
import bank.exceptions.TransactionAttributeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;



public class TransferTest {

    IncomingTransfer inTrans;
    OutgoingTransfer outTrans;

    @BeforeEach
    public void init() {
        Assertions.assertDoesNotThrow(() -> inTrans = new IncomingTransfer("09.10.2000", 100, "Kinoticket", "Roy", "Paul"));
        Assertions.assertDoesNotThrow(() -> outTrans = new OutgoingTransfer("29.03.2002", 10, "Kekse", "Paul", "Roy"));
    }

    @Test
    public void testConstructor() {
        assertEquals("09.10.2000", inTrans.get_date());
        assertEquals(100, inTrans.get_amount());
        assertEquals("Kinoticket", inTrans.get_description());
        assertEquals("Roy", inTrans.get_sender());
        assertEquals("Paul", inTrans.get_recipient());

        assertEquals("29.03.2002", outTrans.get_date());
        assertEquals(10, outTrans.get_amount());
        assertEquals("Kekse", outTrans.get_description());
        assertEquals("Paul", outTrans.get_sender());
        assertEquals("Roy", outTrans.get_recipient());

        Assertions.assertThrows(TransactionAttributeException.class, () -> new IncomingTransfer("09.10.2000", -200, "Kinoticket"));
        Assertions.assertThrows(TransactionAttributeException.class, () -> new OutgoingTransfer("09.10.2000", -200, "Kinoticket"));
    }

    IncomingTransfer inTrans2;
    OutgoingTransfer outTrans2;

    @Test
    public void testCopyConstructor() {
        Assertions.assertDoesNotThrow(() -> inTrans2 = new IncomingTransfer(inTrans));
        assertEquals("09.10.2000", inTrans2.get_date());
        assertEquals(100, inTrans2.get_amount());
        assertEquals("Kinoticket", inTrans2.get_description());
        assertEquals("Roy", inTrans2.get_sender());
        assertEquals("Paul", inTrans2.get_recipient());

        Assertions.assertDoesNotThrow(() -> outTrans2 = new OutgoingTransfer(outTrans));
        assertEquals("29.03.2002", outTrans.get_date());
        assertEquals(10, outTrans.get_amount());
        assertEquals("Kekse", outTrans.get_description());
        assertEquals("Paul", outTrans.get_sender());
        assertEquals("Roy", outTrans.get_recipient());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 20, 300})
    public void testCalculate(Double argument) {
        Assertions.assertDoesNotThrow(() -> inTrans.set_amount(argument));
        Assertions.assertDoesNotThrow(() -> outTrans.set_amount(argument));

        assertEquals(argument, inTrans.calculate());
        assertEquals(-argument, outTrans.calculate());
    }

    IncomingTransfer inTrans3;
    @Test
    public void testEquals() {
        assertTrue(inTrans.equals(inTrans));
        assertFalse(inTrans.equals(outTrans));

        Assertions.assertDoesNotThrow(() -> inTrans3 = new IncomingTransfer("01.01.2023", 99, "Kinoticket", "Bob", "Gustav"));
        assertFalse(inTrans.equals(inTrans3));

    }

    @Test
    public void testString() {
        assertEquals("date: " + inTrans.get_date() + "\n"
                        + "amount: " + inTrans.get_amount() + "\n"
                        + "description: " + inTrans.get_description() + "\n"
                        + "sender: " + inTrans.get_sender() + "\n"
                        + "recipient: " + inTrans.get_recipient() + "\n",
                inTrans.toString());
    }
}
