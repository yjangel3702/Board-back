package com.yujung.boardback.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.response.ResponseCode;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class PatchNicknameResponeDto extends ResponseDto {
  
  private PatchNicknameResponeDto(String code, String message) {
    super(code, message);
  }

  public static ResponseEntity<PatchNicknameResponeDto> success() {
    PatchNicknameResponeDto result = new PatchNicknameResponeDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistUser() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

  public static ResponseEntity<ResponseDto> duplicateNickname() {
    ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_NICKNAME, ResponseMessage.DUPLICATED_NICKNAME);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }


}
