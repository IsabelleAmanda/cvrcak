package hr.isabelle.cvrcakapp.model.request;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

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
    public DateTimeLiteralExpression.DateTime disappearTime;

    /*@Override
    public String toString() {
        return "User{" +
                "username:" + username +
                "firstName:" + firstName +
                "lastName:" + lastName +
                "birthday:" + birthday + "}";
    }*/
}
