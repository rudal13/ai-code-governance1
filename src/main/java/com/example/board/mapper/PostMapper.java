package com.example.board.mapper;

import com.example.board.common.PageRequestVO;
import com.example.board.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 게시글(TB_POST) Mapper.
 * Mapper는 SQL 매핑 및 DB 접근만 담당한다. (MVC-005)
 */
@Mapper
public interface PostMapper {

    List<PostVO> selectPostList(@Param("boardId") String boardId, @Param("page") PageRequestVO pageRequestVO);

    int selectPostCount(@Param("boardId") String boardId);

    PostVO selectPost(String postId);

    int insertPost(PostVO postVO);

    int updatePost(PostVO postVO);

    int deletePost(PostVO postVO);

    int increaseViewCnt(String postId);
}
