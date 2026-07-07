package com.example.board.service;

import com.example.board.common.PageRequestVO;
import com.example.board.vo.PostVO;

import java.util.List;
import java.util.Map;

/**
 * 게시글 Service.
 * Service는 업무 로직과 트랜잭션 처리를 담당한다. (MVC-003)
 */
public interface PostService {

    Map<String, Object> getPostList(String boardId, PageRequestVO pageRequestVO);

    PostVO getPost(String postId);

    PostVO createPost(PostVO postVO, String regUser, String regIp);

    PostVO updatePost(String postId, PostVO postVO, String updUser, String updIp);

    void deletePost(String postId, String updUser, String updIp);
}
