package com.example.board.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * 첨부파일(TB_FILE) VO.
 */
public class FileVO {

    private String fileId;

    @NotBlank(message = "게시글 식별자는 필수입니다.")
    private String postId;

    @NotBlank(message = "파일명은 필수입니다.")
    private String fileNm;

    @NotBlank(message = "파일 경로는 필수입니다.")
    private String path;

    @NotNull(message = "파일 크기는 필수입니다.")
    private Integer size;

    @NotBlank(message = "확장자는 필수입니다.")
    private String ext;

    private Integer sortSeq;

    private LocalDateTime regDttm;
    private String regUser;
    private String regIp;
    private LocalDateTime updDttm;
    private String updUser;
    private String updIp;
    private String delYn;
    private String useYn;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Integer getSortSeq() {
        return sortSeq;
    }

    public void setSortSeq(Integer sortSeq) {
        this.sortSeq = sortSeq;
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
