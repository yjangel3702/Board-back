package com.yujung.boardback.service.implement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yujung.boardback.dto.request.board.PatchBoardRequestDto;
import com.yujung.boardback.dto.request.board.PostBoardRequestDto;
import com.yujung.boardback.dto.request.board.PostCommentRequestDto;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.board.DeleteBoardResponseDto;
import com.yujung.boardback.dto.response.board.GetBoardResponseDto;
import com.yujung.boardback.dto.response.board.GetCommentListResponseDto;
import com.yujung.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.yujung.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.yujung.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.yujung.boardback.dto.response.board.GetUserBoardListResponseDto;
import com.yujung.boardback.dto.response.board.IncreaseViewCountResponseDto;
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
    
    System.out.println(dto.getContent());

    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PostCommentResponseDto.notExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PostCommentResponseDto.notExistUser();

      CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
      commentRepository.save(commentEntity);

      boardEntity.increaseCommentCount();
      boardRepository.save(boardEntity);

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
  public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {

    List<BoardViewEntity> boardViewEntities = new ArrayList<>();

    try {

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return GetUserBoardListResponseDto.notExistUser();

      boardViewEntities = boardViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetUserBoardListResponseDto.sucess(boardViewEntities);
  }

  @Override
  public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

      List<BoardViewEntity> boardViewEntities = new ArrayList<>();

      try {

          Date now = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String sevenDaysAgo = simpleDateFormat.format(now);

          boardViewEntities = boardViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDesc(sevenDaysAgo);

      } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
      }

      return GetTop3BoardListResponseDto.success(boardViewEntities);

  }

  @Override
  public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
    
    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return PutFavoriteResponseDto.notExistBoard();

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return PutFavoriteResponseDto.notExistUser();

      boolean isFavorite = favoriteRepository.existsByUserEmailAndBoardNumber(email, boardNumber);
      
      FavoriteEntity favoriteEntity = new FavoriteEntity(email, boardNumber);

      if (isFavorite) {
        favoriteRepository.delete(favoriteEntity);
        boardEntity.decreaseFavoriteCount();
      }
      else {
        favoriteRepository.save(favoriteEntity);
        boardEntity.increaseFavoriteCount();
      }

      boardRepository.save(boardEntity);

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

  @Override
  public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
    
    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return IncreaseViewCountResponseDto.notExistBoard();

      boardEntity.increaseViewCount();
      boardRepository.save(boardEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return IncreaseViewCountResponseDto.success();

  }

  @Override
  public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {

    try {

      boolean existedUser = userRepository.existsByEmail(email);
      if (!existedUser) return DeleteBoardResponseDto.notExistUser();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return DeleteBoardResponseDto.notExistBoard();
      
      boolean isWriter = boardEntity.getWriterEmail().equals(email);
      if (!isWriter) return DeleteBoardResponseDto.noPermission();

      commentRepository.deleteByBoardNumber(boardNumber);
      favoriteRepository.deleteByBoardNumber(boardNumber);
      boardImageRepository.deleteByBoardNumber(boardNumber);
      boardRepository.delete(boardEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return DeleteBoardResponseDto.success();
  }






}
