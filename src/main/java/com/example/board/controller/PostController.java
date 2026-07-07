package com.example.board.controller;

import com.example.board.common.ApiResponse;
import com.example.board.common.PageRequestVO;
import com.example.board.service.PostService;
import com.example.board.vo.PostVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 게시글 Controller.
 * Controller는 요청 수신, 파라미터 검증, Service 호출, 결과 반환만 수행한다. (MVC-002)
 */
@RestController
@RequestMapping("/api/boards/{boardId}/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ApiResponse<Map<String, Object>> getPostList(@PathVariable String boardId,
                                                          @ModelAttribute PageRequestVO pageRequestVO) {
        return ApiResponse.success(postService.getPostList(boardId, pageRequestVO));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostVO> getPost(@PathVariable String boardId, @PathVariable String postId) {
        return ApiResponse.success(postService.getPost(postId));
    }

    @PostMapping
    public ApiResponse<PostVO> createPost(@PathVariable String boardId,
                                           @Valid @RequestBody PostVO postVO,
                                           HttpServletRequest request) {
        postVO.setBoardId(boardId);
        String regUser = resolveUser(request);
        String regIp = resolveIp(request);
        return ApiResponse.success("게시글이 등록되었습니다.", postService.createPost(postVO, regUser, regIp));
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostVO> updatePost(@PathVariable String boardId,
                                           @PathVariable String postId,
                                           @Valid @RequestBody PostVO postVO,
                                           HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        return ApiResponse.success("게시글이 수정되었습니다.", postService.updatePost(postId, postVO, updUser, updIp));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable String boardId,
                                         @PathVariable String postId,
                                         HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        postService.deletePost(postId, updUser, updIp);
        return ApiResponse.success("게시글이 삭제되었습니다.", null);
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }

    private String resolveIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
