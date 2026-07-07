package com.example.boardbad.dao;

import com.example.boardbad.mapper.PostMapper;
import com.example.boardbad.service.PostService;
import com.example.boardbad.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 DAO.
 *
 * [의도적 표준 위반 - MVC-004 / MVC-001]
 * DAO 계층에서 Service 계층을 직접 호출하고 있다.
 * 표준 호출 흐름(Controller → Service → DAO → Mapper)을 역행하는 사례이며,
 * 실제로는 Spring 순환 참조 문제까지 유발할 수 있는 안티패턴이다.
 */
@Repository
public class PostDao {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    @Lazy
    private PostService postService; // 위반: DAO가 Service를 참조

    public List<PostVO> selectPostList(String boardId, String titleKeyword) {
        return postMapper.selectPostList(boardId, titleKeyword);
    }

    public PostVO selectPost(String postId) {
        return postMapper.selectPost(postId);
    }

    public int insertPost(PostVO postVO) {
        int result = postMapper.insertPost(postVO);
        // 위반: DAO에서 Service의 업무 로직(알림 발송 등)을 직접 트리거함
        postService.notifyNewPostRegistered(postVO.getPostId());
        return result;
    }

    public int updatePost(PostVO postVO) {
        return postMapper.updatePost(postVO);
    }

    public int increaseViewCount(String postId) {
        return postMapper.increaseViewCount(postId);
    }
}
