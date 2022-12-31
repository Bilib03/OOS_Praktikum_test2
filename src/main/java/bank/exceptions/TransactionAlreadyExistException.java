package bank.exceptions;

/**
 * Exception-class for already existing transaction.
 */
public class TransactionAlreadyExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public TransactionAlreadyExistException(String message) {
        super(message);
    }
}