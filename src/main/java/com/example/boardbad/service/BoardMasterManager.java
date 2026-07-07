package com.example.boardbad.service;

import com.example.boardbad.dao.BoardDao;
import com.example.boardbad.mapper.BoardMapper;
import com.example.boardbad.vo.BoardMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 게시판 마스터 업무 처리 클래스.
 *
 * [의도적 표준 위반 - MVC-202]
 * 클래스명이 'Service'로 끝나지 않는다 (BoardMasterManager → BoardMasterService 이어야 함).
 *
 * [의도적 표준 위반 - MVC-001 / MVC-003 / High]
 * Service 계층임에도 BoardDao를 거치지 않고 BoardMapper를 직접 호출한다.
 */
@Service
public class BoardMasterManager {

    @Autowired
    private BoardDao boardDao; // 주입은 되어 있으나 아래에서 사용하지 않고 Mapper를 직접 사용함

    @Autowired
    private BoardMapper boardMapper; // 위반: Service에서 Mapper 직접 주입/호출

    public List<BoardMasterVO> getBoardList() {
        // 위반: DAO를 호출하지 않고 Mapper를 바로 호출
        return boardMapper.selectBoardList();
    }

    public BoardMasterVO getBoard(String boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public BoardMasterVO createBoard(BoardMasterVO boardMasterVO, String regUser, String regIp) {
        boardMasterVO.setBoardId("BRD-" + UUID.randomUUID().toString().substring(0, 8));
        boardMasterVO.setRegDttm(LocalDateTime.now());
        boardMasterVO.setRegUser(regUser);
        boardMasterVO.setRegIp(regIp);
        boardMasterVO.setUpdDttm(LocalDateTime.now());
        boardMasterVO.setUpdUser(regUser);
        boardMasterVO.setUpdIp(regIp);

        // 위반: System.out.println으로 처리 결과 출력 (MVC-601)
        System.out.println("board created : " + boardMasterVO.getBoardId());

        boardMapper.insertBoard(boardMasterVO);
        return boardMasterVO;
    }
}
