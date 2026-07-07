package com.example.boardbad.dao;

import com.example.boardbad.mapper.BoardMapper;
import com.example.boardbad.vo.BoardMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardMasterVO> selectBoardList() {
        return boardMapper.selectBoardList();
    }

    public BoardMasterVO selectBoard(String boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public int insertBoard(BoardMasterVO boardMasterVO) {
        return boardMapper.insertBoard(boardMasterVO);
    }
}
