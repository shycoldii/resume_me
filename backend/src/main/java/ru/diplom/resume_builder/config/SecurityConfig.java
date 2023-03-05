package ru.diplom.resume_builder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.diplom.resume_builder.repository.UserRepository;
import ru.diplom.resume_builder.security.JwtAuthenticationEntryPoint;
import ru.diplom.resume_builder.security.JwtAuthenticationFilter;
import ru.diplom.resume_builder.service.UserDetailsServiceImpl;

/**
 * Конфигурация безопасности приложения.
 *
 * @author Darya Alexandrova
 * @since 2023.03.01
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    /**
     * Бин сервиса пользовательских деталей.
     *
     * @param userRepository репозиторий пользователей
     * @return сервис пользовательских деталей
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    /**
     * Бин фильтра аутентификации.
     *
     * @return фильтр аутентификации
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Бин входной точки аутентификации.
     *
     * @return входная точка аутентификации.
     */
    @Bean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    /**
     * Бин менеджера аутентификации. Нужно переопределить, чтобы настроить свои параметры.
     *
     * @param http           http
     * @param userRepository репозиторий пользователей
     * @return менеджер аутентификации
     * @throws Exception исключение
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http, UserRepository userRepository) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService(userRepository))
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Бин цепочки фильтров безопасности. Переопределяем для того, чтобы подключить JWT и настроить систему.
     *
     * @param http http
     * @return цепочка фильтров безопасности
     * @throws Exception исключение
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //todo потом включить
                .csrf()
                .disable().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .mvcMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Бин энкодера паролей.
     *
     * @return энкодер паролей
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
