-- 1. 회원 테이블 (복수형 명사 사용 위반, 식별자 Suffix 위반)
CREATE TABLE TB_MEMBERS (
    MBR_KEY     VARCHAR(30)   NOT NULL, -- 위반: 식별자는 고유 규칙상 MBR_ID여야 함 (KEY는 미등록 단어)
    EMAIL       VARCHAR(100)  NOT NULL, 
    PWD         VARCHAR(255)  NOT NULL, 
    NM          VARCHAR(50)   NOT NULL, 
    NICK        VARCHAR(50),            
    TEL_NO      VARCHAR(20),            
    STAT_CODE   VARCHAR(10)   DEFAULT 'ACTV', -- 위반: 상태코드는 Suffix 규칙에 따라 STAT_CD여야 함
    
    -- 공통 컬럼 표준 누락 및 변형
    CREATE_DTTM DATETIME      NOT NULL, -- 위반: 등록일시는 표준 복합어인 REG_DTTM이어야 함
    REG_USER    VARCHAR(30)   NOT NULL, 
    REG_IP      VARCHAR(45)   NOT NULL, 
    UPD_DATE    DATETIME      NOT NULL, -- 위반: 일시는 DT/TM이 아닌 DTTM(UPD_DTTM) 규격이어야 함
    UPD_USER    VARCHAR(30)   NOT NULL, 
    UPD_IP      VARCHAR(45)   NOT NULL, 
    DEL_FLAG    CHAR(1)       DEFAULT 'N' NOT NULL, -- 위반: Boolean 여부는 Suffix YN(DEL_YN)이어야 함
    USE_YN      CHAR(1)       DEFAULT 'Y' NOT NULL, 
    
    CONSTRAINT PK_TB_MEMBERS PRIMARY KEY (MBR_KEY)
);

-- 2. 게시판 마스터 테이블 (Prefix 미준수 위반)
CREATE TABLE BOARD_MASTER ( -- 위반: 테이블 Prefix 표준인 TB_가 누락됨, MASTER는 용어집 미등록 단어
    BOARD_ID    VARCHAR(30)   NOT NULL, 
    TITLE       VARCHAR(100)  NOT NULL, 
    DESC        TEXT,                   
    SORT_ORDER  INT           DEFAULT 0, -- 위반: 정렬순번은 표준 복합어 가이드에 따라 SORT_SEQ여야 함
    
    REG_DTTM    DATETIME      NOT NULL,
    REG_USER    VARCHAR(30)   NOT NULL,
    REG_IP      VARCHAR(45)   NOT NULL,
    UPD_DTTM    DATETIME      NOT NULL,
    UPD_USER    VARCHAR(30)   NOT NULL,
    UPD_IP      VARCHAR(45)   NOT NULL,
    DEL_YN      CHAR(1)       DEFAULT 'N' NOT NULL,
    USE_YN      CHAR(1)       DEFAULT 'Y' NOT NULL,
    
    CONSTRAINT PK_BOARD_MASTER PRIMARY KEY (BOARD_ID)
);

-- 3. 게시글 테이블 (데이터베이스 예약어 직접 사용 위반)
CREATE TABLE TB_POST (
    POST_ID     VARCHAR(30)   NOT NULL, 
    BOARD_ID    VARCHAR(30)   NOT NULL, 
    MBR_ID      VARCHAR(30)   NOT NULL, 
    TITLE       VARCHAR(200)  NOT NULL, 
    CONTENT     TEXT          NOT NULL, 
    VIEW_COUNT  INT           DEFAULT 0 NOT NULL, -- 위반: 건수/횟수는 Suffix 규칙상 VIEW_CNT여야 함
    RCMD_CNT    INT           DEFAULT 0 NOT NULL, 
    NOTICE_YN   CHAR(1)       DEFAULT 'N' NOT NULL, 
    PIN_YN      CHAR(1)       DEFAULT 'N' NOT NULL, 
    PUB_YN      CHAR(1)       DEFAULT 'Y' NOT NULL, 
    
    -- 공통 컬럼 표준 명명 규칙 완전 위반 및 임의 축약
    R_DTTM      DATETIME      NOT NULL, -- 위반: 축약어 임의 생성 금지 (REG_DTTM)
    R_USER      VARCHAR(30)   NOT NULL, -- 위반: 축약어 임의 생성 금지 (REG_USER)
    REG_IP      VARCHAR(45)   NOT NULL, 
    U_DTTM      DATETIME      NOT NULL, -- 위반: 축약어 임의 생성 금지 (UPD_DTTM)
    U_USER      VARCHAR(30)   NOT NULL, -- 위반: 축약어 임의 생성 금지 (UPD_USER)
    UPD_IP      VARCHAR(45)   NOT NULL, 
    DEL_YN      CHAR(1)       DEFAULT 'N' NOT NULL,
    USE_YN      CHAR(1)       DEFAULT 'Y' NOT NULL,
    
    CONSTRAINT PK_TB_POST PRIMARY KEY (POST_ID)
);

-- 4. 댓글 테이블 (용어집 미등록 단어 조합 및 객체 표준 길이 초과 위반)
CREATE TABLE TB_POST_COMMENT_REPLY_HISTORY_DATA ( -- 위반: 테이블명 최대 길이인 30자 초과 (41자)
    CMT_ID      VARCHAR(30)   NOT NULL, 
    POST_ID     VARCHAR(30)   NOT NULL, 
    MBR_ID      VARCHAR(30)   NOT NULL, 
    TEXT        TEXT          NOT NULL, -- 위반: 대입된 명사 TEXT는 용어집(CONTENT)에 없는 미등록 단어
    
    REG_DTTM    DATETIME      NOT NULL,
    REG_USER    VARCHAR(30)   NOT NULL,
    REG_IP      VARCHAR(45)   NOT NULL,
    UPD_DTTM    DATETIME      NOT NULL,
    UPD_USER    VARCHAR(30)   NOT NULL,
    UPD_IP      VARCHAR(45)   NOT NULL,
    DEL_YN      CHAR(1)       DEFAULT 'N' NOT NULL,
    USE_YN      CHAR(1)       DEFAULT 'Y' NOT NULL,
    
    CONSTRAINT PK_TB_POST_COMMENT_REPLY_HISTORY_DATA PRIMARY KEY (CMT_ID) -- 위반: 제약조건명 30자 초과
);

-- 5. 제약조건 명명 규칙 위반 및 외래키 명명 표준 미준수
-- 위반: 외래키는 참조 대상 테이블의 PK명을 그대로 사용해야 하며 Prefix(FK_)를 컬럼명에 쓰면 안 됨
ALTER TABLE TB_POST ADD COLUMN FK_BOARD_ID VARCHAR(30); 
-- 위반: 인덱스 명명 규칙 'IDX_<TABLE>_<COLUMN>' 미준수 및 임의 형태 지정
CREATE INDEX POST_T_IDX ON TB_POST (TITLE);