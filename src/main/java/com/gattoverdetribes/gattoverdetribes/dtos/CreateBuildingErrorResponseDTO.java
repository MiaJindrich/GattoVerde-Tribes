package com.gattoverdetribes.gattoverdetribes.dtos;

public class CreateBuildingErrorResponseDTO {

  private String status;
  private String message;

  public CreateBuildingErrorResponseDTO(String message) {
    this.status = "error";
    this.message = message;
  }

  public CreateBuildingErrorResponseDTO() {
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
