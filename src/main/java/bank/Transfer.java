package bank;

import bank.exceptions.TransactionAttributeException;

/**
 * subclass of {@link Transaction}
 */
public abstract class Transfer extends Transaction{
    //spezifische Attribute
    /**
     * private attribute: sender
     */
    private String sender;
    /**
     * private attribute: recipient
     */
    private String recipient;

    //Getter + Setter
    /**
     * specified setter for amount to check for positive value
     * @return double
     */
    public void set_amount(double temp_amount) throws TransactionAttributeException {
        if (temp_amount >= 0)
            amount = temp_amount;
        else {
           throw new TransactionAttributeException("Amount muss positiv sein!");
        }
    }

    /**
     * returns private attribute "sender" of a {@link Transfer}-object
     * @return String
     */
    public String get_sender() {return sender;}

    /**
     * sets the private attribute "sender" of a {@link Transfer}-object
     * @param temp_sender specified by the user
     */
    public void set_sender(String temp_sender) { sender = temp_sender;}

    /**
     * returns private attribute "recipient" of a {@link Transfer}-object
     * @return String
     */
    public String get_recipient() {return recipient;}

    /**
     * sets the private attribute "recipient" of a {@link Transfer}-object
     * @param temp_recipient specified by the user
     */
    public void set_recipient( String temp_recipient) {recipient = temp_recipient;}

    //Konstruktoren
    /**
     * creates a new {@link Transfer}-object with the same attributes of the transferring object
     * @param temp_date
     * @param temp_amount specified by the user
     * @param temp_description specified by the user
     */
    public Transfer (String temp_date, double temp_amount, String temp_description) throws TransactionAttributeException {
        super(temp_date, temp_amount, temp_description);
    }

    /**
     * creates a new {@link Transfer}-object with the same attributes of the transferring object
     * @param temp_date
     * @param temp_amount given by the user
     * @param temp_description given by the user
     * @param temp_sender given by the user
     * @param temp_recipient given by the user
     */
    public Transfer (String temp_date, double temp_amount, String temp_description, String temp_sender, String temp_recipient) throws TransactionAttributeException {
        this(temp_date, temp_amount, temp_description);
        set_sender(temp_sender);
        set_recipient((temp_recipient));
    }

    //Copy-Konstruktor
    /**
     * creates a new {@link Transfer}-object with the same attributes of the transferring object
     * @param x is the already existing object, which will be copied
     */
    public Transfer(Transfer x) throws TransactionAttributeException {
        super(x);
        set_sender(x.get_sender());
        set_recipient(x.get_recipient());
    }

    //Methoden:
    /**
     * turns {@link Transfer}-object in a described String
     * @return String
     */
    @Override
    public String toString() {
        return (super.toString()
                + "sender: " + sender + "\n"
                + "recipient: " + recipient + "\n");
    }

    /**
     * @return same amount as before as double
     */
    @Override
    public double calculate() {
        return get_amount();
    }

    /**
     * compares two objects of the same class on their attributes
     * @param obj is the second {@link Transfer}-object which will be compared
     * @return boolean: true if both are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Transfer other = (Transfer) obj;
        if (super.equals(obj) && get_sender() == other.get_sender() && get_recipient() == other.get_recipient()) {
            return true;
        }
        return false;
    }
}
