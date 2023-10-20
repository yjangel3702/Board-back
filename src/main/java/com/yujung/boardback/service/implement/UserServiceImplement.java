package com.yujung.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yujung.boardback.dto.request.user.PatchNicknameRequestDto;
import com.yujung.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.user.GetSignInUserResponseDto;
import com.yujung.boardback.dto.response.user.GetUserResponseDto;
import com.yujung.boardback.dto.response.user.PatchNicknameResponeDto;
import com.yujung.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.yujung.boardback.entity.UserEntity;
import com.yujung.boardback.repository.UserRepository;
import com.yujung.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  
  @Override
  public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

    UserEntity userEntity = null;

    try {

      userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return GetSignInUserResponseDto.notExistUser();

    } catch(Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetSignInUserResponseDto.success(userEntity);
  }

  @Override
  public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

    UserEntity userEntity = null;

    try {

      userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return GetUserResponseDto.notExistUser();

    } catch(Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetUserResponseDto.success(userEntity);

  }

  @Override
  public ResponseEntity<? super PatchNicknameResponeDto> patchNickname(PatchNicknameRequestDto dto, String email) {
    
    try {
      
      String nickname = dto.getNickname();
      boolean existdNickname = userRepository.existsByNickname(nickname);
      if (existdNickname) return PatchNicknameResponeDto.duplicateNickname();

      UserEntity userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return PatchNicknameResponeDto.notExistUser();

      userEntity.patchNickname(dto);
      userRepository.save(userEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PatchNicknameResponeDto.success();
  }

  @Override
  public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
      
      try {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) return PatchProfileImageResponseDto.notExistUser();

        userEntity.patchProfileImage(dto);
        userRepository.save(userEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PatchProfileImageResponseDto.success();

  }
}
