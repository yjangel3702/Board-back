package com.yujung.boardback.service;

import org.springframework.http.ResponseEntity;

import com.yujung.boardback.dto.request.board.PostBoardRequestDto;
import com.yujung.boardback.dto.request.board.PostCommentRequestDto;
import com.yujung.boardback.dto.response.board.GetBoardResponseDto;
import com.yujung.boardback.dto.response.board.GetCommentListResponseDto;
import com.yujung.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.yujung.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.yujung.boardback.dto.response.board.PostBoardResponseDto;
import com.yujung.boardback.dto.response.board.PostCommentResponseDto;
import com.yujung.boardback.dto.response.board.PutFavoriteResponseDto;

public interface BoardService {
  
  ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
  ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

  ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
  ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);

  ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
  
  ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();

  ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

}
