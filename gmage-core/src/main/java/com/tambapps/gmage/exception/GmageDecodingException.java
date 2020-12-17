package com.tambapps.gmage.exception;

public class GmageDecodingException extends RuntimeException {
  public GmageDecodingException(String message) {
    super(message);
  }

  public GmageDecodingException(String message, Throwable cause) {
    super(message, cause);
  }
}
