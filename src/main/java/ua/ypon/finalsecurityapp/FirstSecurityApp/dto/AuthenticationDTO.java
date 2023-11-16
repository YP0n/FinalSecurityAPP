package ua.ypon.finalsecurityapp.FirstSecurityApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * @author ua.ypon 10.11.2023
 */
public class AuthenticationDTO {

    @NotEmpty(message = "Ім'я не повинно бути пустим")
    @Size(min = 2, max = 100, message = "Ім'я повинно бути від 2 до 100 символів у довжину")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
