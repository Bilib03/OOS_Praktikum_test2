package bank.exceptions;

/**
 * Exception-class for already existing account.
 */
public class AccountAlreadyExistsException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
