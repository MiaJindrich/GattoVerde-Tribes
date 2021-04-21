package com.gattoverdetribes.gattoverdetribes.exceptions;

public class MissingParameterException extends RuntimeException {

  public MissingParameterException(String errorMessage) {
    super(errorMessage);
  }
}