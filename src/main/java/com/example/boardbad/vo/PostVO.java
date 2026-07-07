package com.example.boardbad.vo;

import java.time.LocalDateTime;

/**
 * 게시글(TB_POST) VO.
 * TB_POST의 임의 축약 컬럼(R_DTTM, R_USER, U_DTTM, U_USER, VIEW_COUNT)을 그대로 매핑한다.
 */
public class PostVO {

    private String postId;
    private String boardId;
    private String mbrId;
    private String title;
    private String content;
    private Integer viewCount; // TB_POST.VIEW_COUNT (Suffix 규칙 위반 컬럼 그대로 매핑)
    private Integer rcmdCnt;
    private String noticeYn;
    private String pinYn;
    private String pubYn;

    private LocalDateTime rDttm;  // TB_POST.R_DTTM
    private String rUser;          // TB_POST.R_USER
    private String regIp;
    private LocalDateTime uDttm;  // TB_POST.U_DTTM
    private String uUser;          // TB_POST.U_USER
    private String updIp;
    private String delYn;
    private String useYn;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getMbrId() {
        return mbrId;
    }

    public void setMbrId(String mbrId) {
        this.mbrId = mbrId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getRcmdCnt() {
        return rcmdCnt;
    }

    public void setRcmdCnt(Integer rcmdCnt) {
        this.rcmdCnt = rcmdCnt;
    }

    public String getNoticeYn() {
        return noticeYn;
    }

    public void setNoticeYn(String noticeYn) {
        this.noticeYn = noticeYn;
    }

    public String getPinYn() {
        return pinYn;
    }

    public void setPinYn(String pinYn) {
        this.pinYn = pinYn;
    }

    public String getPubYn() {
        return pubYn;
    }

    public void setPubYn(String pubYn) {
        this.pubYn = pubYn;
    }

    public LocalDateTime getrDttm() {
        return rDttm;
    }

    public void setrDttm(LocalDateTime rDttm) {
        this.rDttm = rDttm;
    }

    public String getrUser() {
        return rUser;
    }

    public void setrUser(String rUser) {
        this.rUser = rUser;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public LocalDateTime getuDttm() {
        return uDttm;
    }

    public void setuDttm(LocalDateTime uDttm) {
        this.uDttm = uDttm;
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser;
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
