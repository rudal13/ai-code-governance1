package com.example.boardbad.controller;

import com.example.boardbad.service.PostService;
import com.example.boardbad.vo.PostVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시글 API.
 *
 * [의도적 표준 위반 목록]
 * - MVC-501: ApiResponse 미사용, Map/객체 직접 반환
 * - MVC-602: e.printStackTrace() 사용
 * - MVC-603: 동일한 형태의 try-catch를 모든 메서드에서 반복 작성
 */
@RestController
@RequestMapping("/api/boards/{boardId}/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public Map<String, Object> getPostList(@PathVariable String boardId,
                                            @RequestParam(required = false) String titleKeyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<PostVO> list = postService.getPostList(boardId, titleKeyword);
            result.put("list", list);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace(); // 위반: MVC-602
            result.put("success", false);
        }
        return result; // 위반: MVC-501
    }

    @GetMapping("/{postId}")
    public Map<String, Object> getPost(@PathVariable String boardId, @PathVariable String postId) {
        Map<String, Object> result = new HashMap<>();
        try {
            PostVO postVO = postService.getPost(postId);
            result.put("data", postVO);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @PostMapping
    public Map<String, Object> createPost(@PathVariable String boardId,
                                           @RequestBody PostVO postVO,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        postVO.setBoardId(boardId);
        try {
            PostVO created = postService.createPost(postVO, resolveUser(request), request.getRemoteAddr());
            result.put("data", created);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @PutMapping("/{postId}")
    public Map<String, Object> updatePost(@PathVariable String boardId,
                                           @PathVariable String postId,
                                           @RequestBody PostVO postVO,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            PostVO updated = postService.updatePost(postId, postVO, resolveUser(request), request.getRemoteAddr());
            result.put("data", updated);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }
}
