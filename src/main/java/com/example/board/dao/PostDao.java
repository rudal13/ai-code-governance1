package com.example.board.dao;

import com.example.board.common.PageRequestVO;
import com.example.board.mapper.PostMapper;
import com.example.board.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 DAO.
 * DAO는 Mapper 호출 및 데이터 접근 요청 처리를 담당한다. (MVC-004)
 */
@Repository
public class PostDao {

    @Autowired
    private PostMapper postMapper;

    public List<PostVO> selectPostList(String boardId, PageRequestVO pageRequestVO) {
        return postMapper.selectPostList(boardId, pageRequestVO);
    }

    public int selectPostCount(String boardId) {
        return postMapper.selectPostCount(boardId);
    }

    public PostVO selectPost(String postId) {
        return postMapper.selectPost(postId);
    }

    public int insertPost(PostVO postVO) {
        return postMapper.insertPost(postVO);
    }

    public int updatePost(PostVO postVO) {
        return postMapper.updatePost(postVO);
    }

    public int deletePost(PostVO postVO) {
        return postMapper.deletePost(postVO);
    }

    public int increaseViewCnt(String postId) {
        return postMapper.increaseViewCnt(postId);
    }
}
