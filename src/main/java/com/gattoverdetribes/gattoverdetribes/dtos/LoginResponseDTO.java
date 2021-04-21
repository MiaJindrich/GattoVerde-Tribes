package com.gattoverdetribes.gattoverdetribes.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LoginResponseDTO {

  private String status;
  private String message;
  private String token;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(String message) {
    this.message = message;
  }

  public LoginResponseDTO(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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