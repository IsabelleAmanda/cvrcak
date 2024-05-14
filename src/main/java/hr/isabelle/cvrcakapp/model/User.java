package hr.isabelle.cvrcakapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.PasswordView;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Integer userId;
    String username;
    String firstName;
    String lastName;
    String password;
    String email;
    String country;
    Character gender;
    Date birthday;
    String image;
    Date registerDate;
    Boolean isDeleted;
    List<User> followers;
    List<User> following;
    List<Post> posts;
    List<Conversation> conversations;
    Integer activityId;
}
