package bank.exceptions;

/**
 * Exception-class if the transaction to the account does not exist
 */
public class TransactionDoesNotExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public TransactionDoesNotExistException(String message) {
        super(message);
    }
}