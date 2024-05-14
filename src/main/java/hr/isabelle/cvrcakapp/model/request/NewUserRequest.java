package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Validated
public class NewUserRequest {
    public Integer userId;
    public String username;
    public String firstName;
    public String lastName;

    //TODO: validacija za password - duzina npr
    public String password;

    //TODO: validacija za mail - regex
    public String email;
    public String country;

    //TODO: dodati custom validaciju da moze biti samo F ili M!
    public String gender;
    //TODO: validacija za format datuma - regex
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
