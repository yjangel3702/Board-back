package com.yujung.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yujung.boardback.entity.BoardEntity;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

  boolean existsByBoardNumber(Integer boardNumber);

  BoardEntity findByBoardNumber(Integer boardNumber);
  
}
