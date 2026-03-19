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
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
@Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUserName(s);
        if(person.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new PersonDetails(person.get());
    }
}
