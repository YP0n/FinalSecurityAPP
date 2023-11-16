package ua.ypon.finalsecurityapp.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.ypon.finalsecurityapp.FirstSecurityApp.services.PersonDetailsService;


@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {


    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    // За допомогою анотації @Autowired виконуємо ін'єкцію залежностей
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    // Анотація @Bean позначає метод, який повертає бін (компонент контейнера Spring)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //конфігуруємо сам spring security
        //конфігуруємо авторизацію
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN"))
                .formLogin((formLogin) -> formLogin.loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/hello", true)
                        .failureUrl("/auth/login?error"))
                .logout((log) -> log.logoutUrl("/logout"))
                .logout((logSuc) -> logSuc.logoutSuccessUrl("/auth/login"))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//вказуємо, що сесія на сервері не зберігається

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//додаємо наш фільтр який допомагає проводити аутентифікацію

        return http.build();
    }

//    //Налаштування aутентифікації
//    //@Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Використовуємо сервіс personDetailsService для перевірки імені користувача та паролю
//        auth.userDetailsService(personDetailsService)
//                .passwordEncoder(getPasswordEncoder());
//    }

    // Метод, що повертає бін PasswordEncoder
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

