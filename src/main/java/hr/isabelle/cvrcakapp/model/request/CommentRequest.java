package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

@Validated
public class CommentRequest {
    public Integer commentId;
    public Integer userId;
    public Integer postId;
    public String content;

}
