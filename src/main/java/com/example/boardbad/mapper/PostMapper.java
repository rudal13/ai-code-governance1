package com.example.boardbad.mapper;

import com.example.boardbad.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostVO> selectPostList(@Param("boardId") String boardId, @Param("titleKeyword") String titleKeyword);

    PostVO selectPost(String postId);

    int insertPost(PostVO postVO);

    int updatePost(PostVO postVO);

    int increaseViewCount(String postId);
}
