package com.yujung.boardback.service;

import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.request.auth.SignUpRequestDto;
import com.yujung.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {

  ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
  
}
