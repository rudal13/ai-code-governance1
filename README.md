# 게시판 Back-end (표준 위반 포함 버전)

이 프로젝트는 `docs/java-governance.md`에 정의된 사내 Java MVC 표준을 **검증하기 위한
테스트용 프로젝트**로, 의도적으로 다양한 표준 위반 사례를 포함하고 있습니다.

- 위반 사례를 적용/검사할 표준 원본: `docs/java-governance.md`
- 사용한 DDL(다수의 명명 규칙 위반 포함): `src/main/resources/sql/board-tables_violate.sql`
- **위반 사항 전체 목록(정답지)**: `VIOLATIONS.md`

## 사용 목적

사내 Java 표준 검사 Agent를 개발/검증할 때, 이 프로젝트를 입력으로 주고 Agent가
`VIOLATIONS.md`에 정리된 위반 항목들을 빠짐없이 탐지하는지, 그리고 심각도에 따라
PASS/WARNING/REJECT 중 올바르게 REJECT로 판정하는지 확인하는 용도로 사용합니다.

## 디렉토리 구조

```text
board_v/
├── VIOLATIONS.md                 # 의도적으로 삽입한 위반 사항 전체 목록 (정답지)
├── docs/java-governance.md       # 검사 기준이 되는 표준 문서
├── pom.xml
└── src/main/
    ├── java/com/example/boardbad/
    │   ├── BoardBadApplication.java
    │   ├── controller/
    │   │   ├── MbrCtrl.java          # ❌ 명명 위반 + Controller→DAO/Mapper 직접 호출
    │   │   ├── BoardController.java  # ❌ 반복문 내 DAO 직접 호출 + Controller @Transactional
    │   │   ├── PostController.java   # ❌ Map 직접 반환 + 반복 try-catch + printStackTrace
    │   │   └── CommentController.java# ❌ Service/DAO 모두 우회, Mapper 직접 호출
    │   ├── service/
    │   │   ├── BoardMasterManager.java # ❌ 명명 위반 + Service→Mapper 직접 호출
    │   │   └── PostService.java        # ❌ 예외 삼킴 + printStackTrace + Transactional 누락
    │   ├── dao/
    │   │   ├── MbrDao.java     # 정상 작성(참고용, Controller에서 우회 호출됨)
    │   │   ├── BoardDao.java   # 정상 작성
    │   │   ├── PostDao.java    # ❌ DAO→Service 역참조
    │   │   └── CmtRepo.java    # ❌ 명명 위반 + 업무 판단 로직 포함
    │   ├── mapper/              # Mapper 인터페이스
    │   └── vo/
    │       ├── MemberInfo.java # ❌ VO 명명 위반, 비밀번호 필드 포함
    │       ├── BoardMasterVO.java
    │       ├── PostVO.java
    │       └── CommentData.java# ❌ VO 명명 위반
    └── resources/
        ├── mapper/
        │   ├── MbrMapper.xml   # ❌ ${ } 문자열 치환 (SQL Injection)
        │   ├── BoardMapper.xml
        │   ├── PostMapper.xml  # ❌ ${ } 문자열 치환 (SQL Injection)
        │   └── CmtMapper.xml
        └── sql/board-tables_violate.sql
```

## 주의

본 프로젝트는 운영 배포용이 아니며, 일부 클래스(`PostDao` ↔ `PostService` 간 상호 참조 등)는
실제 Spring 구동 시 순환 참조 경고/오류가 발생할 수 있습니다. 이는 "DAO가 Service를 호출하면
안 된다"는 표준 위반을 코드 수준에서 보여주기 위해 의도적으로 작성된 것입니다.
