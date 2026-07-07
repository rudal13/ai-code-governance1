package com.example.boardbad.mapper;

import com.example.boardbad.vo.BoardMasterVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardMasterVO> selectBoardList();

    BoardMasterVO selectBoard(String boardId);

    int insertBoard(BoardMasterVO boardMasterVO);
}
