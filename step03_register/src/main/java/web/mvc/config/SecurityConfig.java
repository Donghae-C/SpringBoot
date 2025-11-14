package web.mvc.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("bCryptPasswordEncoder call.....");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain filterChain(HttpSecurity http) call.....");
       /////////////////////////////////
        //csrf disable
        http.csrf((auth) -> auth.disable()); //csrf공격을 방어하기 위한 토큰 주고 받는 부분을 비활성화!
        //Form 로그인 방식 disable -> React, JWT 인증 방식으로 변경예정
        //disable 를 설정하면 시큐리티의 UsernamePasswordAuthenticationFilter비활성됨.
        http.formLogin((auth) -> auth.disable());
        //http.formLogin(Customizer.withDefaults());
        //http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/index", "/members", "/members/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/v3/api-docs", "swagger-ui.html",
                                "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        // [1] GET 요청: 누구나 접근 가능
                        .requestMatchers(HttpMethod.GET, "/boards").permitAll()
                        .requestMatchers(HttpMethod.GET, "/boards/**").permitAll()

                        // [2] POST 요청: 인증 필요
                        .requestMatchers(HttpMethod.POST, "/boards").authenticated()
                        // [3] PUT 요청: 인증 필요
                        .requestMatchers(HttpMethod.PUT, "/boards").authenticated()
                        // [4] DELETE 요청: 인증 필요
                        .requestMatchers(HttpMethod.DELETE, "/boards").authenticated()
                        /*https://docs.spring.io/spring-security/site/docs/5.5.6/api/org/springframework/security/config/annotation/web/configurers/AuthorizeHttpRequestsConfigurer.AuthorizedUrl.html#hasAnyRole(java.lang.String...)
                        * 사용자가 적어도 하나 이상 가져야 하는 역할(예: ADMIN, USER 등). 각 역할은 ROLE_로 시작하면 안 됩니다. 이미 자동으로 ROLE_이 붙기 때문입니
                        * */
                        .requestMatchers("/admin").hasRole("ADMIN") // 자동으로 ROLE_ 붙는다.
                        .anyRequest().authenticated());
        SecurityFilterChain chain = http.build();
        System.out.println("--------------------------");
        chain.getFilters().forEach(System.out::println);

        System.out.println("-------------------------");
        return chain;
    }
}