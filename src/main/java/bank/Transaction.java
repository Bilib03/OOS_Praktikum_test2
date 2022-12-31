package bank;

import bank.exceptions.TransactionAttributeException;

/**
 * superclass for {@link Payment} and {@link Transfer} for outsourcing same attributes and methods
 */
public abstract class Transaction implements CalculateBill {
    /**
     * protected attribute: transaction day
     * shows the day of the {@link Transaction}
     */
    protected String date;
    /**
     * protected attribute: debt or income
     * shows the amount of the {@link Transaction} from the user
     */
    protected double amount;
    /**
     * protected attribute: purpose of the bank transfer
     * shows the description of the {@link Transaction} from the user
     */
    protected String description;

    //Getter-Setter

    /**
     * returns protected attribute "date" of a {@link Transaction}-object
     *
     * @return date
     */
    public String get_date() {
        return date;
    }

    /**
     * sets protected attribute "date" of a {@link Transaction}-object
     *
     * @param temp_date specified by the user
     */
    public void set_date(String temp_date) {
        date = temp_date;
    }

    /**
     * returns protected attribute "amount" of a {@link Transaction}-object
     *
     * @return amount
     */
    public double get_amount() {
        return amount;
    }

    /**
     * sets protected attribute "amount" of a {@link Transaction}-object
     *
     * @param temp_amount specified by the user
     */
    public void set_amount(double temp_amount) throws TransactionAttributeException {
        amount = temp_amount;
    }

    /**
     * returns protected attribute "description" of a {@link Transaction}-object
     *
     * @return description
     */
    public String get_description() {
        return description;
    }

    /**
     * sets protected attribute "description" of a {@link Transaction}-object
     *
     * @param temp_description specified by the user
     */
    public void set_description(String temp_description) {
        description = temp_description;
    }

    //Konstruktoren

    /**
     * creates a new {@link Transaction}-object with the given parameters
     *
     * @param temp_date
     * @param temp_amount      given by the user
     * @param temp_description given by the user
     */
    public Transaction(String temp_date, double temp_amount, String temp_description) throws TransactionAttributeException {
        set_date(temp_date);
        set_amount(temp_amount);
        set_description(temp_description);
    }

    //Copy-Konstruktor

    /**
     * creates a new {@link Transaction}-object with the same attributes of the transferring object
     *
     * @param x is the already existing object, which will be copied
     */
    public Transaction(Transaction x) throws TransactionAttributeException {
        set_date(x.get_date());
        set_amount(x.get_amount());
        set_description(x.get_description());
    }

    //Methoden:

    /**
     * turns {@link Transaction}-object in a described String
     *
     * @return String
     */
    @Override
    public String toString() {
        return ("date: " + date + "\n"
                + "amount: " + amount + "\n"
                + "description: " + description + "\n");
    }

    /**
     * compares two objects of the same class on their attributes
     *
     * @param obj is the second {@link Transaction}-object which will be compared
     * @return boolean: true if both are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Transaction other = (Transaction) obj;
        if (get_date() == other.get_date() && get_amount() == other.get_amount() && get_description() == other.get_description()) {
            return true;
        }
        return false;
    }
}
