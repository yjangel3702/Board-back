package com.yujung.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yujung.boardback.dto.request.board.PatchBoardRequestDto;
import com.yujung.boardback.dto.request.board.PostBoardRequestDto;
import com.yujung.boardback.dto.request.board.PostCommentRequestDto;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.board.GetBoardResponseDto;
import com.yujung.boardback.dto.response.board.GetCommentListResponseDto;
import com.yujung.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.yujung.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.yujung.boardback.dto.response.board.PatchBoardResponseDto;
import com.yujung.boardback.dto.response.board.PostBoardResponseDto;
import com.yujung.boardback.dto.response.board.PostCommentResponseDto;
import com.yujung.boardback.dto.response.board.PutFavoriteResponseDto;
import com.yujung.boardback.entity.BoardEntity;
import com.yujung.boardback.entity.BoardImageEntity;
import com.yujung.boardback.entity.BoardViewEntity;
import com.yujung.boardback.entity.CommentEntity;
import com.yujung.boardback.entity.FavoriteEntity;
import com.yujung.boardback.entity.UserEntity;
import com.yujung.boardback.repository.BoardImageRepository;
import com.yujung.boardback.repository.BoardRepository;
import com.yujung.boardback.repository.BoardViewRepository;
import com.yujung.boardback.repository.CommentRepository;
import com.yujung.boardback.repository.FavoriteRepository;
import com.yujung.boardback.repository.UserRepository;
import com.yujung.boardback.repository.resultSet.CommentListResultSet;
import com.yujung.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService{
  
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final FavoriteRepository favoriteRepository;
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
  public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {
    
    try {

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return PostCommentResponseDto.notExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PostCommentResponseDto.notExistUser();

      CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
      commentRepository.save(commentEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PostCommentResponseDto.success();
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

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return GetFavoriteListResponseDto.notExistBoard();

      userEntities = userRepository.findByBoardFavorite(boardNumber);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetFavoriteListResponseDto.success(userEntities);

  }

  @Override
  public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
    
    List<CommentListResultSet> resultSets = new ArrayList<>();

    try {

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return GetCommentListResponseDto.notExistBoard();

      resultSets = commentRepository.findByCommentList(boardNumber);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetCommentListResponseDto.success(resultSets);
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

  @Override
  public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
    
    try {

      boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existedBoard) return PutFavoriteResponseDto.notExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PutFavoriteResponseDto.notExistUser();

      boolean isFavorite = favoriteRepository.existsByUserEmailAndBoardNumber(email, boardNumber);
      
      FavoriteEntity favoriteEntity = new FavoriteEntity(email, boardNumber);

      if (isFavorite) favoriteRepository.delete(favoriteEntity);
      else favoriteRepository.save(favoriteEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PutFavoriteResponseDto.success();
  }

  @Override
  public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {
    
    try {

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PatchBoardResponseDto.notExistUser();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PatchBoardResponseDto.notExistBoard();

      boolean equalWriter = boardEntity.getWriterEmail().equals(email);
      if (!equalWriter) return PatchBoardResponseDto.noPermission();

      boardEntity.patch(dto);
      boardRepository.save(boardEntity);

      List<String> boardImageList = dto.getBoardImageList();

      boardImageRepository.deleteByBoardNumber(boardNumber); // 원래 있던거 다 지우고 밑 코드로 다시 만듦

      List<BoardImageEntity> boardImageEntities = new ArrayList<>();
      for (String boardImage: boardImageList) {
        BoardImageEntity boardImageEntity = new BoardImageEntity(boardNumber, boardImage);
        boardImageEntities.add(boardImageEntity);
      }
      boardImageRepository.saveAll(boardImageEntities);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return PatchBoardResponseDto.success();
  }



}
