package com.example.boardbad.mapper;

import com.example.boardbad.vo.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MbrMapper {

    List<MemberInfo> selectMemberList(@Param("sortColumn") String sortColumn);

    MemberInfo selectMember(String mbrKey);

    int insertMember(MemberInfo memberInfo);
}
