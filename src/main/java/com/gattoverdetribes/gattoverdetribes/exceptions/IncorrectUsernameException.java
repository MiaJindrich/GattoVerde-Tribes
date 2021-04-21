package com.gattoverdetribes.gattoverdetribes.exceptions;

public class IncorrectUsernameException extends RuntimeException {

  public IncorrectUsernameException(String errorMessage) {
    super(errorMessage);
  }
}
