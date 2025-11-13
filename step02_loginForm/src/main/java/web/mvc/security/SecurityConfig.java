package web.mvc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    /**
     * SecurityFilterChain = security 정책
     * HttpSecurity는 각 요청에 해당하는 정책들을 어떻게 할 것인지 결정
     *   ex) 어떤 정책은 무엇을 해야하고 안해도 되고 해도 되고...(must should...)이런 옵션을 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        http.formLogin(f -> f
                //.loginPage("/loginPage")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/", false)
                .failureUrl("/failed")
                .usernameParameter("userId")
                .passwordParameter("userPass")
                .successHandler((request, response, authentication) -> {
                    System.out.println("authentication : " + authentication);
                    response.sendRedirect("/home");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("exception : " + exception);
                    response.sendRedirect("/login");
                })

                .permitAll()
        );
        http.httpBasic(httpBasic -> httpBasic.disable());
        SecurityFilterChain build = http.build();
        System.out.println("*****************************");
        build.getFilters().forEach(f -> System.out.println(f));
        System.out.println("*****************************");

        return build;
    }

    /**
     * 여러명의 계정을 추가 - inMemory
     */
    @Bean
    public UserDetailsService userDetailsService() {
        log.info("init UserDetailsService");
        UserDetails user1 = User.withUsername("user").password("{noop}1234").roles("USER").build();
        UserDetails user2 = User.withUsername("dh").password("{noop}1234").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
