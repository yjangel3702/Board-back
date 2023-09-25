package com.yujung.boardback.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.response.ResponseCode;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.ResponseMessage;
import com.yujung.boardback.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSignInUserResponseDto extends ResponseDto {

  private String email;
  private String nickname;
  private String profileImage;

  private GetSignInUserResponseDto(String code, String message, UserEntity userEntity) {
    super(code, message);
    this.email = userEntity.getEmail();
    this.nickname = userEntity.getNickname();
    this.profileImage = userEntity.getProfileImageUrl();
  }

  public static ResponseEntity<GetSignInUserResponseDto> success(UserEntity userEntity) {
    GetSignInUserResponseDto result = new GetSignInUserResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, userEntity);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  
}
