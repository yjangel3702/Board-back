package com.yujung.boardback.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yujung.boardback.common.object.BoardListItem;
import com.yujung.boardback.dto.response.ResponseCode;
import com.yujung.boardback.dto.response.ResponseDto;
import com.yujung.boardback.dto.response.ResponseMessage;
import com.yujung.boardback.entity.BoardViewEntity;

import lombok.Getter;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {
  
  private List<BoardListItem> latestList;

  private GetLatestBoardListResponseDto(String code, String message, List<BoardViewEntity> boardViewEntities) {
    super(code, message);
    this.latestList = BoardListItem.getList(boardViewEntities);
  }

  public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardViewEntity> boardViewEntities) {
    GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, boardViewEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
