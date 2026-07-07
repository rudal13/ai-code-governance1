package com.example.board.mapper;

import com.example.board.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 첨부파일(TB_FILE) Mapper.
 * Mapper는 SQL 매핑 및 DB 접근만 담당한다. (MVC-005)
 */
@Mapper
public interface FileMapper {

    List<FileVO> selectFileList(String postId);

    FileVO selectFile(String fileId);

    int insertFile(FileVO fileVO);

    int deleteFile(FileVO fileVO);
}
