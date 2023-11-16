package ua.ypon.finalsecurityapp.FirstSecurityApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.ypon.finalsecurityapp.FirstSecurityApp.security.PersonDetails;
import ua.ypon.finalsecurityapp.FirstSecurityApp.services.AdminService;

/**
 * net.ukr@caravell 28/06/2023
 */
@Controller
public class HelloController {

    private final AdminService adminService;

    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }


    // Анотація @GetMapping вказує на обробник GET-запиту за шляхом "/hello"
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    // Анотація @GetMapping вказує на обробник GET-запиту за шляхом "/showUserInfo"
    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        // Отримуємо об'єкт Authentication з контексту безпеки
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Отримуємо об'єкт PersonDetails, який містить деталі про авторизованого користувача
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Виконуємо дії з об'єктом personDetails, який містить інформацію про авторизованого користувача
        return personDetails.getUsername();
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }
}
