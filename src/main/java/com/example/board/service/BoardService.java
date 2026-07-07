package com.example.board.service;

import com.example.board.vo.BoardVO;

import java.util.List;

/**
 * 게시판 마스터 Service.
 * Service는 업무 로직과 트랜잭션 처리를 담당한다. (MVC-003)
 */
public interface BoardService {

    List<BoardVO> getBoardList();

    BoardVO getBoard(String boardId);

    BoardVO createBoard(BoardVO boardVO, String regUser, String regIp);

    BoardVO updateBoard(String boardId, BoardVO boardVO, String updUser, String updIp);

    void deleteBoard(String boardId, String updUser, String updIp);
}
