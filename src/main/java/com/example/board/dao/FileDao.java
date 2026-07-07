package com.example.board.dao;

import com.example.board.mapper.FileMapper;
import com.example.board.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 첨부파일 DAO.
 * DAO는 Mapper 호출 및 데이터 접근 요청 처리를 담당한다. (MVC-004)
 */
@Repository
public class FileDao {

    @Autowired
    private FileMapper fileMapper;

    public List<FileVO> selectFileList(String postId) {
        return fileMapper.selectFileList(postId);
    }

    public FileVO selectFile(String fileId) {
        return fileMapper.selectFile(fileId);
    }

    public int insertFile(FileVO fileVO) {
        return fileMapper.insertFile(fileVO);
    }

    public int deleteFile(FileVO fileVO) {
        return fileMapper.deleteFile(fileVO);
    }
}
