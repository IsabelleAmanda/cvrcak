package hr.isabelle.cvrcakapp.model;

import ch.qos.logback.core.joran.action.TimestampAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    Integer postId;
    String title;
    String content;
    String image;   //razmislit o Image image??? ili nes slicno tome
    Timestamp postingDate;
    Date updateDate;
    Date deleteDate;
    Boolean isPublic;
    Boolean isPermanent;
    DateTimeLiteralExpression.DateTime disappearTime;
    List<Comment> comments; //pogledati kak se radi kolekcija unutar mappera!
    List<Like> likes;       //pogledati kak se radi kolekcija unutar mappera!
    Integer activityId;
}
