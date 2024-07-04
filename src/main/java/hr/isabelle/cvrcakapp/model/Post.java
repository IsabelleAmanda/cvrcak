package hr.isabelle.cvrcakapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    Integer userId;
    Integer postId;
    String title;
    String content;
    String image;   //razmislit o Image image??? ili nes slicno tome
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    Date postingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    Date updateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    Date deleteDate;
    Boolean isPublic;
    Boolean isDeleted;
    Boolean isPermanent;
    LocalDateTime disappearTime;
    List<Comment> comments; //pogledati kak se radi kolekcija unutar mappera!
    List<Like> likes;       //pogledati kak se radi kolekcija unutar mappera!
    Integer activityId;
}