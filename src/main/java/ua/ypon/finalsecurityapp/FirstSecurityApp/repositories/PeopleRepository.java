package ua.ypon.finalsecurityapp.FirstSecurityApp.repositories;

import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * net.ukr@caravell 28/06/2023
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUsername(String username);
}
