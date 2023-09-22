package com.yujung.boardback.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yujung.boardback.dto.request.auth.SignUpRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity {
  @Id
  private String email;
  private String password;
  private String nickname;
  private String tellNumber;
  private String address;
  private String addressDetail;
  private boolean agreedPersonal;
  private String profileImageUrl;

  public UserEntity(SignUpRequestDto dto) {
    this.email = dto.getEmail();
    this.password = dto.getPassword();
    this.nickname = dto.getNickname();
    this.tellNumber = dto.getTellNumber();
    this.address = dto.getAddress();
    this.addressDetail = dto.getAddressDetail();
    this.agreedPersonal = dto.getAgreedPersonal();
  }
}
