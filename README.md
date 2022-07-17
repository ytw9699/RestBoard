# RestBoard
- REST 기반의 게시판 API 서버를 만들었습니다.
## Clone 주소  
` https : https://github.com/ytw9699/RestBoard.git`  
` ssh : git@github.com:ytw9699/RestBoard.git`  

## 개발환경 및 스펙
- Spring Boot 2.7.1
- Java8
- Security
- Spring Data Jpa, Hibernate, Mariadb 
- Jwt
- Tomcat
- Gradle 7.4.1
- Junit5
- Git, SourceTree
- Swagger 3.0
- Postman

## Swagger
http://localhost:8090/swagger-ui/index.html

## 서비스 소개 및 기능 요구사항 

### 인증
- 토큰 방식 인증 JWT + 스프링 시큐리티
- 아이디/패스워드 기반 회원가입, 로그인
- 회원 정보에 아이디, 비밀번호, 닉네임 포함

### 게시판(등록, 조회, 수정, 삭제)
- 로그인한 회원은 게시판에 글을 작성할 수 있다. 
- 로그인한 회원은 본인이 작성한 게시판 글을 수정할 수 있다. 
- 로그인한 회원은 본인이 작성한 게시판 글을 삭제할 수 있다.
- 로그인한 회원만 게시판에 접근할 수 있다.



