package com.kjm.resource.sampleresourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable) // post request 403 error fix
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
                .hasAuthority("SCOPE_read") // 읽기 권한 필요
                .antMatchers(HttpMethod.POST, "/api/foos")
                .hasAuthority("SCOPE_write") // 쓰기 권한 필요 (post)
                .antMatchers("/h2/**") // 그냥 호출 가능
                .permitAll()
                .antMatchers(HttpMethod.POST, "/member/**") // 그냥 호출 가능 (POST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

    /**
     * antMatchers + SCOPE_read -> GET 메소드로 해당 url을 요청하려면 read 권한 필요
     * antMatchers + SCOPE_write -> POST 메소드로 해당 url을 요청하려면 write 권한 필요
     * anyRequest().authenticated() -> 그 외 모든 요청은 인증이 필요함
     * oauth2ResourceServer().jwt() -> Oauth2 리소스 서버를 설정해서 JWT 형태의 토큰 사용
     * 
     */
}