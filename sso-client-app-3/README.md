# Spring Keycloak SSO Client Sample 
Keycloak Client 생성순서 입니다.  
## 목차
1. spring maven 생성
2. Maven Dependencies 설정 
3. keycloak에 client 생성
4. realm file export 후 import 하기
5. 폴더 생성 
6. Security Configuration 설정 
7. keycloak에 client 추가
8. test 진행

## 
## spring maven 생성
1. 터미널에 명령어를 실행 한다.(프로젝트 생성)

```bash
mvn archetype:generate -DgroupId=com.kjm.client -DartifactId=sso-client-app-3 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
[주의사항]
- DgroupId={프로젝트 그룹이름} , DartifactId={프로젝트 ID}를 넣음 
- 프로젝트 그룹이름은 최상단 pom.xml 참조
- 프로젝트 폴더는 생성하지 않고 명령어를 실행하는게 좋음
sso-client-app-3디렉토리가 사전애 존재시, sso-client-app-3> sso-client-app-3 구조로 됨 


## Maven Dependencies 설정
1. packaing parent 변경 (이유 reference : parent 변경 참조)
  ```html
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.kjm.client</groupId>
  <artifactId>sso-client-app-3</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>sso-client-app-3</name>
  
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.5</version>
   <relativePath /> 
  </parent>

  ```

2. pom.xml에 dependeny 및 plugs 설치 (이유 reference : 라이브러리 추가 및 소스생성 참조)
```html

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity5</artifactId>
	<version>3.1.2.RELEASE</version>
    </dependency>
    
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webflux</artifactId>
    </dependency>
    
    <dependency>
        <groupId>io.projectreactor.netty</groupId>
        <artifactId>reactor-netty</artifactId>
    </dependency>

    <dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
    </dependency>

    <!-- spring-boot-starter-parent 버전 3.0 이상이여서 추가
    <dependency>
      <groupId>javax</groupId>
      <artifactId>javaee-api</artifactId>
      <version>8.0.1</version>
      <scope>provided</scope>
    </dependency>-->

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>

  </dependencies>

  <build>
    <finalName>sso-client-app-3</finalName>
    <!-- Spring Boot Maven Plugin 설정 -->
    <plugins>
      <plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
```

3. .gitignore 추가
```bash
HELP.md
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/
```

- 2. 터미널에 sso-client-app-3 이동 후 
 ```bash
 cd sso-client-app-3
 ```
 ```bash
 mvn clean install
 ```
	
  

## keycloak에 client 생성

<img width="1699" alt="1" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/181245d8-98ca-4a3c-b4a7-bfda4bf0becf">


<img width="1698" alt="2" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/4c5b29e2-cb4d-4801-af0f-a899a35bc1c6">


<img width="1676" alt="3" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/04cdf4ec-b524-4a05-8c43-84fd53ba36e5">


<img width="1692" alt="4" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/a821d623-3456-46c8-a57b-23663f798b30">


<img width="1702" alt="5" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/5dcc1607-0abf-42e3-b6ba-fdd86c4d400f">



## realm file export 후 import 하기
<img width="1688" alt="6" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/3a63677a-827d-4e84-9c62-27ae3933a0ce">

<img width="1680" alt="7" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/cc49bbed-2d2a-4b90-9c3b-53217493938a">



## realm file export 후 import시 주의사항

<img width="1701" alt="key값 확인" src="https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/3511882d-7fc6-4119-9609-8f1c902574d7">

![8](https://github.com/grandff/sample-keycloak-sso-spring/assets/29056140/764b8ed8-4c1a-47b5-a580-ad11932afa33)

- 클라이언트 생성후 key값 확인후 .josn 비번이 "*****"로 되어 있으면 변경을 해줘야함



## Security Configuration 설정 
1.  폴더 생성 및 파일 생성
```bash
mkdir -p sso-client-app-3/src/main/java/com/kjm/client/spring/
mkdir -p sso-client-app-3/src/main/java/com/kjm/web/controller/
mkdir -p sso-client-app-3/src/main/java/com/kjm/web/model/
mkdir -p sso-client-app-3/src/main/java/com/kjm/web/
mkdir -p sso-client-app-3/src/main/resources/templates/

touch sso-client-app-3/src/main/java/com/kjm/client/spring/UiSecurityConfig.java
touch sso-client-app-3/src/main/java/com/kjm/client/web/controller/FooClientController.java
touch sso-client-app-3/src/main/java/com/kjm/client/web/model/FooModel.java
touch sso-client-app-3/src/main/java/com/kjm/client/web/FooClientController.java

touch sso-client-app-3/src/main/resources/templates/foos.html
touch sso-client-app-3/src/main/resources/templates/index.html
touch sso-client-app-3/src/main/resources/application.yml
 ```



## Security Configuration 설정 
1. application.yml 설정 
```html
spring:
  security:
    oauth2:
      client:
        registration:
          custom:
            client-id: ssoClient-3
            client-secret: ayjc4k326TKW1Wmm6CZRpdchtxR7zKfl
            scope: read,write,openid
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8085/ui-three/login/oauth2/code/custom
        provider:
          custom:
            authorization-uri: http://localhost:8083/auth/realms/dev/protocol/openid-connect/auth
            token-uri: http://localhost:8083/auth/realms/dev/protocol/openid-connect/token
            user-info-uri: http://localhost:8083/auth/realms/dev/protocol/openid-connect/userinfo
            jwk-set-uri: http://localhost:8083/auth/realms/dev/protocol/openid-connect/certs
            user-name-attribute: preferred_username
  thymeleaf:
    cache: false
  cache:
    type: NONE

server:
  port: 8085
  servlet:
    context-path: /ui-three

logging:
  level:
    org.springframework: INFO

resourceserver:
  api:
    url: http://localhost:8081/sso-resource-server/api/foos/     
```
[주의]
아래는 Client 생성시 입력한 내용을 참고로 내용을 입력해야함 
- client-id
- redirect-uri
- client-secret은 변경
- server.port
- server.context-path


##UiSecurityConfig.java 소스입력 
```java
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebSecurity
public class UiSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/login**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login(); //OAuth 2.0 로그인 지원을 활성화 필수 함수
        return http.build();
    }

    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, 
      OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = 
          new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, 
          authorizedClientRepository);
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
            .apply(oauth2.oauth2Configuration())
            .build();
    }

}
```

##FooClientController .java 소스입력 
```java
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.client.web.model.FooModel;

@Controller
public class FooClientController {

    @Value("${resourceserver.api.url}")
    private String fooApiUrl;

    @Autowired
    private WebClient webClient;

    private static Logger log = LoggerFactory.getLogger(FooClientController.class);

    @GetMapping("/foos")
    public String getFoos(Model model) {
        log.info("[호출 /foosa]sss");

        List<FooModel> foos = this.webClient.get()
                .uri(fooApiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FooModel>>() {
                })
                .block();
        model.addAttribute("foos", foos);
        return "foos";
    }
}

```

##FooModel .java 소스입력 
```java
public class FooModel {
    private Long id;
    private String name;

    public FooModel() {
    }

    public FooModel(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FooModel other = (FooModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Foo [id=" + id + ", name=" + name + "]";
    }

}
```
##SSOClientApplication .java 소스입력 
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.client")
public class SSOClientApplication 
{
    public static void main( String[] args )
    {
        System.out.println( "SSOClientApplication is main" );
        SpringApplication.run(SSOClientApplication.class,args);
    }
}
```

##foos.html 소스입력 
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Spring OAuth Client Thymeleaf - 2</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" />
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3 mb-5">
		<a class="navbar-brand" th:href="@{/foos/}">Spring OAuth Client
			Thymeleaf -2</a>
		<ul class="navbar-nav ml-auto">
			<li class="navbar-text">Hi, <span sec:authentication="name">preferred_username</span>&nbsp;&nbsp;&nbsp;
			</li>
		</ul>
	</nav>

	<div class="container">
		<h1>All Foos:</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>ID</td>
					<td>Name</td>
					<!--  <td>Creation Date</td>-->
				</tr>
			</thead>
			<tbody>
				<!--<tr th:if="${foos.empty}">
					<td colspan="4">No foos</td>
				</tr>
				<tr th:each="foo : ${foos}">
					<td><span th:text="${foo.id}"> ID </span></td>
					<td><span th:text="${foo.name}"> Name </span></td>
				</tr>-->
			</tbody>
		</table>
	</div>

</body>
</html>
```


##index.html 소스입력 
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Spring OAuth Client Thymeleaf - 2</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" />
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3 mb-5">
		<a class="navbar-brand" th:href="@{/foos/}">Spring OAuth Client
			Thymeleaf - 2</a>
	</nav>

	<div class="container">
		<label>Welcome ! </label> <br /> <a th:href="@{/foos/}"
			class="btn btn-primary">Login</a>
	</div>
</body>
</html>
```




### test
- 1. http://localhost:8085/ui-three/ 접속
- 2. id : louis.second@example.com, pw : changeme 입력  
- 3. foo의 정보가 나오면 정상


## reference
(keycloak전반적인 흐름) https://velog.io/@juhyeon1114/Keycloak-Authorization-code-인증-흐름
(parent 변경) https://recordsoflife.tistory.com/393
(spring-boot-starter-parent설정이유) https://recordsoflife.tistory.com/393
(라이브러리 추가 및 소스생성)  https://www.baeldung.com/sso-spring-security-oauth2


