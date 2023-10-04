package com.yujung.boardback.common.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListItem {
  private int boardNumber;
  private String title;
  private String content;
  private String boardTitleImage;
  private int viewCount;
  private int favoriteCount;
  private int commentCount;
  private String writeDatetime;
  private String writerEmail;
  private String writerNickname;
  private String writerProfileImage;
}
