package com.example.board.vo;

import java.time.LocalDateTime;

/**
 * 회원(TB_MBR) VO.
 * 비밀번호(PWD)는 응답 객체에 포함되지 않도록 별도 필드로 관리하며,
 * 외부에는 getter를 제공하지 않는다. (MVC-802 민감정보 처리)
 */
public class MemberVO {

    private String mbrId;
    private String email;
    private String nm;
    private String nick;
    private String telNo;
    private String statCd;

    private LocalDateTime regDttm;
    private String regUser;
    private String regIp;
    private LocalDateTime updDttm;
    private String updUser;
    private String updIp;
    private String delYn;
    private String useYn;

    public String getMbrId() {
        return mbrId;
    }

    public void setMbrId(String mbrId) {
        this.mbrId = mbrId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getStatCd() {
        return statCd;
    }

    public void setStatCd(String statCd) {
        this.statCd = statCd;
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
