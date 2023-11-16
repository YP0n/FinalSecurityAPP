package ua.ypon.finalsecurityapp.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author ua.ypon 11.11.2023
 */
@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOME_OTHER')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
