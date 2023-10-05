package com.yujung.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yujung.boardback.dto.request.board.PostBoardRequestDto;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.board.GetBoardResponseDto;
import com.yujung.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.yujung.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.yujung.boardback.dto.response.board.PostBoardResponseDto;
import com.yujung.boardback.entity.BoardEntity;
import com.yujung.boardback.entity.BoardImageEntity;
import com.yujung.boardback.entity.BoardViewEntity;
import com.yujung.boardback.entity.UserEntity;
import com.yujung.boardback.repository.BoardImageRepository;
import com.yujung.boardback.repository.BoardRepository;
import com.yujung.boardback.repository.BoardViewRepository;
import com.yujung.boardback.repository.UserRepository;
import com.yujung.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService{
  
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final BoardViewRepository boardViewRepository;
  private final BoardImageRepository boardImageRepository;

  @Override
  public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

    try {

        boolean existedUser = userRepository.existsByEmail(email);
        if (!existedUser) return PostBoardResponseDto.notExistUser();

        BoardEntity boardEntity = new BoardEntity(dto, email);
        boardRepository.save(boardEntity);

        List<String> boardImageList = dto.getBoardImageList();
        Integer boardNumber = boardEntity.getBoardNumber();

        List<BoardImageEntity> boardImageEntities = new ArrayList<>();
        for (String boardImage: boardImageList) {
          BoardImageEntity boardImageEntity = new BoardImageEntity(boardNumber, boardImage);
          boardImageEntities.add(boardImageEntity);
        }

        boardImageRepository.saveAll(boardImageEntities);

    } catch(Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PostBoardResponseDto.success();
    
  }

  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

    BoardViewEntity boardViewEntity = null;
    List<BoardImageEntity> boardImageEntities = new ArrayList<>();
    
    try {

      boardViewEntity = boardViewRepository.findByBoardNumber(boardNumber);
      if (boardViewEntity == null) return GetBoardResponseDto.notExistBoard();

      boardImageEntities = boardImageRepository.findByBoardNumber(boardNumber);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetBoardResponseDto.success(boardViewEntity, boardImageEntities);

  }

  @Override
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

    List<UserEntity> userEntities = new ArrayList<>();

    try {

      boolean hasBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!hasBoard) return GetFavoriteListResponseDto.notExistBoard();

      userEntities = userRepository.findByBoardFavorite(boardNumber);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetFavoriteListResponseDto.success(userEntities);

  }

  @Override
  public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

    List<BoardViewEntity> boardViewEntities = new ArrayList<>();

    try {

      boardViewEntities = boardViewRepository.findByOrderByWriteDatetimeDesc();

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetLatestBoardListResponseDto.success(boardViewEntities);
  }



}
