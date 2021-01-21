package com.tambapps.gmage.exception;

/**
 * an exception that can be thrown when decoding a gmage
 */
public class GmageDecodingException extends RuntimeException {
  public GmageDecodingException(String message) {
    super(message);
  }

  public GmageDecodingException(String message, Throwable cause) {
    super(message, cause);
  }
}
