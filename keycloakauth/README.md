# Authorization Server with keycloak

## What is it

keycloak을 embedded 한 spring auth server이다.

## How To Embed Keycloak

### Pre-Configuration

realm을 구성할 수 있는 json 형식의 설정 파일이 필요하다. Keycloak 관리 콘솔을 사용해서 구성할 수 있는 모든 내용은 이 JSON에 유지된다.

권한 부여 서버는 sample-realm.json으로 사전 구성된다. 이 파일에서 몇가지 구성이 확인 가능하다.

- users : 기본 사용자는 <john@test.com> 및 <mike@other.com> 이고, 자격 증명도 포함되어 있다. 하지만 별도 provider를 쓸 거라 users는 무시한다.
- clients : id가 newClient인 클라이언트를 정의한다.
- standardFlowEnabled : true로 설정하여, newClient에 대한 인증 코드 흐름을 활성화한다.
- redirectUris : 인증 성공 후 서버가 리다이렉션할 newClient의 URl이 나열된다.
- webOrigins : redirectUris로 나열된 모든 URL에 대해 CORS 지원을 허용하려면 "+"로 설정한다.

Keycloak 서버는 기본적으로 JWT 토큰을 발급하므로 이를 위한 별도 구성이 필요하진 않는다.

### Maven Configuration

## Custom Storage Provider

## keycloak server json

- connectionsJpa : keycloak 기본 데이터베이스 설정 경로(최초 기동 시 생성되는 데이터). 없으면 오류남.

## Keycloak Server Setting Export

## Change Database Connection Settings

application 이 아닌 Dbutils 파일을 참조해야한다. 서비스에 맞게 해당 파일을 변경해서 사용한다. 현재 다른 설정 방법을 찾아 보는 중이다...

## Warning

시큐리티 설정을 넣으면 안된다. 오류가 계속 나서 이 설정 그대로 놔둬야한다.

## Settings Info

### 1. roles

Invalid scopes: read write openid 가 나오는 경우, client scope에 각각의 roles를 추가해준다.
