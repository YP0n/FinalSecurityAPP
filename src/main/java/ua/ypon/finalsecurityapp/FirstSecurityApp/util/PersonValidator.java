package ua.ypon.finalsecurityapp.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.ypon.finalsecurityapp.FirstSecurityApp.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;//все ок, користувач не знайдений
        }
        errors.rejectValue("username", "", "така людина вже зареєстрована");
    }
}
