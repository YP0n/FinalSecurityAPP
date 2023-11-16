package ua.ypon.finalsecurityapp.FirstSecurityApp.services;

import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import ua.ypon.finalsecurityapp.FirstSecurityApp.repositories.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//реєстрація нових користувачів
@Service
public class RegistrationService {

    // Анотація @Autowired вказує на ін'єкцію залежностей
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Анотація @Transactional вказує на те, що цей метод має бути виконаний в межах транзакції
    @Transactional
    public void register(Person person) {
        // Кодуємо пароль за допомогою passwordEncoder
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        //призначаємо всім звичайним користувачам роль-user
        person.setRole("ROLE_USER");
        // Зберігаємо користувача в базі даних через peopleRepository
        peopleRepository.save(person);
    }
}
