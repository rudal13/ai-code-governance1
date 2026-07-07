package com.example.board.dao;

import com.example.board.mapper.BoardMapper;
import com.example.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 마스터 DAO.
 * DAO는 Mapper 호출 및 데이터 접근 요청 처리를 담당한다. (MVC-004)
 */
@Repository
public class BoardDao {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardVO> selectBoardList() {
        return boardMapper.selectBoardList();
    }

    public BoardVO selectBoard(String boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public int insertBoard(BoardVO boardVO) {
        return boardMapper.insertBoard(boardVO);
    }

    public int updateBoard(BoardVO boardVO) {
        return boardMapper.updateBoard(boardVO);
    }

    public int deleteBoard(BoardVO boardVO) {
        return boardMapper.deleteBoard(boardVO);
    }
}
