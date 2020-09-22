package io.cosmos.exception;

/**
 * BadURLException wraps errors due to malformed URLs.
 */
public class BadURLException extends CosmosException {
  /**
   * Initializes exception with its message attribute.
   * @param message error message
   */
  public BadURLException(String message) {
    super(message);
  }
}
