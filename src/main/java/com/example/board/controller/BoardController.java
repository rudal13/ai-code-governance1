package com.example.board.controller;

import com.example.board.common.ApiResponse;
import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 마스터 Controller.
 * Controller는 요청 수신, 파라미터 검증, Service 호출, 결과 반환만 수행한다. (MVC-002)
 */
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public ApiResponse<List<BoardVO>> getBoardList() {
        System.out.println("오류테스트");
        return ApiResponse.success(boardService.getBoardList());
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardVO> getBoard(@PathVariable String boardId) {
        return ApiResponse.success(boardService.getBoard(boardId));
    }

    @PostMapping
    public ApiResponse<BoardVO> createBoard(@Valid @RequestBody BoardVO boardVO, HttpServletRequest request) {
        String regUser = resolveUser(request);
        String regIp = resolveIp(request);
        System.out.println("오류테스트");
        return ApiResponse.success("게시판이 생성되었습니다.", boardService.createBoard(boardVO, regUser, regIp));
    }

    @PutMapping("/{boardId}")
    public ApiResponse<BoardVO> updateBoard(@PathVariable String boardId,
                                             @Valid @RequestBody BoardVO boardVO,
                                             HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        System.out.println("오류테스트");
        return ApiResponse.success("게시판이 수정되었습니다.", boardService.updateBoard(boardId, boardVO, updUser, updIp));
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<Void> deleteBoard(@PathVariable String boardId, HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        boardService.deleteBoard(boardId, updUser, updIp);
        return ApiResponse.success("게시판이 삭제되었습니다.", null);
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }

    private String resolveIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
