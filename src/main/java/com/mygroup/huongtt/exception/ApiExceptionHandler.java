package com.mygroup.huongtt.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.annotation.JsonInclude;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({ MethodArgumentNotValidException.class })
  public ResponseEntity<ErrorResponse> handle(
      MethodArgumentNotValidException e) {
    ErrorResponse errors = new ErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      ErrorItem error = new ErrorItem();
      error.setCode(fieldError.getField());
      error.setMessage(fieldError.getDefaultMessage());
      errors.addError(error);
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorItem> handle(ResourceNotFoundException e) {
    ErrorItem error = new ErrorItem();
    error.setMessage(e.getMessage());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  public static class ErrorItem {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    private String message;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

  public static class ErrorResponse {

    private List<ErrorItem> errors = new ArrayList<>();

    public List<ErrorItem> getErrors() {
      return errors;
    }

    public void setErrors(List<ErrorItem> errors) {
      this.errors = errors;
    }

    public void addError(ErrorItem error) {
      this.errors.add(error);
    }

  }
}
