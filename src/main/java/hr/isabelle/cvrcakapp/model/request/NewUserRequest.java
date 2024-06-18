package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.Date;

@Validated
public class NewUserRequest {
    public Integer userId;
    @NotBlank(message = "Username cannot be blank")
    public String username;
    @NotBlank(message = "First name cannot be blank")
    public String firstName;
    @NotBlank(message = "Last name cannot be blank")
    public String lastName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    public String password;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    public String email;
    public String country;
    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "^[MF]$", message = "Gender can only be 'M' or 'F'")
    public String gender;
    @NotNull(message = "Birthday cannot be null")
    public Date birthday;
    public String image;

    @Override
    public String toString() {
        return "User{" +
                "username:" + username +
                "firstName:" + firstName +
                "lastName:" + lastName +
                "email:" + email +
                "birthday:" + birthday + "}";
    }
}
