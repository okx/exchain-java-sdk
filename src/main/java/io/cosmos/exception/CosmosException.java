package io.cosmos.exception;

/**
 * Base exception class for the Chain Core SDK.
 */
public class CosmosException extends Exception {
    /**
     * Default constructor.
     */
    public CosmosException() {
        super();
    }

    /**
     * Initializes exception with its message attribute.
     *
     * @param message error message
     */
    public CosmosException(String message) {
        super(message);
    }

    /**
     * Initializes a new exception while storing the original cause.
     *
     * @param message the error message
     * @param cause   the cause of the exception
     */
    public CosmosException(String message, Throwable cause) {
        super(message, cause);
    }
}
