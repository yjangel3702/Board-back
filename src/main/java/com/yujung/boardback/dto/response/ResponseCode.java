package com.yujung.boardback.dto.response;

public interface ResponseCode {
  
  String SUCCESS = "SU";

  String VALIDATION_FAILED = "VF";
  String DUPLICATED_EMAIL = "DE";
  String DUPLICATED_NICKNAME = "DN";
  String DUPLICATED_TELL_NUMBER = "DT";
  String NOT_EXIST_USER = "NU";

  String SIGN_IN_FAILED = "SF";

  String DATABASE_ERROR = "DBE";

}
