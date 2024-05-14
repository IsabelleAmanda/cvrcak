package hr.isabelle.cvrcakapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    Integer commentId;
    Integer postId;
    Integer userId;
    String content;
    Date commentingDate;
    Date updateDate;
    Date deleteDate;
    Integer activityId;
}
