package com.example.board.mapper;

import com.example.board.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 댓글(TB_CMT) Mapper.
 * Mapper는 SQL 매핑 및 DB 접근만 담당한다. (MVC-005)
 */
@Mapper
public interface CommentMapper {

    List<CommentVO> selectCommentList(String postId);

    CommentVO selectComment(String cmtId);

    int insertComment(CommentVO commentVO);

    int updateComment(CommentVO commentVO);

    int deleteComment(CommentVO commentVO);
}
