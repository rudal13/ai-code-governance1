package com.example.boardbad.controller;

import com.example.boardbad.dao.BoardDao;
import com.example.boardbad.service.BoardMasterManager;
import com.example.boardbad.vo.BoardMasterVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 마스터 API.
 *
 * [의도적 표준 위반 목록]
 * - MVC-002 / MVC-001: bulkCreateBoards()에서 반복문을 통해 DAO를 직접 호출하며 대량 데이터 처리
 * - MVC-701: Controller 메서드에 @Transactional 적용 (트랜잭션은 Service 계층 책임)
 */
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardMasterManager boardMasterManager;

    @Autowired
    private BoardDao boardDao; // 위반: 정상 흐름과 별개로 아래 메서드에서 직접 사용됨

    @GetMapping
    public List<BoardMasterVO> getBoardList() {
        return boardMasterManager.getBoardList();
    }

    @GetMapping("/{boardId}")
    public BoardMasterVO getBoard(@PathVariable String boardId) {
        return boardMasterManager.getBoard(boardId);
    }

    @PostMapping
    public BoardMasterVO createBoard(@RequestBody BoardMasterVO boardMasterVO, HttpServletRequest request) {
        return boardMasterManager.createBoard(boardMasterVO, resolveUser(request), request.getRemoteAddr());
    }

    /**
     * 게시판 일괄 등록.
     * [의도적 표준 위반 - MVC-002 / MVC-001]
     * Controller에서 반복문을 사용해 대량 데이터를 처리하며, DAO를 직접 호출한다.
     * [의도적 표준 위반 - MVC-701]
     * 트랜잭션 제어를 Service가 아닌 Controller에서 직접 수행한다.
     */
    @PostMapping("/bulk")
    @Transactional
    public List<BoardMasterVO> bulkCreateBoards(@RequestBody List<BoardMasterVO> boardList, HttpServletRequest request) {
        for (BoardMasterVO boardMasterVO : boardList) {
            boardMasterVO.setBoardId("BRD-" + System.currentTimeMillis());
            boardDao.insertBoard(boardMasterVO); // 위반: Controller → DAO 직접 호출
        }
        return boardList;
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }
}
