package com.yujung.boardback.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  
  String upload(MultipartFile file);

}