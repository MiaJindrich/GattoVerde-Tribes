package com.gattoverdetribes.gattoverdetribes.exceptions;

public class IncorrectPasswordException extends RuntimeException {

  public IncorrectPasswordException(String errorMessage) {
    super(errorMessage);
  }
}
