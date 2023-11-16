package ua.ypon.finalsecurityapp.FirstSecurityApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author ua.ypon 10.11.2023
 */
@SpringBootApplication
public class FinalSecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(FinalSecurityApp.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
