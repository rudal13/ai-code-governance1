# 의도적 표준 위반 목록 (검사 Agent 테스트용 정답지)

본 프로젝트(`board_v` / patch명 `board.zip`)는 `docs/java-governance.md` 표준을 검사하는
Agent를 테스트하기 위해 **의도적으로** 다수의 표준 위반 사례를 포함하여 작성되었습니다.
실제 운영 코드로 사용하면 안 되며, 검사 Agent가 아래 위반들을 정확히 탐지하는지 검증하는
용도로만 사용하시기 바랍니다.

## 1. 테이블/컬럼 명명 위반 (board-tables_violate.sql 유래)

| 위반 항목 | 위치 | 내용 |
|---|---|---|
| 식별자 Suffix 위반 | TB_MEMBERS.MBR_KEY | `MBR_ID`가 아닌 `MBR_KEY` 사용 → VO/Mapper에도 그대로 반영(`mbrKey`) |
| Prefix 미준수 | BOARD_MASTER | 테이블 Prefix `TB_` 누락 → BoardMapper.xml에서 `FROM BOARD_MASTER` 그대로 사용 |
| 복합어 표준 미준수 | BOARD_MASTER.SORT_ORDER, TB_POST.VIEW_COUNT | `SORT_SEQ`, `VIEW_CNT` 대신 사용 → VO 필드도 `sortOrder`, `viewCount`로 그대로 반영 |
| 축약어 임의 생성 | TB_POST.R_DTTM/R_USER/U_DTTM/U_USER | `REG_*`, `UPD_*` 대신 사용 → PostVO 필드도 `rDttm`, `rUser`, `uDttm`, `uUser` |
| 용어집 미등록 단어 | TB_POST_COMMENT_REPLY_HISTORY_DATA.TEXT | `CONTENT` 대신 `TEXT` 사용 → CommentData VO에 `text` 필드로 반영 |
| 테이블명 길이 초과 | TB_POST_COMMENT_REPLY_HISTORY_DATA | 41자 (표준 30자 초과) |

## 2. Java 표준(java-governance.md) 위반 목록

| 규칙 ID | 심각도 | 위치 | 위반 내용 |
|---|---|---|---|
| MVC-001 | High | `MbrCtrl.java` | Controller가 `MbrDao`, `MbrMapper`를 직접 필드 주입하여 호출 |
| MVC-001 | High | `CommentController.java` | Controller가 Service/DAO를 모두 우회하고 `CmtMapper`를 직접 호출 |
| MVC-001 / MVC-003 | High | `BoardMasterManager.java` | Service 계층임에도 `BoardDao`를 거치지 않고 `BoardMapper`를 직접 호출 |
| MVC-001 | High | `BoardController.java` (`bulkCreateBoards`) | Controller가 `BoardDao`를 직접 호출 |
| MVC-004 | High/Medium | `PostDao.java` | DAO가 `PostService`를 역참조하여 호출 (계층 역행) |
| MVC-002 | Medium | `BoardController.java` (`bulkCreateBoards`) | Controller에서 반복문으로 대량 데이터 처리 + DB 접근 |
| MVC-004 | Medium | `CmtRepo.java` | DAO 계층에서 비속어 필터링이라는 복잡한 업무 판단 로직 수행 |
| MVC-201 | Low | `MbrCtrl.java` | 클래스명이 `Controller`로 끝나지 않음 |
| MVC-202 | Low | `BoardMasterManager.java` | 클래스명이 `Service`로 끝나지 않음 |
| MVC-203 | Low | `CmtRepo.java` | 클래스명이 `Dao`/`DAO`로 끝나지 않음 |
| MVC-205 | Low | `MemberInfo.java`, `CommentData.java` | 클래스명이 `VO`로 끝나지 않음 |
| MVC-401 | High | `MbrCtrl.java` | 계층 구조를 위반하는 대상(`MbrDao`, `MbrMapper`)에 `@Autowired` 주입 |
| MVC-501 | Medium | `MbrCtrl.java`, `PostController.java` | `ApiResponse` 미사용, `Map`을 직접 반환 |
| MVC-601 | Medium | `MbrCtrl.java`, `BoardMasterManager.java`, `PostService.java` | `System.out.println()` 사용 |
| MVC-602 | Medium | `PostController.java`, `PostService.java` | `e.printStackTrace()` 사용 |
| MVC-603 | Low | `PostController.java` | 모든 메서드에 동일한 형태의 try-catch 반복 작성 |
| MVC-604 | Medium | `MbrCtrl.java`, `PostService.java` | catch 블록에서 예외를 의미 있는 처리 없이 무시(Swallow) |
| MVC-701 | Medium | `BoardController.java` (`bulkCreateBoards`) | Controller 메서드에 `@Transactional` 적용 (Service 계층 책임 위반) |
| MVC-701 | Medium | `PostService.java` | 데이터 변경(쓰기) 메서드에 `@Transactional` 미적용 |
| MVC-801 | High | `MbrMapper.xml` (`selectMemberList`, `selectMember`) | 정렬 컬럼 및 PK 조건에 `${ }` 문자열 치환 사용 (검증 없음) |
| MVC-801 | High | `PostMapper.xml` (`selectPostList`) | 제목 검색어(`titleKeyword`)에 `${ }` 문자열 치환 사용 |
| MVC-802 | High(검토 대상) | `MemberInfo.java` | 비밀번호(`pwd`) 필드가 VO에 포함되어 응답 객체로 그대로 노출될 수 있는 구조 |

## 3. 예상 판정 결과

위 위반 중 High 등급(MVC-001, MVC-004, MVC-401, MVC-801 일부)이 다수 존재하므로,
`java-governance.md` 11장 채점 기준에 따라 본 프로젝트는 **REJECT**로 판정되는 것이 정상입니다.
(특히 MVC-001 계층 위반과 MVC-801 SQL Injection 패턴은 "즉시 REJECT 처리 항목"에 해당합니다.)
