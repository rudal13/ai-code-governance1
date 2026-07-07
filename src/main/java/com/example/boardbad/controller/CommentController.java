package com.example.boardbad.controller;

import com.example.boardbad.mapper.CmtMapper;
import com.example.boardbad.vo.CommentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 댓글 API.
 *
 * [의도적 표준 위반 - MVC-001 / High, 즉시 REJECT 대상]
 * Service와 DAO 계층을 모두 건너뛰고 Controller가 Mapper를 직접 호출한다.
 * (표준 호출 구조 Controller → Service → DAO → Mapper를 가장 심각하게 위반하는 사례)
 */
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CmtMapper cmtMapper; // 위반: Controller가 Mapper를 직접 주입

    @GetMapping
    public List<CommentData> getCommentList(@PathVariable String postId) {
        // 위반: Controller → Mapper 직접 호출
        return cmtMapper.selectCommentList(postId);
    }

    @PostMapping
    public CommentData createComment(@PathVariable String postId, @RequestBody CommentData commentData) {
        commentData.setCmtId("CMT-" + UUID.randomUUID().toString().substring(0, 8));
        commentData.setPostId(postId);
        commentData.setRegDttm(LocalDateTime.now());
        commentData.setUpdDttm(LocalDateTime.now());

        // 위반: Controller → Mapper 직접 호출하여 등록까지 처리
        cmtMapper.insertComment(commentData);
        return commentData;
    }
}
