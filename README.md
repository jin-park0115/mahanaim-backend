# Mahanaim Backend 🏃‍♂️⚽
## Spring Boot + MySQL로 구현한 마하나임 축구 선교단 전용 경기 관리 시스템
---

### 🎯 프로젝트 목적
* 마하나임 축구 선교단을 위한 웹/앱 경기 관리 시스템 구축

* 회원: 경기 일정 확인 및 참여 신청

* 관리자: 인원 파악, 라인업 배치, 경기 기록 입력, 회원 관리

* MOM 투표: 경기 후 24시간 내 투표 (본인 제외)

DB 기본 구성,

---
### 🗄️DB 설계 (ERD)
```mermaid
ERDiagram 
USER ||--o{ MATCH_ATTENDANCE : "참여신청"
USER ||--o{ MATCH_STAT : "기록됨" 
USER ||--o{ MOM_VOTE : "투표함(Voter)"
USER ||--o{ MOM_VOTE : "득표함(Candidate)"
MATCH ||--o{ MATCH_ATTENDANCE : "명단발생"
MATCH ||--o{ MATCH_STAT : "결과발생" 
MATCH ||--o{ MOM_VOTE : "투표진행"
MATCH ||--o{ LINEUP : "전술배치" 

USER {
 bigint user_id PK 
 string email 
 string name
 int age
 string position 
string status "ACTIVE, BANNED, INACTIVE" 
} 

MATCH {
 bigint match_id PK 
 datetime match_date 
 string location 
 string match_type 
 int max_players 
 string description 
}

MOM_VOTE { 
 bigint vote_id PK bigint match_id FK "어느 경기인가" 
 bigint voter_id FK "투표한 사람(User)" 
 bigint candidate_id FK "표를 받은 사람(User)" 
 datetime voted_at 
}

MATCH_ATTENDANCE {
 bigint attendance_id PK 
 bigint user_id FK 
 bigint match_id FK 
 string status "ATTEND, ABSENT, PENDING" 
 datetime applied_at 
} 

MATCH_STAT { 
 bigint stat_id PK 
 bigint user_id FK 
 bigint match_id FK 
 int goals int assists
 boolean is_mom float rating 
}

LINEUP {
 bigint lineup_id
 PK bigint match_id
 FK json formation_data "좌표 및 선수 배치 정보" 
}
---

---
### 📋 핵심 엔티티
Entity |	주요 필드	역할
User	| user_id, email, name, age, position, status |	회원 정보
Match	| match_id, match_date, location, match_type |	경기 일정
MatchAttendance	| user_id, match_id, status |	참여 신청
MatchStat	| user_id, match_id, goals, assists, is_mom |	경기 기록
Lineup  |	match_id, formation_data(JSON) |	라인업 배치
MomVote	| match_id, voter_id, candidate_id |	MOM 투표
```

---

### 🚀개발 진행상황
---

개발일지 2026년01월07일
* MySQL : schema 생성, table 생성 완료
* Spring boot: Entity 클래스  생성
* 공부 포인트
  JPA란? Java에서 객체랑 관계형 DB(테이블)를 매핑해서, 객체 단위로 DB를 다루게 해주는 표준 API라는것을 배움,
  궁금했던 점: MySql에 table만들고 Schema르 다 만들었음에도 불구하고 Spring boot에서 Entity를 또 만드는지,
  찾아보니 애플리케이션 세계와 DB세계를 1:1로 연결해서. 자바 코드로 DB를 쉽게 다루기 위해서 라고 이야기한다.

엔티티의 역할
 -엔티티 클래스는 특정 Table의 모델(설계도)이며, JPA가 이걸 보고 해당 테이블과 매핑해서 CRUD를 대신 처리한다고 한다.
 -@Entity, @Table, @Id, @Column 같은 애노테이션으로 어떤 클래스/필드 가 어느 테이블/컬럼과 연결되는지 '정의' 해두면 직접 SQL을 작성하지 않고 메서드 호출만으로 insert, update가 된다.

  오늘 한 개발: Entity생성 + 환경변수 설정 + MySQl연결

* *다음 목표 Repository 생성, RESTAPI 개발, Spring Security
--- 
