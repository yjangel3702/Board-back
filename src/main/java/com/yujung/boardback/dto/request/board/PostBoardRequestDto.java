package com.yujung.boardback.dto.request.board;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequestDto {
  
  @NotBlank
  private String title;

  @NotBlank
  private String content;

  @NonNull
  private List<String> boardImageList;

}
