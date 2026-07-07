package com.example.boardbad.vo;

import java.time.LocalDateTime;

/**
 * 회원(TB_MEMBERS) 데이터 객체.
 *
 * [의도적 표준 위반]
 * - MVC-205 위반: 클래스명이 'VO'로 끝나지 않음 (MemberInfo → MemberVO 이어야 함)
 */
public class MemberInfo {

    private String mbrKey;   // TB_MEMBERS.MBR_KEY (식별자 Suffix 위반 컬럼 그대로 매핑)
    private String email;
    private String pwd;      // [위반 후보] 민감정보를 VO에 그대로 보관 (MVC-802 검토 대상)
    private String nm;
    private String nick;
    private String telNo;
    private String statCode; // TB_MEMBERS.STAT_CODE

    private LocalDateTime createDttm; // TB_MEMBERS.CREATE_DTTM
    private String regUser;
    private String regIp;
    private LocalDateTime updDate;    // TB_MEMBERS.UPD_DATE
    private String updUser;
    private String updIp;
    private String delFlag;           // TB_MEMBERS.DEL_FLAG
    private String useYn;

    public String getMbrKey() {
        return mbrKey;
    }

    public void setMbrKey(String mbrKey) {
        this.mbrKey = mbrKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getStatCode() {
        return statCode;
    }

    public void setStatCode(String statCode) {
        this.statCode = statCode;
    }

    public LocalDateTime getCreateDttm() {
        return createDttm;
    }

    public void setCreateDttm(LocalDateTime createDttm) {
        this.createDttm = createDttm;
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

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
