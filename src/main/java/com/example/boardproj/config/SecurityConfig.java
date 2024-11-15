package com.example.boardproj.config;

import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@WebListener
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // 페이지 권한
                .authorizeHttpRequests(
                        authorization -> authorization
                                .requestMatchers("/user/login/**").permitAll()          // 로그인 페이지
                                .requestMatchers("/board/rigister").authenticated()     // 로그인한 사람만 게시물 작성가능
                                .requestMatchers("/user/list").hasRole("ADMIN")         // ADMIN만 회원관리 가능
                                .anyRequest().permitAll()                                 // 다른 페이지는 누구나 접근가능
                )

        // 위변조 방지 (웹에서 form으로)(설정만함)
                .csrf( csrf -> csrf.disable() )


        // 로그인
                .formLogin(
                        formLogin -> formLogin.loginPage("/user/login")     // 기본 로그인 페이지
                                .defaultSuccessUrl("/board/list")           // 로그인 성공후 페이지
                                .usernameParameter("email")                 // 로그인 이메일이 같은지 확인
                )

        // 로그아웃
                .logout(
                        logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        // 로그아웃 진행
                                .invalidateHttpSession(true)        // 세션 초기화
                                .logoutSuccessUrl("/user/login")    // 로그아웃 성공후 페이지
                )


        // 예외처리
//                .exceptionHandling(             지금하면 예외처리 되버리니 주석처리
////                        a -> a
////                )

        // 종결
        ;
        return httpSecurity.build();

    }

    // 패스워드 암호화를 위한 의존성 주입 객체 생성
    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



}
