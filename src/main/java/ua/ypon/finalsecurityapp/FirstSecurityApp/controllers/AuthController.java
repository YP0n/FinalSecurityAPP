package ua.ypon.finalsecurityapp.FirstSecurityApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ua.ypon.finalsecurityapp.FirstSecurityApp.dto.AuthenticationDTO;
import ua.ypon.finalsecurityapp.FirstSecurityApp.dto.PersonDTO;
import ua.ypon.finalsecurityapp.FirstSecurityApp.models.Person;
import ua.ypon.finalsecurityapp.FirstSecurityApp.security.JWTUtil;
import ua.ypon.finalsecurityapp.FirstSecurityApp.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import ua.ypon.finalsecurityapp.FirstSecurityApp.services.RegistrationService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Використовуємо анотацію @Autowired для ін'єкції залежностей
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

//    // Анотація @GetMapping вказує на обробник GET-запиту за шляхом "/auth/login"
//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }
//
//    // Анотація @GetMapping вказує на обробник GET-запиту за шляхом "/auth/registration"
//    // Анотація @ModelAttribute("person") створює модель "person" для передачі на сторінку
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") Person person) {
//        return "auth/registration";
//    }

    // Анотація @PostMapping вказує на обробник POST-запиту за шляхом "/auth/registration"
    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                      BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);//отримуємо людину
        // Виконуємо валідацію об'єкта Person з використанням PersonValidator
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("massage", "Помилка!");//повертаємо json але краще handler
        }
        // Виконуємо реєстрацію за допомогою RegistrationService
        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());//після реєстрації створюємо токен з імʼя користувача
        return Map.of("jwt-token", token);//jackson сконвертує це в json і відправить користувачу
    }

    @PostMapping("/login")
    public Map<String, String> performLog(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {//конвертація DTO в людину
        return this.modelMapper.map(personDTO, Person.class);
    }
}
