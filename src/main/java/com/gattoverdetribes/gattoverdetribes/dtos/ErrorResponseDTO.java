package com.gattoverdetribes.gattoverdetribes.dtos;

public class ErrorResponseDTO {

  private String status;
  private String message;

  public ErrorResponseDTO(String message) {
    this.status = "error";
    this.message = message;
  }

  public ErrorResponseDTO() {
    this.status = "error";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}