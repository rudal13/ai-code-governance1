package com.example.board.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 게시글(TB_POST) VO.
 */
public class PostVO {

    private String postId;

    @NotBlank(message = "게시판 식별자는 필수입니다.")
    private String boardId;

    private String mbrId;

    /** 작성자 이름 (TB_MBR 조인 결과, 조회 응답 전용 필드) */
    private String mbrNm;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private Integer viewCnt;
    private Integer rcmdCnt;

    @NotNull(message = "공지 여부는 필수입니다.")
    private String noticeYn;

    private String pinYn;
    private String pubYn;

    private LocalDateTime regDttm;
    private String regUser;
    private String regIp;
    private LocalDateTime updDttm;
    private String updUser;
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

    public String getMbrNm() {
        return mbrNm;
    }

    public void setMbrNm(String mbrNm) {
        this.mbrNm = mbrNm;
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

    public Integer getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Integer viewCnt) {
        this.viewCnt = viewCnt;
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
