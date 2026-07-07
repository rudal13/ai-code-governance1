# MVC 소스코드 표준 검사 규칙 (v2.0)

> 본 문서는 Java/Spring 기반 프로젝트의 소스코드가 사내 MVC 표준을 준수하는지
> 자동/반자동으로 검사하기 위한 규칙 정의서이다. 검사 Agent는 본 문서의 규칙 ID,
> 심각도, 판정 기준을 그대로 파싱하여 사용할 수 있도록 구조화되어 있다.

## 0. 문서 정보

| 항목 | 내용 |
|---|---|
| 버전 | v2.0 |
| 이전 버전 | v1.0 (계층 구조/명명/로그 규칙 중심) |
| 변경 요약 | 점수화 기준 신설, 검증/예외/보안/로깅/주석 규칙 추가, 예외 대상(제외 범위) 정의, 규칙 ID 총괄표 추가 |
| 적용 대상 | Spring 기반 Controller-Service-DAO-Mapper(MyBatis) 구조 프로젝트 |
| 검사 대상 제외 | `test`, `generated`, `build`, `target` 하위 경로, `*Test.java`, `*Mock*.java` |

---

## 1. 계층 구조 규칙

### MVC-001. 표준 계층 구조 준수

표준 호출 구조는 다음과 같다.

```text
Controller → Service → DAO → Mapper
```

#### 규칙

- Controller는 Service만 호출한다.
- Service는 DAO만 호출한다.
- DAO는 Mapper만 호출한다.
- Controller에서 DAO 또는 Mapper를 직접 호출하면 안 된다.
- Service에서 Mapper를 직접 호출하면 안 된다.
- DAO에서 Controller 또는 Service를 호출하면 안 된다.
- Mapper는 SQL 매핑 및 DB 접근만 담당한다.
- Service 간 상호 호출(Service A → Service B)은 순환 호출이 발생하지 않는 범위 내에서 허용한다.
- Controller 간 상호 호출은 금지한다.

#### 위반 예시

```java
@RestController
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;
}
```

#### 권장 예시

```java
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
}
```

#### 탐지 힌트 (Agent용)

- Controller 클래스(`@Controller`, `@RestController`) 내 필드/생성자 타입이 `*Dao`, `*DAO`, `*Mapper`로 끝나는 경우 위반
- Service 클래스(`@Service`) 내 필드/생성자 타입이 `*Mapper`로 끝나는 경우 위반
- DAO/Repository 클래스 내 필드 타입이 `*Service`, `*Controller`로 끝나는 경우 위반

#### 심각도

High

---

### MVC-002. Controller 책임 제한

#### 규칙

Controller는 다음 역할만 수행한다.

- 요청 수신
- 파라미터 검증(형식 검증 수준)
- Service 호출
- 결과 반환

Controller에서 다음 로직은 작성하지 않는다.

- SQL 처리
- DAO 또는 Mapper 직접 호출
- 복잡한 비즈니스 로직 (조건 분기 3단계 이상 중첩, 계산 로직 등)
- DB 접근 로직
- 반복문을 이용한 대량 데이터 처리

#### 탐지 힌트

- Controller 메서드 내 `for`/`while` 루프에서 DB 접근성 호출이 포함되는 경우
- Controller 메서드의 Cyclomatic Complexity가 임계치(예: 10) 초과 시 경고

#### 심각도

Medium

---

### MVC-003. Service 책임 정의

#### 규칙

Service 계층은 다음 역할을 담당한다.

- 업무 로직 수행
- 트랜잭션 처리
- DAO 호출
- Controller와 DAO 사이의 업무 흐름 제어

Service에서 Mapper를 직접 호출하지 않는다.

#### 심각도

Medium

---

### MVC-004. DAO 책임 정의

#### 규칙

DAO는 다음 역할을 담당한다.

- Mapper 호출
- 데이터 접근 요청 처리
- SQL 실행 결과 반환

DAO에는 복잡한 비즈니스 판단 로직을 작성하지 않는다.

#### 심각도

Medium

---

### MVC-005. Mapper 책임 정의

#### 규칙

Mapper는 다음 역할만 수행한다.

- SQL 매핑
- DB 접근
- SQL 실행 결과 매핑

Mapper XML에는 SQL 이외의 복잡한 업무 판단 로직을 과도하게 포함하지 않는다.

#### 추가 규칙 (보안)

- Mapper XML 내 `${ }` (문자열 직접 치환) 사용은 원칙적으로 금지하고 `#{ }` (PreparedStatement 바인딩)를 사용한다.
- 부득이하게 `${ }`를 사용해야 하는 경우(정렬 컬럼명 동적 처리 등) 화이트리스트 검증 로직을 Service/DAO에 동반해야 한다.

#### 탐지 힌트

- Mapper XML 내 `${` 패턴 검색 → 발견 시 보안 위반 후보로 표시

#### 심각도

Medium (단, `${ }` 미검증 사용은 High로 격상)

---

## 2. 패키지 구조 규칙

### MVC-101. 표준 패키지 구조 사용

#### 권장 패키지

```text
controller
service
service.impl
dao
mapper
vo
dto
common
config
exception
```

#### 규칙

- Controller 클래스는 `controller` 패키지 하위에 위치한다.
- Service 인터페이스는 `service` 패키지, 구현체는 `service.impl` 패키지 하위에 위치한다(인터페이스/구현 분리 시).
- DAO 클래스는 `dao` 패키지 하위에 위치한다.
- Mapper 인터페이스 또는 Mapper XML은 `mapper` 패키지 또는 mapper 리소스 경로 하위에 위치한다.
- VO 클래스는 `vo` 패키지, 외부 연동/화면 전달용 DTO는 `dto` 패키지 하위에 위치한다.
- 공통 유틸/상수는 `common` 패키지, 설정 클래스는 `config` 패키지, 커스텀 예외는 `exception` 패키지 하위에 위치한다.

#### 심각도

Low

---

## 3. 클래스 명명 규칙

### MVC-201. Controller 클래스 명명

#### 규칙

Controller 클래스명은 `Controller`로 끝나야 한다.

#### 심각도

Low

---

### MVC-202. Service 클래스 명명

#### 규칙

Service 클래스명은 `Service`로 끝나야 한다.

Service 구현체를 분리하는 경우 `ServiceImpl` 사용을 허용한다.

#### 심각도

Low

---

### MVC-203. DAO 클래스 명명

#### 규칙

DAO 클래스명은 `Dao` 또는 `DAO`로 끝나야 한다.

프로젝트 내에서는 하나의 표기 방식을 일관되게 사용한다. (프로젝트 최초 결정 표기와 다른 표기 혼용 시 위반)

#### 심각도

Low

---

### MVC-204. Mapper 명명

#### 규칙

Mapper 인터페이스 및 Mapper XML 파일명은 `Mapper`로 끝나는 것을 권장하며, 인터페이스명과 XML 파일명은 동일해야 한다 (예: `CustomerMapper.java` ↔ `CustomerMapper.xml`).

#### 심각도

Low

---

### MVC-205. VO/DTO 명명

#### 규칙

- 계층 간(Controller↔Service↔DAO) 데이터 전달 객체는 VO를 사용하며, 클래스명은 `VO`로 끝나는 것을 권장한다.
- 외부 시스템 연동 또는 화면(API 응답 전용) 객체는 DTO를 사용하며, 클래스명은 `DTO` 또는 `Dto`로 끝나는 것을 권장한다.
- VO/DTO는 불필요한 비즈니스 로직 메서드를 포함하지 않고 getter/setter 및 단순 검증 수준으로 제한한다.

#### 심각도

Low

---

### MVC-206. 상수/Enum 명명

#### 규칙

- 상수는 `static final`로 선언하고 전체 대문자와 언더스코어(`SNAKE_CASE`)를 사용한다.
- Enum 클래스명은 의미를 나타내는 명사로 작성하며 상수 값은 대문자로 작성한다.
- 매직 넘버/매직 스트링을 코드 내 직접 사용하지 않고 상수 또는 Enum으로 정의한다.

#### 심각도

Low

---

## 4. 메서드 명명 규칙

### MVC-301. 조회 메서드 명명

권장 Prefix: `get`, `find`, `search`, `select`, `list`

#### 심각도

Low

---

### MVC-302. 등록 메서드 명명

권장 Prefix: `create`, `register`, `insert`, `add`

#### 심각도

Low

---

### MVC-303. 수정 메서드 명명

권장 Prefix: `update`, `modify`, `change`

#### 심각도

Low

---

### MVC-304. 삭제 메서드 명명

권장 Prefix: `delete`, `remove`, `cancel`

#### 심각도

Low

---

### MVC-305. boolean 반환 메서드 명명

#### 규칙

boolean을 반환하는 메서드는 `is`, `has`, `can` 접두어를 사용한다. (예: `isValid()`, `hasPermission()`, `canCancel()`)

#### 심각도

Low

---

## 5. 의존성 주입 규칙

### MVC-401. `@Autowired` 사용 허용

#### 규칙

- 프로젝트 표준에 따라 `@Autowired`를 사용한 필드 주입을 허용한다.
- `@Autowired` 사용 자체는 위반으로 판단하지 않는다.
- 단, 주입 대상 계층이 표준 호출 구조를 위반하는 경우에는 위반으로 판단한다.
- 신규 코드는 가능한 경우 생성자 주입(Constructor Injection)을 권장하며, 필드 주입은 기존 표준상 허용(Pass)으로 유지한다.

#### 허용 예시

```java
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
}
```

#### 위반 예시

```java
@Controller
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;
}
```

#### 심각도

- `@Autowired` 사용 자체: Pass
- 계층 구조 위반 주입: High
- 필드 주입 대신 생성자 주입 미사용: Low (권고 사항, 감점 없음)

---

## 6. 응답 처리 규칙

### MVC-501. Controller 응답 객체 표준화

#### 규칙

- Controller는 프로젝트 표준 응답 형식(공통 Response Wrapper, 예: `ApiResponse<T>`)을 사용한다.
- 응답 데이터는 VO 또는 DTO 기반으로 구성한다.
- `Map<String, Object>` 또는 `Object`를 직접 반환하는 방식은 지양한다.
- 정상/오류 응답 모두 동일한 공통 포맷(code, message, data)을 따른다.

#### 심각도

Medium

---

### MVC-502. 입력 값 검증(Validation) 표준화

#### 규칙

- 외부 입력을 받는 VO/DTO에는 Bean Validation 어노테이션(`@NotNull`, `@Size`, `@Pattern` 등)을 사용한다.
- Controller 메서드 파라미터에는 `@Valid` 또는 `@Validated`를 적용한다.
- 검증 실패 시 별도의 if문 분기로 처리하지 않고 공통 예외 핸들러(`@ControllerAdvice`)를 통해 일괄 처리한다.

#### 심각도

Medium

---

## 7. 로그 및 예외 처리 규칙

### MVC-601. System.out.println 사용 금지

#### 규칙

운영 코드에서 `System.out.println()`, `System.err.println()` 사용을 금지한다.

로그 출력은 SLF4J 기반 Logger(`org.slf4j.Logger`) 사용을 표준으로 한다.

#### 심각도

Medium

---

### MVC-602. printStackTrace 사용 금지

#### 규칙

`e.printStackTrace()` 사용을 금지한다.

예외 발생 시 Logger 또는 공통 예외 처리 방식을 사용한다.

#### 심각도

Medium

---

### MVC-603. 반복적인 try-catch 지양

#### 규칙

Controller에서 동일한 형태의 try-catch를 반복적으로 작성하는 것을 지양한다.

공통 예외 처리(`@ControllerAdvice`, `@ExceptionHandler`) 방식 사용을 권장한다.

#### 심각도

Low

---

### MVC-604. 예외 삼킴(Exception Swallowing) 금지

#### 규칙

- `catch` 블록 내부를 비워두거나, 로그 없이 무시하는 코드는 금지한다.
- 예외를 catch한 후 의미 있는 처리(로깅, 재처리, 커스텀 예외 전환) 없이 흐름을 계속 진행하면 안 된다.
- 모든 사용자 정의 예외는 공통 예외 클래스(`BusinessException` 등)를 상속하여 일관된 방식으로 처리한다.

#### 위반 예시

```java
try {
    service.process();
} catch (Exception e) {
    // 아무 처리 없음
}
```

#### 심각도

Medium (운영 장애로 이어질 가능성이 있는 핵심 트랜잭션 구간에서는 High)

---

### MVC-605. 로그 레벨 사용 기준

#### 규칙

- `ERROR`: 시스템 오류, 예외 발생 등 즉시 조치가 필요한 경우
- `WARN`: 정상 흐름이지만 주의가 필요한 경우
- `INFO`: 주요 업무 흐름 기록
- `DEBUG`: 개발/디버깅 목적 상세 정보 (운영 환경에서는 비활성화 권장)
- 로그 메시지에 비밀번호, 주민등록번호, 카드번호 등 민감정보를 그대로 출력하지 않는다.

#### 심각도

Medium (민감정보 로그 노출 시 High)

---

## 8. 트랜잭션 규칙

### MVC-701. 트랜잭션 위치

#### 규칙

`@Transactional`은 Service 계층에서 사용하는 것을 원칙으로 한다.

Controller, DAO, Mapper 계층에서는 트랜잭션을 직접 제어하지 않는다.

조회 전용 메서드에는 `@Transactional(readOnly = true)` 적용을 권장한다.

#### 허용 예시

```java
@Service
public class CustomerService {
    @Transactional
    public void updateCustomer(CustomerVO customerVO) {
        customerDao.updateCustomer(customerVO);
    }
}
```

#### 심각도

Medium

---

## 9. 보안 규칙

### MVC-801. SQL Injection 방지

#### 규칙

- Mapper XML 및 동적 쿼리 작성 시 `${ }` 미검증 사용을 금지한다 (MVC-005 참조).
- DAO/Service에서 직접 SQL 문자열을 조합(String concatenation)하여 실행하는 방식을 금지한다.

#### 심각도

High

---

### MVC-802. 민감정보 처리

#### 규칙

- 비밀번호, 인증키, 토큰 등은 소스코드 내 하드코딩을 금지하고 별도 설정(Config, Vault, 환경변수)으로 분리한다.
- 민감정보는 응답(Response) 객체에 그대로 포함하여 반환하지 않는다 (예: 비밀번호 필드를 DTO에서 제외).

#### 심각도

High

---

## 10. 주석 및 문서화 규칙

### MVC-901. 클래스/메서드 주석

#### 규칙

- Controller, Service의 public 메서드에는 기능 설명, 파라미터, 반환값에 대한 Javadoc 작성을 권장한다.
- 임시 처리, 추후 수정이 필요한 코드는 `TODO` 또는 `FIXME` 주석으로 명시한다.
- 주석 처리된(comment-out) 코드 블록을 장기간 방치하지 않는다.

#### 심각도

Low

---

## 11. 검사 결과 판정 및 점수화 기준

### 11.1 기본 점수 체계

검사는 100점 만점에서 위반 사항별로 감점하는 방식으로 점수화한다.

| 심각도 | 건당 감점 | 비고 |
|---|---|---|
| High | -30점 | 동일 규칙 반복 위반 시 누적 감점하되, 동일 규칙은 최대 -60점까지만 누적 |
| Medium | -10점 | 동일 규칙은 최대 -30점까지만 누적 |
| Low | -3점 | 동일 규칙은 최대 -15점까지만 누적 |

> 점수는 0점 미만으로 내려가지 않으며, 최종 점수는 0~100 범위로 표시한다.

### 11.2 등급 판정 기준

| 등급 | 점수 범위 | 추가 조건 |
|---|---|---|
| PASS | 90점 이상 | High 위반 0건 |
| WARNING | 70점 이상 90점 미만 | High 위반 0건, Medium 위반 1건 이상 |
| REJECT | 70점 미만 또는 High 위반 1건 이상 | 점수와 무관하게 High 위반이 1건이라도 있으면 REJECT 확정 |

### 11.3 즉시 REJECT 처리 항목 (점수와 무관하게 REJECT)

- Controller에서 DAO 직접 호출 (MVC-001)
- Controller에서 Mapper 직접 호출 (MVC-001)
- Service에서 Mapper 직접 호출 (MVC-001)
- DAO에서 Service 또는 Controller 호출 (MVC-001)
- Mapper XML `${ }` 미검증 사용에 의한 SQL Injection 가능 패턴 (MVC-801)
- 민감정보 하드코딩 또는 응답 노출 (MVC-802)

### 11.4 검사 결과 리포트 형식 (Agent 출력 권장 포맷)

```json
{
  "score": 82,
  "grade": "WARNING",
  "violations": [
    {
      "ruleId": "MVC-601",
      "severity": "Medium",
      "file": "CustomerController.java",
      "line": 42,
      "message": "System.out.println 사용 발견"
    }
  ],
  "summary": {
    "high": 0,
    "medium": 2,
    "low": 1
  }
}
```

---

## 12. 규칙 ID 총괄표 (Agent 파싱용)

| 규칙 ID | 분류 | 심각도 |
|---|---|---|
| MVC-001 | 계층 구조 | High |
| MVC-002 | Controller 책임 | Medium |
| MVC-003 | Service 책임 | Medium |
| MVC-004 | DAO 책임 | Medium |
| MVC-005 | Mapper 책임 / 보안 | Medium (조건부 High) |
| MVC-101 | 패키지 구조 | Low |
| MVC-201~206 | 클래스/상수 명명 | Low |
| MVC-301~305 | 메서드 명명 | Low |
| MVC-401 | 의존성 주입 | Pass / High |
| MVC-501 | 응답 표준화 | Medium |
| MVC-502 | 입력 검증 | Medium |
| MVC-601 | println 금지 | Medium |
| MVC-602 | printStackTrace 금지 | Medium |
| MVC-603 | 반복 try-catch 지양 | Low |
| MVC-604 | 예외 삼킴 금지 | Medium (조건부 High) |
| MVC-605 | 로그 레벨 | Medium (조건부 High) |
| MVC-701 | 트랜잭션 위치 | Medium |
| MVC-801 | SQL Injection 방지 | High |
| MVC-802 | 민감정보 처리 | High |
| MVC-901 | 주석/문서화 | Low |

---

## 13. 향후 검토 항목 (v3.0 후보)

- 단위 테스트 커버리지 기준 연동
- API 버저닝 규칙
- 캐시(Cache) 사용 표준
- 비동기(Async)/배치 작업 표준
- 코드 포맷터(Checkstyle/Spotless) 자동 연동 기준
