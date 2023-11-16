package ua.ypon.finalsecurityapp.FirstSecurityApp.services;

import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import ua.ypon.finalsecurityapp.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.ypon.finalsecurityapp.FirstSecurityApp.security.PersonDetails;

import java.util.Optional;

/**
 * net.ukr@caravell 28/06/2023
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    // Анотація @Autowired вказує на ін'єкцію залежності
    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    // Метод інтерфейсу UserDetailsService, який знаходить користувача за іменем користувача (логіном)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Отримуємо інформацію про користувача за його ім'ям користувача
        Optional<Person> person = peopleRepository.findByUsername(username);

        // Перевіряємо, чи знайдений користувач
        if (person.isEmpty())
            throw new UsernameNotFoundException("Користувача не знайдено!");

        // Створюємо та повертаємо об'єкт PersonDetails, який містить інформацію про користувача
        return new PersonDetails(person.get());
    }
}
