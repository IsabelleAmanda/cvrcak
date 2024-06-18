package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
public class PostRequest {
    public Integer postId;
    public Integer userId;
    public String title;
    public String content;
    public String image;
    public Boolean isPublic;
    public Boolean isPermanent;
    public Boolean isDeleted;
    public LocalDateTime disappearTime;

    /*@Override
    public String toString() {
        return "User{" +
                "username:" + username +
                "firstName:" + firstName +
                "lastName:" + lastName +
                "birthday:" + birthday + "}";
    }*/
}
