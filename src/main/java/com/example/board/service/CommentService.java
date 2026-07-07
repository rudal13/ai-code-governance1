package com.example.board.service;

import com.example.board.vo.CommentVO;

import java.util.List;

/**
 * 댓글 Service.
 * Service는 업무 로직과 트랜잭션 처리를 담당한다. (MVC-003)
 */
public interface CommentService {

    List<CommentVO> getCommentList(String postId);

    CommentVO createComment(CommentVO commentVO, String regUser, String regIp);

    CommentVO updateComment(String cmtId, CommentVO commentVO, String updUser, String updIp);

    void deleteComment(String cmtId, String updUser, String updIp);
}
