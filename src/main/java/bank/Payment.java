package bank;

import bank.exceptions.*;

import static java.lang.System.exit;

/**
 * subclass of {@link Transaction}
 */
public class Payment extends Transaction {
    //spezifische Attribute
    /**
     * private attribute: incoming Interest
     */
    private double incomingInterest;
    /**
     * private attribute: outgoing Interest
     */
    private double outgoingInterest;

    //Getter + Setter

    /**
     * returns private attribute "incomingInterst" of a {@link Payment}-object
     *
     * @return double
     */
    public double get_incomingInterest() {
        return incomingInterest;
    }

    /**
     * sets the value of the private attribute "incomingInterest" of a {@link Payment}-object
     *
     * @param temp_inInterest must be percentage between 0 and 1
     */
    public void set_incomingInterest(double temp_inInterest) throws TransactionAttributeException {
        if (temp_inInterest >= 0 && temp_inInterest <= 1)
            incomingInterest = temp_inInterest;
        else
            throw new TransactionAttributeException("Eine der Transaktion ist so nicht möglich.");
    }

    /**
     * returns private attribute "outgoingInterest" of a {@link Payment}-object
     *
     * @return double
     */
    public double get_outgoingInterest() {
        return outgoingInterest;
    }

    /**
     * sets the value of the private attribute "outgoingInterest" of a {@link Payment}-object
     *
     * @param temp_outInterest must be percentage between 0 and 1
     */
    public void set_outgoingInterest(double temp_outInterest) throws TransactionAttributeException {
        if (temp_outInterest >= 0 && temp_outInterest <= 1)
            outgoingInterest = temp_outInterest;
        else
            throw new TransactionAttributeException("Eine der Transaktion ist so nicht möglich.");
    }


    //Konstruktoren

    /**
     * creates a new {@link Payment}-object with the same attributes of the transferring object
     *
     * @param temp_date
     * @param temp_amount      given by the user
     * @param temp_description given by the user
     */
    public Payment(String temp_date, double temp_amount, String temp_description) throws TransactionAttributeException {
        super(temp_date, temp_amount, temp_description);
    }

    /**
     * creates a new {@link Payment}-object with the same attributes of the transferring object
     *
     * @param temp_date
     * @param temp_amount      given by the user
     * @param temp_description given by the user
     * @param temp_inInterest  must be percentage between 0 and 1
     * @param temp_outInterest must be percentage between 0 and 1
     */
    public Payment(String temp_date, double temp_amount, String temp_description, double temp_inInterest, double temp_outInterest)
            throws TransactionAttributeException {
        this(temp_date, temp_amount, temp_description);
        set_incomingInterest(temp_inInterest);
        set_outgoingInterest(temp_outInterest);
    }

    //Copy-Konstruktor

    /**
     * creates a new {@link Payment}-object with the same attributes of the transferring object
     *
     * @param x is the already existing object, which will be copied
     */
    public Payment(Payment x)
            throws TransactionAttributeException {
        super(x);
        set_incomingInterest(x.get_incomingInterest());
        set_outgoingInterest(x.get_outgoingInterest());
    }

    //Methoden:

    /**
     * turns {@link Payment}-object in a described String
     *
     * @return String
     */
    @Override
    public String toString() {
        return (super.toString()
                + "incomingInterest: " + incomingInterest + "\n"
                + "outcomingInterest: " + outgoingInterest + "\n");
    }

    /**
     * @return new amount as double
     */
    @Override
    public double calculate() {
        if (get_amount() > 0) {
            return (get_amount() - get_amount() * get_incomingInterest());
        } else {
            return (get_amount() + get_amount() * get_outgoingInterest());
        }
    }

    /**
     * compares two objects of the same class on their attributes
     *
     * @param obj is the second {@link Payment}-object which will be compared
     * @return boolean: true if both are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Payment other = (Payment) obj;
        if (super.equals(obj) && get_incomingInterest() == other.get_incomingInterest() && get_outgoingInterest() == other.get_outgoingInterest()) {
            return true;
        }
        return false;
    }
}
