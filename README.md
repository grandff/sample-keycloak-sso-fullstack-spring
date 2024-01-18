# Spring Keycloak SSO Sample Application

## What is it

keycloak을 embedded 한 spring auth server이다. sso 테스트와 동시에 custom storage provider를 추가했다.

## Project list

- authorization-server
  - [X] maria db 연결
- client-app1
  - login test client
- client-app2
  - basic cleint
- client-app3 (아예 새로 만들어서 테스트용)
- resource-server
- resource-server2
  - 추가 기능 연계용

## Environment

Spring Boot 3, OpenJDK17, Keycloak, H2, JPA

## Api endpoint

## To Do

- [X] 외부 데이터 베이스를 사용하도록 설정
- [X] 로그인 완료 후 응답값 새로 설정 (로그인 시도 - 로그인화면 - 로그인성공 - 토큰 확인 - 토큰 정보로 userinfo 요청)
- [ ] 회원가입 기능 구현 (local은 불가)
- [ ] 로그인하고 볼 수 있는 마이페이지 내에서 회원 탈퇴 기능, 회원정보 찾기 기능 구현
- [ ] 로그인 커스텀 테마 설정
- [ ] 마리아디비 연결 및 기능 테스트
- [ ] thymeleaf 기본 설정 및 tailwindcss 설정
- [ ] 로그인 유지 방법 변경 (토큰 유효기간 늘리기, 자동 재발급 등)
- [ ] 게시판 CRUD 기능 구현
- [ ] 신규 Client에서 적용 방법 정리 및 테스트
- [ ] Realm 설정 export 방법
- [ ] Resource Server 하나 더 만들어서 연계하기
- [ ] 로그 프레임워크로 자세히 나오게 (MDC) -> Client, Resource 두 곳에 설정
- [ ] 필터, 인터셉터 등 기본 설정 -> Client, Resource 두 곳에 설정
- [ ] 문서 최종 정리

## Functions

- SSO 인증
- 회원관리 화면
- 회원관리 기능
- 회원관리자 페이지 접속 화면
- 회원관리자 권한 확인 화면
- 로그인 후 회원정보 확인
- 토큰 검증
- 접속자 세션 정보 확인
- CORS 처리
- API 목록 조회
- API DOCS 관리자 인증
- 외부 데이터베이스 저장소 연결
...MORE...

## FIX ME

### auth server 초기설정으로 되돌아가는 현상

authorization server의 시작이 안될경우 최신 버전으로 깃을 내려받아본다. 버그인지 설정파일이 삭제되는 경우가 종종 있다.

```bash
git fetch --all
git reset --hard origin/master
git pull origin master
```

### client의 application.yml 인자를 못읽어들임

소스 파일 또는 메인 파일을 일부러 수정시켜서 다시 실행시켜본다.

### reaml file

1. 생성

- create realm > create 후
- user fderation을 통해 DB 설정
   (※ 사전에 데이터 베이스를 생성을 해줘야 함, 미생성시 realm 등록 안됨)
   [데이터베이스 생성]
   CREATE DATABASE IF NOT EXISTS customdb1;
   CREATE DATABASE IF NOT EXISTS customdb2;

2. 반출

- Configure > Realm Settings 우측 상단 partial export 클릭
- user provider의 db password가 ***** 로 변경되는데, 이때 원래 비밀번호를 입력해야함. 안그러면 디비 커넥션 실패로 realm 생성에 실패하게 됨.

3. 등록

- EmbeddedKeycloakApplication createRealms 함수을 통해서 realm.json 파일 등록  

## Cautions

1. local 환경에서 인증서버와 리소스서버의 schema.sql 파일을 동일하게 설정해야한다. 기준은 인증서버 파일을 기준으로 한다.

## References

(keycloak에 대한 한글 설명)<https://malwareanalysis.tistory.com/273> </br>
(keycloak embedded spring boot)<https://www.baeldung.com/keycloak-embedded-in-spring-boot-app> </br>
(keycloak endpoint)<https://www.baeldung.com/postman-keycloak-endpoints> </br>
(sso keycloak spring)<https://www.baeldung.com/sso-spring-security-oauth2> </br>
(oauth2에 대한 기본 설명)<https://adjh54.tistory.com/221> </br>
