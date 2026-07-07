package com.example.boardbad.controller;

import com.example.boardbad.dao.MbrDao;
import com.example.boardbad.mapper.MbrMapper;
import com.example.boardbad.vo.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원 관련 API.
 *
 * [의도적 표준 위반 목록]
 * - MVC-201: 클래스명이 'Controller'로 끝나지 않음 (MbrCtrl → MemberController 이어야 함)
 * - MVC-001 / High: Controller에서 DAO(MbrDao)와 Mapper(MbrMapper)를 직접 호출
 * - MVC-401: 주입 대상 계층이 표준 호출 구조를 위반 (Controller → DAO/Mapper 직접 주입)
 * - MVC-501: 응답을 ApiResponse가 아닌 Map으로 직접 반환
 * - MVC-601: System.out.println 사용
 * - MVC-604: catch 블록에서 예외를 빈 채로 무시 (Exception Swallowing)
 */
@RestController
@RequestMapping("/api/members")
public class MbrCtrl {

    @Autowired
    private MbrDao mbrDao; // 위반: Controller가 DAO를 직접 주입

    @Autowired
    private MbrMapper mbrMapper; // 위반: Controller가 Mapper를 직접 주입

    @GetMapping
    public Map<String, Object> getMemberList(@RequestParam(defaultValue = "CREATE_DTTM") String sort) {
        System.out.println("회원 목록 조회 요청, sort=" + sort); // 위반: println 사용

        // 위반: Controller에서 DAO를 직접 호출
        List<MemberInfo> list = mbrDao.selectMemberList(sort);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result; // 위반: 공통 ApiResponse 미사용, Map 직접 반환
    }

    @GetMapping("/{mbrKey}")
    public MemberInfo getMember(@PathVariable String mbrKey) {
        try {
            // 위반: Controller에서 Mapper를 직접 호출 (DAO/Service 모두 우회)
            return mbrMapper.selectMember(mbrKey);
        } catch (Exception e) {
            // 위반: 예외를 잡고도 아무 처리 없이 무시함
            return null;
        }
    }

    @PostMapping
    public MemberInfo createMember(@RequestBody MemberInfo memberInfo) {
        // 위반: Controller에서 DAO를 직접 호출하여 등록 처리까지 수행 (Service 계층 완전 우회)
        mbrDao.insertMember(memberInfo);
        return memberInfo;
    }
}
