package vn.edu.volunteer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import vn.edu.volunteer.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Cho phép truy cập tài nguyên tĩnh
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                // Cho phép truy cập trang công khai
                .antMatchers("/", "/home", "/login", "/register", "/access-denied", "/error").permitAll()
                // Yêu cầu xác thực cho /manage/**
                .antMatchers("/manage/**").authenticated()
                // Phân quyền cho các role
                .antMatchers("/manage/admin/**").hasRole("ADMIN")
                .antMatchers("/manage/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/manage/home")
                .failureUrl("/login?error=true")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
            .and()
                .csrf()
            .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired=true");

        return http.build();
    }
}