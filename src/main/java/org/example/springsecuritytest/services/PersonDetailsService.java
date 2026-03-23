package org.example.springsecuritytest.services;

import org.example.springsecuritytest.models.Person;
import org.example.springsecuritytest.repositories.PeopleRepository;
import org.example.springsecuritytest.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {// реализуем интерфейс UserDetailsService
    // он нужен Spring Security для загрузки пользователя
    private final PeopleRepository peopleRepository;// репозиторий для работы с БД (получаем пользователя)
@Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {// главный метод Spring Security
        // вызывается автоматически при логине
        // s — это username, который ввёл пользователь
        Optional<Person> person = peopleRepository.findByUsername(s);// ищем пользователя в БД по username
        if(person.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new PersonDetails(person.get());// если пользователь найден:
        // оборачиваем его в PersonDetails (реализация UserDetails)
        // и возвращаем Spring Security
    }
}
