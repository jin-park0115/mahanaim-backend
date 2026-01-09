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
```
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
```
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

개발일지 2026년01월08일
* 오늘 한 개발: Repository 생성, MoMvote(Service)로직 생성, DTO생성, PostManTest 200OK
* 공부 포인트
  Repository: - 특정 엔티티 타입용으로 "데이터 저장/조회/수정/삭제" 메서드를 모아둔 interface이다. CRUD가 가능하도록 해준다.
  - 애플리케이션 코드(Service 등)에서 직접 SQL을 쓰지 않고, Repository 메서드를 호출해서 DB를 다루게 한다.
 
  
  findBy...: Repository interface에 findBy... 같은 이름을 쓰면 JPA가 자바 객체의 필드 구조를 분석해서 자동으로 SQL을 만든다.
  이걸 쿼리메서드라고 부른다.
  
  DTO: Data Transfer Object, 계층 간(controller <=> service <=> Respository) 데이터 전달 전용 객체다.
  엔티티 처럼 DB구조를 반영하지 않고, 클라이언트와 서버 간 필요한 데이터만 담아서 직렬화(JSON)하기 쉽게 설계한다.
  역할 및 특징: 순수 데이터 컨테이너 getter/setter만 있고 비즈니스 로직은 없음
  DB 테이블 구조 노출 방지
  요청/응답 분리
  
  핵심: Controller는 DTO만 다루고, Service에서만 Entity/DTO변환. 이렇게 하면 계층 분리가 깔끔해진다.

  ** 다음 목표: Service로직 나머지 다 완성, RESTAPI 생성 
  ---

  개발일지 2026년01월09일
  * 오늘 한 개발: User(Login, join)로직, MatchAttendance(참가, 불참, 미정)로직, Swagger 테스트, BCrypt Encode(비밀번호 암호화)
  * 공부 포인트
    계속 Dto, Repository, Controller, Service 부분 이해하며 공부하기, Security 쪽 이해하기

    핵심: 이제 웬만한 기능은 만들었고 추후 추가를 해야할거 같다,
    만든 기능들은 로그인, 회원가입, 일정등록(아직 Role권한 설정 안함), 일정생성, 일정조회, 유저정보 조회, MoM투표(24시간, 중복, 본인 금지), 경기 참가(참, 미, 불)

  향후 추가 할 기능: 일정생성은 Role=admin만 가능하도록, 소셜로그인, 프론트엔드 연동, 현재는 Session으로 보안을 하지만 JWT로 변경등
  

  
  
  
