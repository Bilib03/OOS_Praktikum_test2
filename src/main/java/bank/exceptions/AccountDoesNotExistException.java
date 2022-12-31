package bank.exceptions;

/**
 * Exception-class if the account does not exist
 */
public class AccountDoesNotExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public AccountDoesNotExistException(String message) {
        super(message);
    }
}