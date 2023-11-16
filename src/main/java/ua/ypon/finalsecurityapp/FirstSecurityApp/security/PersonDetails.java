package ua.ypon.finalsecurityapp.FirstSecurityApp.security;

import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * net.ukr@caravell 28/06/2023
 */
public class PersonDetails implements UserDetails {

    private final Person person;

    // Конструктор класу, який приймає об'єкт Person
    public PersonDetails(Person person) {
        this.person = person;
    }

    // Метод, який повертає список ролей користувача (авторизованих)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //повертаємо колекцію ролей для користувача
        // SHOW_ACCOUNT, WITHDRAW_MONEY, SEND_MONEY
        // ROLE_ADMIN, ROLE_USER - це ролі
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    // Метод, який повертає пароль користувача
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    // Метод, який повертає ім'я користувача (логін)
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // Метод, який повертає true, якщо обліковий запис користувача не закінчився
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Метод, який повертає true, якщо обліковий запис користувача не заблокований
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Метод, який повертає true, якщо облікові дані користувача не закінчилися
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Метод, який повертає true, якщо обліковий запис користувача активний
    @Override
    public boolean isEnabled() {
        return true;
    }
    //потрібно, щоб отримати дані аутентифікованого користувача
    public Person getPerson() {
        return this.person;
    }
}
