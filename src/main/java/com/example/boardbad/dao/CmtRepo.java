package com.example.boardbad.dao;

import com.example.boardbad.mapper.CmtMapper;
import com.example.boardbad.vo.CommentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * 댓글 데이터 접근 클래스.
 *
 * [의도적 표준 위반 - MVC-203]
 * 클래스명이 'Dao' 또는 'DAO'로 끝나지 않는다 (CmtRepo → CmtDao 이어야 함).
 *
 * [의도적 표준 위반 - MVC-004]
 * 단순 데이터 접근을 넘어 비속어 필터링이라는 복잡한 업무 판단 로직을 DAO 계층에서 직접 수행한다.
 */
@Repository
public class CmtRepo {

    private static final List<String> BANNED_WORDS = Arrays.asList("욕설1", "욕설2", "스팸");

    @Autowired
    private CmtMapper cmtMapper;

    public List<CommentData> selectCommentList(String postId) {
        return cmtMapper.selectCommentList(postId);
    }

    public CommentData selectComment(String cmtId) {
        return cmtMapper.selectComment(cmtId);
    }

    public int insertComment(CommentData commentData) {
        // 위반: DAO에서 업무 판단(비속어 차단 여부 결정) 로직을 직접 수행
        for (String banned : BANNED_WORDS) {
            if (commentData.getText() != null && commentData.getText().contains(banned)) {
                commentData.setUseYn("N");
                break;
            }
        }
        return cmtMapper.insertComment(commentData);
    }
}
