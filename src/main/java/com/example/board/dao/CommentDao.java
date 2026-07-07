package com.example.board.dao;

import com.example.board.mapper.CommentMapper;
import com.example.board.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 DAO.
 * DAO는 Mapper 호출 및 데이터 접근 요청 처리를 담당한다. (MVC-004)
 */
@Repository
public class CommentDao {

    @Autowired
    private CommentMapper commentMapper;

    public List<CommentVO> selectCommentList(String postId) {
        return commentMapper.selectCommentList(postId);
    }

    public CommentVO selectComment(String cmtId) {
        return commentMapper.selectComment(cmtId);
    }

    public int insertComment(CommentVO commentVO) {
        return commentMapper.insertComment(commentVO);
    }

    public int updateComment(CommentVO commentVO) {
        return commentMapper.updateComment(commentVO);
    }

    public int deleteComment(CommentVO commentVO) {
        return commentMapper.deleteComment(commentVO);
    }
}
