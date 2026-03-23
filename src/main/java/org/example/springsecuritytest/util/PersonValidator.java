package org.example.springsecuritytest.util;

import org.example.springsecuritytest.models.Person;
import org.example.springsecuritytest.repositories.PeopleRepository;
import org.example.springsecuritytest.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
       if(peopleRepository.findByUsername(person.getUsername()).isPresent()){
           errors.rejectValue("username", "", "Человек с таким именем уже существует ");
       }
    }
}
