package com.gattoverdetribes.gattoverdetribes.exceptions;

public class InvalidUsernameException extends RuntimeException {

  public InvalidUsernameException(String errorMessage) {
    super(errorMessage);
  }
}