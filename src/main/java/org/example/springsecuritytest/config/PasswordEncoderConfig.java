package org.example.springsecuritytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {// BCryptPasswordEncoder — класс для хеширования паролей
        // (пароль хранится не в открытом виде)
        return new BCryptPasswordEncoder();// // создаём и возвращаем encoder
    }
}
