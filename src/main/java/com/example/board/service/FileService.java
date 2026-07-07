package com.example.board.service;

import com.example.board.vo.FileVO;

import java.util.List;

/**
 * 첨부파일 Service.
 * Service는 업무 로직과 트랜잭션 처리를 담당한다. (MVC-003)
 */
public interface FileService {

    List<FileVO> getFileList(String postId);

    FileVO registerFile(FileVO fileVO, String regUser, String regIp);

    void removeFile(String fileId, String updUser, String updIp);
}
