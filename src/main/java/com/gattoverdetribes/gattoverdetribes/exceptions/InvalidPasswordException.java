package com.gattoverdetribes.gattoverdetribes.exceptions;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String errorMessage) {
    super(errorMessage);
  }
}
