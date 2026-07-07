package com.example.boardbad.mapper;

import com.example.boardbad.vo.CommentData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmtMapper {

    List<CommentData> selectCommentList(String postId);

    CommentData selectComment(String cmtId);

    int insertComment(CommentData commentData);
}
