package com.yujung.boardback.dto.response;

public interface ResponseMessage {

  String SUCCESS = "Success.";

  String VALIDATION_FAILED = "Validation failed.";
  String DUPLICATED_EMAIL = "Duplicate email.";
  String DUPLICATED_NICKNAME = "Duplicate nickname.";
  String DUPLICATED_TELL_NUMBER = "Duplicate telephone number.";

  String SIGN_IN_FAILED = "Login information mismatch.";

  String DATABASE_ERROR = "Database error.";
  
}
