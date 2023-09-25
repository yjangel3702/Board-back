package com.yujung.boardback.dto.response.auth;

import com.yujung.boardback.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponseDto extends ResponseDto {
  private String token;
  private int expirationTime;

  private SignInResponseDto (String code, String message, String token) {
    super(code, message);
    this.token = token;
    this.expirationTime = 18000;
  }
}
