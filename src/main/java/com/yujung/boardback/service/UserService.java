package com.yujung.boardback.service;

import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.request.user.PatchNicknameRequestDto;
import com.yujung.boardback.dto.response.user.GetSignInUserResponseDto;
import com.yujung.boardback.dto.response.user.GetUserResponseDto;
import com.yujung.boardback.dto.response.user.PatchNicknameResponeDto;

public interface UserService {

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
  ResponseEntity<? super GetUserResponseDto> getUser(String email);

  ResponseEntity<? super PatchNicknameResponeDto> patchNickname(PatchNicknameRequestDto dto, String email);
  
}
