package com.yujung.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yujung.boardback.entity.CommentEntity;
import com.yujung.boardback.repository.resultSet.CommentListResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

  @Query(
    value = 
    "SELECT " +
      "U.nickname AS nickname, " +
      "U.profile_image_url AS profileImage, " +
      "C.contents AS content, " +
      "C.write_datetime AS writeDatetime " +
    "FROM comment AS C " +
    "INNER JOIN user AS U " +
    "ON C.user_email = U.email " +
    "WHERE C.board_number = ?1 " +
    "ORDER BY C.write_datetime ",
    nativeQuery = true
  )
  List<CommentListResultSet> findByCommentList(Integer boardNumber);
}
