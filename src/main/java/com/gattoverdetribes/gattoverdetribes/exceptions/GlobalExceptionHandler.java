package com.gattoverdetribes.gattoverdetribes.exceptions;

import com.gattoverdetribes.gattoverdetribes.dtos.ErrorResponseDTO;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger;

  public GlobalExceptionHandler(Logger logger) {
    this.logger = logger;
  }

  //region Player Exceptions:
  @ExceptionHandler(InvalidUsernameException.class)
  public ResponseEntity<ErrorResponseDTO> invalidUsername(InvalidUsernameException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorResponseDTO> invalidPassword(InvalidPasswordException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(IncorrectUsernameException.class)
  public ResponseEntity<ErrorResponseDTO> incorrectUsername(IncorrectUsernameException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IncorrectPasswordException.class)
  public ResponseEntity<ErrorResponseDTO> incorrectPassword(IncorrectPasswordException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }
  //endregion

  //region Kingdom Exceptions:
  @ExceptionHandler(InvalidKingdomException.class)
  public ResponseEntity<ErrorResponseDTO> invalidKingdom(InvalidKingdomException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }
  //endregion

  //region Resource Exceptions:
  @ExceptionHandler(InvalidResourceException.class)
  public ResponseEntity<ErrorResponseDTO> invalidResource(InvalidResourceException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(NotEnoughResourcesException.class)
  public ResponseEntity<ErrorResponseDTO> notEnoughResources(NotEnoughResourcesException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MissingResourceException.class)
  public ResponseEntity<ErrorResponseDTO> missingResource(MissingResourceException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
  }
  //endregion

  //region Building Exceptions:
  @ExceptionHandler(InvalidBuildingException.class)
  public ResponseEntity<ErrorResponseDTO> invalidBuilding(InvalidBuildingException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(InvalidBuildingLevelException.class)
  public ResponseEntity<ErrorResponseDTO> invalidBuildingLevel(InvalidBuildingLevelException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }

  //endregion

  // region Troop Exceptions:
  @ExceptionHandler(InvalidTroopException.class)
  public ResponseEntity<ErrorResponseDTO> invalidTroop(InvalidTroopException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
  }
  //endregion

  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<ErrorResponseDTO> missingParameter(MissingParameterException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoContentException.class)
  public ResponseEntity<ErrorResponseDTO> noContent(NoContentException e) {
    logger.warn(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler(IdNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> idNotFound(IdNotFoundException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
  }
}
