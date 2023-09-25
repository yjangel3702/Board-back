package com.yujung.boardback.service;

import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
  
}
