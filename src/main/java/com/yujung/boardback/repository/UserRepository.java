package com.yujung.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yujung.boardback.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);
  boolean existsByTellNumber(String tellNumber);

  UserEntity findByEmail(String email);


  
}
