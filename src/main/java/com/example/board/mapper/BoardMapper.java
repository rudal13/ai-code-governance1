package com.example.board.mapper;

import com.example.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 게시판 마스터(TB_BOARD) Mapper.
 * Mapper는 SQL 매핑 및 DB 접근만 담당한다. (MVC-005)
 */
@Mapper
public interface BoardMapper {

    List<BoardVO> selectBoardList();

    BoardVO selectBoard(String boardId);

    int insertBoard(BoardVO boardVO);

    int updateBoard(BoardVO boardVO);

    int deleteBoard(BoardVO boardVO);
}
