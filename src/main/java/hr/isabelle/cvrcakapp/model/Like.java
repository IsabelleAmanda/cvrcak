package hr.isabelle.cvrcakapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    Integer likeId;
    Integer userId;
    Integer postId;
    Date likingDate;
    Integer activityId;
}
