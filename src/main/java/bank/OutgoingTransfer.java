package bank;

import bank.exceptions.TransactionAttributeException;

public class OutgoingTransfer extends Transfer {
    //Konstruktoren
    /**
     * creates a new {@link OutgoingTransfer}-object with the same attributes of the transferring object
     * @param temp_date transaction day
     * @param temp_amount specified by the user
     * @param temp_description specified by the user
     */
    public OutgoingTransfer (String temp_date, double temp_amount, String temp_description) throws TransactionAttributeException {
        super(temp_date, temp_amount, temp_description);
    }

    /**
     * creates a new {@link OutgoingTransfer}-object with the same attributes of the transferring object
     * @param temp_date
     * @param temp_amount given by the user
     * @param temp_description given by the user
     * @param temp_sender given by the user
     * @param temp_recipient given by the user
     */
    public OutgoingTransfer (String temp_date, double temp_amount, String temp_description, String temp_sender, String temp_recipient) throws TransactionAttributeException {
        super(temp_date, temp_amount, temp_description, temp_sender, temp_recipient);
    }

    //Copy-Konstruktor
    /**
     * creates a new {@link OutgoingTransfer}-object with the same attributes of the transferring object
     * @param x is the already existing object, which will be copied
     */
    public OutgoingTransfer(OutgoingTransfer x) throws TransactionAttributeException {
        super(x);
    }




    /**
     * @return same amount as before as double
     */
    @Override
    public double calculate() {
        return -super.calculate();
    }
}
