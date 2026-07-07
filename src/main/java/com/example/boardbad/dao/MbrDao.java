package com.example.boardbad.dao;

import com.example.boardbad.mapper.MbrMapper;
import com.example.boardbad.vo.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회원 DAO.
 * (참고) 본 DAO는 정상적으로 작성되었으나, MbrCtrl(Controller)에서
 * 본 DAO를 거치지 않고 MbrMapper를 직접 호출하는 위반 사례가 존재한다.
 */
@Repository
public class MbrDao {

    @Autowired
    private MbrMapper mbrMapper;

    public List<MemberInfo> selectMemberList(String sortColumn) {
        return mbrMapper.selectMemberList(sortColumn);
    }

    public MemberInfo selectMember(String mbrKey) {
        return mbrMapper.selectMember(mbrKey);
    }

    public int insertMember(MemberInfo memberInfo) {
        return mbrMapper.insertMember(memberInfo);
    }
}
