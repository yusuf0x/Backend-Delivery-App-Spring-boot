package com.delivery.app.services;

import com.delivery.app.models.Person;
import com.delivery.app.repositories.PersonRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public void savePerson(Person person){
        personRepo.save(person);
    }
}
