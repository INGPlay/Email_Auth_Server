package server.api.emailAuth.config;

import server.api.emailAuth.config.security.authenticationManager.CustomUserDetailsService;
import server.api.emailAuth.config.security.handler.CustomAuthenticationFailureHandler;
import server.api.emailAuth.config.security.handler.CustomAuthenticationSuccessHandler;
import server.api.emailAuth.config.security.handler.CustomLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLogoutHandler customLogoutHandler;

//    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring()
                .antMatchers("/css/*", "/js/*", "/favicon.ico")
                // 경로 잘 확인할 것. 정적 파일이 다 들어가지 않으면 통과된 정적파일이 URI로 들어가서 반환됨
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c
                        .ignoringAntMatchers("/api/auth/email/**")
                )
                .authorizeRequests(a -> a
                        // 기본 페이지
                        .antMatchers("*", "/account/login", "/account/register", "/error").permitAll()
                )
                .formLogin(f -> f
                        .loginPage("/account/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/account/login?fail")
                        .usernameParameter("accountName")
                        .passwordParameter("accountPassword")
                        .loginProcessingUrl("/account/login-process")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                )
//                .oauth2Login(o -> o
//                        .loginPage("/account/login")
//                        .userInfoEndpoint(u -> u
//                                .userService(customOAuth2UserService)
//                        )
//                        .failureUrl("/account/login?fail")
//                        .successHandler(customAuthenticationSuccessHandler)
//                        .failureHandler(customAuthenticationFailureHandler)
//                )
                .logout(l -> l
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .addLogoutHandler(customLogoutHandler)
                )
                .rememberMe(r -> r
                                .rememberMeParameter("remember-me")         // 기본 파라미터명은 remember-me
                                .tokenValiditySeconds(3600)              // Default 는 14일
                                .alwaysRemember(false)                    // 리멤버 미 기능이 활성화되지 않아도 항상 실행
//                        .userDetailsService(customUserDetailsService)
                )
                .sessionManagement(m -> m
//                        .sessionFixation().changeSessionId()
                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                        .invalidSessionUrl("/invalid")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
//                        .expiredUrl("expired")
                )
                .exceptionHandling(e ->
                        e.accessDeniedPage("/?dinied")
                )
                .headers(h -> h
                        .xssProtection()
                        .and()
                        .contentSecurityPolicy("script-src 'self'")
                );

        return http.build();
    }


    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\nROLE_MANAGER > ROLE_USER");

        return roleHierarchy;
    }
}
