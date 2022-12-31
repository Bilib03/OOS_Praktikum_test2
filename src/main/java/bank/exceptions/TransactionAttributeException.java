package bank.exceptions;

/**
 * Exception-class for wrong transaction attributes
 * -> outsourced to setter
 */
public class TransactionAttributeException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public TransactionAttributeException(String message) {
        super(message);
    }
}
