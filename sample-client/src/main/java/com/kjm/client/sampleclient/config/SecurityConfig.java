package com.kjm.client.sampleclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebSecurity
public class SecurityConfig {
        /**
         * Spring Seucirty의 Oauth2.0 로그인 지원을 활성화하는데 사용되는 oauth2Login 메서드 사용.
         * 인덱스페이지, 로그인페이지, 그외 기타 테스트 페이지는 로그인 필요 없음
         * 
         * @param http
         * @return
         * @throws Exception
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable) // post request 403 error fix
                                .authorizeRequests()
                                .antMatchers("/", "/login**", "/test1", "/member/register", "/css/**",
                                                "/templates/**/**", "/js/**", "/templates/fragments/**",
                                                "/member/register/**", "/member/logout/**", "/member/find/**",
                                                "/templates/layouts/**",
                                                "**/*.css", "/ui-one/css/output.css")
                                .permitAll()
                                .antMatchers(HttpMethod.POST, "/member/register", "/ui-one/member/register",
                                                "/register", "/member/register/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .oauth2Login();

                return http.build();
        }

        /**
         * WebClient Bean 생성
         * 
         * @param clientRegistrationRepository
         * @param authorizedClientRepository
         * @return
         */
        @Bean
        WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
                        OAuth2AuthorizedClientRepository authorizedClientRepository) {
                ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                                clientRegistrationRepository, authorizedClientRepository);
                oauth2.setDefaultOAuth2AuthorizedClient(true);
                return WebClient.builder()
                                .apply(oauth2.oauth2Configuration())
                                .clientConnector(new ReactorClientHttpConnector())
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited
                                                                                                      // memory size
                                .build();
        }
}
