package org.example.springsecuritytest.services;

import jakarta.transaction.Transactional;
import org.example.springsecuritytest.models.Person;
import org.example.springsecuritytest.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;// репозиторий для работы с БД
    private final PasswordEncoder passwordEncoder;// для шифрования пароля

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
      person.setPassword(passwordEncoder.encode(person.getPassword()));//Шифруем пароль перед сохранением в БД
        person.setRole("ROLE_USER");//по умолчанию при регистрации роль юзер
        peopleRepository.save(person);//Сохраняем пользователя в БД
    }
}
