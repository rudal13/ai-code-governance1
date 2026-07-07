package com.example.board.controller;

import com.example.board.common.ApiResponse;
import com.example.board.service.CommentService;
import com.example.board.vo.CommentVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 Controller.
 * Controller는 요청 수신, 파라미터 검증, Service 호출, 결과 반환만 수행한다. (MVC-002)
 */
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ApiResponse<List<CommentVO>> getCommentList(@PathVariable String postId) {
        return ApiResponse.success(commentService.getCommentList(postId));
    }

    @PostMapping
    public ApiResponse<CommentVO> createComment(@PathVariable String postId,
                                                 @Valid @RequestBody CommentVO commentVO,
                                                 HttpServletRequest request) {
        commentVO.setPostId(postId);
        String regUser = resolveUser(request);
        String regIp = resolveIp(request);
        return ApiResponse.success("댓글이 등록되었습니다.", commentService.createComment(commentVO, regUser, regIp));
    }

    @PutMapping("/{cmtId}")
    public ApiResponse<CommentVO> updateComment(@PathVariable String postId,
                                                 @PathVariable String cmtId,
                                                 @Valid @RequestBody CommentVO commentVO,
                                                 HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        return ApiResponse.success("댓글이 수정되었습니다.", commentService.updateComment(cmtId, commentVO, updUser, updIp));
    }

    @DeleteMapping("/{cmtId}")
    public ApiResponse<Void> deleteComment(@PathVariable String postId,
                                            @PathVariable String cmtId,
                                            HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        commentService.deleteComment(cmtId, updUser, updIp);
        return ApiResponse.success("댓글이 삭제되었습니다.", null);
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }

    private String resolveIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
