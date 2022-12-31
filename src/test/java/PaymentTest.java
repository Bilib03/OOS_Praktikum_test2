import bank.IncomingTransfer;
import bank.Payment;
import bank.exceptions.TransactionAttributeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Payment payment;
    private Payment payment2;

    @BeforeEach
    public void init() {
        Assertions.assertDoesNotThrow(() -> payment = new Payment("09.10.2000", 100, "Kinoticket", 0.0, 0.3));
        Assertions.assertDoesNotThrow(() -> payment2 = new Payment("29.03.2002", 10, "Kekse", 0.3, 0.0));
    }

    @Test
    public void testConstructor() {
        assertEquals("09.10.2000", payment.get_date());
        assertEquals(100, payment.get_amount());
        assertEquals("Kinoticket", payment.get_description());
        assertEquals(0.0, payment.get_incomingInterest());
        assertEquals(0.3, payment.get_outgoingInterest());

        Assertions.assertThrows(TransactionAttributeException.class, () -> new Payment("09.10.2000", 100, "Kinoticket", 20, 0.0));
        Assertions.assertThrows(TransactionAttributeException.class, () -> new Payment("09.10.2000", 100, "Kinoticket", 0.0, 20));
    }

    @Test
    public void testCopyConstructor() {
        Assertions.assertDoesNotThrow(() -> payment2 = new Payment(payment));

        assertEquals("09.10.2000", payment2.get_date());
        assertEquals(100, payment2.get_amount());
        assertEquals("Kinoticket", payment2.get_description());
        assertEquals(0.0, payment2.get_incomingInterest());
        assertEquals(0.3, payment2.get_outgoingInterest());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-20, 0, 300})
    public void testCalculate(Double argument) {
        Assertions.assertDoesNotThrow(() -> payment.set_amount(argument));

        if (argument > 0) {
            assertEquals(argument - argument * payment.get_incomingInterest(), payment.calculate());
        } else {
            assertEquals(argument + argument * payment.get_outgoingInterest(), payment.calculate());
        }
    }

    private IncomingTransfer inTrans;
    @Test
    public void testEquals() {
        assertEquals(payment, payment);
        assertFalse(payment.equals(payment2));

        Assertions.assertDoesNotThrow(() -> inTrans = new IncomingTransfer("01.01.2023", 80, "Döner"));
        assertFalse(payment.equals(inTrans));
    }

    @Test
    public void testString() {
        assertEquals("date: " + payment.get_date() + "\n"
                + "amount: " + payment.get_amount() + "\n"
                + "description: " + payment.get_description() + "\n"
                + "incomingInterest: " + payment.get_incomingInterest() + "\n"
                + "outcomingInterest: " + payment.get_outgoingInterest() + "\n",
                payment.toString());
    }
}
