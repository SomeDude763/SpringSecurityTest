package org.example.springsecuritytest.config;

import org.example.springsecuritytest.services.PersonDetailsService;
import org.example.springsecuritytest.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // включает Spring Security в приложении (активирует фильтры безопасности)
@EnableMethodSecurity(prePostEnabled = true)// включает безопасность на уровне методов (PreAuthorize, Secured и т.д.)
//2ой способ для роли
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) { // основной метод конфигурации безопасности
        // (замена старого configure(HttpSecurity))
        http
                //          .csrf(csrf -> csrf.disable()) //
                // отключаем CSRF-защиту (обычно для обучения или API)
                // CSRF защищает от поддельных POST-запросов
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests(auth -> auth
                                //.requestMatchers("/admin").hasRole("ADMIN")// доступ к /admin только для роли ADMIN
                                //1ый способ для роли
                                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                                .anyRequest().hasAnyRole("USER","ADMIN")
                        // разрешаем доступ
                        // БЕЗ авторизации к:
                        // - странице логина
                        // - странице ошибок
                        // .anyRequest().authenticated() // ВСЕ остальные запросы требуют авторизации
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")// указываем свою кастомную страницу логина
                        // иначе будет стандартная страница от Spring
                        .loginProcessingUrl("/process/login")// URL, на который отправляется форма логина (POST)
                        // этот URL обрабатывает сам Spring Security
                        .defaultSuccessUrl("/hello", true)// куда перенаправлять
                        // после успешного логина
                        // true = всегда редиректить на /hello
                        .failureUrl("/auth/login?error") // куда отправлять при ошибке логина
                        .permitAll()// разрешаем доступ к форме логина всем

                )
                .logout(logout -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/auth/login")//делает логаут
                );
        return http.build();// собираем и возвращаем цепочку фильтров безопасности
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(personDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    //    @Bean// бин кодировщика паролей
//    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();// НЕ шифрует пароль (plain text)
//        // используется только для обучения!
//    }

}



