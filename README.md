# SpringBootSecurity_JWT-Mysql
make Spring security using JWT with database mysql 


# 참고자료 
https://webfirewood.tistory.com/115

https://sup2is.github.io/2020/03/05/spring-security-login-with-jwt.html

https://www.techgeeknext.com/spring/spring-boot-security-token-authentication-jwt-mysql



# 전체적인 프로세스 

1. Http Request: login infomation( email,password)
2. Authentication Filter 가 intercept, 가로챈 정보를 통해 
 UsernamePasswordAuthentionToken 인증용 객체 생성
3. ProviderManager에게 UsernamePasswordAuthenticationToken 객체 전달
4. AuthenticationProvider에 UsernamePasswordAuthentication Token객체 전달
5. UserDeatailService(실제 데이터베이스 인증정보) 에 사용자 정보 전달
6. 받은 정보를 통해 DB에서 찾은 사용자 정보인 UseDeatails 객체 생성
7. AuthentiacationProvider 는 UserDetails 를 넘겨받고 사용자 정보와 비교 
8. Authentication 객체 반환( 인증 완료시)
9. AuthentiacationFilter  에 Authenticaion 객체 반환



# JWT JSON 객체를 통해 안전하게 정보를 전송할수있는 웹표준
.을 구자로 헤더, 내용 서명으로 저장되어 진다. 이는 jwt에서 encode 할수있다.

프론트에서 JWT를 헤더에 담아 권한이 필요한 api를 호출할떄 사용한다. 
claim은 hashmap으로 구성되어 name,value가 들어간다.


# 허용할 URL 설정과 권한이 필요한 URL 설정
허용할 URL 의 설정
- .antMatchers("/**").permitAll(); localhost:포트/ 에 해당 하는 모든 URL에 대해 허용 해주고있다. 


권한에 따른 설정

- antMatchers("/admin/**").hasRole("ADMIN") admin이 들어간 api호출시 ADMIN을 가진것만 사용이 가능하다. 
- antMatchers("/user/**").hasRole("USER") admin이 들어간 api호출시 ADMIN을 가진것만 사용이 가능하다. 




# Controller 설명 

@PostMapping("/join")
- 회원가입을 할수있는 API이다 .
- User 객체를 받으며 현재는 Email,name,password만 받고있다.


@PostMapping("/login")
- 로그인을 하는 API 이다 
- User 객체로 받으며 join api 에서 받는것과 마찬가지로 email, name, password를 받고있다.



# 사용하는 방법

1. join을 통해 사용자를 등록한다.


2. login 을 호출하여 Token을 발급받는다
(* 이떄 token은 30분으로 제한 시간을 설정해 두었다)

3. 받은 Token을 헤더의 X-AUTH-TOKEN에 넣어 권한이 있는 API를 호출 할수있다.




