package com.yujung.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yujung.boardback.entity.FavoriteEntity;
import com.yujung.boardback.entity.primaryKey.FavoritePk;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk> {

  boolean existsByUserEmailAndBoardNumber(String userEmail, Integer boardNumber);
  
}
