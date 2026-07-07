package com.example.board.service.impl;

import com.example.board.common.IdGenerator;
import com.example.board.dao.BoardDao;
import com.example.board.exception.NotFoundException;
import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시판 마스터 Service 구현체. (MVC-003)
 */
@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Autowired
    private BoardDao boardDao;

    @Override
    @Transactional(readOnly = true)
    public List<BoardVO> getBoardList() {
        return boardDao.selectBoardList();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardVO getBoard(String boardId) {
        BoardVO boardVO = boardDao.selectBoard(boardId);
        if (boardVO == null) {
            throw new NotFoundException("게시판 정보를 찾을 수 없습니다. boardId=" + boardId);
        }
        return boardVO;
    }

    @Override
    @Transactional
    public BoardVO createBoard(BoardVO boardVO, String regUser, String regIp) {
        boardVO.setBoardId(IdGenerator.generate("BRD"));
        boardVO.setRegDttm(LocalDateTime.now());
        boardVO.setRegUser(regUser);
        boardVO.setRegIp(regIp);
        boardVO.setUpdDttm(LocalDateTime.now());
        boardVO.setUpdUser(regUser);
        boardVO.setUpdIp(regIp);

        boardDao.insertBoard(boardVO);
        logger.info("게시판이 생성되었습니다. boardId={}", boardVO.getBoardId());
        return boardVO;
    }

    @Override
    @Transactional
    public BoardVO updateBoard(String boardId, BoardVO boardVO, String updUser, String updIp) {
        BoardVO existing = getBoard(boardId);
        existing.setTitle(boardVO.getTitle());
        existing.setDesc(boardVO.getDesc());
        existing.setSortSeq(boardVO.getSortSeq());
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        boardDao.updateBoard(existing);
        logger.info("게시판이 수정되었습니다. boardId={}", boardId);
        return existing;
    }

    @Override
    @Transactional
    public void deleteBoard(String boardId, String updUser, String updIp) {
        BoardVO existing = getBoard(boardId);
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        boardDao.deleteBoard(existing);
        logger.info("게시판이 삭제(논리) 처리되었습니다. boardId={}", boardId);
    }
}
