package com.example.boardbad.vo;

import java.time.LocalDateTime;

/**
 * 댓글(TB_POST_COMMENT_REPLY_HISTORY_DATA) 데이터 객체.
 *
 * [의도적 표준 위반]
 * - MVC-205 위반: 클래스명이 'VO'로 끝나지 않음 (CommentData → CommentVO 이어야 함)
 */
public class CommentData {

    private String cmtId;
    private String postId;
    private String mbrId;
    private String text; // TB_POST_COMMENT_REPLY_HISTORY_DATA.TEXT (용어집 미등록 단어 컬럼 그대로 매핑)

    private LocalDateTime regDttm;
    private String regUser;
    private String regIp;
    private LocalDateTime updDttm;
    private String updUser;
    private String updIp;
    private String delYn;
    private String useYn;

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMbrId() {
        return mbrId;
    }

    public void setMbrId(String mbrId) {
        this.mbrId = mbrId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(LocalDateTime regDttm) {
        this.regDttm = regDttm;
    }

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public LocalDateTime getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(LocalDateTime updDttm) {
        this.updDttm = updDttm;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdIp() {
        return updIp;
    }

    public void setUpdIp(String updIp) {
        this.updIp = updIp;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
